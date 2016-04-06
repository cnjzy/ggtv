package cn.oneweone.video.activity.adapter;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import cn.oneweone.video.R;
import cn.oneweone.video.base.BaseActivity;
import cn.oneweone.video.base.ETSBaseAdapter;
import cn.oneweone.video.util.VideoHistoryUtils;
import cn.oneweone.video.util.ViewHolderUtil;
import cn.oneweone.video.vo.ProductInfoVO;

public class VideoHistoryAdatper extends ETSBaseAdapter{

	public VideoHistoryAdatper(BaseActivity context, AbsListView listView) {
		super(context, listView);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void loadData() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.item_video_history, parent, false);
		}
		
		TextView item_name_tv = ViewHolderUtil.get(convertView, R.id.item_name_tv);
		ImageView item_remove_iv = ViewHolderUtil.get(convertView, R.id.item_remove_iv);
		
		final ProductInfoVO vo = (ProductInfoVO) getItem(position);
		if(vo != null){
			item_name_tv.setText(vo.getName());
			item_remove_iv.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					VideoHistoryUtils.removeHistory(vo);
					remove(position);
				}
			});
		}
		
		return convertView;
	}

}
