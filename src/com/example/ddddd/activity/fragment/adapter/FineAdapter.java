package com.example.ddddd.activity.fragment.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ddddd.R;
import com.example.ddddd.base.BaseActivity;
import com.example.ddddd.base.ETSBaseAdapter;
import com.example.ddddd.util.PayUtils;
import com.example.ddddd.util.ViewHolderUtil;
import com.example.ddddd.vo.ProductInfoVO;
import com.example.ddddd.widget.view.ScaleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class FineAdapter extends ETSBaseAdapter {

	public FineAdapter(BaseActivity context, AbsListView listView) {
		super(context, listView);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void loadData() {
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.item_fine, parent, false);
		}
		
		ScaleImageView item_image_iv = ViewHolderUtil.get(convertView, R.id.item_image_iv);
		ImageView item_vip_iv = ViewHolderUtil.get(convertView, R.id.item_vip_iv);
		TextView item_name_tv = ViewHolderUtil.get(convertView, R.id.item_name_tv);
		TextView item_count_tv = ViewHolderUtil.get(convertView, R.id.item_count_tv);
		
		ProductInfoVO vo = (ProductInfoVO) getItem(position);
		
		if(vo != null){
			item_name_tv.setText(vo.getName());
			item_count_tv.setText(String.valueOf(vo.getNum()));
			ImageLoader.getInstance().displayImage(vo.getImgUrl(), item_image_iv, options, context.mApp.animateFirstListener);
			if(vo.getAuthority() == 2){
				item_vip_iv.setVisibility(View.VISIBLE);
			}else{
				item_vip_iv.setVisibility(View.INVISIBLE);
			}
			
		}
		
		return convertView;
	}
}
