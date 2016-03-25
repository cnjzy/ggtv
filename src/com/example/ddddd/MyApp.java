package com.example.ddddd;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.BaseJson;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ddddd.util.StringUtil;
import com.example.ddddd.util.preference.Preferences;
import com.example.ddddd.util.preference.PreferencesUtils;
import com.example.ddddd.vo.UserInfoVO;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class MyApp extends Application {
	private static Context context;

	/**
	 * 登陆用户
	 */
	private UserInfoVO mUserInfo;

	/**
	 * 配置文件信息
	 */
	public static PreferencesUtils preferencesUtils;

	/**
	 * 图片加载参数选项
	 */
	public static DisplayImageOptions options;

	/**
	 * 图片第一次加载动画
	 */
	public ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	@Override
	public void onCreate() {
		super.onCreate();

		context = this.getApplicationContext();

		// 初始化加载图片
		initImageLoader(getApplicationContext());

		// 本地化
		preferencesUtils = new PreferencesUtils(getApplicationContext(), Preferences.USER_INFO_FILE);
		mUserInfo = (UserInfoVO) (BaseJson.parser(new TypeToken<UserInfoVO>() {
		}, preferencesUtils.getString(Preferences.PREFERENSE_USER_INFO, "")));

	}

	/**
	 * 初始化图片加载
	 * 
	 * @param context
	 */
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator()).diskCacheSize(50 * 1024 * 1024) // 50
																										// Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				// .writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);

		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.f20_video_bg_w460_h300).showImageForEmptyUri(R.drawable.f20_video_bg_w460_h300)
				.showImageOnFail(R.drawable.f20_video_bg_w460_h300).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				// .displayer(new RoundedBitmapDisplayer(20))
				.build();

	}

	public DisplayImageOptions getDefaultImageLoaderOptions() {
		return options;
	}

	public UserInfoVO getUserInfo() {
		return mUserInfo;
	}

	public void setUserInfo(UserInfoVO mUserInfo) {
		this.mUserInfo = mUserInfo;
		preferencesUtils.putString(Preferences.PREFERENSE_USER_INFO, BaseJson.toJson(mUserInfo));
	}

	public static void showToast(String msg) {
		Toast toast = Toast.makeText(context, (!StringUtil.isEmpty(msg)) ? msg : "", Toast.LENGTH_SHORT);
		toast.show();
	}

	public ImageLoadingListener getAnimateFirstListener() {
		return animateFirstListener;
	}

	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
