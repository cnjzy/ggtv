package cn.oneweone.video.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import cn.oneweone.video.R;
import cn.oneweone.video.activity.adapter.VideoHistoryAdatper;
import cn.oneweone.video.base.BaseActivity;
import cn.oneweone.video.util.PayUtils;
import cn.oneweone.video.util.DialogUtil.OnAlertSelectId;
import cn.oneweone.video.util.VideoHistoryUtils;
import cn.oneweone.video.vo.ProductInfoVO;

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
