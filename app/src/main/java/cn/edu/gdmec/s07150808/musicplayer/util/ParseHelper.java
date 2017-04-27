package cn.edu.gdmec.s07150808.musicplayer.util;

import java.io.IOException;
import java.util.ArrayList;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import cn.edu.gdmec.s07150808.musicplayer.constant.PlayerFinal;
import cn.edu.gdmec.s07150808.musicplayer.entity.MusicInfo;


/**
 * 在线音乐获取帮助类
 * @author Wangyan
 *
 */
public class ParseHelper {
	// ip
	public static final String IP = "http://192.168.2.128:8080";
	/**
	 * 得到在线音乐实体类集合
	 * @param curPage 页数
	 * @return ArrayList<MusicInfo> 返回该页实体类集合
	 */
	public ArrayList<MusicInfo> getMusicListByPage(int curPage) {

		String uri = IP + "/MusicPage/music?curPage=" + curPage;
		// 生成一个请求对象，参数网址
		HttpGet request = new HttpGet(uri);
		// 得到一个客户端的对象
		HttpClient client = new DefaultHttpClient();
		try {
			// 得到响应的对象
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				/**
				 * 因为直接调用toString可能会导致某些中文字符出现乱码的情况。所以此处使用toByteArray
				 * 如果需要转成String对象，可以先调用EntityUtils.toByteArray()方法将消息实体转成byte数组，
				 * 在由new String(byte[] bArray)转换成字符串。
				 */
				byte[] bResultXml = EntityUtils.toByteArray(response
						.getEntity());
				if (bResultXml != null) {
					String strXml = new String(bResultXml, "utf-8");
					// json的解析
					JSONObject json = new JSONObject(strXml);
					Log.e(PlayerFinal.TAG, "strXml---->" + strXml);
					// jsonObj，得到JSONArray
					JSONArray array = (JSONArray) json.get("music");
					Log.e(PlayerFinal.TAG,
							" array.length---->" + array.length());
					ArrayList<MusicInfo> musicList = new ArrayList<MusicInfo>();
					for (int i = 0; i < array.length(); i++) {
						// 每次循环，返回一个json对象
						JSONObject obj = (JSONObject) array.get(i);
						MusicInfo m = new MusicInfo();
						m.setId(obj.getInt("id"));
						m.setTitle(obj.getString("mp3Name"));
						m.setArtist(obj.getString("mp3Artist"));
						m.setAlbum_img_path(obj.getString("mp3Image"));
						String path = IP + "/res/music/"
								+ obj.getString("mp3FileName");
						Log.e(PlayerFinal.TAG, "path---->" + path);
						m.setPath(path);
						m.setAlbum(obj.getString("mp3Album"));
						m.setSize(obj.getLong("mp3Size"));
						m.setDuration(obj.getLong("mp3Duration"));
						m.setLyric_file_name(obj.getString("mp3FileName").replace(".mp3", ".lrc"));
						musicList.add(m);
					}
					Log.e(PlayerFinal.TAG, "musicList---->" + musicList.size());
					return musicList;
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	/**
	 * 得到在线音乐总页数
	 * @return int 页面总数
	 */
	public int getMusicTotalPage() {
		String uri = IP + "/MusicPage/music?curPage=" + 0;
		HttpGet request = new HttpGet(uri);
		HttpClient client = new DefaultHttpClient();
		try {
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				/**
				 * 因为直接调用toString可能会导致某些中文字符出现乱码的情况。所以此处使用toByteArray
				 * 如果需要转成String对象，可以先调用EntityUtils.toByteArray()方法将消息实体转成byte数组，
				 * 在由new String(byte[] bArray)转换成字符串。
				 */
				byte[] bResultXml = EntityUtils.toByteArray(response
						.getEntity());
				if (bResultXml != null) {
					String strXml = new String(bResultXml, "utf-8");
					JSONObject json = new JSONObject(strXml);
					int totalPage = json.getInt("totalPage");
					Log.e(PlayerFinal.TAG, "bResultXml"+totalPage);
					return totalPage;
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}
}
