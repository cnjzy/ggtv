package com.example.ddddd.activity;

import java.util.Date;

import org.apache.http.util.EncodingUtils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.ddddd.MyApp;
import com.example.ddddd.R;
import com.example.ddddd.base.BaseActivity;
import com.example.ddddd.common.Constants;
import com.example.ddddd.net.utils.Utils;
import com.example.ddddd.util.DateUtils;
import com.example.ddddd.util.DeviceUtil;
import com.example.ddddd.util.Md5Encode;
import com.example.ddddd.util.UMengUtils;
import com.example.ddddd.vo.GatewayModel;

public class WebViewActivity extends BaseActivity {
	private WebView webView;
	private String orderNo;
	private String amount;
	private String url = "http://gateway.71pay.cn/Pay/GateWay10.shtml";
	private GatewayModel gatewayModel;

	@Override
	public void initLayout() {
		setContentView(R.layout.activity_web);
	}

	@Override
	public void init() {
		orderNo = getIntent().getExtras().getString("orderNo");
		amount = getIntent().getExtras().getString("amount");
//		amount = "0.5";
	}

	@Override
	public void initView() {
		webView = (WebView) findViewById(R.id.webView);
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initValue() {
		String mStrAppId = DeviceUtil.getChannelName(context, "HFT_APP_ID");
		String mStrPayType = "2";
		String mStrOrderId = orderNo;
		String mStrNotifyUrl = "http://api.76iw.com/order/hftnotify";
		final String mStrReturnUrl = "http://pay_success";
		String mStrTime = DateUtils.getStringByDate(new Date(), DateUtils.parsePatterns[11]);
		String mStrKey = Md5Encode.getMD5(DeviceUtil.getChannelName(context, "HFT_APP_KEY"));
		String mysign = String
				.format(String
						.format("app_id=%s&pay_type=%s&order_id=%s&order_amt=%s&notify_url=%s&return_url=%s&time_stamp=%s&key=%s",
								mStrAppId, mStrPayType, mStrOrderId,
								amount, mStrNotifyUrl, mStrReturnUrl,
								mStrTime, mStrKey));

		String sign = Md5Encode.getMD5(mysign);

		gatewayModel = new GatewayModel();
		gatewayModel.setAppID(mStrAppId);
		gatewayModel.setpay_type(mStrPayType);
		gatewayModel.setOrderID(mStrOrderId);
		gatewayModel.setpay_amt(amount);
		gatewayModel.setnotify_url(mStrNotifyUrl);
		gatewayModel.setreturn_url(mStrReturnUrl);
		gatewayModel.setgoods_name("终身会员");
		gatewayModel.setgoods_num("1");
		gatewayModel.setgoods_note(DeviceUtil.getChannelName(context, "UMENG_CHANNEL"));
		gatewayModel.setExtends("No");
		gatewayModel.setagent_bill_time(mStrTime);
		gatewayModel.setSign(sign);
		

		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webView.postUrl(url, EncodingUtils.getBytes(postSubmitUrl(gatewayModel), "BASE64"));

		webView.setWebViewClient(new WebViewClient() {

			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				System.err.println("onPageStarted():url = " + url + ";");

				if(url.indexOf(mStrReturnUrl) != -1){
					UMengUtils.addPaySuccess(context);
					MyApp.preferencesUtils.putInt("userType", Constants.MEMBER_TYPE_IS_TENURE);
					showToast("支付成功");
					finish();
				}
				
				webView.setVisibility(View.INVISIBLE);

			}

			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				System.err.println("shouldOverrideUrlLoading() ;" + "url=" + url);
				if (url.startsWith("http:") || url.startsWith("https:")) {
					return false;
				}

				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(intent);
				return true;
			}

			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				System.err.println("onReceivedError()");

				System.err.println("onReceivedError():failingUrl = " + failingUrl + " 、description = " + description + ";" + " 、errorCode = " + errorCode + ";");
				if ("找不到该网址".equals(description)) {
					System.err.println("亲，找不到该网址");

					return;
				}

				if (!failingUrl.substring(0, 3).equals("tel")) {
					System.err.println("亲，您的网络不给力！");
					return;
				}
			}

			/** 页面完成 */
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				System.err.println("onPageFinished():url = " + url + ";");

				webView.setVisibility(View.VISIBLE);
				// finish();
			}

		});
	}
	
	public static String postSubmitUrl(GatewayModel gatewayModel) {
		StringBuilder sbURL = new StringBuilder();
		sbURL.append("app_id=" + gatewayModel.getAppID());
		sbURL.append("&pay_type=" + gatewayModel.getpay_type());
		sbURL.append("&order_id=" + gatewayModel.getOrderID());
		sbURL.append("&order_amt=" + gatewayModel.getpay_amt());
		sbURL.append("&notify_url=" + gatewayModel.getnotify_url());
		sbURL.append("&return_url=" + gatewayModel.getreturn_url());
		sbURL.append("&goods_name=" + gatewayModel.getgoods_name());
		sbURL.append("&goods_num=" + gatewayModel.getgoods_num());
		sbURL.append("&goods_note=" + gatewayModel.getgoods_note());
		sbURL.append("&extends=" + gatewayModel.getExtends());
		sbURL.append("&time_stamp=" + gatewayModel.getagent_bill_time());
		sbURL.append("&sign=" + gatewayModel.getSign());
		return sbURL.toString();

	}

	@Override
	public void reLoadView() {
		// TODO Auto-generated method stub

	}

}
