package com.example.ddddd.activity.fragment.adapter;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ddddd.R;
import com.example.ddddd.activity.DetailActivity;
import com.example.ddddd.base.BaseActivity;
import com.example.ddddd.base.ETSBaseAdapter;
import com.example.ddddd.util.PayUtils;
import com.example.ddddd.util.ViewHolderUtil;
import com.example.ddddd.util.DialogUtil.OnAlertSelectId;
import com.example.ddddd.vo.ProductInfoVO;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ChannelDataAdapter extends ETSBaseAdapter {

	public ChannelDataAdapter(BaseActivity context, AbsListView listView) {
		super(context, listView);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void loadData() {
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.item_channel_data, parent, false);
		}
		
		ImageView item_image_iv = ViewHolderUtil.get(convertView, R.id.item_image_iv);
		ImageView item_vip_iv = ViewHolderUtil.get(convertView, R.id.item_vip_iv);
		TextView item_name_tv = ViewHolderUtil.get(convertView, R.id.item_name_tv);
		TextView item_desc_tv = ViewHolderUtil.get(convertView, R.id.item_desc_tv);
		TextView item_tag_1_tv = ViewHolderUtil.get(convertView, R.id.item_tag_1_tv);
		TextView item_tag_2_tv = ViewHolderUtil.get(convertView, R.id.item_tag_2_tv);
		
		ProductInfoVO vo = (ProductInfoVO) getItem(position);
		
		if(vo != null){
			item_name_tv.setText(vo.getName());
			item_desc_tv.setText(vo.getMessage());
			ImageLoader.getInstance().displayImage(vo.getImgUrl(), item_image_iv, options, context.mApp.getAnimateFirstListener());
			if(vo.getTags().size() > 1){
				item_tag_1_tv.setVisibility(View.VISIBLE);
				item_tag_1_tv.setText(vo.getTags().get(1));
			}else{
				item_tag_1_tv.setVisibility(View.GONE);
			}
			if(vo.getTags().size() > 0){
				item_tag_2_tv.setVisibility(View.VISIBLE);
				item_tag_2_tv.setText(vo.getTags().get(0));
			}else{
				item_tag_2_tv.setVisibility(View.GONE);
			}
			if(vo.getAuthority() == 2){
				item_vip_iv.setVisibility(View.VISIBLE);
			}else{
				item_vip_iv.setVisibility(View.INVISIBLE);
			}
			
		}
		
		return convertView;
	}
}
