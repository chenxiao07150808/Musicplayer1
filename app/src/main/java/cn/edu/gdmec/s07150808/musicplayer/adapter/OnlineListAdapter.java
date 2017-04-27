package cn.edu.gdmec.s07150808.musicplayer.adapter;
import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import cn.edu.gdmec.s07150808.musicplayer.R;
import cn.edu.gdmec.s07150808.musicplayer.download.RandThread;
import cn.edu.gdmec.s07150808.musicplayer.download.ThreadPoolUtilsImg;
import cn.edu.gdmec.s07150808.musicplayer.entity.MusicInfo;
import cn.edu.gdmec.s07150808.musicplayer.util.ToastShow;


/**
 * 在线音乐自定义adapter
 * @author Wangyan
 *
 */
public class OnlineListAdapter extends BaseAdapter {
	// 上下文
	private Context context;
	// 数据源，实体类集合
	private ArrayList<MusicInfo> musicList;
	// 构造方法
	public OnlineListAdapter(Context context, ArrayList<MusicInfo> musicList) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.musicList = musicList;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return musicList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return musicList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.online_music_listview_item, null);
			holder.title = (TextView) convertView
					.findViewById(R.id.online_listitem_song_text);
			holder.artist = (TextView) convertView
					.findViewById(R.id.online_listitem_artist_text);
			holder.downloadCheck = (CheckBox) convertView
					.findViewById(R.id.online_listitem_down);
			convertView.setTag(holder);
			holder.downloadCheck.setTag(position);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Handler onlineHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case 1:
						ToastShow.toastShow(context, "开始下载");
						break;
					case 2:
						int pro = msg.arg1;
						// 弹出下载进度
						ToastShow.toastShow(context, "已下载" + pro + "%");
						break;
					case 3:
						ToastShow.toastShow(context, "下载完毕");
						break;

				}
				if (msg.arg1 == 0) {
				}
				super.handleMessage(msg);
			}
		};
		holder.title.setText(musicList.get(position).getTitle());
		holder.artist.setText(musicList.get(position).getArtist());
		// 点击开始下载文件
		holder.downloadCheck
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
												 boolean isChecked) {
						// TODO Auto-generated method stub
						int p = (Integer) buttonView.getTag();
						if (isChecked) {
							String HttpUrl = musicList.get(p).getPath();
							ThreadPoolUtilsImg
									.execute(new RandThread(HttpUrl,
											onlineHandler, musicList.get(p)
											.getTitle()));
							buttonView.setClickable(false);
						} else {
							buttonView.setClickable(true);
						}
					}
				});
		return convertView;
	}

	private class ViewHolder {
		TextView title;
		TextView artist;
		CheckBox downloadCheck;
	}
}
