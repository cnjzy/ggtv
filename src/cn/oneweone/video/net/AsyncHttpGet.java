package cn.oneweone.video.net;


/**
 * 
 * 
 * @author zxy
 * 
 */

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;

import cn.oneweone.video.common.Constants;
import cn.oneweone.video.db.CacheDB;
import cn.oneweone.video.net.exception.RequestException;
import cn.oneweone.video.net.utils.ErrorUtil;
import cn.oneweone.video.net.utils.RequestParameter;
import cn.oneweone.video.net.utils.Utils;
import cn.oneweone.video.util.LogUtil;
import cn.oneweone.video.widget.dialog.CustomLoadingDialog;


/**
 * 
 * 
 * @author zxy
 * 
 */
@SuppressLint("HandlerLeak")
public class AsyncHttpGet extends BaseRequest {
    private static final long serialVersionUID = 2L;
    DefaultHttpClient httpClient;
    List<RequestParameter> parameter;
    CustomLoadingDialog customLoadingDialog;
    private int resultCode = -1;
    private Context context;

    private CacheDB cacheDB;

    Handler resultHandler = new Handler() {
        public void handleMessage(Message msg) {
            String resultData = (String) msg.obj;
            ThreadCallBack callBack = (ThreadCallBack) msg.getData().getSerializable("callback");
            if (resultCode == -1)
                callBack.onCallbackFromThread(resultData);
            callBack.onCallbackFromThread(resultData, resultCode);
        }
    };
    ThreadCallBack callBack;

    public AsyncHttpGet(ThreadCallBack callBack, String url, List<RequestParameter> parameter,
            boolean isShowLoadingDialog, String loadingCode, boolean isHideCloseBtn, int resultCode, Context context) {
        this.callBack = callBack;
        this.resultCode = resultCode;
        this.context = context;
        if (isShowLoadingDialog) {
            if (callBack != null && callBack instanceof Context) {
                customLoadingDialog = new CustomLoadingDialog((Context) callBack, loadingCode, isHideCloseBtn);
                if (customLoadingDialog != null && !customLoadingDialog.isShowing()) {
                    customLoadingDialog.show();
                }
            } else if (callBack != null && callBack instanceof Fragment) {
                Context tempContext = ((Fragment) callBack).getActivity();
                if(tempContext != null){
                    customLoadingDialog = new CustomLoadingDialog(tempContext, loadingCode,
                            isHideCloseBtn);
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

        if (cacheDB == null)
            cacheDB = new CacheDB();
    }

    public AsyncHttpGet(ThreadCallBack callBack, String url, List<RequestParameter> parameter,
            boolean isShowLoadingDialog, int connectTimeout, int readTimeout, Context context) {
        this(callBack, url, parameter, isShowLoadingDialog, "", false, -1, context);
        if (connectTimeout > 0) {
            this.connectTimeout = connectTimeout;
        }
        if (readTimeout > 0) {
            this.readTimeout = readTimeout;
        }
    }

    public AsyncHttpGet(ThreadCallBack callBack, String url, List<RequestParameter> parameter,
            boolean isShowLoadingDialog, String loadingDialogContent, boolean isHideCloseBtn, int connectTimeout,
            int readTimeout, int resultCode, Context context) {
        this(callBack, url, parameter, isShowLoadingDialog, loadingDialogContent, isHideCloseBtn, resultCode, context);
        if (connectTimeout > 0) {
            this.connectTimeout = connectTimeout;
        }
        if (readTimeout > 0) {
            this.readTimeout = readTimeout;
        }
    }

    
    public void run() {
        String ret = "";

        if (parameter != null && parameter.size() > 0) {
            StringBuilder bulider = new StringBuilder();
            for (RequestParameter p : parameter) {
                if (bulider.length() != 0) {
                    bulider.append("/");
                }

                bulider.append(Utils.encode(p.getName()));
                bulider.append("/");
                bulider.append(Utils.encode(p.getValue()));
            }
//            url += "?" + bulider.toString();
            url += bulider.toString();
        }

        // get cache
//        List<Cache> cacheList = cacheDB.getCache(context, Md5Encode.getMD5(url));
//        if (cacheList != null && cacheList.size() > 0) {
//            Cache cache = cacheList.get(0);
//            if (System.currentTimeMillis() > cache.getCreate_time()) {
//                // 过期
//                cacheDB.delCache(context, Md5Encode.getMD5(url));
//            } else {
//                // 没过期接着用
//                LogUtil.d(AsyncHttpGet.class.getName(), "AsyncHttpGet Get Cache request to url :" + url
//                        + "  finished !");
//                Message msg = new Message();
//                msg.obj = cache.getContent();
//                LogUtil.d("result", cache.getContent());
//                msg.getData().putSerializable("callback", callBack);
//                resultHandler.sendMessage(msg);
//                return;
//            }
//        }

        try {
            LogUtil.d(AsyncHttpGet.class.getName(), "AsyncHttpGet  request to url :" + url);
            request = new HttpGet(url);
            HttpManager.addCookies(request);
            if (Constants.isGzip) {
                request.addHeader("Accept-Encoding", "gzip");
            } else {
                request.addHeader("Accept-Encoding", "default");
            }
            // 添加json数据格式
         	request.addHeader("Content-type", "application/json");
            // 请求超时
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectTimeout);
            // 读取超时
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, readTimeout);

            for (int i = 0; i < Constants.CONNECTION_COUNT; i++) {
                try {
                    HttpResponse response = httpClient.execute(request);
                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == HttpStatus.SC_OK) {
                        HttpManager.saveCookies(response);
                        InputStream is = null;
                        try {
                            is = response.getEntity().getContent();
                        } catch (IOException e) {
                            Log.d("AsyncHttpGet", "读取超时");
                            // 读取超时
                            LogUtil.e(e);
                            if (i == Constants.CONNECTION_COUNT - 1) {
                                RequestException exception = new RequestException(RequestException.IO_EXCEPTION,
                                        "网络连接超时");
                                ret = ErrorUtil.errorJson("false", exception.getMessage());
                            } else {
                                Log.d("connection url", "连接超时" + i);
                                continue;
                            }
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
                        RequestException exception = new RequestException(RequestException.IO_EXCEPTION, Constants.ERROR_MESSAGE);
                        ret = ErrorUtil.errorJson("false", exception.getMessage());
                    }

                    LogUtil.d(AsyncHttpGet.class.getName(), "AsyncHttpGet  request to url :" + url + "  finished !");
//                    // add cache
//                    JSONParser parser = new JSONParser();
//                    JSONObject obj = (JSONObject) parser.parse(ret);
//                    long expires = 0;
//                    if (obj.containsKey("expires")) {
//                        expires = Long.parseLong(obj.get("expires").toString());
//                    }
//                    cacheDB.addCache(context, Md5Encode.getMD5(url), ret, System.currentTimeMillis() + expires);
//                    LogUtil.d(AsyncHttpGet.class.getName(), "Add Cache Success");

                    break;
                } catch (ConnectTimeoutException e) {
                    LogUtil.e(e);
                    if (i == Constants.CONNECTION_COUNT - 1) {
                        RequestException exception = new RequestException(RequestException.IO_EXCEPTION, Constants.ERROR_MESSAGE);
                        ret = ErrorUtil.errorJson("false", exception.getMessage());
                    } else {
                        Log.d("connection url", "连接超时" + i);
                        continue;
                    }
                }catch (SocketException e) {
                    LogUtil.e(e);
                    if (i == Constants.CONNECTION_COUNT - 1) {
                        RequestException exception = new RequestException(RequestException.IO_EXCEPTION, Constants.ERROR_MESSAGE);
                        ret = ErrorUtil.errorJson("false", exception.getMessage());
                    } else {
                        Log.d("connection url", "连接超时" + i);
                        continue;
                    }
                } catch (UnknownHostException e) {
                    LogUtil.e(e);
                    if (i == Constants.CONNECTION_COUNT - 1) {
                        RequestException exception = new RequestException(RequestException.IO_EXCEPTION, Constants.ERROR_NO_NETWORK_MESSAGE);
                        ret = ErrorUtil.errorJson("false", exception.getMessage());
                    } else {
                        Log.d("connection url", "连接超时" + i);
                        continue;
                    }
                } catch (Exception e){
                    LogUtil.e(e);
                    RequestException exception = new RequestException(RequestException.IO_EXCEPTION, "");
                    ret = ErrorUtil.errorJson("false", exception.getMessage());
                }
            }
        } catch (java.lang.IllegalArgumentException e) {

            RequestException exception = new RequestException(RequestException.IO_EXCEPTION, Constants.ERROR_MESSAGE);
            ret = ErrorUtil.errorJson("false", exception.getMessage());
            LogUtil.d(AsyncHttpGet.class.getName(),
                    "AsyncHttpGet  request to url :" + url + "  onFail  " + e.getMessage());
        } finally {
            // request.
            if (customLoadingDialog != null && customLoadingDialog.isShowing()) {
                customLoadingDialog.dismiss();
                customLoadingDialog = null;
            }

            try {
                Message msg = new Message();
                msg.obj = ret;
                LogUtil.d("result", ret);
                msg.getData().putSerializable("callback", callBack);
                resultHandler.sendMessage(msg);
            } catch (Exception e) {
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
