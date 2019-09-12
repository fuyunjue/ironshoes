package cms.wxj.community.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.InputStream;

/**
 * Created by 54966 on 2018/6/28.
 */

public class GameAnimation {

	private Bitmap[]	mFrameBitmap	= null;

	private boolean		isLoop;					// 是否循环

	private boolean		misEnd;					// 是否结束

	private int			mPlayID;				// 播放id

	private long		mLastTime;				// 上次播放时间

	private int			ANIM_TIME		= 100;	// 动画播放间隙时间

	private int			mFrameCount;			// 总共几张图

	public GameAnimation(Context context, int[] mFrameBitmapResId, boolean isLoop) {
		this.isLoop = isLoop;
		mFrameCount = mFrameBitmapResId.length;

		mFrameBitmap = new Bitmap[mFrameCount];
		for (int i = 0; i < mFrameCount; i++) {
			mFrameBitmap[i] = readBitmap(context, mFrameBitmapResId[i]);
		}
	}

	/***
	 * 绘制图片
	 * 
	 * @param canvas
	 * @param paint
	 * @param x
	 * @param y
	 */
	public void drawAnimation(Canvas canvas, Paint paint, int x, int y) {
		if (!misEnd) {
			canvas.drawBitmap(mFrameBitmap[mPlayID], x, y, paint);
			long time = System.currentTimeMillis();
			if (time - mLastTime > ANIM_TIME) {
				mPlayID++;
				mLastTime = time;
				if (mPlayID >= mFrameCount) {
					misEnd = true;
					if (isLoop) {
						misEnd = false;
						mPlayID = 0;
					}
				}
			}
		}

	}

	/***
	 * 读取图片
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public Bitmap readBitmap(Context context, int resId) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		InputStream inputStream = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(inputStream, null, options);
	}

}
