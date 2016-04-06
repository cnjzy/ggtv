package cn.oneweone.video.util;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.android.unipay.Starter;
import cn.oneweone.video.MyApp;
import cn.oneweone.video.activity.VideoPlayerActivity;
import cn.oneweone.video.activity.WebViewActivity;
import cn.oneweone.video.base.BaseActivity;
import cn.oneweone.video.common.Constants;
import cn.oneweone.video.util.DialogUtil.OnAlertSelectId;
import cn.oneweone.video.util.preference.Preferences;
import cn.oneweone.video.vo.ProductInfoVO;

public class PayUtils {

	private static final String PAGETAG = "dddddd";

	public static void pay(final Activity context, String orderNo, String mStrPayMode, String amount, String feeName, Handler mHandler){
		// 测试参数
		String key = "A911741992D14E0B8C220433A7797834";
		String partnerId = "1000100020000318";
		String appId = "2535";
		String qn = DeviceUtil.getChannelName(context, "UMENG_CHANNEL");
		String money = amount;
		String tradeId = orderNo;
		String tradeName = feeName;
		String returnUrl="http://pay_success";
		String notifyUrl="http://uni.api.76iw.com/index.php/order/uninotify";
		String includeChannels="ALIPAY2,WECHAT";
		String layoutType="port";
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("partnerId", partnerId);
		map.put("key", key);
		map.put("money", money);
		map.put("tradeId", tradeId);
		map.put("appId", appId);
		map.put("qn", qn);
		map.put("tradeName", tradeName);
		map.put("notifyUrl", notifyUrl);
		map.put("returnUrl", returnUrl);
		map.put("includeChannels", includeChannels);
		map.put("layoutType", layoutType);
		Starter.getInstance().webPay(context, map, mHandler);
	}
	
	
	public static void payByH5(final BaseActivity mContext, final String orderNo, String mStrPayMode, final String amount) {
		Bundle data = new Bundle();
		data.putString("orderNo", orderNo);
		data.putString("amount", amount);
		BaseActivity.showActivityForResult(mContext, WebViewActivity.class, 988, data);
	}

	/** 获取App_Key */
	static String getAppKey(Context context) {
		return getStringMetaData(context, "HFT_APP_KEY");
	}

	/** 获取App_Key */
	static String getAppId(Context context) {
		return getStringMetaData(context, "HFT_APP_ID");
	}

	static String getStringMetaData(Context context, String key) {
		Bundle metaData = getMetaData(context);
		String strVal = metaData != null ? metaData.getString(key) : null;
		return strVal != null ? strVal : "";
	}

	static Bundle getMetaData(Context context) {
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
	static String getToSeconds() {
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
//			DialogUtil.showPay2Dialog(context, new OnAlertSelectId() {
//				public void onClick(int whichButton, Object o) {
//					DialogUtil.showPay3Dialog(context, onClickListener, Integer.parseInt(o.toString()));
//				}
//			});
			DialogUtil.showPay2Dialog(context, onClickListener);
			return false;
		} else if (status == Constants.MEMBER_TYPE_IS_YEAR && authority == Constants.MEMBER_TYPE_IS_YEAR) {
			return true;
		} else if (status == Constants.MEMBER_TYPE_IS_YEAR && authority == Constants.MEMBER_TYPE_IS_TENURE) {
//			DialogUtil.showPay1Dialog(context, new OnAlertSelectId() {
//				public void onClick(int whichButton, Object o) {
//					DialogUtil.showPay3Dialog(context, onClickListener, Integer.parseInt(o.toString()));
//				}
//			});
			DialogUtil.showPay1Dialog(context, onClickListener);
			return false;
		}
		return false;
	}

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
	static void showNextPage(final BaseActivity context, ProductInfoVO vo, int type) {
		if (!TextUtils.isEmpty(vo.getTestUrl()) && !TextUtils.isEmpty(vo.getTestSecond())) {
			Bundle data = new Bundle();
			data.putSerializable("vo", vo);
			BaseActivity.showActivity(context, VideoPlayerActivity.class, data);
		} else {
			if (PayUtils.isPay(context, vo.getAuthority(), new OnAlertSelectId() {
				public void onClick(int pay_type, Object o) {
					int amount = Integer.parseInt(o.toString());
					int member_type = Constants.MEMBER_TYPE_IS_TENURE;
					if(amount == Constants.VIP_TENURE){
						member_type = Constants.MEMBER_TYPE_IS_TENURE;
					}else if(amount == Constants.VIP_YEAR){
						member_type = Constants.MEMBER_TYPE_IS_YEAR;
					}else if(amount == Constants.VIP_YEAR_TO_TENURE){
						member_type = Constants.MEMBER_TYPE_IS_TENURE;
					}
					context.getOrder(amount, pay_type, member_type);
				}
			})) {
				Bundle data = new Bundle();
				data.putSerializable("vo", vo);
				BaseActivity.showActivity(context, VideoPlayerActivity.class, data);
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
