package cn.oneweone.video.activity;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import cn.oneweone.video.R;
import cn.oneweone.video.base.BaseActivity;
import cn.oneweone.video.util.UMengUtils;

public class SplashActivity extends BaseActivity{
	private ImageView ivWelcome;
	private AlphaAnimation animation = new AlphaAnimation(1.0f, 1.0f);
	
	private void showNextActivity(){
//	    if(preferencesUtils.getBoolean(Preferences.FIRST_START, true)){
//	        showActivity(context, GuideActivity.class);
//	    }else if(mUserInfo != null && !TextUtils.isEmpty(mUserInfo.getPassword())){
//            showActivity(context, MainActivity.class);
//	    }else{
	        showActivity(context, MainActivity.class);
//	    }
	    finish();
	}

	@Override
	public void init() {
		UMengUtils.addShowSplash(context);
	    animation.setDuration(1000);
        animation.setFillAfter(true);
        animation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                
            }
            
            @Override
            public void onAnimationRepeat(Animation animation) {
                
            }
            
            @Override
            public void onAnimationEnd(Animation animation) {
                showNextActivity();
            }
        });
	}

	@Override
	public void initView() {
	    ivWelcome = (ImageView)findViewById(R.id.iv_welcome);
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initValue() {
	    ivWelcome.startAnimation(animation);		
	}

    @Override
    public void initLayout() {
        setContentView(R.layout.activity_splash);
    }

	@Override
	public void reLoadView() {
		// TODO Auto-generated method stub
		
	}

}
