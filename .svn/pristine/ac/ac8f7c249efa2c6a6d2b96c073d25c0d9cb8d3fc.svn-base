package com.example.ddddd.util;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

import com.example.ddddd.R;

public class DownloadManagerUtils {
	private static long reference;
	
	public static long getReference(){
		return reference;
	}
	
	public static void downloadApk(Context context, String url) {
		if (url == null)
			return;
		DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
		request.setTitle(context.getString(R.string.app_name));
		request.setDescription(context.getString(R.string.app_name));
		request.setMimeType("application/vnd.android.package-archive");
		request.setDestinationInExternalPublicDir("down", "ggtv.apk");
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		reference = downloadManager.enqueue(request);
	}
}
