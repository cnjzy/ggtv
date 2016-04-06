/*
 * File Name: EtaoShiDialogView.java 
 * History:
 * Created by LiBingbing on 2013-9-6
 */
package cn.oneweone.video.widget.dialog;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import cn.oneweone.video.R;

public class EtaoShiDialogView extends RelativeLayout {
    // ==========================================================================
    // Constants
    // ==========================================================================

    // ==========================================================================
    // Fields
    // ==========================================================================
    private Activity mActivity;

    private LinearLayout mVisibleArea;

    private RelativeLayout mGrpTitle;

    private TextView mTxtTitle;

    private View mTitleDivider;
    private View mBottomDivider;

    private View mContent;

    private RelativeLayout mContainer;

    private LinearLayout mGrpButtons;

    private TextView mBtnPositive;

    private TextView mBtnNeutral;

    private TextView mBtnNegative;

    // ==========================================================================
    // Constructors
    // ==========================================================================
    public EtaoShiDialogView(Activity activity) {
        super(activity);
        mActivity = activity;
        initLayout();
    }

    // ==========================================================================
    // Getters
    // ==========================================================================

    public View getContentView() {
        return mContent;
    }

    // ==========================================================================
    // Setters
    // ==========================================================================

    // ==========================================================================
    // Methods
    // ==========================================================================
    private void initLayout() {
        RelativeLayout.LayoutParams rp;
        LinearLayout.LayoutParams lp;

        mVisibleArea = new LinearLayout(mActivity);
        mVisibleArea.setOrientation(LinearLayout.VERTICAL);
        rp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        rp.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(mVisibleArea, rp);

        // 标题栏
        mGrpTitle = new RelativeLayout(mActivity);
        mGrpTitle.setId(3);
        int padding = mActivity.getResources().getDimensionPixelSize(R.dimen.time_title_padding_top_bottom);
        mGrpTitle.setPadding(0, padding, 0, padding);
        lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
        mVisibleArea.addView(mGrpTitle, lp);
        // 标题文字
        mTxtTitle = new TextView(mActivity);
        mTxtTitle.setTextColor(mActivity.getResources().getColor(R.color.text_black));
        mTxtTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                mActivity.getResources().getDimensionPixelSize(R.dimen.time_title_textsize));
        mTxtTitle.setText(R.string.time_title_hint);
        rp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rp.addRule(RelativeLayout.CENTER_IN_PARENT);
        mGrpTitle.addView(mTxtTitle, rp);

        // 分隔线
        mTitleDivider = new View(mActivity);
        mTitleDivider.setId(4);
        mTitleDivider.setBackgroundColor(mActivity.getResources().getColor(R.color.order_textview_color_border));
        // mTitleDivider.setBackgroundResource(R.drawable.dlg_divider_tile);
        lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 1);
        // rp.addRule(RelativeLayout.BELOW, mGrpTitle.getId());
        mVisibleArea.addView(mTitleDivider, lp);

        mContainer = new RelativeLayout(mActivity);
        lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        lp.weight = 1;
        lp.setMargins(mActivity.getResources().getDimensionPixelSize(R.dimen.dlg_content_padding), mActivity
                .getResources().getDimensionPixelSize(R.dimen.dlg_title_bar_padding), mActivity.getResources()
                .getDimensionPixelSize(R.dimen.dlg_content_padding),
                mActivity.getResources().getDimensionPixelSize(R.dimen.dlg_title_bar_padding));
        mVisibleArea.addView(mContainer, lp);
        // 分隔线
        mBottomDivider = new View(mActivity);
        mBottomDivider.setId(7);
        mBottomDivider.setBackgroundColor(mActivity.getResources().getColor(R.color.order_textview_color_border));
        // mTitleDivider.setBackgroundResource(R.drawable.dlg_divider_tile);
        lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 1);
        // rp.addRule(RelativeLayout.BELOW, mGrpTitle.getId());
        mVisibleArea.addView(mBottomDivider, lp);
        // 底部按钮区域
        mGrpButtons = new LinearLayout(mActivity);
        mGrpButtons.setId(10);
        mGrpButtons.setOrientation(LinearLayout.HORIZONTAL);
        mGrpButtons.setGravity(Gravity.CENTER);
        padding = mActivity.getResources().getDimensionPixelSize(R.dimen.dlg_button_margin);
        mGrpButtons.setPadding(padding, mActivity.getResources().getDimensionPixelSize(R.dimen.dlg_title_bar_padding),
                padding, mActivity.getResources().getDimensionPixelSize(R.dimen.dlg_title_bar_padding));
        lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        // rp.addRule(RelativeLayout.BELOW, mGrpTitle.getId());
        mVisibleArea.addView(mGrpButtons, lp);

        // 左侧按钮
        int buttonWidth = mActivity.getResources().getDimensionPixelSize(R.dimen.dlg_2_button_width);
        int buttonHeight = mActivity.getResources().getDimensionPixelSize(R.dimen.dlg_button_height);
        mBtnNegative = new TextView(mActivity);
        mBtnNegative.setGravity(Gravity.CENTER);
        mBtnNegative.setTextColor(getResources().getColor(R.color.order_textview_color_normal_right));
        mBtnNegative.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                mActivity.getResources().getDimensionPixelSize(R.dimen.dlg_button_text_size));

        // LinearLayout.LayoutParams negativeParams = new LinearLayout.LayoutParams(buttonWidth,
        // LinearLayout.LayoutParams.WRAP_CONTENT);
        // mGrpButtons.setLayoutParams(negativeParams);

        // 中间按钮
        mBtnNeutral = new TextView(mActivity);
        mBtnNeutral.setVisibility(View.GONE);
        mBtnNeutral.setGravity(Gravity.CENTER);
        mBtnNeutral.setTextColor(mActivity.getResources().getColor(R.color.order_textview_color_normal_right));
        mBtnNeutral.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                mActivity.getResources().getDimensionPixelSize(R.dimen.dlg_button_text_size));
        // 分隔线
        View mTitleDividerHView = new View(mActivity);
        mTitleDividerHView.setBackgroundColor(mActivity.getResources().getColor(R.color.order_textview_color_border));
        // mTitleDivider.setBackgroundResource(R.drawable.dlg_divider_tile);
        android.widget.LinearLayout.LayoutParams lpHView = new LinearLayout.LayoutParams(1,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
        // 右侧按钮
        mBtnPositive = new TextView(mActivity);
        mBtnPositive.setGravity(Gravity.CENTER);
        mBtnPositive.setTextColor(mActivity.getResources().getColor(R.color.text_red));
        mBtnPositive.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                mActivity.getResources().getDimensionPixelSize(R.dimen.dlg_button_text_size));

        // LinearLayout.LayoutParams positiveParams = new LinearLayout.LayoutParams(buttonWidth,
        // LinearLayout.LayoutParams.WRAP_CONTENT);
        // mGrpButtons.setLayoutParams(positiveParams);
        // TODO 显示左右按钮状态
        // if (BuildOption.USE_ICS_FEATURE) {
        // lp = new LinearLayout.LayoutParams(buttonWidth, buttonHeight);
        // lp.weight = 0;
        // mGrpButtons.addView(mBtnNegative, lp);
        // lp = new LinearLayout.LayoutParams(buttonWidth, buttonHeight);
        // lp.weight = 0;
        // lp.leftMargin = padding;
        // mGrpButtons.addView(mBtnNeutral, lp);
        // lp = new LinearLayout.LayoutParams(buttonWidth, buttonHeight);
        // lp.weight = 0;
        // lp.leftMargin = padding;
        // mGrpButtons.addView(mBtnPositive, lp);
        // } else {

        padding = mActivity.getResources().getDimensionPixelSize(R.dimen.dlg_operation_btn_margin);

        lp = new LinearLayout.LayoutParams(buttonWidth, buttonHeight);
        lp.weight = 1;
        mGrpButtons.addView(mBtnNegative, lp);
        lp = new LinearLayout.LayoutParams(buttonWidth, buttonHeight);
        lp.weight = 1;
        lp.leftMargin = padding;
        mGrpButtons.addView(mBtnNeutral, lp);
        lp = new LinearLayout.LayoutParams(2, buttonHeight);
        mGrpButtons.addView(mTitleDividerHView, lp);
        lp = new LinearLayout.LayoutParams(buttonWidth, buttonHeight);
        lp.weight = 1;
        mGrpButtons.addView(mBtnPositive, lp);

        // }

        // // 分隔线
        // View buttonDivider = new View(mActivity);
        // buttonDivider.setId(R.id.dlg_button_bar_divider);
        // buttonDivider.setBackgroundResource(R.drawable.divider_dialog);
        // rp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 1);
        // rp.addRule(RelativeLayout.ABOVE, mGrpButtons.getId());
        // mVisibleArea.addView(buttonDivider, rp);
    }

    private void resetButtonsWidth() {
        int visibleButtons = 1 + (mBtnNegative.getVisibility() == View.GONE ? 0 : 1)
                + (mBtnNeutral.getVisibility() == View.GONE ? 0 : 1);

        int buttonWidth;
        if (visibleButtons == 3) {
            buttonWidth = mActivity.getResources().getDimensionPixelSize(R.dimen.dlg_3_button_width);
            mBtnPositive.getLayoutParams().width = buttonWidth;
            mBtnNeutral.getLayoutParams().width = buttonWidth;
            mBtnNegative.getLayoutParams().width = buttonWidth;
        } else if (visibleButtons == 2) {
            buttonWidth = mActivity.getResources().getDimensionPixelSize(R.dimen.dlg_2_button_width);
            mBtnPositive.getLayoutParams().width = buttonWidth;
            mBtnNeutral.getLayoutParams().width = buttonWidth;
            mBtnNegative.getLayoutParams().width = buttonWidth;
        } else {
            buttonWidth = mActivity.getResources().getDimensionPixelSize(R.dimen.dlg_1_button_width);
            mBtnPositive.getLayoutParams().width = buttonWidth;
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mBtnPositive.getLayoutParams();
            lp.leftMargin = 5;
            // mBtnNeutral.getLayoutParams().width = buttonWidth;
            // mBtnNegative.getLayoutParams().width = buttonWidth;
        }
    }

    public void setTitle(int textResId) {
        setTitle(mActivity.getString(textResId));
    }

    public void setTitle(CharSequence text) {
        if (mTxtTitle != null) {
            mTxtTitle.setText(text);
        }
    }
    
    // public void setTitleBackgroundColor(int color) {
    //
    // mGrpTitle.setBackgroundColor(color);
    // }
    //
    // public void setContainerBackgroundColor(int color) {
    //
    // mContainer.setBackgroundColor(color);
    // }

    public void setBottomBackgroundColor(int color) {

        mGrpButtons.setBackgroundColor(color);
    }

    public void setPositiveButtonText(int textResId) {
        setPositiveButtonText(mActivity.getString(textResId));
    }

    public void setPositiveButtonText(CharSequence text) {
        mBtnPositive.setText(text);
    }

    public void setPositiveButtonListener(OnClickListener l) {
        mBtnPositive.setOnClickListener(l);
    }

    public void setNeutralButtonVisible(boolean visible) {
        mBtnNeutral.setVisibility(visible ? View.VISIBLE : View.GONE);
        resetButtonsWidth();
    }

    public void setNeutralButtonText(int textResId) {
        setNeutralButtonText(mActivity.getString(textResId));
    }

    public void setNeutralButtonText(CharSequence text) {
        mBtnNeutral.setText(text);
    }

    public void setNeutralButtonListener(OnClickListener l) {
        mBtnNeutral.setOnClickListener(l);
    }

    public void setNegativeButtonVisible(boolean visible) {
        mBtnNegative.setVisibility(visible ? View.VISIBLE : View.GONE);
        resetButtonsWidth();
    }

    public void setNegativeButtonText(int textResId) {
        setNegativeButtonText(mActivity.getString(textResId));
    }

    public void setNegativeButtonTextColor(int textColor) {

        mBtnNegative.setTextColor(textColor);
    }

    public void setNegativeButtonText(CharSequence text) {
        mBtnNegative.setText(text);
        mBtnNegative.setVisibility(View.VISIBLE);
    }

    public void setNegativeButtonListener(OnClickListener l) {
        mBtnNegative.setOnClickListener(l);
    }

    public void setTitleVisible(boolean b) {
        int visibility = b ? View.VISIBLE : View.GONE;
        mGrpTitle.setVisibility(visibility);
        mTitleDivider.setVisibility(visibility);
    }

    public void setButtonsVisible(boolean b) {
        int visibility = b ? View.VISIBLE : View.GONE;
        mGrpButtons.setVisibility(visibility);
    }

    public void setButtonBarBackgroundResource(int resId) {
        mGrpButtons.setBackgroundResource(resId);
    }

    public void setButtonBarBackgroundDrawable(Drawable drawable) {
        mGrpButtons.setBackgroundDrawable(drawable);
    }

    public void setTitleTextColor(int color) {
        mTxtTitle.setTextColor(color);
    }

    public void setButtonTextColor(int color) {
        mBtnPositive.setTextColor(color);
        mBtnNegative.setTextColor(color);
    }

    public void setButtonResource(int resId) {
        mBtnPositive.setBackgroundResource(resId);
        mBtnNegative.setBackgroundResource(resId);
    }

    public void setButtonDrawable(int resId) {
        mBtnPositive.setBackgroundDrawable(mActivity.getResources().getDrawable(resId));
        mBtnNegative.setBackgroundDrawable(mActivity.getResources().getDrawable(resId));
    }

    public void setDivider(int resId) {
        mTitleDivider.setBackgroundResource(resId);
    }

    public void setTitleView(View v) {
        if (v == null) {
            return;
        }
        mGrpTitle.removeAllViews();
        mGrpTitle.getLayoutParams().height = LayoutParams.WRAP_CONTENT;
        ViewGroup.LayoutParams lp = v.getLayoutParams();
        if (!(lp instanceof RelativeLayout.LayoutParams)) {
            v.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }
        mGrpTitle.addView(v);
    }

    public void setContentView(final View v) {
        if (null != mContent) {
            mContainer.removeView(mContent);
        }

        mContent = v;
        if (mContent == null) {
            return;
        }

        mContent.setId(33);
        final RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT);
        mContainer.addView(mContent, rp);
    }

    public void setTextContent(int msgResId) {
        setTextContent(msgResId, false);
    }

    public void setTextContent(int msgResId, boolean scrollable) {
        setTextContent(mActivity.getString(msgResId), scrollable);
    }

    public void setTextContent(final CharSequence msg) {
        setTextContent(msg, false);
    }

    public void setTextContent(final CharSequence msg, boolean scrollable) {
        final TextView v = new TextView(mActivity);
        v.setTextColor(mActivity.getResources().getColor(R.color.text_black));
        v.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity.getResources().getDimensionPixelSize(R.dimen.sp_16));
        v.setGravity(Gravity.CENTER);
        int padding = mActivity.getResources().getDimensionPixelSize(R.dimen.global_item_height_small);
        v.setPadding(padding, padding, padding, padding);
        v.setText(msg);
        setContentView(v);

        if (scrollable) {
            // 文字过长时需要在外部套用一层ScrollView，但是由于添加ScrollView的效率较低，会导致Dialog弹出速度变慢，因此使用
            // postDelayed的方式在一小段时间之后再添加，可保证Dialog的弹出速度
            postDelayed(new Runnable() {

                @Override
                public void run() {
                    setContentView(null);
                    ScrollView scrollView = new ScrollView(getContext());
                    RelativeLayout container = new RelativeLayout(getContext());
                    container.addView(v, new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT));
                    scrollView.addView(container, new ScrollView.LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT));
                    setContentView(scrollView);
                }

            }, 300);
        }
    }

    public void setListContent(ListAdapter adapter) {
        ListView v = new ListView(mActivity);
        v.setCacheColorHint(0);
        v.setBackgroundColor(0);
        v.setAdapter(adapter);

        setContentView(v);
    }

    public void setWebContent(String msg) {
        WebView v = new WebView(mActivity);
        // v.loadData(msg, "text/html", "UTF-8");;
        v.loadDataWithBaseURL("", msg, "text/html", "utf-8", "");
        v.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                try {
                    mActivity.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                }
                return true;
            }
        });

        setContentView(v);
    }

    // ==========================================================================
    // Inner/Nested Classes
    // ==========================================================================
}
