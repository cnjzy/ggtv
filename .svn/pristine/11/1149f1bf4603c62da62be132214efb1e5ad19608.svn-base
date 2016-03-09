package com.example.ddddd.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.ddddd.R;
import com.example.ddddd.activity.adapter.VideoHistoryAdatper;
import com.example.ddddd.base.BaseActivity;
import com.example.ddddd.util.PayUtils;
import com.example.ddddd.util.DialogUtil.OnAlertSelectId;
import com.example.ddddd.util.VideoHistoryUtils;
import com.example.ddddd.vo.ProductInfoVO;

public class VideoHistoryActivity extends BaseActivity{

	private ListView listView;
	private VideoHistoryAdatper adapter;
	
	@Override
	public void initLayout() {
		setContentView(R.layout.activity_video_history);
	}

	@Override
	public void init() {
		
	}

	@Override
	public void initView() {
		listView = (ListView) findViewById(R.id.listView);
	}

	@Override
	public void initListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ProductInfoVO vo = (ProductInfoVO) adapter.getItem(position);
				if(vo != null){
					PayUtils.isShowPayDialog(context, vo);
				}
			}
		});
	}

	@Override
	public void initValue() {
		setNavigationTitle(getString(R.string.setting_history));
		
		adapter = new VideoHistoryAdatper(context, listView);
		listView.setAdapter(adapter);
		adapter.addLast(VideoHistoryUtils.getHistory(), false);
	}

	@Override
	public void reLoadView() {
		// TODO Auto-generated method stub
		
	}

}
