package cn.edu.gdmec.s07150808.musicplayer.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import cn.edu.gdmec.s07150808.musicplayer.activity.MainActivity;


/**
 * �Զ���ؼ�
 * @author Wangyan
 *
 */
public class MyHSV extends HorizontalScrollView {


	private int currentOffset = 0;
	// ��õ�ǰwindow�Ŀ��
	private int sumWidth;

	private int btnWidth;
	private int txtArgWidth;
	private int appWidth;

	public static float deX;

	public MyHSV(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * ����
	 * 
	 * @param context
	 */
	void init(Context context) {
		// remove the fading as the HSV looks better without it
		setHorizontalFadingEdgeEnabled(false);// ɾ��������Ӱ����
		setVerticalFadingEdgeEnabled(false);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		currentOffset = (int) ev.getRawX();// ���������
		sumWidth = this.getMeasuredWidth();// ��õ�ǰwindow�Ŀ��
		// ���Scroll��������������λ��
		MainActivity.offset = computeHorizontalScrollOffset();
		System.out.println("~~~" + currentOffset + "~~" + sumWidth + "--"
				+ btnWidth + "~" + computeHorizontalScrollOffset());
		if (MainActivity.offset == 0) {// û�л���
			if (currentOffset <= (sumWidth - btnWidth)) {
				return false;// Do not allow touch events.
			} else {
//				comEvent(ev);
				return super.onTouchEvent(ev);
			}
		} else {
			return super.onTouchEvent(ev);
		}
	}

	private void comEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float x = 0;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x = event.getX();
			break;
		case MotionEvent.ACTION_UP:
			break;
		case MotionEvent.ACTION_MOVE:
			float preX = x;
			float nowX = event.getX();
			int deltaX = (int) (preX - nowX);
			if (Math.abs(deltaX) < 150) {
				smoothScrollBy(deltaX, 0);
			}
			break;
		}
	}

	public void setBtnWith(int btnWidth) {
		this.btnWidth = btnWidth;
	}

	public void setTxtArgWidth(int txtArgWidth) {
		this.txtArgWidth = txtArgWidth;
	}

	public void setAppWidth(int appWidth) {
		this.appWidth = appWidth;
	}
}
