package com.example.ddddd.util;

import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.ddddd.MyApp;
import com.example.ddddd.activity.VideoPlayerActivity;
import com.example.ddddd.activity.WebViewActivity;
import com.example.ddddd.base.BaseActivity;
import com.example.ddddd.common.Constants;
import com.example.ddddd.net.utils.RequestParameter;
import com.example.ddddd.net.utils.Utils;
import com.example.ddddd.util.DialogUtil.OnAlertSelectId;
import com.example.ddddd.util.preference.Preferences;
import com.example.ddddd.vo.ProductInfoVO;
import com.hft.juhepay.api.HftJuhePay;
import com.hft.juhepay.api.OnPayFinishedListener;

public class PayUtils {

	private static final String PAGETAG = "dddddd";

	public static void pay(final Activity mContext, String orderNo, String mStrPayMode, String amount) {
		HashMap<String, String> params = new HashMap<String, String>();

		params.put("pay_mode", mStrPayMode); // 支付方式：1：微信;2:支付宝
		params.put("order_id", orderNo); // 订单号
		params.put("pay_amt", amount); // 支付金额
		params.put("notify_url", "http://api.76iw.com/order/hftnotify"); // 回调URL
		params.put("goods_name", "终身会员"); // 商品名称
		params.put("goods_note", "终身会员"); // 商品价格信息 // 可为空
		params.put("extends_info", "无"); // 标记
		params.put("goods_num", "1"); // 商品数量
		HftJuhePay.getInstance().pay(mContext, params, new OnPayFinishedListener() {

			@Override
			public void onPaySuccess(Map params) {
				// TODO Auto-generated method stub
				UMengUtils.addPaySuccess(mContext);
				MyApp.preferencesUtils.putInt("userType", Constants.MEMBER_TYPE_IS_TENURE);
				if (mContext instanceof VideoPlayerActivity) {
					mContext.finish();
				}

				Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
				String mStrOrderId = ((Map<String, String>) params).get("order_id");
				String mStrTotalAmount = ((Map<String, String>) params).get("total_amt");
				String mStrGoodsName = ((Map<String, String>) params).get("goods_name");
				String mStrGoodsNote = ((Map<String, String>) params).get("goods_note");
				String mStrGoodsNum = ((Map<String, String>) params).get("goods_num");
				String mStrExtendsInfo = ((Map<String, String>) params).get("extends_info");
				Log.i(PAGETAG, "params=" + params.toString());
			}

			@Override
			public void onPayFail(Map params, int errorInt) {
				Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
				String mStrOrderId = ((Map<String, String>) params).get("order_id");
				String mStrTotalAmount = ((Map<String, String>) params).get("total_amt");
				String mStrGoodsName = ((Map<String, String>) params).get("goods_name");
				String mStrGoodsNote = ((Map<String, String>) params).get("goods_note");
				String mStrGoodsNum = ((Map<String, String>) params).get("goods_num");
				String mStrExtendsInfo = ((Map<String, String>) params).get("extends_info");
				if (mContext instanceof VideoPlayerActivity) {
					mContext.finish();
				}
				Log.i(PAGETAG, "params=" + params.toString());
			}

			@Override
			public void onPayCancel(Map params) {
				Toast.makeText(mContext, "支付取消", Toast.LENGTH_SHORT).show();
				String mStrOrderId = ((Map<String, String>) params).get("order_id");
				String mStrTotalAmount = ((Map<String, String>) params).get("total_amt");
				String mStrGoodsName = ((Map<String, String>) params).get("goods_name");
				String mStrGoodsNote = ((Map<String, String>) params).get("goods_note");
				String mStrGoodsNum = ((Map<String, String>) params).get("goods_num");
				String mStrExtendsInfo = ((Map<String, String>) params).get("extends_info");
				if (mContext instanceof VideoPlayerActivity) {
					mContext.finish();
				}
				Log.i(PAGETAG, "params=" + params.toString());

			}
		});
	}

	public static void payByH5(final BaseActivity mContext, final String orderNo, String mStrPayMode, final String amount) {

//		new Thread() {
//			public void run() {
//				String url = "http://gateway.71pay.cn/Pay/GateWay10.shtml";
//				// 设置签名
//				StringBuffer sb = new StringBuffer();
//				sb.append("app_id=2021")
//				.append("&pay_type=" + 2)
//				.append("&order_id=" + orderNo)
//				.append("&order_amt=" + amount)
//				.append("&notify_url="+ Utils.encode("http://api.76iw.com/order/hftnotify"))
//				.append("&return_url="+ Utils.encode("http://www.baidu.com"))
//				.append("&time_stamp=" + DateUtils.getStringByDate(new Date(), DateUtils.parsePatterns[11]));
//				String signStr =  sb.toString() + "&key=" + Md5Encode.getMD5(getAppKey(mContext));
//				System.err.println("signStr:" + signStr);
//				String sign = Md5Encode.getMD5(signStr);
//				
//				List<RequestParameter> parameter = new ArrayList<RequestParameter>();
//				parameter.add(new RequestParameter("app_id", 2021));
//				parameter.add(new RequestParameter("pay_type", 2));
//				parameter.add(new RequestParameter("order_id", orderNo));
//				parameter.add(new RequestParameter("order_amt", amount));
//				parameter.add(new RequestParameter("notify_url", "http://api.76iw.com/order/hftnotify"));
//				parameter.add(new RequestParameter("return_url", "http://www.baidu.com"));
//				parameter.add(new RequestParameter("time_stamp", DateUtils.getStringByDate(new Date(), DateUtils.parsePatterns[11])));
//				parameter.add(new RequestParameter("goods_name", "终身会员"));
//				parameter.add(new RequestParameter("sign", sign));
//				
//				try {
//					System.err.println("request url:" + url);
//					// 创建http请求
//					HttpPost request = new HttpPost(url);
//					// 添加json数据格式
//					request.addHeader("Content-type", "application/json");
//					if (parameter != null && parameter.size() > 0) {
//		                List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
//		                for (RequestParameter p : parameter) {
//		                    list.add(new BasicNameValuePair(p.getName(), p.getValue()));
//		                    System.err.println(p.getName() + ":" + p.getValue());
//		                }
//		                ((HttpPost) request).setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
//		            }
//					
//					// 创建http客户端
//					HttpClient httpClient = new DefaultHttpClient();
//					// 请求超时
//					httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10 * 1000);
//					// 读取超时
//					httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10 * 1000);
//					// 执行请求http
//					httpClient.execute(request);
//
//					// 获取请求response
//					HttpResponse response = httpClient.execute(request);
//					int statusCode = response.getStatusLine().getStatusCode();
//					if (statusCode == HttpStatus.SC_OK) {
//						InputStreamReader reader = new InputStreamReader(response.getEntity().getContent(), "utf-8");
//						char[] data = new char[100];
//						int readSize;
//						StringBuffer ret = new StringBuffer();
//						while ((readSize = reader.read(data)) > 0) {
//							ret.append(data, 0, readSize);
//						}
//
//						System.err.println("result:" + ret);
//					}
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			};
//		}.start();
		
		Bundle data = new Bundle();
		data.putString("orderNo", orderNo);
		data.putString("amount", amount);
		BaseActivity.showActivityForResult(mContext, WebViewActivity.class, 988, data);
	}

	/** 获取App_Key */
	private static String getAppKey(Context context) {
		return getStringMetaData(context, "HFT_APP_KEY");
	}
	
	/** 获取App_Key */
	private static String getAppId(Context context) {
		return getStringMetaData(context, "HFT_APP_ID");
	}

	private static String getStringMetaData(Context context, String key) {
		Bundle metaData = getMetaData(context);
		String strVal = metaData != null ? metaData.getString(key) : null;
		return strVal != null ? strVal : "";
	}

	private static Bundle getMetaData(Context context) {
		if (context == null) {
			return null;
		}

		PackageManager pm = context.getPackageManager();
		try {
			ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(), 128);

			if (appInfo != null)
				return appInfo.metaData;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	/** 获取当前完整时间：20141230114605 */
	private static String getToSeconds() {
		String time = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		time = sdf.format(new java.util.Date());

		return time;
	}

	/**
	 * 判断是否需要支付
	 * 
	 * @param context
	 * @param authority
	 *            视频对应权限 0普通 1包年 2终身
	 * @param onClickListener
	 * @return
	 */
	public static boolean isPay(final BaseActivity context, int authority, final OnAlertSelectId onClickListener) {
		int status = MyApp.preferencesUtils.getInt(Preferences.USER_STATUS, 0);
		if (status == Constants.MEMBER_TYPE_IS_TENURE || Constants.isDebug) {
			return true;
		} else if (status == Constants.MEMBER_TYPE_IS_NOT) {
			DialogUtil.showPay3Dialog(context, onClickListener, Constants.VIP_TENURE);
			return false;
		} else if (status == Constants.MEMBER_TYPE_IS_YEAR && authority == Constants.MEMBER_TYPE_IS_YEAR) {
			return true;
		} else if (status == Constants.MEMBER_TYPE_IS_YEAR && authority == Constants.MEMBER_TYPE_IS_TENURE) {
			DialogUtil.showPay3Dialog(context, onClickListener, Constants.VIP_TENURE);
			return false;
		}
		return false;
	}

	// /**
	// * 判断是否需要支付
	// * @param context
	// * @param authority 视频对应权限 0普通 1包年 2终身
	// * @param onClickListener
	// * @return
	// */
	// public static boolean isPay(final BaseActivity context, int authority,
	// final OnAlertSelectId onClickListener){
	// int status = MyApp.preferencesUtils.getInt(Preferences.USER_STATUS, 0);
	// if(status == Constants.MEMBER_TYPE_IS_TENURE || Constants.isDebug){
	// return true;
	// }else if(status == Constants.MEMBER_TYPE_IS_NOT){
	// DialogUtil.showPay2Dialog(context, new OnAlertSelectId() {
	// public void onClick(int whichButton, Object o) {
	// DialogUtil.showPay3Dialog(context, onClickListener,
	// Integer.parseInt(o.toString()));
	// }
	// });
	// return false;
	// }else if(status == Constants.MEMBER_TYPE_IS_YEAR && authority ==
	// Constants.MEMBER_TYPE_IS_YEAR){
	// return true;
	// }else if(status == Constants.MEMBER_TYPE_IS_YEAR && authority ==
	// Constants.MEMBER_TYPE_IS_TENURE){
	// DialogUtil.showPay1Dialog(context, new OnAlertSelectId() {
	// public void onClick(int whichButton, Object o) {
	// DialogUtil.showPay3Dialog(context, onClickListener,
	// Integer.parseInt(o.toString()));
	// }
	// });
	// return false;
	// }
	// return false;
	// }

	/**
	 * 是否有权限
	 * 
	 * @param authority
	 * @return
	 */
	public static boolean isPermission(int authority) {
		int status = MyApp.preferencesUtils.getInt(Preferences.USER_STATUS, Constants.MEMBER_TYPE_IS_NOT);
		if (authority <= status) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 显示下一窗口
	 * 
	 * @param context
	 *            上下文
	 * @param vo
	 *            产品对象
	 * @param type
	 *            1显示详情 2播放
	 */
	private static void showNextPage(final BaseActivity context, ProductInfoVO vo, int type) {
		if (!TextUtils.isEmpty(vo.getTestUrl()) && !TextUtils.isEmpty(vo.getTestSecond())) {
			Bundle data = new Bundle();
			data.putSerializable("vo", vo);
			BaseActivity.showActivity(context, VideoPlayerActivity.class, data);
		} else {
			if (PayUtils.isPay(context, vo.getAuthority(), new OnAlertSelectId() {
				public void onClick(int pay_type, Object o) {
					int amount = Constants.VIP_TENURE;
					int member_type = Constants.MEMBER_TYPE_IS_TENURE;
					context.getOrder(amount, pay_type, member_type);
				}
			})) {
				// if(type == 1){
				// Bundle data = new Bundle();
				// data.putSerializable("vo", vo);
				// BaseActivity.showActivity(context, DetailActivity.class,
				// data);
				// }else{
				Bundle data = new Bundle();
				data.putSerializable("vo", vo);
				BaseActivity.showActivity(context, VideoPlayerActivity.class, data);
				// }
			}
		}
	}

	/**
	 * 是否显示支付窗口
	 */
	public static void isShowPayDialog(final BaseActivity context, ProductInfoVO vo) {
		showNextPage(context, vo, 1);
	}

	/**
	 * 是否进行播放
	 */
	public static void isShowVideoPlayer(final BaseActivity context, ProductInfoVO vo) {
		showNextPage(context, vo, 2);
	}
}
