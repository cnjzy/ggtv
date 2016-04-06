package cn.oneweone.video.receiver;

import java.io.File;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import cn.oneweone.video.util.DeviceUtil;
import cn.oneweone.video.util.DownloadManagerUtils;

public class DownloadReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
		if (reference == DownloadManagerUtils.getReference()) {
			// 对下载的文件进行一些操作
			DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
			Uri uri = downloadManager.getUriForDownloadedFile(reference);
			DeviceUtil.openFile(context, new File(uri.getPath()));
		}
	}
}
