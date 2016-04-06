package cn.oneweone.video.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.google.gson.reflect.TypeToken;
import cn.oneweone.video.MyApp;
import cn.oneweone.video.R;
import cn.oneweone.video.activity.MainActivity;
import cn.oneweone.video.activity.fragment.adapter.BannerAdapter;
import cn.oneweone.video.activity.fragment.adapter.HomeAdapter;
import cn.oneweone.video.base.BaseActivity;
import cn.oneweone.video.base.BaseFragment;
import cn.oneweone.video.common.Constants;
import cn.oneweone.video.net.utils.RequestParameter;
import cn.oneweone.video.procotol.BaseResponseMessage;
import cn.oneweone.video.util.DeviceUtil;
import cn.oneweone.video.util.preference.Preferences;
import cn.oneweone.video.vo.ProductInfoVO;
import cn.oneweone.video.vo.ProductListVO;
import cn.oneweone.video.vo.UserInfoVO;
import cn.oneweone.video.widget.view.ChildViewPager;
import cn.oneweone.video.widget.view.PointWidget;

public class HomeFragment extends BaseFragment {

	private final int REQUEST_LIST_KEY = 10001;
	private final int REQUEST_GET_USER_STATUS_KEY = 10002;
	

	/**
	 * 布局控制器
	 */
	private LayoutInflater inflater;
	
	/**
	 * 首页列表
	 */
	private ListView listView;
	 private HomeAdapter homeAdapter;

	/**
	 * 首页广告栏
	 */
	private View headerView;
	private LayoutParams viewPagerLayoutParams;
	private ChildViewPager viewPager;
	private BannerAdapter bannerAdapter;
	private PointWidget pw;

	/**
	 * 加载显示View
	 */
	private View footView;

	@Override
	public void init() {
		viewPagerLayoutParams = new LayoutParams(LayoutParams.FILL_PARENT, context.getResources().getDimensionPixelSize(R.dimen.banner_home_height));
	}

	@Override
	public View initLayout(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		return inflater.inflate(R.layout.fragment_home, container, false);
	}

	@Override
	public void initView() {
		listView = (ListView) rootView.findViewById(R.id.listView);
	}

	@Override
	public void initListener() {

	}

	@Override
	public void initValue() {
//		getUserStatus();
		getListData();
	}

	/**
	 * 获取首页列表
	 * 
	 * @param isShowLoadingDialog
	 */
	public void getListData() {
		List<RequestParameter> params = new ArrayList<RequestParameter>();
		startHttpRequest(Constants.HTTP_GET, Constants.URL_HOME_LIST, params,
				true, "", REQUEST_LIST_KEY);
	}

	/**
	 * 获取用户状态
	 * 
	 * @param isShowLoadingDialog
	 */
	public void getUserStatus() {
		List<RequestParameter> params = new ArrayList<RequestParameter>();
		params.add(new RequestParameter("imei", DeviceUtil.getImei(context)));
		startHttpRequest(Constants.HTTP_GET, Constants.URL_GET_USER_STATUS, params,
				true, "", REQUEST_GET_USER_STATUS_KEY);
	}

	@Override
	public void onCallbackFromThread(String resultJson, int resultCode) {
		super.onCallbackFromThread(resultJson, resultCode);
		try {
			switch (resultCode) {
			case REQUEST_LIST_KEY:
				BaseResponseMessage br = new BaseResponseMessage();
				br.parseResponse(resultJson, new TypeToken<ProductListVO>() {});
				ProductListVO vo = (ProductListVO) br.getResult();
				
				if (vo != null && vo.getBanner() != null && vo.getBanner().size() > 0) {
					if(vo.getBanner().size() == 2)
						vo.getBanner().addAll(vo.getBanner());
					
					// add header
					headerView = inflater.inflate(R.layout.item_listview_viewpager, null);
					viewPager = (ChildViewPager) headerView.findViewById(R.id.viewPager);
					pw = (PointWidget) headerView.findViewById(R.id.litu_welcome_ponit);
//					RelativeLayout.LayoutParams pwParams = (RelativeLayout.LayoutParams) pw.getLayoutParams();
//					pwParams.bottomMargin = context.getResources().getDimensionPixelSize(R.dimen.time_title_padding_top_bottom);
//					pw.setLayoutParams(pwParams);
					bannerAdapter = new BannerAdapter(getContext(), vo.getBanner(), viewPager, pw);
					viewPager.setAdapter(bannerAdapter);
					viewPager.setLayoutParams(viewPagerLayoutParams);
					listView.addHeaderView(headerView);
					
					// 设置banner
					viewPager.setCurrentItem(1000 - 1000 % bannerAdapter.getCount());
				}

				// 设置列表
				homeAdapter = new HomeAdapter((BaseActivity) getActivity(), listView);
				listView.setAdapter(homeAdapter);
				homeAdapter.addLast(vo.getVideo());
				break;
			case REQUEST_GET_USER_STATUS_KEY:
				br = new BaseResponseMessage();
				br.parseResponse(resultJson, new TypeToken<UserInfoVO>() {}, null, false);
				if(br.isSuccess()){
					UserInfoVO userInfoVO = (UserInfoVO)br.getResult();
					MyApp.preferencesUtils.putInt(Preferences.USER_STATUS, userInfoVO.getUser_type());
					int userType = userInfoVO.getUser_type();
					if(userType == Constants.MEMBER_TYPE_IS_YEAR){
						((MainActivity)context).setVipButton(View.VISIBLE, R.drawable.icon_vip_year);
					}else if(userType == Constants.MEMBER_TYPE_IS_TENURE){
						((MainActivity)context).setVipButton(View.VISIBLE, R.drawable.icon_vip_end);
					}else{
						((MainActivity)context).setVipButton(View.INVISIBLE, 0);
					}
				}else{
					((MainActivity)context).setVipButton(View.INVISIBLE, 0);
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
