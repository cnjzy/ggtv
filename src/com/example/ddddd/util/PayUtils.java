package com.example.ddddd.util;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.ddddd.MyApp;
import com.example.ddddd.common.Constants;
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
				mContext.finish();
				
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

				Log.i(PAGETAG, "params=" + params.toString());

			}
		});
	}

	/** 获取App_Key */
	private static String getAppKey(Context context) {
		return getStringMetaData(context, "HFT_APP_KEY");
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
}
