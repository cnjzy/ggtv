package com.example.ddddd.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ddddd.MyApp;
import com.example.ddddd.R;
import com.example.ddddd.activity.fragment.ChannelFragment;
import com.example.ddddd.activity.fragment.FineFragment;
import com.example.ddddd.activity.fragment.HomeFragment;
import com.example.ddddd.activity.fragment.UserFragment;
import com.example.ddddd.activity.fragment.VipFragment;
import com.example.ddddd.base.BaseActivity;
import com.example.ddddd.common.Constants;
import com.example.ddddd.net.utils.RequestParameter;
import com.example.ddddd.procotol.BaseResponseMessage;
import com.example.ddddd.util.DeviceUtil;
import com.example.ddddd.util.DialogUtil;
import com.example.ddddd.util.DownloadManagerUtils;
import com.example.ddddd.util.UMengUtils;
import com.example.ddddd.util.preference.Preferences;
import com.example.ddddd.vo.VersionVO;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends BaseActivity {
	private final int REQUEST_GET_VERSION_KEY = 10001;

	private ImageButton search_btn;
	private ImageButton pay_year_btn;
	private RadioGroup tab_foot_bar;
	private ImageView main_foot_bottom_icon_iv;
	private int bottom_icon_width = 0;

	/**
	 * Fragment 管理
	 */
	private FragmentManager fm;
	private Fragment showFragment = null;

	private HomeFragment homeFragment;
	private ChannelFragment channelFragment;
	private VipFragment vipFragment;
	private FineFragment fineFragment;
	private UserFragment userFragment;

	private Dialog dialog;

	@Override
	public void initLayout() {
		setContentView(R.layout.activity_main);
	}

	@Override
	public void init() {
		UMengUtils.addShowMain(context);
		fm = getSupportFragmentManager();
	}

	@Override
	public void initView() {
		search_btn = (ImageButton) findViewById(R.id.search_btn);
		pay_year_btn = (ImageButton) findViewById(R.id.pay_year_btn);
		tab_foot_bar = (RadioGroup) findViewById(R.id.tab_foot_bar);
		main_foot_bottom_icon_iv = (ImageView) findViewById(R.id.main_foot_bottom_icon_iv);
	}

	@Override
	public void initListener() {
		search_btn.setOnClickListener(this);
		pay_year_btn.setOnClickListener(this);
		for (int i = 0; i < tab_foot_bar.getChildCount(); i++) {
			((RadioButton) tab_foot_bar.getChildAt(i)).setOnClickListener(this);
		}
	}

	@Override
	public void initValue() {
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) main_foot_bottom_icon_iv
				.getLayoutParams();
		bottom_icon_width = getWindowManager().getDefaultDisplay().getWidth() / 5;
		params.width = bottom_icon_width;
		main_foot_bottom_icon_iv.setLayoutParams(params);

		((RadioButton) tab_foot_bar.getChildAt(0)).performClick();
//		mHandler.postDelayed(new Runnable() {
//			public void run() {
//				if (MyApp.preferencesUtils.getInt(Preferences.USER_STATUS, Constants.MEMBER_TYPE_IS_NOT) != 2) {
//					pay();
//				}
//			}
//		}, 2 * 1000);

		getVersion();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.main_foot_message_rb:
			if (homeFragment == null) {
				homeFragment = new HomeFragment();
			}
			showFragment(homeFragment);
			setBottomIcon(0);
			break;
		case R.id.main_foot_grow_up_rb:
			if (channelFragment == null) {
				channelFragment = new ChannelFragment();
			}
			showFragment(channelFragment);
			setBottomIcon(1);
			break;
		case R.id.main_foot_campus_rb:
			if (vipFragment == null) {
				vipFragment = new VipFragment();
			}
			showFragment(vipFragment);
			setBottomIcon(2);
			break;
		case R.id.main_foot_pilot_rb:
			if (fineFragment == null) {
				fineFragment = new FineFragment();
			}
			showFragment(fineFragment);
			setBottomIcon(3);
			break;
		case R.id.main_foot_more_rb:
			if (userFragment == null) {
				userFragment = new UserFragment();
			}
			showFragment(userFragment);
			setBottomIcon(4);
			break;
		case R.id.pay_year_btn:
			break;
		case R.id.search_btn:
//			pay();
			break;
		}
	}

//	private void pay() {
//		if (!isFinishing()) {
//			dialog = DialogUtil.showPay2Dialog(context, new OnAlertSelectId() {
//				public void onClick(int whichButton, Object o) {
//					DialogUtil.showPay3Dialog(context, new OnAlertSelectId() {
//						public void onClick(int pay_type, Object o) {
//							int amount = Integer.parseInt(o.toString());
//							int member_type = Constants.MEMBER_TYPE_IS_NOT;
//							if (amount == Constants.VIP_YEAR) {
//								member_type = Constants.MEMBER_TYPE_IS_YEAR;
//							} else if (amount == Constants.VIP_TENURE || amount == Constants.VIP_YEAR_TO_TENURE) {
//								member_type = Constants.MEMBER_TYPE_IS_TENURE;
//							}
//							context.getOrder(amount, pay_type, member_type);
//						}
//					}, Integer.parseInt(o.toString()));
//				}
//			});
//			((RadioButton) tab_foot_bar.getChildAt(0)).setChecked(true);
//		}
//	}

	/**
	 * 显示Fragment
	 * 
	 * @param f
	 */
	public void showFragment(Fragment f) {
		FragmentTransaction ft = fm.beginTransaction();

		ft.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out,
				R.anim.abc_fade_in, R.anim.abc_fade_out);

		if (!f.isAdded()) {
			if (showFragment != null) {
				ft.hide(showFragment).add(R.id.fragment_content, f);
			} else {
				ft.add(R.id.fragment_content, f);
			}
		} else {
			if (showFragment != null) {
				ft.hide(showFragment).show(f);
			} else {
				ft.show(f);
			}
		}
		ft.commit();
		showFragment = f;
	}

	/**
	 * 设置底部icon
	 * 
	 * @param i
	 */
	private void setBottomIcon(int i) {
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) main_foot_bottom_icon_iv
				.getLayoutParams();
		params.leftMargin = i * bottom_icon_width;
		main_foot_bottom_icon_iv.setLayoutParams(params);
	}

	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode ==
	// KeyEvent.KEYCODE_BACK){
	// return false;
	// }
	// return super.onKeyDown(keyCode, event);
	// }

	@Override
	protected void onDestroy() {
		DialogUtil.cannelDialog();
		super.onDestroy();
	}

	long first = 0;

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_DOWN
					&& event.getRepeatCount() == 0) {
				long second = System.currentTimeMillis();
				long temp = second - first;
				if (temp < 1000 * 2) {
					if (dialog != null && dialog.isShowing()) {
						dialog.cancel();
					}
					finish();
				} else {
					Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				}
				first = second;

			}
			return false;
		}

		return super.dispatchKeyEvent(event);
	}

	/**
	 * 设置vip按钮状态
	 * 
	 * @param visible
	 * @param resBg
	 */
	public void setVipButton(int visible, int resid) {
		pay_year_btn.setVisibility(visible);
		if (resid != 0) {
			pay_year_btn.setImageResource(resid);
		}
	}

	@Override
	public void reLoadView() {
		int userStatus = MyApp.preferencesUtils.getInt(Preferences.USER_STATUS, Constants.MEMBER_TYPE_IS_NOT);
		if (userStatus == 0) {
			setVipButton(View.INVISIBLE, Constants.MEMBER_TYPE_IS_NOT);
		} else if (userStatus == Constants.MEMBER_TYPE_IS_YEAR) {
			setVipButton(View.VISIBLE, R.drawable.icon_vip_year);
		} else if (userStatus == Constants.MEMBER_TYPE_IS_TENURE) {
			setVipButton(View.VISIBLE, R.drawable.icon_vip_end);
		}
	}

	/**
	 * 获取版本更新
	 * 
	 * @param isShowLoadingDialog
	 */
	public void getVersion() {
		List<RequestParameter> params = new ArrayList<RequestParameter>();
		startHttpRequest(Constants.HTTP_GET, Constants.URL_GET_VERSION, params,
				true, "", REQUEST_GET_VERSION_KEY);
	}

	@Override
	public void onCallbackFromThread(String resultJson, int resultCode) {
		super.onCallbackFromThread(resultJson, resultCode);
		try {
			BaseResponseMessage br = new BaseResponseMessage();
			switch (resultCode) {
			case REQUEST_GET_VERSION_KEY:
				br.parseResponse(resultJson, new TypeToken<VersionVO>() {
				});
				if (br.isSuccess()) {
					VersionVO vo = (VersionVO) br.getResult();
					if (vo.getVersionCode() > DeviceUtil
							.getVersionCode(context)) {
						showToast("正在下载最新版本");
						// 升级
						DownloadManagerUtils.downloadApk(context,
								vo.getDownloadUrl());
					}
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
