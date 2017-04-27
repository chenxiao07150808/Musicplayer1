package cn.edu.gdmec.s07150808.musicplayer.service;

import java.util.List;

import cn.edu.gdmec.s07150808.musicplayer.entity.MusicInfo;


public interface OnPlayerStateChangeListener {
	void onStateChange(int state, int mode, List<MusicInfo> musicList,
					   int position);
}
