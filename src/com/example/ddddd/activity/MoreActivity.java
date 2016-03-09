package com.example.ddddd.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.ddddd.R;
import com.example.ddddd.activity.fragment.adapter.ChannelCategoryAdapter;
import com.example.ddddd.activity.fragment.adapter.ChannelDataAdapter;
import com.example.ddddd.base.BaseActivity;
import com.example.ddddd.common.Constants;
import com.example.ddddd.net.utils.RequestParameter;
import com.example.ddddd.procotol.BaseResponseMessage;
import com.example.ddddd.util.PayUtils;
import com.example.ddddd.util.DialogUtil.OnAlertSelectId;
import com.example.ddddd.vo.ChannelVO;
import com.example.ddddd.vo.ProductInfoVO;
import com.google.gson.reflect.TypeToken;

public class MoreActivity extends BaseActivity{

	private final int REQUEST_CATEGORY_LIST_KEY = 10001;
	private final int REQUEST_DATA_LIST_KEY = 10002;
	
	private ListView catgory_listView;
	private ListView data_listView;
	
	private ChannelCategoryAdapter categoryAdapter;
	private ChannelDataAdapter dataAdapter;
	
	private ChannelVO channelVO;
	private List<ProductInfoVO> video;
	private ProductInfoVO productInfoVO;
	
	@Override
	public void initLayout() {
		setContentView(R.layout.activity_more);
	}

	@Override
	public void init() {
		productInfoVO = (ProductInfoVO) getIntent().getExtras().getSerializable("vo");
		if(productInfoVO == null || TextUtils.isEmpty(productInfoVO.getMore())){
			showToast("链接地址错误，请重试");
			finish();
			return;
		}
	}

	@Override
	public void initView() {
		catgory_listView = (ListView) findViewById(R.id.catgory_listView);
		data_listView = (ListView) findViewById(R.id.data_listView);
	}

	@Override
	public void initListener() {
		catgory_listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				categoryAdapter.setSelected(position);
				ChannelVO vo = (ChannelVO) categoryAdapter.getItem(position);
				if(vo != null){
					getDataListData(vo.getTarget());
				}
			}
		});
		
		data_listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ProductInfoVO vo = (ProductInfoVO) dataAdapter.getItem(position);
				if(vo != null){
					PayUtils.isShowPayDialog(context, vo);
				}
			}
		});
	}

	@Override
	public void initValue() {
		setNavigationTitle("更多");
		
		categoryAdapter = new ChannelCategoryAdapter((BaseActivity) context, catgory_listView);
		dataAdapter = new ChannelDataAdapter((BaseActivity) context, data_listView);

		catgory_listView.setAdapter(categoryAdapter);
		data_listView.setAdapter(dataAdapter);
		
		getListData();
		getDataListData(productInfoVO.getMore());
	}

	/**
	 * 获取频道信息
	 */
	public void getListData() {
		List<RequestParameter> params = new ArrayList<RequestParameter>();
		startHttpRequest(Constants.HTTP_GET, Constants.URL_GET_CHANNEL, params,
				true, "", REQUEST_CATEGORY_LIST_KEY);
	}
	
	/**
	 * 获取频道信息
	 */
	public void getDataListData(String url) {
		List<RequestParameter> params = new ArrayList<RequestParameter>();
		startHttpRequest(Constants.HTTP_GET, url, params,
				true, "", REQUEST_DATA_LIST_KEY);
	}
	
	@Override
	public void onCallbackFromThread(String resultJson, int resultCode) {
		super.onCallbackFromThread(resultJson, resultCode);
		try{
			BaseResponseMessage br = new BaseResponseMessage();
			switch(resultCode){
			case REQUEST_CATEGORY_LIST_KEY:
				br.parseResponse(resultJson, new TypeToken<ChannelVO>(){});
				channelVO = (ChannelVO) br.getResult();
				if(br.isSuccess()){
					for (int i = 0; i < channelVO.getChannel().size(); i++) {
						if(channelVO.getChannel().get(i).getChannel_name().equals(productInfoVO.getChannel_name())){
							categoryAdapter.setSelected(i);
							break;
						}
					}
					categoryAdapter.addLast(channelVO.getChannel());
				}
				break;
			case REQUEST_DATA_LIST_KEY:
				br.parseResponse(resultJson, new TypeToken<List<ProductInfoVO>>(){});
				if(br.isSuccess()){
					video = (List<ProductInfoVO>) br.getResultList();
					dataAdapter.clear();
					dataAdapter.addLast(video);
					data_listView.setSelection(0);
				}
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void reLoadView() {
		// TODO Auto-generated method stub
		
	}

}
