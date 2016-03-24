package com.example.ddddd.activity.fragment.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ddddd.R;
import com.example.ddddd.activity.DetailActivity;
import com.example.ddddd.activity.MoreActivity;
import com.example.ddddd.activity.VideoPlayerActivity;
import com.example.ddddd.base.BaseActivity;
import com.example.ddddd.base.ETSBaseAdapter;
import com.example.ddddd.util.DialogUtil.OnAlertSelectId;
import com.example.ddddd.util.PayUtils;
import com.example.ddddd.util.ViewHolderUtil;
import com.example.ddddd.vo.ProductInfoVO;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HomeAdapter extends ETSBaseAdapter{

	private int count = 0;
	private List<ProductInfoVO> mDataList = new ArrayList<ProductInfoVO>();
	
	public HomeAdapter(BaseActivity context, AbsListView listView) {
		super(context, listView);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void loadData() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.item_home_list, parent, false);
		}
		
		LinearLayout item_title_bar_ll = ViewHolderUtil.get(convertView, R.id.item_title_bar_ll);
		ImageView item_title_icon_iv = ViewHolderUtil.get(convertView, R.id.item_title_icon_iv);
		TextView item_title_tv = ViewHolderUtil.get(convertView, R.id.item_title_tv);
		TextView item_more_tv = ViewHolderUtil.get(convertView, R.id.item_more_tv);
		
		RelativeLayout image_layout_left = ViewHolderUtil.get(convertView, R.id.image_layout_left);
		ImageView item_image_iv = ViewHolderUtil.get(convertView, R.id.item_image_iv);
		ImageView item_vip_iv = ViewHolderUtil.get(convertView, R.id.item_vip_iv);
		TextView item_tag_1_tv = ViewHolderUtil.get(convertView, R.id.item_tag_1_tv);
		TextView item_tag_2_tv = ViewHolderUtil.get(convertView, R.id.item_tag_2_tv);
		TextView item_name_tv = ViewHolderUtil.get(convertView, R.id.item_name_tv);
		
		RelativeLayout image_layout_right = ViewHolderUtil.get(convertView, R.id.image_layout_right);
		ImageView item_image_right_iv = ViewHolderUtil.get(convertView, R.id.item_image_right_iv);
		ImageView item_vip_right_iv = ViewHolderUtil.get(convertView, R.id.item_vip_right_iv);
		TextView item_tag_1_right_tv = ViewHolderUtil.get(convertView, R.id.item_tag_1_right_tv);
		TextView item_tag_2_right_tv = ViewHolderUtil.get(convertView, R.id.item_tag_2_right_tv);
		TextView item_name_right_tv = ViewHolderUtil.get(convertView, R.id.item_name_right_tv);
		
		final ProductInfoVO vo = (ProductInfoVO) getItem(position*2);
		final ProductInfoVO nextVo = (ProductInfoVO) getItem(position*2+1);
		ProductInfoVO preVO = (ProductInfoVO) getItem(position*2-1);
		if(vo != null){
			if(position == 0 || !vo.getChannel_name().equals(preVO.getChannel_name())){
				item_title_bar_ll.setVisibility(View.VISIBLE);
				item_title_tv.setText(vo.getChannel_name());
				item_more_tv.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Bundle data = new Bundle();
						data.putSerializable("vo", vo);
						BaseActivity.showActivity(context, MoreActivity.class, data);
					}
				});
			}else{
				item_title_bar_ll.setVisibility(View.GONE);
			}
			
			item_name_tv.setText(vo.getName());
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
			
			image_layout_left.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					PayUtils.isShowPayDialog(context, vo);
				}
			});
		}
		
		if(nextVo != null && nextVo.getChannel_name().equals(vo.getChannel_name())){
			image_layout_right.setVisibility(View.VISIBLE);
			
			item_name_right_tv.setText(nextVo.getName());
			ImageLoader.getInstance().displayImage(nextVo.getImgUrl(), item_image_right_iv, options);
			if(nextVo.getTags().size() > 1){
				item_tag_1_right_tv.setVisibility(View.VISIBLE);
				item_tag_1_right_tv.setText(nextVo.getTags().get(1));
			}else{
				item_tag_1_right_tv.setVisibility(View.GONE);
			}
			if(nextVo.getTags().size() > 0){
				item_tag_2_right_tv.setVisibility(View.VISIBLE);
				item_tag_2_right_tv.setText(nextVo.getTags().get(0));
			}else{
				item_tag_2_right_tv.setVisibility(View.GONE);
			}
			if(nextVo.getAuthority() == 2){
				item_vip_right_iv.setVisibility(View.VISIBLE);
			}else{
				item_vip_right_iv.setVisibility(View.INVISIBLE);
			}
			image_layout_right.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					PayUtils.isShowPayDialog(context, nextVo);
				}
			});
		}else{
			image_layout_right.setVisibility(View.INVISIBLE);
		}
		
		
		return convertView;
	}
	
	@Override
	public void addLast(List list) {
		for (int i = 0; list != null && i < list.size(); i++) {
			ProductInfoVO vo = (ProductInfoVO) list.get(i);
			for (int j = 0; j < vo.getContent().size(); j++) {
				ProductInfoVO bean = new ProductInfoVO();
				bean.setChannel_name(vo.getChannel_name());
				bean.setMore(vo.getMore());
				bean.setAction(vo.getContent().get(j).getAction());
				bean.setAuthority(vo.getContent().get(j).getAuthority());
				bean.setImgUrl(vo.getContent().get(j).getImgUrl());
				bean.setName(vo.getContent().get(j).getName());
				bean.setTarget(vo.getContent().get(j).getTarget());
				bean.setTags(vo.getContent().get(j).getTags());
				bean.setTestUrl(vo.getContent().get(j).getTestUrl());
				bean.setTestSecond(vo.getContent().get(j).getTestSecond());
				bean.setVideoUrl(vo.getContent().get(j).getVideoUrl());
//				if(!bean.getChannel_name().equals("试播专区")){
					mDataList.add(bean);
//				}
			}
		}
		System.out.println("mDataList.size():" + mDataList.size());
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDataList.size() / 2 + (mDataList.size() % 2 == 0 ? 0 : 1);
	}

	 @Override
	    public Object getItem(int position) {
	        if (position >= 0 && position < mDataList.size())
	            return mDataList.get(position);
	        else
	            return null;
	    }
}
