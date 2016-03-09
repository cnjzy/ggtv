package com.example.ddddd.activity.fragment;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Random;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ddddd.R;
import com.example.ddddd.activity.AboutActivity;
import com.example.ddddd.activity.ProtocolActivity;
import com.example.ddddd.activity.UserInfoActivity;
import com.example.ddddd.activity.VideoHistoryActivity;
import com.example.ddddd.base.BaseActivity;
import com.example.ddddd.base.BaseFragment;
import com.example.ddddd.util.DeviceUtil;
import com.example.ddddd.util.DialogUtil;
import com.example.ddddd.util.NumberUtils;

public class UserFragment extends BaseFragment {

	private LinearLayout setting_user_account_ll;
	private TextView setting_user_account_tv;
	private TextView setting_phone_tv;
	private TextView setting_protocol_tv;
	private TextView setting_history_tv;
	private TextView setting_clear_cache_tv;
	private TextView setting_about_tv;
	
	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public View initLayout(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_user, container, false);
	}

	@Override
	public void initView() {
		setting_user_account_ll = (LinearLayout) rootView.findViewById(R.id.setting_user_account_ll);
		setting_user_account_tv = (TextView) rootView.findViewById(R.id.setting_user_account_tv);
		setting_phone_tv = (TextView) rootView.findViewById(R.id.setting_phone_tv);
		setting_protocol_tv = (TextView) rootView.findViewById(R.id.setting_protocol_tv);
		setting_history_tv = (TextView) rootView.findViewById(R.id.setting_history_tv);
		setting_clear_cache_tv = (TextView) rootView.findViewById(R.id.setting_clear_cache_tv);
		setting_about_tv = (TextView) rootView.findViewById(R.id.setting_about_tv);
	}

	@Override
	public void initListener() {
		setting_user_account_ll.setOnClickListener(this);
		setting_phone_tv.setOnClickListener(this);
		setting_protocol_tv.setOnClickListener(this);
		setting_history_tv.setOnClickListener(this);
		setting_clear_cache_tv.setOnClickListener(this);
		setting_about_tv.setOnClickListener(this);
	}

	@Override
	public void initValue() {
		setting_user_account_tv.setText(DeviceUtil.getImei(context));
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch(v.getId()){
		case R.id.setting_user_account_ll:
			BaseActivity.showActivity(context, UserInfoActivity.class);
			break;
		case R.id.setting_phone_tv:
			DialogUtil.showDoubleBtnTipDialog(context, new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which) {
					DeviceUtil.openCallPhone((Activity) context, getString(R.string.phone));
				}
			} , getString(R.string.setting_phone), getString(R.string.setting_phone_desc));
//			
			break;
		case R.id.setting_protocol_tv:
			BaseActivity.showActivity(context, ProtocolActivity.class);
			break;
		case R.id.setting_history_tv:
			BaseActivity.showActivity(context, VideoHistoryActivity.class);
			break;
		case R.id.setting_clear_cache_tv:
			showToast(getString(R.string.clear_cache_content));
			break;
		case R.id.setting_about_tv:
			BaseActivity.showActivity(context, AboutActivity.class);
			break;
		}
	}

	@Override
	public void onCallbackFromThread(InputStream is, int resultCode) {

	}
}
