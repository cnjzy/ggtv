package cn.oneweone.video.activity.fragment.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import cn.oneweone.video.R;
import cn.oneweone.video.base.BaseActivity;
import cn.oneweone.video.base.ETSBaseAdapter;
import cn.oneweone.video.util.ViewHolderUtil;
import cn.oneweone.video.vo.ChannelVO;

public class ChannelCategoryAdapter extends ETSBaseAdapter {

	private int selectIndex = 0;
	
	public ChannelCategoryAdapter(BaseActivity context, AbsListView listView) {
		super(context, listView);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void loadData() {
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.item_channel_category, null);
		}
		
		ImageView item_icon_iv = ViewHolderUtil.get(convertView, R.id.item_icon_iv);
		TextView item_name_tv = ViewHolderUtil.get(convertView, R.id.item_name_tv);
		
		ChannelVO vo = (ChannelVO) getItem(position);
		if(vo != null){
			item_name_tv.setText(vo.getChannel_name());
		}
		if(position == selectIndex){
			item_icon_iv.setVisibility(View.VISIBLE);
			item_name_tv.setBackgroundColor(context.getResources().getColor(R.color.background));
		}else{
			item_icon_iv.setVisibility(View.INVISIBLE);
			item_name_tv.setBackgroundColor(context.getResources().getColor(R.color.text_white));
		}
		
		
		return convertView;
	}
	
	public void setSelected(int select) {
        this.selectIndex = select;
        handler.post(new Runnable() {
            public void run() {
                notifyDataSetChanged();
            }
        });
    }
}
