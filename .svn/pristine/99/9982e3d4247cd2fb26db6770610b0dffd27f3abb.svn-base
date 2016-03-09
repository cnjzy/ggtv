/*
 * File Name: EtaoShiDialog.java 
 * History:
 * Created by LiBingbing on 2013-9-16
 */
package com.example.ddddd.widget.dialog;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ListAdapter;

public class EtaoShiDialog extends Dialog {

    // ==========================================================================
    // Constants
    // ==========================================================================

    // ==========================================================================
    // Fields
    // ==========================================================================
    private Activity mActivity;
    protected EtaoShiDialogView mView;

    // ==========================================================================
    // Constructors
    // ==========================================================================
    public EtaoShiDialog(Activity activity) {
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

    public EtaoShiDialogView getDialogView() {
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

        private EtaoShiDialog mDialog;

        private DialogInterface.OnClickListener mPositiveClickListener;

        private DialogInterface.OnClickListener mNeutralClickListener;

        private DialogInterface.OnClickListener mNegativeClickListener;

        public Builder(Activity activity) {
            mActivity = activity;
            mDialog = new EtaoShiDialog(activity);
            mDialog.mView = new EtaoShiDialogView(mActivity);
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
            mDialog.mView.setNeutralButtonText("dd");
            mDialog.mView.setNeutralButtonListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    if (null != mNeutralClickListener) {
                        mNeutralClickListener.onClick(mDialog, DialogInterface.BUTTON_NEUTRAL);
                    }
                }

            });
            mDialog.mView.setNeutralButtonVisible(false);
            mDialog.mView.setNegativeButtonText(R.string.cancel);
            mDialog.mView.setNegativeButtonListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    if (null != mNegativeClickListener) {
                        mNegativeClickListener.onClick(mDialog, DialogInterface.BUTTON_NEGATIVE);
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

        public Builder setNeutralButtonVisible(boolean visible) {
            mDialog.mView.setNeutralButtonVisible(visible);
            return this;
        }

        public Builder setNegativeButtonTextColor(int textColor) {

            mDialog.mView.setNegativeButtonTextColor(textColor);

            return this;
        }

        public Builder setNeutralButtonText(int textResId) {
            mDialog.mView.setNeutralButtonText(textResId);
            return this;
        }

        public Builder setNeutralButtonText(CharSequence text) {
            if (null != text) {
                mDialog.mView.setNeutralButtonText(text);
            }
            return this;
        }

        public Builder setNeutralButtonListener(DialogInterface.OnClickListener l) {
            mNeutralClickListener = l;
            return this;
        }

        public Builder setNegativeButtonVisible(boolean visible) {
            mDialog.mView.setNegativeButtonVisible(visible);
            return this;
        }

        public Builder setNegativeButtonText(int textResId) {
            mDialog.mView.setNegativeButtonText(textResId);
            return this;
        }

        public Builder setNegativeButtonText(CharSequence text) {
            if (null != text) {
                mDialog.mView.setNegativeButtonText(text);
            }
            return this;
        }

        public Builder setNegativeButtonListener(DialogInterface.OnClickListener l) {
            mNegativeClickListener = l;
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

        public EtaoShiDialog create() {
            return mDialog;
        }

    }
    // ==========================================================================
    // Inner/Nested Classes
    // ==========================================================================
}
