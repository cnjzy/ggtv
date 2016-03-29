package com.example.ddddd.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.ddddd.R;
import com.example.ddddd.base.BaseActivity;
import com.example.ddddd.common.Constants;
import com.example.ddddd.widget.dialog.EtaoShiDialog;
import com.example.ddddd.widget.dialog.EtaoShiTipDialog;
import com.umeng.analytics.MobclickAgent;

public class DialogUtil {
	private static Dialog dialog;
	
    public interface OnAlertSelectId {
        void onClick(int whichButton, Object o);
    }

    /**
     * 无网络Dilaog
     * 
     * @param context
     */
    public static Dialog showSettingWIFIDialog(final Context context) {
        EtaoShiDialog.Builder builder = new EtaoShiDialog.Builder((Activity) context);

        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_network_error, null);
        TextView descTv = (TextView) view.findViewById(R.id.dialog_desc_tv);
        descTv.setText(R.string.not_network_content);

        builder.setContentView(view);
        // title 标题
        builder.setTitle(R.string.not_network_title);
        builder.setTitleColor(context.getResources().getColor(R.color.text_red));
        // 背景色
        builder.setBackgroundColor(context.getResources().getColor(R.color.text_white));
        builder.setBottomBackgroundColor(context.getResources().getColor(R.color.text_white));

        builder.setPositiveButtonText(R.string.not_network_submit);
        builder.setNegativeButtonText(R.string.not_network_cancel);
        builder.setNegativeButtonTextColor(context.getResources().getColor(R.color.order_textview_color_normal_right));
        builder.setPositiveButtonListener(new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButtonListener(new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
//                if (!(context instanceof MainActivity) && !(context instanceof HomeActivity)
//                        && !(context instanceof OrderActivity) && !(context instanceof SettingActivity))
//                    ((BaseActivity) context).goBack();
            }
        });

        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

    public static Dialog showDoubleBtnTipDialog(final Context context, final DialogInterface.OnClickListener listener,
            String tipContent) {
        EtaoShiDialog.Builder builder = new EtaoShiDialog.Builder((Activity) context);

        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_network_error, null);
        TextView descTv = (TextView) view.findViewById(R.id.dialog_desc_tv);
        descTv.setText(tipContent);

        builder.setContentView(view);
        // title 标题
        builder.setTitle(R.string.app_name);
        builder.setTitleVisible(false);
        builder.setTitleColor(context.getResources().getColor(R.color.text_red));
        // 背景色
        builder.setBackgroundColor(context.getResources().getColor(R.color.text_white));
        builder.setBottomBackgroundColor(context.getResources().getColor(R.color.text_white));

        builder.setPositiveButtonText(R.string.btn_submit);
        builder.setNegativeButtonText(R.string.not_network_cancel);
        builder.setNegativeButtonTextColor(context.getResources().getColor(R.color.order_textview_color_normal_right));
        builder.setPositiveButtonListener(listener);

        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }
    
    public static Dialog showDoubleBtnTipDialog(final Context context, final DialogInterface.OnClickListener listener,
            String title, String tipContent) {
        EtaoShiDialog.Builder builder = new EtaoShiDialog.Builder((Activity) context);

        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_network_error, null);
        TextView descTv = (TextView) view.findViewById(R.id.dialog_desc_tv);
        descTv.setText(tipContent);

        builder.setContentView(view);
        // title 标题
        builder.setTitle(title);
        builder.setTitleVisible(true);
        builder.setTitleColor(context.getResources().getColor(R.color.text_red));
        // 背景色
        builder.setBackgroundColor(context.getResources().getColor(R.color.text_white));
        builder.setBottomBackgroundColor(context.getResources().getColor(R.color.text_white));

        builder.setPositiveButtonText(R.string.btn_submit);
        builder.setNegativeButtonText(R.string.btn_cancel);
        builder.setNegativeButtonTextColor(context.getResources().getColor(R.color.order_textview_color_normal_right));
        builder.setPositiveButtonListener(listener);

        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

    public static Dialog showTipDialog(final Context context, final DialogInterface.OnClickListener listener,
            String tipContent) {
        EtaoShiTipDialog.Builder builder = new EtaoShiTipDialog.Builder((Activity) context);

        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_network_error, null);
        TextView descTv = (TextView) view.findViewById(R.id.dialog_desc_tv);
        descTv.setText(tipContent);

        builder.setContentView(view);
        // title 标题
        builder.setTitle(R.string.app_name);
        builder.setTitleColor(context.getResources().getColor(R.color.text_red));
        // 背景色
        builder.setBackgroundColor(context.getResources().getColor(R.color.text_white));
        builder.setBottomBackgroundColor(context.getResources().getColor(R.color.text_white));

        builder.setPositiveButtonText(R.string.btn_submit);
        builder.setPositiveButtonListener(listener);

        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }
    
    /**
     * 弹出系统提示框
     * @param context
     * @param title
     * @param tipContent
     * @return
     */
    public static Dialog showSystemTipDialog(final Activity context, String title, 
    		String tipContent, final OnAlertSelectId onClickListener){
    	final Dialog dialog = new AlertDialog.Builder(context)
    	.setTitle(title)
    	.setMessage(tipContent)
    	.setNegativeButton("确定", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if(onClickListener != null){
					MobclickAgent.onEvent(context, "first");
					onClickListener.onClick(0, null);
					
				}
				dialog.cancel();
			}
		})
    	.create();
    	
    	DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    	WindowManager.LayoutParams lp = new LayoutParams();
    	lp.width = (int) (displayMetrics.widthPixels * 0.8);
    	lp.height = (int) (displayMetrics.heightPixels * 0.75);
    	lp.horizontalMargin = 0;
    	lp.verticalMargin = 0;
    	lp.dimAmount= 0.8f;
    	
    	
    	dialog.show();
    	dialog.setCancelable(false);
    	dialog.setCanceledOnTouchOutside(false);
    	dialog.getWindow().setAttributes(lp);
    	
    	return dialog;
    }

    public static Dialog showTipDialog(final Activity context, String tipContent) {
        return showTipDialog(context, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        }, tipContent);
    }
    
    /**
     * 支付1
     * @param context
     * @return
     */
    public static Dialog showPay1Dialog(final BaseActivity context, final OnAlertSelectId onClickListener) {
    	UMengUtils.addShowPay40Dialog(context);
    	final Dialog mDialog = new Dialog(context, R.style.custom_dialog);
    	dialog = mDialog;
    	mDialog.setContentView(R.layout.dialog_pay1);

        Button pay_btn = (Button) mDialog.findViewById(R.id.pay_btn);
        Button close_btn = (Button) mDialog.findViewById(R.id.close_btn);

        close_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mDialog.cancel();
			}
		});
        
        pay_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mDialog.cancel();
				if(onClickListener != null)
					onClickListener.onClick(0, Constants.VIP_YEAR_TO_TENURE);
			}
		});
        
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        
        // 设置居中
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        mDialog.show();
        return mDialog;
    }
    
    /**
     * 支付2
     * @param context
     * @return
     */
    public static Dialog showPay2Dialog(final BaseActivity context, final OnAlertSelectId onClickListener) {
        UMengUtils.addShowPayDialog(context);
    	final Dialog mDialog = new Dialog(context, R.style.custom_dialog);
    	dialog = mDialog;
    	mDialog.setContentView(R.layout.dialog_pay2);

        Button pay_year_btn = (Button) mDialog.findViewById(R.id.pay_year_btn);
        Button pay_all_btn = (Button) mDialog.findViewById(R.id.pay_all_btn);
        ImageButton close_btn = (ImageButton) mDialog.findViewById(R.id.close_btn);

        close_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mDialog.cancel();
			}
		});
        
        pay_year_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mDialog.cancel();
				if(onClickListener != null)
					onClickListener.onClick(0, Constants.VIP_YEAR);
			}
		});
        pay_all_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mDialog.cancel();
				if(onClickListener != null)
					onClickListener.onClick(0, Constants.VIP_TENURE);
			}
		});
        
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        
        // 设置居中
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        mDialog.show();
        return mDialog;
    }
    
    
    /**
     * 支付3
     * @param context
     * @return
     */
    public static Dialog showPay3Dialog(final BaseActivity context, final OnAlertSelectId onClickListener, final int amount) {
    	return showPay3Dialog(context, onClickListener, amount, false);
    }
    /**
     * 支付3
     * @param context
     * @return
     */
    public static Dialog showPay3Dialog(final BaseActivity context, final OnAlertSelectId onClickListener, final int amount, final boolean isBack) {
    	UMengUtils.addShowPayDialog(context);
    	final Dialog mDialog = new Dialog(context, R.style.custom_dialog);
    	dialog = mDialog;
    	mDialog.setContentView(R.layout.dialog_pay3);

        TextView pay_amount_tv = (TextView) mDialog.findViewById(R.id.pay_amount_tv);
        TextView member_type_tv = (TextView) mDialog.findViewById(R.id.member_type_tv);
        final RadioButton pay_radio_alipay_rb = (RadioButton) mDialog.findViewById(R.id.pay_radio_alipay_rb);
        RadioButton pay_radio_wx_rb = (RadioButton) mDialog.findViewById(R.id.pay_radio_wx_rb);
        Button pay_btn = (Button) mDialog.findViewById(R.id.pay_btn);
        Button close_btn = (Button) mDialog.findViewById(R.id.close_btn);

        pay_amount_tv.setText(String.valueOf(amount));
        if(amount == Constants.VIP_TENURE){
        	member_type_tv.setText("包年会员");
        }else if(amount == Constants.VIP_YEAR){
        	member_type_tv.setText("包月会员");
        }else if(amount == Constants.VIP_YEAR_TO_TENURE){
        	member_type_tv.setText("升级包年");
        }
        
        close_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mDialog.cancel();
				if(isBack){
					context.finish();
				}
			}
		});
        
        pay_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
//				mDialog.cancel();
				int type = pay_radio_alipay_rb.isChecked() ? 1 : 2;
				if(type == 1){
					UMengUtils.addClickAlipay(context);
				}else{
					UMengUtils.addClickWechat(context);
				}
				if(onClickListener != null)
					onClickListener.onClick(type, amount);
			}
		});
        
        mDialog.setCanceledOnTouchOutside(false);
        if(isBack){
	        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if(keyCode == event.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
						context.finish();
					}
					return false;
				}
			});
        }
//        mDialog.setCancelable(false);
        
        // 设置居中
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        mDialog.show();
        return mDialog;
    }
    
    public static void cannelDialog(){
    	if(dialog != null && dialog.isShowing()){
    		dialog.cancel();
    	}
    }
}
