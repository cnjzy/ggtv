/**
 * 
 */
package cn.oneweone.video.widget.dialog;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListAdapter;

import cn.oneweone.video.widget.dialog.EtaoShiDialog.Builder;

/**
 * @ClassName: EtaoShiTipDialog 
 * @Description: TODO
 * @author sr
 * @date 2014-12-22 下午5:31:13 
 */
public class EtaoShiTipDialog extends Dialog{

    // ==========================================================================
    // Constants
    // ==========================================================================

    // ==========================================================================
    // Fields
    // ==========================================================================
    private Activity mActivity;
    protected EtaoShiTipDialogView mView;

    // ==========================================================================
    // Constructors
    // ==========================================================================
    public EtaoShiTipDialog(Activity activity) {
        super(activity);
        mActivity = activity;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        setCanceledOnTouchOutside(false);
    }

    // ==========================================================================
    // Getters
    // ==========================================================================
    public Activity getActivity() {
        return mActivity;
    }

    public EtaoShiTipDialogView getDialogView() {
        return mView;
    }

    // ==========================================================================
    // Setters
    // ==========================================================================

    // ==========================================================================
    // Methods
    // ==========================================================================
    public static class Builder {

        private Activity mActivity;

        private EtaoShiTipDialog mDialog;

        private DialogInterface.OnClickListener mPositiveClickListener;

        public Builder(Activity activity) {
            mActivity = activity;
            mDialog = new EtaoShiTipDialog(activity);
            mDialog.mView = new EtaoShiTipDialogView(mActivity);
            mDialog.mView.setPositiveButtonText(R.string.ok);
            mDialog.mView.setPositiveButtonListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    if (null != mPositiveClickListener) {
                        mPositiveClickListener.onClick(mDialog, DialogInterface.BUTTON_POSITIVE);
                    }
                }

            });
            mDialog.setContentView(mDialog.mView);
            mDialog.getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }

        public Builder setBackgroundColor(int resID) {
            mDialog.mView.setBackgroundColor(resID);
            return this;
        }

        // public Builder setTitleBackgroundColor(int color) {
        // mDialog.mView.setTitleBackgroundColor(color);
        // return this;
        // }
        //
        // public Builder setContainerBackgroundColor(int color) {
        // mDialog.mView.setContainerBackgroundColor(color);
        // return this;
        // }

        public Builder setBottomBackgroundColor(int color) {
            mDialog.mView.setBottomBackgroundColor(color);
            return this;
        }

        public Builder setTitle(int textResId) {
            mDialog.mView.setTitle(textResId);
            return this;
        }

        public Builder setTitle(CharSequence text) {
            if (null != text) {
                mDialog.mView.setTitle(text);
            }
            return this;
        }
        
        public Builder setTitleColor(int color) {
            mDialog.mView.setTitleTextColor(color);
            return this;
        }

        public Builder setTitleVisible(boolean b) {
            mDialog.mView.setTitleVisible(b);
            return this;
        }

        public Builder setButtonsVisible(boolean b) {
            mDialog.mView.setButtonsVisible(b);
            return this;
        }

        public Builder setPositiveButtonText(int textResId) {
            mDialog.mView.setPositiveButtonText(textResId);
            return this;
        }

        public Builder setPositiveButtonText(CharSequence text) {
            if (null != text) {
                mDialog.mView.setPositiveButtonText(text);
            }
            return this;
        }

        public Builder setPositiveButtonListener(DialogInterface.OnClickListener l) {
            mPositiveClickListener = l;
            return this;
        }

        public Builder setTextContent(int textResId) {
            mDialog.mView.setTextContent(textResId);
            return this;
        }

        public Builder setTextContent(int textResId, boolean scrollable) {
            mDialog.mView.setTextContent(textResId, scrollable);
            return this;
        }

        public Builder setTextContent(CharSequence text) {
            if (null != text) {
                mDialog.mView.setTextContent(text);
            }
            return this;
        }

        public Builder setTextContent(CharSequence text, boolean scrollable) {
            if (null != text) {
                mDialog.mView.setTextContent(text, scrollable);
            }
            return this;
        }

        public Builder setListContent(ListAdapter contentAdapter) {
            mDialog.mView.setListContent(contentAdapter);
            return this;
        }

        public Builder setContentView(View v) {
            mDialog.mView.setContentView(v);
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener l) {
            mDialog.setOnDismissListener(l);
            return this;
        }

        public EtaoShiTipDialog create() {
            return mDialog;
        }

    }
    // ==========================================================================
    // Inner/Nested Classes
    // ==========================================================================
}
