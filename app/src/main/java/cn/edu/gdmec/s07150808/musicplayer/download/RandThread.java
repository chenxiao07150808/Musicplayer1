package cn.edu.gdmec.s07150808.musicplayer.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import cn.edu.gdmec.s07150808.musicplayer.constant.PlayerFinal;


/**
 * 下载音乐线程
 * @author Wangyan
 *
 */
public class RandThread extends Thread {

	private String mHttpUrl;
	private Handler hand;
	private String fileName;

	public RandThread(String mHttpUrl, Handler hand, String fileName) {

		this.mHttpUrl = mHttpUrl;
		this.hand = hand;
		this.fileName = fileName;
	}

	public void run() {
		if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
			hand.sendMessage(hand.obtainMessage(1));
		} else {
			Log.e(PlayerFinal.TAG, "sd卡错误");
		}
		String mNewFile = Environment.getExternalStorageDirectory().toString()
				+ "/" + fileName + ".mp3";
		try {
			URL url = new URL(mHttpUrl);
			HttpURLConnection httpc = (HttpURLConnection) url.openConnection();
			httpc.setConnectTimeout(15 * 1000);
			httpc.connect();
			int totalSize = httpc.getContentLength();
			int length = 0;
			int curSize = 0;
			int progress = 0;
			InputStream istream = httpc.getInputStream();
			File file = new File(mNewFile);
			file.createNewFile();
			OutputStream output = new FileOutputStream(file);
			byte[] buffer = new byte[1024 * 4];
			while ((length = istream.read(buffer)) != -1) {
				output.write(buffer, 0, length);
				Thread.sleep(2000);
				curSize = curSize + length;
				progress = (int) ((float) curSize / (float) totalSize * 100.0f);
				hand.sendMessage(hand.obtainMessage(2, progress, 0));
				Log.e(PlayerFinal.TAG, "progress------>" + progress
						+ "totalSize------->" + totalSize);
			}
			hand.sendMessage(hand.obtainMessage(3));
			output.flush();
			output.close();
			istream.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
