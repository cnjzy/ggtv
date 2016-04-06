package cn.oneweone.video.net;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;

import cn.oneweone.video.common.Constants;
import cn.oneweone.video.net.exception.RequestException;
import cn.oneweone.video.net.utils.ErrorUtil;
import cn.oneweone.video.net.utils.RequestParameter;
import cn.oneweone.video.util.LogUtil;
import cn.oneweone.video.widget.dialog.CustomLoadingDialog;


/**
 * 
 * 异步HTTPPOST请求
 * 
 * 线程的终止工作交给线程池，当activity停止的时候，设置回调函数为false ，就不会执行回调方法。
 * 
 * @author sailor
 * 
 */
@SuppressLint("HandlerLeak")
public class AsyncHttpPost extends BaseRequest {
    private static final long serialVersionUID = 2L;
    DefaultHttpClient httpClient;
    List<RequestParameter> parameter = null;
    CustomLoadingDialog customLoadingDialog;
    private int resultCode = -1;//大于20000为流

    Handler resultHandler = new Handler() {
        public void handleMessage(Message msg) {
            ThreadCallBack callBack = (ThreadCallBack) msg.getData().getSerializable("callback");
            if (callBack != null) {
                if (resultCode == -1){
                	String resultData = (String) msg.obj;
                    callBack.onCallbackFromThread(resultData);
                }else if(resultCode>20000){
                	callBack.onCallbackFromThread((InputStream) msg.obj,resultCode);
                }else{
	                String resultData = (String) msg.obj;
	                callBack.onCallbackFromThread(resultData, resultCode);
                }
            }
        }
    };
    ThreadCallBack callBack;

    public AsyncHttpPost(ThreadCallBack callBack, String url, List<RequestParameter> parameter,
            boolean isShowLoadingDialog, String loadingCode, boolean isHideCloseBtn, int resultCode) {
        this.callBack = callBack;
        this.resultCode = resultCode;
        if (isShowLoadingDialog) {
            if (callBack != null && callBack instanceof Context) {
                customLoadingDialog = new CustomLoadingDialog((Context) callBack, loadingCode, isHideCloseBtn);
                if (customLoadingDialog != null && !customLoadingDialog.isShowing()) {
                    customLoadingDialog.show();
                }
            } else if (callBack != null && callBack instanceof Fragment) {
                Context tempContext = ((Fragment) callBack).getActivity();
                if (tempContext != null) {
                    customLoadingDialog = new CustomLoadingDialog(tempContext, loadingCode, isHideCloseBtn);
                    if (customLoadingDialog != null && !customLoadingDialog.isShowing()) {
                        customLoadingDialog.show();
                    }
                }

            }
        }
        this.url = url;
        this.parameter = parameter;
        if (httpClient == null)
            httpClient = new DefaultHttpClient();
    }

    public AsyncHttpPost(ThreadCallBack callBack, String url, List<RequestParameter> parameter,
            boolean isShowLoadingDialog, int connectTimeout, int readTimeout) {
        this(callBack, url, parameter, isShowLoadingDialog, "", false, -1);
        if (connectTimeout > 0) {
            this.connectTimeout = connectTimeout;
        }
        if (readTimeout > 0) {
            this.readTimeout = readTimeout;
        }
    }

    public AsyncHttpPost(ThreadCallBack callBack, String url, List<RequestParameter> parameter,
            boolean isShowLoadingDialog, String loadingDialogContent, boolean isHideCloseBtn, int connectTimeout,
            int readTimeout, int resultCode) {
        this(callBack, url, parameter, isShowLoadingDialog, loadingDialogContent, isHideCloseBtn, resultCode);
        if (connectTimeout > 0) {
            this.connectTimeout = connectTimeout;
        }
        if (readTimeout > 0) {
            this.readTimeout = readTimeout;
        }
    }

    
    public void run() {
        String ret = "";
        InputStream ris=null;
        try {

            request = new HttpPost(url);
            HttpManager.addCookies(request);
            if (Constants.isGzip) {
                request.addHeader("Accept-Encoding", "gzip");
            } else {
                request.addHeader("Accept-Encoding", "default");
            }
            // 添加json数据格式
            // request.addHeader("Content-type", "application/json");
            LogUtil.d(AsyncHttpPost.class.getName(), "AsyncHttpPost  request to url :" + url);

            if (parameter != null && parameter.size() > 0) {
                List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
                for (RequestParameter p : parameter) {
                    list.add(new BasicNameValuePair(p.getName(), p.getValue()));
                }
                ((HttpPost) request).setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
            }
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, readTimeout);

            for (int i = 0; i < Constants.CONNECTION_COUNT; i++) {
                try {
                    HttpResponse response = httpClient.execute(request);
                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == HttpStatus.SC_OK) {
                        HttpManager.saveCookies(response);
                        InputStream is = response.getEntity().getContent();
                        if (resultCode>20000){
                        	ris=is;
                        	break;
                        }
                        BufferedInputStream bis = new BufferedInputStream(is);
                        bis.mark(2);
                        // 取前两个字节
                        byte[] header = new byte[2];
                        int result = bis.read(header);
                        // reset输入流到开始位置
                        bis.reset();
                        // 判断是否是GZIP格式
                        int headerData = getShort(header);
                        // Gzip 流 的前两个字节是 0x1f8b
                        if (result != -1 && headerData == 0x1f8b) {
                            LogUtil.d("HttpTask", " use GZIPInputStream  ");
                            is = new GZIPInputStream(bis);
                        } else {
                            LogUtil.d("HttpTask", " not use GZIPInputStream");
                            is = bis;
                        }
                        InputStreamReader reader = new InputStreamReader(is, "utf-8");
                        char[] data = new char[100];
                        int readSize;
                        StringBuffer sb = new StringBuffer();
                        while ((readSize = reader.read(data)) > 0) {
                            sb.append(data, 0, readSize);
                        }
                        ret = sb.toString();
                        bis.close();
                        reader.close();

                    } else {
                        RequestException exception = new RequestException(RequestException.IO_EXCEPTION,Constants.ERROR_MESSAGE);
                        ret = ErrorUtil.errorJson("false", exception.getMessage());
                    }

                    LogUtil.d(AsyncHttpPost.class.getName(), "AsyncHttpPost  request to url :" + url + "  finished !");
                    break;
                } catch (ConnectTimeoutException e) {
                    LogUtil.e(e);
                    if (i == Constants.CONNECTION_COUNT - 1) {
                        RequestException exception = new RequestException(RequestException.IO_EXCEPTION,
                                Constants.ERROR_MESSAGE);
                        ret = ErrorUtil.errorJson("false", exception.getMessage());
                    } else {
                        Log.d("connection url", "连接超时" + i);
                        continue;
                    }
                } catch (SocketException e) {
                    LogUtil.e(e);
                    if (i == Constants.CONNECTION_COUNT - 1) {
                        RequestException exception = new RequestException(RequestException.IO_EXCEPTION,
                                Constants.ERROR_MESSAGE);
                        ret = ErrorUtil.errorJson("false", exception.getMessage());
                    } else {
                        Log.d("connection url", "连接超时" + i);
                        continue;
                    }
                } catch (Exception e) {
                    LogUtil.e(e);
                    RequestException exception = new RequestException(RequestException.IO_EXCEPTION, "");
                    ret = ErrorUtil.errorJson("false", exception.getMessage());
                }
            }
        } catch (Exception e) {
            RequestException exception = new RequestException(RequestException.IO_EXCEPTION, Constants.ERROR_MESSAGE);
            ret = ErrorUtil.errorJson("false", exception.getMessage());
            LogUtil.d(AsyncHttpGet.class.getName(),
                    "AsyncHttpPost  request to url :" + url + "  onFail  " + e.getMessage());
        } finally {
            if (customLoadingDialog != null && customLoadingDialog.isShowing()) {
                customLoadingDialog.dismiss();
                customLoadingDialog = null;
            }

            try {
                Message msg = new Message();
                if (resultCode>20000){
                	msg.obj=ris;
                }else
                	msg.obj = ret;
                LogUtil.d("返回结果", ret);
                msg.getData().putSerializable("callback", callBack);
                resultHandler.sendMessage(msg);
            } catch (Exception e) {
                LogUtil.e(e);
            } finally {
                if (customLoadingDialog != null && customLoadingDialog.isShowing()) {
                    customLoadingDialog.dismiss();
                    customLoadingDialog = null;
                }
            }
        }
        super.run();
    }

    private int getShort(byte[] data) {
        return (int) ((data[0] << 8) | data[1] & 0xFF);
    }
}
