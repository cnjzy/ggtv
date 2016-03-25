package com.example.ddddd.activity;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.ddddd.MyApp;
import com.example.ddddd.R;
import com.example.ddddd.base.BaseActivity;
import com.example.ddddd.common.Constants;
import com.example.ddddd.util.DialogUtil;
import com.example.ddddd.util.DialogUtil.OnAlertSelectId;
import com.example.ddddd.util.VideoHistoryUtils;
import com.example.ddddd.util.preference.Preferences;
import com.example.ddddd.vo.ProductInfoVO;
import com.example.ddddd.widget.dialog.CustomLoadingDialog;

public class VideoPlayerActivity extends BaseActivity implements
		MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
	public static final String TAG = "VideoPlayer";
	private VideoView mVideoView;
	private TextView video_count_down_tv;
	private Uri mUri;
	private int mPositionWhenPaused = -1;

	private MediaController mMediaController;

	private ProductInfoVO vo;
	
	private CountDownTimer contDownTimer;

	private boolean isTest =  false;
	private boolean isPlayer = true;
	
	private MediaPlayer mediaPlayer;
	
	@Override
	public void initLayout() {
		setContentView(R.layout.activity_video);
	}

	@Override
	public void init() {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); 
		// Set the screen to landscape.
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		vo = (ProductInfoVO) getIntent().getExtras().getSerializable("vo");
		if (vo == null || (TextUtils.isEmpty(vo.getVideoUrl()) && TextUtils.isEmpty(vo.getTestUrl()))) {
			Toast.makeText(this, "影片地址错误，我们会尽快处理", Toast.LENGTH_LONG).show();
			finish();
			return;
		}
		
		if(!TextUtils.isEmpty(vo.getTestUrl())){
			isTest = true;
		}

		VideoHistoryUtils.addHistory(vo);
		if(!isFinishing()){
			loadingDialog = new CustomLoadingDialog(context, "正在加载，请耐心等待", true);
			loadingDialog.show();
		}
	}

	@Override
	public void initView() {
		mVideoView = (VideoView) findViewById(R.id.video_view);
		video_count_down_tv = (TextView) findViewById(R.id.video_count_down_tv);
	}

	@Override
	public void initListener() {
		mVideoView.setOnPreparedListener(new OnPreparedListener() {
			public void onPrepared(MediaPlayer mp) {
				mediaPlayer = mp;
				mp.setVolume(1, 1);
				dismissLoading();
				if(isTest){
					video_count_down_tv.setVisibility(View.VISIBLE);
					int second = Integer.parseInt(vo.getTestSecond());
		            contDownTimer = new CountDownTimer((second-1) * 1000, 1000) {  
		                public void onTick(long millisUntilFinished) {
		                	video_count_down_tv.setText("试播还剩：" + millisUntilFinished/1000 + "秒");
		                }  
		                public void onFinish() {  
		                    if(!isFinishing() && contDownTimer != null){
		                    	video_count_down_tv.setText("试播还剩：0秒");
		                    	mVideoView.pause();
		                    	isPlayer = false;
		                    	if(MyApp.preferencesUtils.getInt(Preferences.USER_STATUS, Constants.MEMBER_TYPE_IS_NOT) == 0){
		                    		DialogUtil.showPay3Dialog(context, new OnAlertSelectId(){
		                				public void onClick(int pay_type, Object o) {
		                					int amount = Constants.VIP_TENURE;
		                					int member_type = Constants.MEMBER_TYPE_IS_TENURE;
		                					context.getOrder(amount, pay_type, member_type);
		                				}
		                			}, Constants.VIP_TENURE, true);
		                    	}
		                    }
		                }  
		             }.start(); 
				}
			}
		});

	}

	@Override
	public void initValue() {
		// Create media controller，组件可以控制视频的播放，暂停，回复，seek等操作，不需要你实现
		if(!TextUtils.isEmpty(vo.getTestUrl()) && !TextUtils.isEmpty(vo.getTestSecond())){
			// Video file
			mUri = Uri.parse(vo.getTestUrl());
		}else{
			// Video file
			mUri = Uri.parse(vo.getVideoUrl());
			mMediaController = new MediaController(this);
			mVideoView.setMediaController(mMediaController);
		}
	}

	@Override
	public void reLoadView() {

	}

	public void onStart() {
		if(isPlayer){
			showLoading();
			// Play Video
			mVideoView.setVideoURI(mUri);
			mVideoView.start();
		}

		super.onStart();
	}

	public void onResume() {
		if(isPlayer){
			// Resume video player
			if (mPositionWhenPaused >= 0) {
				mVideoView.seekTo(mPositionWhenPaused);
				mPositionWhenPaused = -1;
			}
		}

		super.onResume();
	}

	public void onPause() {
		if(mVideoView != null && mVideoView.isPlaying()){
			// Stop video when the activity is pause.
			try{
				mPositionWhenPaused = mVideoView.getCurrentPosition();
				mVideoView.stopPlayback();
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		super.onPause();
	}

	@Override
	protected void onDestroy() {
		dismissLoading();
		super.onDestroy();
	}

	public boolean onError(MediaPlayer player, int arg1, int arg2) {
		return false;
	}

	public void onCompletion(MediaPlayer mp) {
	}

	private void showLoading() {
		if (loadingDialog != null && !loadingDialog.isShowing()) {
			loadingDialog.show();
		}
	}

	private void dismissLoading() {
		if (loadingDialog != null && loadingDialog.isShowing()) {
			loadingDialog.dismiss();
		}
	}
}
