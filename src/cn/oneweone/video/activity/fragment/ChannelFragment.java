package cn.oneweone.video.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import cn.oneweone.video.R;
import cn.oneweone.video.activity.DetailActivity;
import cn.oneweone.video.activity.fragment.adapter.ChannelCategoryAdapter;
import cn.oneweone.video.activity.fragment.adapter.ChannelDataAdapter;
import cn.oneweone.video.base.BaseActivity;
import cn.oneweone.video.base.BaseFragment;
import cn.oneweone.video.common.Constants;
import cn.oneweone.video.net.utils.RequestParameter;
import cn.oneweone.video.procotol.BaseResponseMessage;
import cn.oneweone.video.util.PayUtils;
import cn.oneweone.video.util.DialogUtil.OnAlertSelectId;
import cn.oneweone.video.vo.ChannelVO;
import cn.oneweone.video.vo.ProductInfoVO;
import com.google.gson.reflect.TypeToken;

public class ChannelFragment extends BaseFragment {

	private final int REQUEST_CATEGORY_LIST_KEY = 10001;
	private final int REQUEST_DATA_LIST_KEY = 10002;
	
	private ListView catgory_listView;
	private ListView data_listView;
	
	private ChannelCategoryAdapter categoryAdapter;
	private ChannelDataAdapter dataAdapter;
	
	private ChannelVO channelVO;
	private List<ProductInfoVO> video;
	
	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public View initLayout(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_channel, container, false);
	}

	@Override
	public void initView() {
		catgory_listView = (ListView) rootView.findViewById(R.id.catgory_listView);
		data_listView = (ListView) rootView.findViewById(R.id.data_listView);
	}

	@Override
	public void initListener() {
		catgory_listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				categoryAdapter.setSelected(position);
				ChannelVO vo = (ChannelVO) categoryAdapter.getItem(position);
				if(vo != null && !TextUtils.isEmpty(vo.getTarget())){
					getDataListData(vo.getTarget());
				}
			}
		});
		
		data_listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ProductInfoVO vo = (ProductInfoVO) dataAdapter.getItem(position);
				if(vo != null){
					PayUtils.isShowPayDialog((BaseActivity)context, vo);
				}
			}
		});
	}

	@Override
	public void initValue() {
		categoryAdapter = new ChannelCategoryAdapter((BaseActivity) context, catgory_listView);
		dataAdapter = new ChannelDataAdapter((BaseActivity) context, data_listView);

		catgory_listView.setAdapter(categoryAdapter);
		data_listView.setAdapter(dataAdapter);
		
		getListData();
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
				ChannelVO vo = (ChannelVO) br.getResult();
				if(br.isSuccess()){
					categoryAdapter.addLast(vo.getChannel());
					video = vo.getVideo();
					dataAdapter.addLast(video);
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
}
