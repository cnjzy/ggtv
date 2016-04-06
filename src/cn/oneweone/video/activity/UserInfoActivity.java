package cn.oneweone.video.activity;

import android.widget.TextView;

import cn.oneweone.video.MyApp;
import cn.oneweone.video.R;
import cn.oneweone.video.base.BaseActivity;
import cn.oneweone.video.common.Constants;
import cn.oneweone.video.util.DeviceUtil;
import cn.oneweone.video.util.preference.Preferences;

public class UserInfoActivity extends BaseActivity{

	private TextView setting_user_account_tv;
	private TextView setting_user_vip_level_tv;
	
	@Override
	public void initLayout() {
		setContentView(R.layout.activity_userinfo);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initView() {
		setting_user_account_tv = (TextView) findViewById(R.id.setting_user_account_tv);
		setting_user_vip_level_tv = (TextView) findViewById(R.id.setting_user_vip_level_tv);
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initValue() {
		int status = MyApp.preferencesUtils.getInt(Preferences.USER_STATUS, Constants.MEMBER_TYPE_IS_NOT);
		
		setNavigationTitle("个人信息");
		setting_user_account_tv.setText(DeviceUtil.getImei(context));
		setting_user_vip_level_tv.setText(getResources().getStringArray(R.array.vip_level_list)[status]);
	}

	@Override
	public void reLoadView() {
		// TODO Auto-generated method stub
		
	}

}
