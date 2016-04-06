package cn.oneweone.video.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import com.google.gson.reflect.TypeToken;
import cn.oneweone.video.R;
import cn.oneweone.video.activity.fragment.adapter.DetailAdapter;
import cn.oneweone.video.activity.fragment.adapter.HomeAdapter;
import cn.oneweone.video.base.BaseActivity;
import cn.oneweone.video.common.Constants;
import cn.oneweone.video.net.utils.RequestParameter;
import cn.oneweone.video.procotol.BaseResponseMessage;
import cn.oneweone.video.util.DeviceUtil;
import cn.oneweone.video.util.ListViewUtils;
import cn.oneweone.video.vo.ProductDetailVO;
import cn.oneweone.video.vo.ProductInfoVO;
import cn.oneweone.video.vo.ProductListVO;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DetailActivity extends BaseActivity{
	private final int REQUEST_GET_DETAIL_KEY = 10001;
	
	private ScrollView scrollView;
	private ImageView detail_icon_iv;
	private TextView detail_name_tv;
	private TextView detail_desc_tv;
	private TextView item_title_tv;
	private TextView item_more_tv;
	
	private ListView listView;
	private DetailAdapter detailAdapter;
	
	private ProductInfoVO vo;
	private ProductDetailVO detailVO;
	
	@Override
	public void initLayout() {
		setContentView(R.layout.activity_product_detail);
	}

	@Override
	public void init() {
		vo = (ProductInfoVO) getIntent().getExtras().getSerializable("vo");
		if(vo == null || TextUtils.isEmpty(vo.getTarget())){
			showToast("获取详情失败，请查看其它视频");
			return;
		}
	}

	@Override
	public void initView() {
		scrollView = (ScrollView) findViewById(R.id.scrollView);
		detail_icon_iv = (ImageView) findViewById(R.id.detail_icon_iv);
		detail_name_tv = (TextView) findViewById(R.id.detail_name_tv);
		detail_desc_tv = (TextView) findViewById(R.id.detail_desc_tv);
		item_title_tv = (TextView) findViewById(R.id.item_title_tv);
		item_more_tv = (TextView) findViewById(R.id.item_more_tv);
		
		listView = (ListView) findViewById(R.id.listView);
	}

	@Override
	public void initListener() {
		detail_icon_iv.setOnClickListener(this);
		item_more_tv.setOnClickListener(this);
	}

	@Override
	public void initValue() {
		setNavigationTitle(vo.getName());
		getDetail();
		
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) listView.getLayoutParams();
		params.height = context.getResources().getDimensionPixelSize(R.dimen.detail_listview_height);
		listView.setLayoutParams(params);
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch(v.getId()){
		case R.id.detail_icon_iv:
			Bundle data = new Bundle();
			data.putSerializable("vo", detailVO.getDetails());
			showActivity(context, VideoPlayerActivity.class, data);
			break;
		case R.id.item_more_tv:
			data = new Bundle();
			data.putSerializable("vo", vo);
			BaseActivity.showActivity(context, MoreActivity.class, data);
			break;
		}
	}
	
	/**
	 * 获取详情信息
	 * 
	 * @param isShowLoadingDialog
	 */
	public void getDetail() {
		List<RequestParameter> params = new ArrayList<RequestParameter>();
		startHttpRequest(Constants.HTTP_GET, vo.getTarget(), params,
				true, "", REQUEST_GET_DETAIL_KEY);
	}

	@Override
	public void onCallbackFromThread(String resultJson, int resultCode) {
		super.onCallbackFromThread(resultJson, resultCode);
		System.err.println("resultJson");
		try{
			switch(resultCode){
			case REQUEST_GET_DETAIL_KEY:
				BaseResponseMessage br = new BaseResponseMessage();
				br.parseResponse(resultJson, new TypeToken<ProductDetailVO>() {});
				detailVO = (ProductDetailVO) br.getResult();
				if(vo != null){
					ImageLoader.getInstance().displayImage(detailVO.getDetails().getImgUrl(), detail_icon_iv, mApp.options, mApp.getAnimateFirstListener());
					detail_name_tv.setText(detailVO.getDetails().getName());
					detail_desc_tv.setText(detailVO.getDetails().getDescription());
					
					detailAdapter = new DetailAdapter(context, listView);
					detailAdapter.addLast(detailVO.getHot());
					listView.setAdapter(detailAdapter);
					ListViewUtils.setListViewHeightBasedOnChildren(listView);
					mHandler.postDelayed(new Runnable() {
						public void run() {
							scrollView.scrollTo(0, 0);
						}
					}, 200);
					
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
