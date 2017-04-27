package cn.edu.gdmec.s07150808.musicplayer.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
/**
 * 网络工具类
 * @author Wangyan
 *
 */
public class NetWorkHelper {
	/**
	 * 检查网络是否连接
	 * @param mContext
	 * @return boolean 有可用网络，返回true
	 */
	public static boolean isNetOpen(Context mContext) {

		try {
			ConnectivityManager conManager = (ConnectivityManager) mContext
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo Info = conManager.getActiveNetworkInfo();
			boolean Inet = conManager.getNetworkInfo(
					ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
			if (!Inet) {
				// 提示用户使用Wifi
				ToastShow.toastShow(mContext, "建议你使用wifi节省流量！");
				return true;
			} else if (Info == null || !Info.isAvailable()) {
				// 当前网络不可用
				ToastShow.toastShow(mContext, "网络未连接，请连接网络！");
				return false;
			} else if (Info.isConnected()) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;

	}

	/**
	 * 判断WIFI是否打开
	 * @param context
	 * @return boolean wifi打开返回true
	 */
	public static boolean isWifiEnabled(Context context) {
		ConnectivityManager mgrConn = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		TelephonyManager mgrTel = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return ((mgrConn.getActiveNetworkInfo() != null && mgrConn
				.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel
				.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
	}

	/**
	 * 判断是否3G网络
	 * @param context
	 * @return boolean 3G网络连接可用返回true
	 */
	public static boolean is3G(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null
				&& networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否wifi网络
	 * @param context
	 * @return boolean wifi网络连接可用返回true
	 */
	public static boolean isWifi(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null
				&& networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	/**
	 * 判断网络是否可用 Judge whether the network is available
	 *
	 * @return Boolean 有网络可用返回true
	 */
	public static Boolean isNetWorkAvaliable(Context context) {
		// TODO Auto-generated method stub
		ConnectivityManager connect = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connect == null) {
			return false;
		} else {
			NetworkInfo[] info = connect.getAllNetworkInfo();
			for (int i = 0; i < info.length; i++) {
				if (info[i].getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
			return false;
		}
	}
}
