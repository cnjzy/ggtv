package com.example.ddddd.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.XListView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ddddd.R;
import com.example.ddddd.activity.DetailActivity;
import com.example.ddddd.activity.VideoPlayerActivity;
import com.example.ddddd.activity.fragment.adapter.FineAdapter;
import com.example.ddddd.base.BaseActivity;
import com.example.ddddd.base.BaseFragment;
import com.example.ddddd.common.Constants;
import com.example.ddddd.net.utils.RequestParameter;
import com.example.ddddd.procotol.BaseResponseMessage;
import com.example.ddddd.util.DialogUtil.OnAlertSelectId;
import com.example.ddddd.util.PayUtils;
import com.example.ddddd.vo.ProductInfoVO;
import com.google.gson.reflect.TypeToken;
import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.huewu.pla.lib.internal.PLA_AdapterView.OnItemClickListener;

public class FineFragment extends BaseFragment {
	private final int REQUEST_LIST_KEY = 10001;
	
	private XListView listView;
	private FineAdapter fineAdapter;
	
	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public View initLayout(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_fine, container, false);
	}

	@Override
	public void initView() {
		listView = (XListView) rootView.findViewById(R.id.listView);
	}

	@Override
	public void initListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(PLA_AdapterView<?> parent, View view,
					int position, long id) {
				ProductInfoVO vo = (ProductInfoVO) fineAdapter.getItem((int)id);
				if(vo != null){
					PayUtils.isShowVideoPlayer((BaseActivity)context, vo);
				}
			}
		});
		
	}

	@Override
	public void initValue() {
		listView.setPullLoadEnable(false);
		listView.setPullRefreshEnable(false);
		
		fineAdapter = new FineAdapter((BaseActivity) context, null);
		listView.setAdapter(fineAdapter);
		
		getListData();
	}

	/**
	 * 获取首页列表
	 * 
	 * @param isShowLoadingDialog
	 */
	public void getListData() {
		List<RequestParameter> params = new ArrayList<RequestParameter>();
		startHttpRequest(Constants.HTTP_GET, Constants.URL_GET_FINE_LIST, params,
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
					fineAdapter.clear();
					fineAdapter.addLast(video);
				}
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
