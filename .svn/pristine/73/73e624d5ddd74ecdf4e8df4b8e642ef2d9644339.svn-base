package com.example.ddddd.util;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

public class UMengUtils {

	/**
	 * 记录自定义事件
	 * @param context
	 * @param eventId
	 */
	public static void addEvent(Context context, String eventId){
		MobclickAgent.onEvent(context, eventId);
	}
	
	/**
	 * 安卓点击支付宝
	 * @param context
	 */
	public static void addClickAlipay(Context context){
		addEvent(context, "a_alipay");
	}
	
	/**
	 * 显示支付对话框
	 * @param context
	 */
	public static void addShowPayDialog(Context context){
		addEvent(context, "a_content");
	}
	
	/**
	 * 显示支付对话框
	 * @param context
	 */
	public static void addShowPay40Dialog(Context context){
		addEvent(context, "a_pay_content_40");
	}
	
	/**
	 * 进入主页次数
	 * @param context
	 */
	public static void addShowMain(Context context){
		addEvent(context, "a_main");
	}
	
	/**
	 * 安卓支付成功
	 * @param context
	 */
	public static void addPaySuccess(Context context){
		addEvent(context, "a_pay_success");
	}
	
	/**
	 * 进入欢迎页面
	 * @param context
	 */
	public static void addShowSplash(Context context){
		addEvent(context, "a_splash");
	}
	
	/**
	 * 安卓微信点击计数
	 * @param context
	 */
	public static void addClickWechat(Context context){
		addEvent(context, "a_wechat");
	}
}
