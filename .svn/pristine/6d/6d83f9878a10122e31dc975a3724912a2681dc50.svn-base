package com.example.ddddd.base;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ddddd.MyApp;
import com.example.ddddd.common.Constants;
import com.example.ddddd.net.AsyncHttpGet;
import com.example.ddddd.net.AsyncHttpPost;
import com.example.ddddd.net.BaseRequest;
import com.example.ddddd.net.DefaultThreadPool;
import com.example.ddddd.net.ThreadCallBack;
import com.example.ddddd.net.utils.CheckNetWorkUtil;
import com.example.ddddd.net.utils.RequestParameter;
import com.example.ddddd.procotol.BaseResponseMessage;
import com.example.ddddd.util.DeviceUtil;
import com.example.ddddd.util.LogUtil;
import com.example.ddddd.util.UMengUtils;
import com.example.ddddd.util.preference.Preferences;
import com.example.ddddd.util.preference.PreferencesUtils;
import com.example.ddddd.vo.OrderVO;
import com.example.ddddd.widget.dialog.CustomLoadingDialog;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;
import com.wo.main.WP_SDK;

public abstract class BaseFragment extends Fragment implements ThreadCallBack,
		View.OnClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 11111L;

	protected final String TAG = this.getClass().getSimpleName();

	/**
	 * 当前activity所持有的所有请求
	 */
	List<BaseRequest> requestList = new ArrayList<BaseRequest>();

	/**
	 * 主线程Handler
	 */
	protected Handler handler = new Handler();

	/**
	 * 加载提示框
	 */
	public CustomLoadingDialog loadingDialog;

	/**
	 * Activity 前缀
	 */
	public final String FIRST_ACTION = "com.etaoshi.app.";

	/**
	 * 配置文件信息
	 */
	public static PreferencesUtils preferencesUtils;

	/**
	 * 是否初始化
	 */
	protected boolean isInit = true;

	/**
	 * 访问网络成功Tag
	 */
	protected final int SUCCESS = 1;

	/**
	 * 上下文
	 */
	protected Context context;

	/**
	 * 延迟请求时间
	 */
	protected long postDelayed = 300;

	/**
	 * 根View
	 */
	protected View rootView;
	/**
	 * 支付请求key
	 */
	private final int REQUEST_PAY_KEY = 10100;

	/**
	 * 私有化当前对象默认构造
	 */
	protected BaseFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		preferencesUtils = new PreferencesUtils(getActivity(),
				Preferences.CONFIG_FILE);
	}

	@Override
	public void onAttach(Activity activity) {
		context = activity;
		super.onAttach(activity);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		cancelRequest();
		super.onDestroy();
	}

	public void onResume() {
	    super.onResume();
	    MobclickAgent.onPageStart(getClass().getSimpleName());
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPageEnd(getClass().getSimpleName()); 
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

	public void startHttpRequest(String requestType, String url,
			List<RequestParameter> parameter, boolean isShowLoadingDialog,
			String loadingDialogContent, int resultCode) {
		startHttpRequst(requestType, url, parameter, isShowLoadingDialog,
				loadingDialogContent, true, Constants.CONNECTION_SHORT_TIMEOUT,
				Constants.READ_SHORT_TIMEOUT, resultCode);
	}

	public void startHttpRequst(String requestType, String url,
			List<RequestParameter> parameter, boolean isShowLoadingDialog,
			String loadingDialogContent) {
		startHttpRequst(requestType, url, parameter, isShowLoadingDialog,
				loadingDialogContent, true, Constants.CONNECTION_SHORT_TIMEOUT,
				Constants.READ_SHORT_TIMEOUT);
	}

	protected void startHttpRequst(String requestType, String url,
			List<RequestParameter> parameter, boolean isShowLoadingDialog,
			String loadingDialogContent, boolean isHideCloseBtn,
			int connectTimeout, int readTimeout) {
		startHttpRequst(requestType, url, parameter, isShowLoadingDialog,
				loadingDialogContent, isHideCloseBtn, connectTimeout,
				readTimeout, -1);
	}

	public void startHttpRequst(String requestType, String url,
			List<RequestParameter> parameter, boolean isShowLoadingDialog,
			String loadingDialogContent, boolean isHideCloseBtn,
			int connectTimeout, int readTimeout, int resultCode) {

		if (!CheckNetWorkUtil.checkNetWork(getActivity())) {
			// return;
		}

		if (null != parameter) {
			// parameter.add(new RequestParameter("channel",
			// DeviceUtil.getChannelName(context, "UMENG_CHANNEL")));
			// parameter.add(new RequestParameter("imei", Constants.IMEI));
			// parameter.add(new RequestParameter("version_code",
			// DeviceUtil.getVersionName(context)));
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
		} else {
			httpRequest = new AsyncHttpGet(this, url, parameter,
					isShowLoadingDialog, loadingDialogContent, isHideCloseBtn,
					connectTimeout, readTimeout, resultCode, context);
		}
		DefaultThreadPool.getInstance().execute(httpRequest);
		this.requestList.add(httpRequest);
	}

	public void showToast(String content) {
		Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
	}

	protected List<RequestParameter> getBaseRequestParams() {
		ArrayList<RequestParameter> params = new ArrayList<RequestParameter>();
		params.add(new RequestParameter("version_code", DeviceUtil
				.getVersionName(context)));
		params.add(new RequestParameter("platform", "Android"));
		// params.add(new RequestParameter("channel",
		// DeviceUtil.getChannelName(context, "test")));
		// params.add(new RequestParameter("channel", "test"));
		params.add(new RequestParameter("deviceId", DeviceUtil.getImei(context)));
		return params;
	}

	/**
	 * 初始化方法
	 */
	public abstract void init();

	public abstract View initLayout(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState);

	public abstract void initView();

	public abstract void initListener();

	public abstract void initValue();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = initLayout(inflater, container, savedInstanceState);
			if (rootView.getParent() != null) {
				((ViewGroup) rootView.getParent()).removeAllViewsInLayout();
			}
			init();
			initView();
			initListener();
			initValue();
		}
		return rootView;
	}

	public void onCallbackFromThread(String resultJson) {
		if (loadingDialog != null) {
			loadingDialog.dismiss();
		}
	}

	@Override
	public void onCallbackFromThread(InputStream is, int resultCode) {

	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * 弹出输入窗口
	 * 
	 * @param view
	 */
	protected void showIMM(final View view) {
		// 弹出键盘
		handler.postDelayed(new Runnable() {
			public void run() {
				DeviceUtil.showIMM(context, view);
			}
		}, 500);
	}

	@Override
	public void onClick(View v) {
		// 关闭键盘
		if (DeviceUtil.getIMMStatus(getActivity())
				&& getActivity().getCurrentFocus() != null) {
			DeviceUtil.hideIMM(context, getActivity().getCurrentFocus());
		}
	}

	/**
	 * 获取订单
	 * 
	 * @param amount
	 * @param pay_type
	 *            1支付宝 2微信
	 * @param member_type
	 *            1包年 2终生
	 */
	public void getOrder(int amount, int pay_type, int member_type) {

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
		if (loadingDialog != null) {
			loadingDialog.dismiss();
		}
		try {
			switch (resultCode) {
			case REQUEST_PAY_KEY:
				BaseResponseMessage br = new BaseResponseMessage();
				br.parseResponse(resultJson, new TypeToken<OrderVO>() {
				});
				if (br.isSuccess()) {
					int amount = MyApp.preferencesUtils.getInt("amount",
							Constants.VIP_TENURE);
					int pay_type = MyApp.preferencesUtils.getInt("pay_type",
							Constants.PAY_TYPE_ALIYUN);
					int member_type = MyApp.preferencesUtils.getInt(
							"member_type", Constants.MEMBER_TYPE_IS_YEAR);
					String feeName = "";
					String feeDesp = "";
					if (member_type == Constants.MEMBER_TYPE_IS_YEAR) {
						feeName = "包年会员";
						feeDesp = "包年会员";
					} else if (member_type == Constants.MEMBER_TYPE_IS_TENURE) {
						if (MyApp.preferencesUtils.getInt(
								Preferences.USER_STATUS, 0) == Constants.MEMBER_TYPE_IS_NOT) {
							feeName = "终身会员";
							feeDesp = "终身会员";
						} else {
							feeName = "升级终身";
							feeDesp = "升级终身";
						}
					}
					OrderVO vo = (OrderVO) br.getResult();
					WP_SDK.on_Recharge(String.valueOf(Constants.VIP_TENURE * 100), feeName,
							feeDesp, vo.getOrder_no(), pay_type - 1);
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
	public void onActivityResult(int requestcode, int responsecode,
			Intent intent) {
		super.onActivityResult(requestcode, responsecode, intent);
		try {
			if (intent != null) {
				int code = intent.getIntExtra("code", 1);
				String value = intent.getStringExtra("info");
				if (responsecode == 0) {// 充值
					if (code == 0) {// 充值成功
						UMengUtils.addPaySuccess(context);
						System.err.println("=======code=" + code + ",info="
								+ value);
						MyApp.preferencesUtils.putInt(Preferences.USER_STATUS,
								MyApp.preferencesUtils.getInt("member_type",
										Constants.MEMBER_TYPE_IS_YEAR));
						Toast.makeText(context, "充值成功!", Toast.LENGTH_LONG)
								.show();
					} else {// 充值失败

					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
