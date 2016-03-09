package com.example.ddddd.util;

import android.os.Bundle;
import android.text.TextUtils;

import com.example.ddddd.MyApp;
import com.example.ddddd.activity.DetailActivity;
import com.example.ddddd.activity.VideoListActivity;
import com.example.ddddd.activity.VideoPlayerActivity;
import com.example.ddddd.base.BaseActivity;
import com.example.ddddd.common.Constants;
import com.example.ddddd.util.DialogUtil.OnAlertSelectId;
import com.example.ddddd.util.preference.Preferences;
import com.example.ddddd.vo.ProductInfoVO;

public class PayUtils {

	/**
	 * 判断是否需要支付
	 * @param context
	 * @param authority 视频对应权限 0普通 1包年 2终身
	 * @param onClickListener
	 * @return
	 */
	public static boolean isPay(final BaseActivity context, int authority, final OnAlertSelectId onClickListener){
		int status = MyApp.preferencesUtils.getInt(Preferences.USER_STATUS, 0);
		if(status == Constants.MEMBER_TYPE_IS_TENURE || Constants.isDebug){
			return true;
		}else if(status == Constants.MEMBER_TYPE_IS_NOT){
//			DialogUtil.showPay3Dialog(context, onClickListener, Constants.VIP_TENURE);
			Bundle data = new Bundle();
			data.putInt("clickposition", authority);
			BaseActivity.showActivity(context, VideoListActivity.class, data);
			return false;
		}else if(status == Constants.MEMBER_TYPE_IS_YEAR && authority == Constants.MEMBER_TYPE_IS_YEAR){
			return true;
		}else if(status == Constants.MEMBER_TYPE_IS_YEAR && authority == Constants.MEMBER_TYPE_IS_TENURE){
//			DialogUtil.showPay3Dialog(context, onClickListener, Constants.VIP_TENURE);
			Bundle data = new Bundle();
			data.putInt("clickposition", authority);
			BaseActivity.showActivity(context, VideoListActivity.class, data);
			return false;
		}
		return false;
	}
	
//	/**
//	 * 判断是否需要支付
//	 * @param context
//	 * @param authority 视频对应权限 0普通 1包年 2终身
//	 * @param onClickListener
//	 * @return
//	 */
//	public static boolean isPay(final BaseActivity context, int authority, final OnAlertSelectId onClickListener){
//		int status = MyApp.preferencesUtils.getInt(Preferences.USER_STATUS, 0);
//		if(status == Constants.MEMBER_TYPE_IS_TENURE || Constants.isDebug){
//			return true;
//		}else if(status == Constants.MEMBER_TYPE_IS_NOT){
//			DialogUtil.showPay2Dialog(context, new OnAlertSelectId() {
//				public void onClick(int whichButton, Object o) {
//					DialogUtil.showPay3Dialog(context, onClickListener, Integer.parseInt(o.toString()));
//				}
//			});
//			return false;
//		}else if(status == Constants.MEMBER_TYPE_IS_YEAR && authority == Constants.MEMBER_TYPE_IS_YEAR){
//			return true;
//		}else if(status == Constants.MEMBER_TYPE_IS_YEAR && authority == Constants.MEMBER_TYPE_IS_TENURE){
//			DialogUtil.showPay1Dialog(context, new OnAlertSelectId() {
//				public void onClick(int whichButton, Object o) {
//					DialogUtil.showPay3Dialog(context, onClickListener, Integer.parseInt(o.toString()));
//				}
//			});
//			return false;
//		}
//		return false;
//	}
	
	/**
	 * 是否有权限
	 * @param authority
	 * @return
	 */
	public static boolean isPermission(int authority){
		int status = MyApp.preferencesUtils.getInt(Preferences.USER_STATUS, Constants.MEMBER_TYPE_IS_NOT);
		if(authority <= status){
			return true;
		}else{
			return false;
		}
	}
	

	/**
	 * 显示下一窗口
	 * @param context 上下文
	 * @param vo 产品对象
	 * @param type 1显示详情 2播放
	 */
	private static void showNextPage(final BaseActivity context, ProductInfoVO vo, int type){
		if(!TextUtils.isEmpty(vo.getTestUrl()) && !TextUtils.isEmpty(vo.getTestSecond())){
			Bundle data = new Bundle();
			data.putSerializable("vo", vo);
			BaseActivity.showActivity(context, VideoPlayerActivity.class, data);
		}else{
			if(PayUtils.isPay(context, vo.getAuthority(), new OnAlertSelectId(){
				public void onClick(int pay_type, Object o) {
					int amount = Constants.VIP_TENURE;
					int member_type = Constants.MEMBER_TYPE_IS_TENURE;
					context.getOrder(amount, pay_type, member_type);
				}
			})){
//				if(type == 1){
//					Bundle data = new Bundle();
//					data.putSerializable("vo", vo);
//					BaseActivity.showActivity(context, DetailActivity.class, data);
//				}else{
					Bundle data = new Bundle();
					data.putSerializable("vo", vo);
					BaseActivity.showActivity(context, VideoPlayerActivity.class, data);
//				}
			}
		}
	}
	
	/**
	 * 是否显示支付窗口
	 */
	public static void isShowPayDialog(final BaseActivity context, ProductInfoVO vo){
		showNextPage(context, vo, 1);
	}
	
	/**
	 * 是否进行播放
	 */
	public static void isShowVideoPlayer(final BaseActivity context, ProductInfoVO vo){
		showNextPage(context, vo, 2);
	}
}
