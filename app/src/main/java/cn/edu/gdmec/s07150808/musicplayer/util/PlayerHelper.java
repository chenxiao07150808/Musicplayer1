package cn.edu.gdmec.s07150808.musicplayer.util;

import java.io.IOException;
import java.util.Random;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;

import cn.edu.gdmec.s07150808.musicplayer.constant.PlayerFinal;
import cn.edu.gdmec.s07150808.musicplayer.service.PlayerService;


/**
 * ���Ÿ���������
 * 
 * @author Wangyan
 * 
 */
public class PlayerHelper {

	private static MediaPlayer myMedia = getMyMedia();

	private static MediaPlayer getMyMedia() {
		if (myMedia == null) {
			myMedia = new MediaPlayer();
		}
		return myMedia;
	}

	public void playInternet(Context context, Uri uri) {
		myMedia.reset();
		myMedia.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			myMedia.setDataSource(context, uri);
			myMedia.prepare();
			myMedia.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ���ź���
	 */
	public void play(String path) {
		try {
			myMedia.reset();
			myMedia.setAudioStreamType(AudioManager.STREAM_MUSIC);
			myMedia.setDataSource(path);
			myMedia.prepare();
			myMedia.start();
			myMedia.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					// ����������ϣ����ݲ���ģʽѡ����һ�ײ��Ÿ�����position
					// ����ģʽ��service�д��
					// ���������б��λ�ö���service�У�����ֱ�Ӹ���service�е�position��state
					switch (PlayerService.mode) {
					// ����ѭ��
					case PlayerFinal.MODE_SINGLE:
						myMedia.setLooping(true);
						break;
					// ȫ��ѭ��
					case PlayerFinal.MODE_LOOP:
						if (PlayerService.servicePosition == PlayerService.serviceMusicList
								.size() - 1) {
							PlayerService.servicePosition = 0;
						} else {
							PlayerService.servicePosition++;
						}
						PlayerService.state = PlayerFinal.STATE_PLAY;
						break;
					// �������
					case PlayerFinal.MODE_RANDOM:
						Random random = new Random();
						int p = PlayerService.servicePosition;
						while (true) {
							PlayerService.servicePosition = random
									.nextInt(PlayerService.serviceMusicList
											.size());
							if (p != PlayerService.servicePosition) {
								PlayerService.state = PlayerFinal.STATE_PLAY;
								break;
							}
						}
						break;
					// ˳�򲥷�
					case PlayerFinal.MODE_ORDER:
						if (PlayerService.servicePosition == PlayerService.serviceMusicList
								.size() - 1) {
							PlayerService.state = PlayerFinal.STATE_STOP;
						} else {
							PlayerService.servicePosition++;
							PlayerService.state = PlayerFinal.STATE_PLAY;
						}
						break;
					}
					PlayerService.stateChange = true;
				}
			});
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ��ͣ����
	 */
	public void pause() {
		myMedia.pause();
	}

	/**
	 * ������������
	 */
	public void continuePlay() {
		myMedia.start();// ������������
	}

	/**
	 * ����ֹͣ
	 */
	public void stop() {
		myMedia.stop();// ����ֹͣ
	}

	/**
	 * �õ�������ǰ����λ��
	 * 
	 * @return int ����ʱ��
	 */
	public int getPlayCurrentTime() {
		return myMedia.getCurrentPosition();
	}

	/**
	 * �õ�����ʱ��
	 * 
	 * @return int ����ʱ��
	 */
	public int getPlayDuration() {
		return myMedia.getDuration();
	}

	/**
	 * ָ������λ��
	 */
	public void seekToMusic(int seek) {
		myMedia.seekTo(seek);// ָ��λ��
		myMedia.start();// ��ʼ����
	}

	/**
	 * �жϵ�ǰ�Ƿ��ڲ���
	 * 
	 * @return
	 */
	public Boolean isPlaying() {
		return myMedia.isPlaying();
	}

}
