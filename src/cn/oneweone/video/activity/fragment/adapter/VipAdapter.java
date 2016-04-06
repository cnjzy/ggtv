package cn.oneweone.video.activity.fragment.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import cn.oneweone.video.R;
import cn.oneweone.video.base.BaseActivity;
import cn.oneweone.video.base.ETSBaseAdapter;
import cn.oneweone.video.util.PayUtils;
import cn.oneweone.video.util.ViewHolderUtil;
import cn.oneweone.video.vo.ProductInfoVO;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class VipAdapter extends ETSBaseAdapter {

	public VipAdapter(BaseActivity context, AbsListView listView) {
		super(context, listView);
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.f20_opening_member_bg)
		.showImageForEmptyUri(R.drawable.f20_opening_member_bg)
		.showImageOnFail(R.drawable.f20_opening_member_bg)
		.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
		.build();
		
	}

	@Override
	public void loadData() {
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.item_vip, parent, false);
		}
		
		ImageView item_image_iv = ViewHolderUtil.get(convertView, R.id.item_image_iv);
		ImageView item_cover_iv = ViewHolderUtil.get(convertView, R.id.item_cover_iv);
		ImageView item_vip_iv = ViewHolderUtil.get(convertView, R.id.item_vip_iv);
		ImageView item_lock_iv = ViewHolderUtil.get(convertView, R.id.item_lock_iv);
		TextView item_name_tv = ViewHolderUtil.get(convertView, R.id.item_name_tv);
		
		ProductInfoVO vo = (ProductInfoVO) getItem(position);
		
		if(vo != null){
			item_name_tv.setText(vo.getName());
			ImageLoader.getInstance().displayImage(vo.getImgUrl(), item_image_iv, options, context.mApp.getAnimateFirstListener());
			if(vo.getAuthority() == 2){
				item_vip_iv.setVisibility(View.VISIBLE);
			}else{
				item_vip_iv.setVisibility(View.INVISIBLE);
			}
			
			if(PayUtils.isPermission(vo.getAuthority())){
				item_lock_iv.setVisibility(View.GONE);
				item_vip_iv.setVisibility(View.GONE);
			}else{
				item_lock_iv.setVisibility(View.VISIBLE);
				item_vip_iv.setVisibility(View.VISIBLE);
			}
		}
		
		return convertView;
	}
}
