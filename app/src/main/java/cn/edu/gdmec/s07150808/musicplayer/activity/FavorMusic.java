package cn.edu.gdmec.s07150808.musicplayer.activity;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import cn.edu.gdmec.s07150808.musicplayer.R;
import cn.edu.gdmec.s07150808.musicplayer.adapter.LocalListAdapter;
import cn.edu.gdmec.s07150808.musicplayer.constant.PlayerFinal;
import cn.edu.gdmec.s07150808.musicplayer.db.MusicDBHelper;
import cn.edu.gdmec.s07150808.musicplayer.entity.MusicInfo;
import cn.edu.gdmec.s07150808.musicplayer.service.PlayerService;
import cn.edu.gdmec.s07150808.musicplayer.util.ToastShow;


/**
 *
 * 我喜欢界面activity
 * @author Wangyan
 *
 */
public class FavorMusic extends Activity {
	// 显示歌曲列表的listview
	private ListView lvMusic;
	// 没有歌曲时显示的图片
	private ImageView nothingImg;
	// 自定义adapter
	private LocalListAdapter lvMusicAdapter;
	// 标题栏按钮
	private ImageButton back;
	// 数据库工具类
	private MusicDBHelper dBHelper;
	// 查询数据库的cursor，自定义adapter的数据源
	private Cursor curFav;
	// 用于传参数给service
	private ArrayList<MusicInfo> musicList;
	// 主线程接收到查询结果，适配adapter
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			curFav = (Cursor) msg.obj;
			if (curFav.getCount() != 0) {
				musicList = dBHelper.getMusicListFromLocal(curFav);
				nothingImg.setVisibility(View.GONE);
				lvMusic.setVisibility(View.VISIBLE);
				lvMusicAdapter = new LocalListAdapter(FavorMusic.this, curFav);
				View headView = getLayoutInflater().inflate(
						R.layout.favor_music_headview, null);
				lvMusic.addHeaderView(headView);
				lvMusic.setAdapter(lvMusicAdapter);
			} else {
				nothingImg.setVisibility(View.VISIBLE);
				lvMusic.setVisibility(View.GONE);
				ToastShow.toastShow(FavorMusic.this, "还没有喜欢的歌曲");
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favor_music);
		initListView();
		initButton();
	}

	/**
	 * 设置listview
	 */
	private void initListView() {
		// TODO Auto-generated method stub
		nothingImg = (ImageView) findViewById(R.id.favor_nothing_img);
		lvMusic = (ListView) findViewById(R.id.favor_listview);
		dBHelper = new MusicDBHelper(FavorMusic.this);
		new Thread() {
			public void run() {
				curFav = dBHelper.queryFavFromLocal();
				handler.sendMessage(handler.obtainMessage(0, curFav));
			};
		}.start();
		lvMusic.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				// TODO Auto-generated method stub
				// 点击item传一个musicList和当前点击位置给service
				// 发送相应广播给service
				int position = arg2 - 1;
				Intent intent = new Intent();
				intent.setAction(PlayerService.ACTION_PLAY_ITEM);
				intent.putParcelableArrayListExtra(PlayerFinal.PLAYER_LIST,
						musicList);
				intent.putExtra(PlayerFinal.PLAYER_POSITION, position);
				intent.putExtra(PlayerFinal.PLAYER_WHERE, "local");
				sendBroadcast(intent);
			}
		});
	}

	/**
	 * 设置按钮点击事件
	 */
	private void initButton() {
		// TODO Auto-generated method stub
		back = (ImageButton) findViewById(R.id.favor_actionbar_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		// 关闭cursor和数据库
		curFav.close();
		dBHelper.close();
		super.onDestroy();
	}
}
