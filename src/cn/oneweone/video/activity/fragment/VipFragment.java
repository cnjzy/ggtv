package cn.oneweone.video.activity.fragment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import cn.oneweone.video.R;
import cn.oneweone.video.activity.DetailActivity;
import cn.oneweone.video.activity.fragment.adapter.VipAdapter;
import cn.oneweone.video.base.BaseActivity;
import cn.oneweone.video.base.BaseFragment;
import cn.oneweone.video.common.Constants;
import cn.oneweone.video.net.utils.RequestParameter;
import cn.oneweone.video.procotol.BaseResponseMessage;
import cn.oneweone.video.util.PayUtils;
import cn.oneweone.video.util.DialogUtil.OnAlertSelectId;
import cn.oneweone.video.vo.ChannelVO;
import cn.oneweone.video.vo.ProductInfoVO;
import cn.oneweone.video.widget.view.HorizontalListView;
import com.google.gson.reflect.TypeToken;

public class VipFragment extends BaseFragment {

	private final int REQUEST_LIST_KEY = 10001;
	
	private HorizontalListView listView;
	private VipAdapter vipAdapter;
	
	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public View initLayout(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_vip, container, false);
	}

	@Override
	public void initView() {
		listView = (HorizontalListView) rootView.findViewById(R.id.listView);
	}

	@Override
	public void initListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ProductInfoVO vo = (ProductInfoVO) vipAdapter.getItem(position);
				if(vo != null){
					PayUtils.isShowVideoPlayer((BaseActivity)context, vo);
//					if(PayUtils.isPay((BaseActivity) context, vo.getAuthority(), new OnAlertSelectId(){
//						public void onClick(int pay_type, Object o) {
//							int amount = Integer.parseInt(o.toString());
//							int member_type = 0;
//							if(amount == 58){
//								member_type = 1;
//							}else if(amount == 98 || amount == 40){
//								member_type = 2;
//							}
//							getOrder(amount, pay_type, member_type);
//						}
//					})){
//						Bundle data = new Bundle();
//						data.putSerializable("vo", vo);
//						BaseActivity.showActivity(context, DetailActivity.class, data);
//					}
				}
			}
		});
	}

	@Override
	public void initValue() {
		vipAdapter = new VipAdapter((BaseActivity) context, null);
		listView.setAdapter(vipAdapter);
		
		getListData();
	}

	/**
	 * 获取首页列表
	 * 
	 * @param isShowLoadingDialog
	 */
	public void getListData() {
		List<RequestParameter> params = new ArrayList<RequestParameter>();
		startHttpRequest(Constants.HTTP_GET, Constants.URL_GET_VIP_LIST, params,
				true, "", REQUEST_LIST_KEY);
	}
	
	@Override
	public void onCallbackFromThread(String resultJson, int resultCode) {
		super.onCallbackFromThread(resultJson, resultCode);
		try{
			BaseResponseMessage br = new BaseResponseMessage();
			switch(resultCode){
			case REQUEST_LIST_KEY:
				br.parseResponse(resultJson, new TypeToken<List<ProductInfoVO>>(){});
				if(br.isSuccess()){
					List<ProductInfoVO> video = (List<ProductInfoVO>) br.getResultList();
					vipAdapter.addLast(video);
				}
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
