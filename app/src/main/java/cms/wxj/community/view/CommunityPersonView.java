package cms.wxj.community.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import cms.wxj.community.utils.CommunityAnimation;

/**
 * Created by 54966 on 2018/6/28.
 */

public class CommunityPersonView extends View {

	private Paint				mPaint;

	private int					currentX;

	private int					currentY;

	private CommunityAnimation	communityAnimation;

	private int					mAnimationState	= CommunityAnimation.ANIM_LEFT;

	public CommunityPersonView(Context context) {
		this(context, null);
	}

	public CommunityPersonView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CommunityPersonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		mPaint = new Paint();
		communityAnimation = new CommunityAnimation();
	}

	@Override protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		communityAnimation.mHeroAnim[mAnimationState].drawAnimation(canvas, mPaint, currentX, currentY);
	}

	/***
	 * 设置小人
	 */
	public void setCommunityPersonType(int type) {
		communityAnimation.initAnimation(getContext(), type);
	}

}
