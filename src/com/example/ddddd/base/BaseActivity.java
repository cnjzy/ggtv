package com.example.ddddd.base;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddddd.MyApp;
import com.example.ddddd.R;
import com.example.ddddd.common.Constants;
import com.example.ddddd.net.AsyncHttpFilePost;
import com.example.ddddd.net.AsyncHttpGet;
import com.example.ddddd.net.AsyncHttpPost;
import com.example.ddddd.net.BaseRequest;
import com.example.ddddd.net.DefaultThreadPool;
import com.example.ddddd.net.ThreadCallBack;
import com.example.ddddd.net.utils.RequestParameter;
import com.example.ddddd.procotol.BaseResponseMessage;
import com.example.ddddd.util.DeviceUtil;
import com.example.ddddd.util.DialogUtil;
import com.example.ddddd.util.LogUtil;
import com.example.ddddd.util.PayUtils;
import com.example.ddddd.util.StringUtil;
import com.example.ddddd.util.UMengUtils;
import com.example.ddddd.util.preference.Preferences;
import com.example.ddddd.util.preference.PreferencesUtils;
import com.example.ddddd.vo.OrderVO;
import com.example.ddddd.vo.UserInfoVO;
import com.example.ddddd.widget.dialog.CustomLoadingDialog;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.umeng.analytics.MobclickAgent;

/**
 * 
 * @author jzy
 * 
 */
public abstract class BaseActivity extends FragmentActivity implements
		ThreadCallBack, View.OnClickListener {

	/**
	 * 增加几种返回方式
	 */
	public static final int REQUEST_LOCATION_UPDATE_CODE = 10001;

	/**
     * 
     */
	private static final long serialVersionUID = 1111L;

	/**
     * 
     */
	protected final String TAG = this.getClass().getSimpleName();

	/**
	 * 上下文
	 */
	protected BaseActivity context = this;

	/**
	 * 当前activity所持有的所有请求
	 */
	protected List<BaseRequest> requestList = null;

	/**
	 * Activity集合
	 */
	public static List<Activity> activityList = new ArrayList<Activity>();

	/**
	 * 主线程Handler
	 */
	protected Handler mHandler = new Handler() {

	};

	/**
	 * 加载提示框
	 */
	public CustomLoadingDialog loadingDialog;

	/**
	 * 配置文件信息
	 */
	public PreferencesUtils preferencesUtils;

	/**
	 * 访问成功tag
	 */
	public final int SUCCESS = 1;

	/**
	 * 当前App对象
	 */
	public MyApp mApp;

	/**
	 * 用户信息
	 */
	protected UserInfoVO mUserInfo;

	/**
	 * 网络弹出框
	 */
	protected Dialog notNetworkDialog;

	/**
	 * 图片加载参数选项
	 */
	public DisplayImageOptions options;

	/**
	 * 支付请求key
	 */
	private final int REQUEST_PAY_KEY = 10100;

	public UserInfoVO getmUserInfo() {
		return mUserInfo;
	}

	public void setmUserInfo(UserInfoVO mUserInfo) {
		this.mUserInfo = mUserInfo;
	}

	// 图片第一次加载动画
	protected ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */

	protected void onCreate(Bundle savedInstanceState) {
		requestList = new ArrayList<BaseRequest>();
		super.onCreate(savedInstanceState);

		preferencesUtils = new PreferencesUtils(context,
				Preferences.CONFIG_FILE);

		mApp = (MyApp) getApplication();
		if (!activityList.contains(this)) {
			activityList.add(this);
		}

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true).build();

		/**
		 * 始终控制媒体音量
		 */
		 this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		initLayout();
		init();
		initView();
		initListener();
		initValue();
	}

	protected void onStart() {
		super.onStart();
	}

	protected void onStop() {
		super.onStop();

	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		mUserInfo = mApp.getUserInfo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */

	protected void onPause() {
		// 关闭键盘
		if (DeviceUtil.getIMMStatus(context) && getCurrentFocus() != null) {
			DeviceUtil.hideIMM(context, getCurrentFocus());
		}
		super.onPause();
		MobclickAgent.onPause(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */

	protected void onDestroy() {
		/**
		 * 在activity销毁的时候同时设置停止请求，停止线程请求回调
		 */
		cancelRequest();
		/**
		 * 清除当前对象
		 */
		if (activityList.contains(this)) {
			activityList.remove(this);
		}
		/**
		 * 取消无网络连接弹出框
		 */
		if (notNetworkDialog != null && notNetworkDialog.isShowing()) {
			notNetworkDialog.dismiss();
		}
		notNetworkDialog = null;
		/**
		 * 释放图片缓存
		 */
		AnimateFirstDisplayListener.displayedImages.clear();

		DialogUtil.cannelDialog();
		
		super.onDestroy();
	}

	public void cancelRequest() {
		if (requestList != null && requestList.size() > 0) {
			for (BaseRequest request : requestList) {
				if (request.getRequest() != null) {
					try {
						request.getRequest().abort();
						requestList.remove(request.getRequest());

						// Log.d("netlib", "netlib ,onDestroy request to  "
						// + request.getRequest().getURI()
						// + "  is removed");
					} catch (UnsupportedOperationException e) {
						// do nothing .
					}
				}
			}
		}
	}

	public void onCallbackFromThread(String resultJson) {
		// TODO Auto-generated method stub

	}

	protected void showToast(String message) {
		Toast toast = Toast.makeText(this,
				(!StringUtil.isEmpty(message)) ? message : "",
				Toast.LENGTH_SHORT);
		toast.show();
	}

	public void startHttpRequest(String requestType, String url,
			List<RequestParameter> parameter, boolean isShowLoadingDialog,
			String loadingDialogContent, int resultCode) {
		startHttpRequst(requestType, url, parameter, isShowLoadingDialog,
				loadingDialogContent, true, Constants.CONNECTION_SHORT_TIMEOUT,
				Constants.READ_SHORT_TIMEOUT, resultCode);
	}

	public void startHttpRequst(String requestType, String url,
			List<RequestParameter> parameter, boolean isShowLoadingDialog,
			String loadingDialogContent, boolean isHideCloseBtn, int resultCode) {
		startHttpRequst(requestType, url, parameter, isShowLoadingDialog,
				loadingDialogContent, isHideCloseBtn,
				Constants.CONNECTION_SHORT_TIMEOUT,
				Constants.READ_SHORT_TIMEOUT, resultCode);
	}

	public void startHttpRequst(String requestType, String url,
			List<RequestParameter> parameter, boolean isShowLoadingDialog,
			String loadingDialogContent, boolean isHideCloseBtn,
			int connectTimeout, int readTimeout, int resultCode) {

		if (!DeviceUtil.isConnectNet(context) && !context.isFinishing()) {
			if (notNetworkDialog == null) {
				notNetworkDialog = DialogUtil.showSettingWIFIDialog(context);
			} else {
				if (!notNetworkDialog.isShowing()) {
					if (notNetworkDialog.getContext() instanceof Activity) {
						if (!((Activity) notNetworkDialog.getContext())
								.isFinishing())
							notNetworkDialog.show();
					} else {
						notNetworkDialog.show();
					}
				}
			}
			return;
		}

		 if (null != parameter) {
//			 parameter.add(new RequestParameter("version", String.valueOf(DeviceUtil.getVersionCode(context))));
//			 parameter.add(new RequestParameter("platform", Constants.PLATFORM));
//			 parameter.add(new RequestParameter("channel", DeviceUtil.getChannelName(context, "UMENG_CHANNEL")));
//			 parameter.add(new RequestParameter("deviceId", DeviceUtil.getImei(context)));
		 }
		for (int i = 0; i < parameter.size(); i++) {
			RequestParameter requestParameter = parameter.get(i);
			LogUtil.d("requestParameter", requestParameter.getName() + "="
					+ requestParameter.getValue());
		}
		BaseRequest httpRequest = null;
		if ("POST".equalsIgnoreCase(requestType)) {
			httpRequest = new AsyncHttpPost(this, url, parameter,
					isShowLoadingDialog, loadingDialogContent, isHideCloseBtn,
					connectTimeout, readTimeout, resultCode);
		} else if ("GET".equalsIgnoreCase(requestType)) {
			httpRequest = new AsyncHttpGet(this, url, parameter,
					isShowLoadingDialog, loadingDialogContent, isHideCloseBtn,
					connectTimeout, readTimeout, resultCode, this);
		} else {
			httpRequest = new AsyncHttpFilePost(this, url, parameter,
					isShowLoadingDialog, loadingDialogContent, isHideCloseBtn,
					connectTimeout, readTimeout, resultCode);
		}
		DefaultThreadPool.getInstance().execute(httpRequest);
		this.requestList.add(httpRequest);
	}

	public static void showActivity(Context context, Class<?> c) {
		showActivity(context, c, "");
	}

	public static void showActivity(Context context, Class<?> c, String info) {
		Bundle data = new Bundle();
		data.putString("info", info);
		showActivity(context, c, data);
	}

	public static void showActivity(Context context, Class<?> cls, Bundle data) {
		if (data == null)
			data = new Bundle();
		Intent intent = new Intent();
		intent.setClass(context, cls);
		intent.putExtras(data);
		context.startActivity(intent);
		if (context instanceof Activity)
			((Activity) context).overridePendingTransition(
					R.anim.activity_right_in, R.anim.activity_left_out);
	}

	/**
	 * 可以接受返回数据跳转Activity
	 * 
	 * @param context
	 * @param c
	 * @param requestCode
	 * @param data
	 */
	public static void showActivityForResult(Activity context, Class<?> c,
			int requestCode, Bundle data) {
		if (data == null)
			data = new Bundle();
		Intent intent = new Intent();
		intent.setClass(context, c);
		intent.putExtras(data);
		context.startActivityForResult(intent, requestCode);
		if (context instanceof Activity)
			((Activity) context).overridePendingTransition(
					R.anim.activity_right_in, R.anim.activity_left_out);
	}

	@Override
	public void onCallbackFromThread(InputStream is, int resultCode) {
		// TODO Auto-generated method stub

	}

	public void onClick(View v) {
		// 关闭键盘
		if (DeviceUtil.getIMMStatus(context) && getCurrentFocus() != null) {
			DeviceUtil.hideIMM(context, getCurrentFocus());
		}
		switch (v.getId()) {
		case R.id.navigation_left_btn:
			goBack();
			break;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& keyCode == KeyEvent.KEYCODE_BACK) {
			goBack();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void goBack() {
		// 退出页面
		finish();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#finish()
	 */

	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.activity_left_in,
				R.anim.activity_right_out);
	}

	/**
	 * 弹出输入窗口
	 * 
	 * @param view
	 */
	protected void showIMM(final View view) {
		// 弹出键盘
		mHandler.postDelayed(new Runnable() {
			public void run() {
				DeviceUtil.showIMM(context, view);
			}
		}, 500);
	}

	protected List<RequestParameter> getBaseRequestParams() {
		ArrayList<RequestParameter> params = new ArrayList<RequestParameter>();
		// params.add(new RequestParameter("version_code",
		// String.valueOf(DeviceUtil.getVersionCode(context))));
		// params.add(new RequestParameter("platform", "Android"));
		// params.add(new RequestParameter("channel",
		// DeviceUtil.getChannelName(context, "test")));
		// params.add(new RequestParameter("channel", "test"));
		// params.add(new RequestParameter("deviceId",
		// DeviceUtil.getImei(context)));
		return params;
	}

	public abstract void initLayout();

	public abstract void init();

	public abstract void initView();

	public abstract void initListener();

	public abstract void initValue();
	
	public abstract void reLoadView();

	public static void clearTask() {
		for (int i = activityList.size() - 1; i >= 0; i--) {
			Activity act = activityList.get(i);
			activityList.remove(i);
			act.finish();
		}
	}

	/**
	 * 设置导航标题
	 * 
	 * @param rid
	 */
	public void setNavigationTitle(String rid) {
		TextView btn_title = (TextView) findViewById(R.id.navigation_title_tv);
		if (btn_title != null && !TextUtils.isEmpty(rid)) {
			btn_title.setText(rid);
		}
	}

	/**
	 * 
	 * @param amount
	 * @param pay_type
	 *            1支付宝 2微信
	 * @param member_type
	 *            1包年 2终生
	 */
	public void getOrder(int amount, int pay_type, int member_type) {
		
		MyApp.preferencesUtils.putInt("amount", amount);
		MyApp.preferencesUtils.putInt("pay_type", pay_type);
		MyApp.preferencesUtils.putInt("member_type", member_type);
		
		List<RequestParameter> params = new ArrayList<RequestParameter>();
		params.add(new RequestParameter("imei", DeviceUtil.getImei(context)));
		params.add(new RequestParameter("device_os", 1));
		params.add(new RequestParameter("amount", amount));
		params.add(new RequestParameter("pay_type", pay_type));
		params.add(new RequestParameter("member_type", member_type));
		startHttpRequest(Constants.HTTP_POST, Constants.URL_GET_ORDER, params,
				true, "", REQUEST_PAY_KEY);
	}

	public void onCallbackFromThread(String resultJson, int resultCode) {
		try {
			switch (resultCode) {
			case REQUEST_PAY_KEY:
				BaseResponseMessage br = new BaseResponseMessage();
				br.parseResponse(resultJson, new TypeToken<OrderVO>(){});
				if(br.isSuccess()){
					int amount = MyApp.preferencesUtils.getInt("amount", Constants.VIP_TENURE);
					int pay_type = MyApp.preferencesUtils.getInt("pay_type", Constants.PAY_TYPE_ALIYUN);
					int member_type = MyApp.preferencesUtils.getInt("member_type", Constants.MEMBER_TYPE_IS_YEAR);
					String feeName = "";
					String feeDesp = "";
					if(member_type == Constants.MEMBER_TYPE_IS_YEAR){
						feeName = "包年会员";
						feeDesp = "包年会员";
					}else if(member_type == Constants.MEMBER_TYPE_IS_TENURE){
						if(MyApp.preferencesUtils.getInt(Preferences.USER_STATUS, 0) == Constants.MEMBER_TYPE_IS_NOT){
							feeName = "终身会员";
							feeDesp = "终身会员";
						}else{
							feeName = "升级终身";
							feeDesp = "升级终身";
						}
					}
					OrderVO vo = (OrderVO) br.getResult();
					int mStrPayMode = pay_type == 1 ? 2 : 1;
					PayUtils.pay(context, vo.getOrder_no(), String.valueOf(mStrPayMode), String.valueOf(Constants.VIP_TENURE));
				}
				break;
			}
		} catch (Exception e) {
			LogUtil.e(e);
		}
	}
	
	/***
	 * @param requestcode
	 *            请求编号
	 * @param responsecode
	 *            响应编号
	 * @param intent
	 *            响应内容
	 */
	public void onActivityResult(int requestcode, int responsecode, Intent intent) {
		super.onActivityResult(requestcode, responsecode, intent);
		try {
			if (intent != null) {
				int code = intent.getIntExtra("code", 1);
				String value = intent.getStringExtra("info");
				if (responsecode == 0) {// 充值
					if (code == 0) {// 充值成功
						UMengUtils.addPaySuccess(context);
						System.err.println("=======code=" + code + ",info=" + value);
						MyApp.preferencesUtils.putInt(Preferences.USER_STATUS, MyApp.preferencesUtils.getInt("member_type", Constants.MEMBER_TYPE_IS_YEAR));
						reLoadView();
						Toast.makeText(this, "充值成功!", Toast.LENGTH_LONG).show();
						finish();
					} else {// 充值失败
							
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
