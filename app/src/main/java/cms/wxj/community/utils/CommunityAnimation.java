package cms.wxj.community.utils;

import android.content.Context;

import com.cn.jyz.ironshoes.R;


/**
 * Created by 54966 on 2018/6/28.
 */

public class CommunityAnimation {

    /**
     * 向左移动动画
     **/
    public final static int	ANIM_LEFT		= 0;

    /**
     * 向右移动动画
     **/
    public final static int	ANIM_RIGHT		= 1;

    /**
     * 动画的总数量
     **/
    public final static int	ANIM_COUNT		= 2;

    public final static int	PERSON_TYPE_NPC	= 11;							// npc小人

    public final static int	PERSON_TYPE_0	= 0;

    public final static int	PERSON_TYPE_1	= 1;

    public final static int	PERSON_TYPE_2	= 2;

    public final static int	PERSON_TYPE_3	= 3;

    public final static int	PERSON_TYPE_4	= 4;

    public GameAnimation	mHeroAnim[]		= new GameAnimation[ANIM_COUNT];

    public void initAnimation(Context context, int personType) {
        // 这里可以用循环来处理总之我们需要把动画的ID传进去
        if (personType == PERSON_TYPE_NPC) {
            mHeroAnim[ANIM_LEFT] = new GameAnimation(context, new int[] { R.mipmap.img_person_npc_left_nv_1, R.mipmap.img_person_npc_left_nv_2 }, true);
            mHeroAnim[ANIM_RIGHT] = new GameAnimation(context, new int[] { R.mipmap.img_person_npc_right_nv_1, R.mipmap.img_person_npc_right_nv_1 }, true);
        } else if (personType == PERSON_TYPE_0) {
            mHeroAnim[ANIM_LEFT] = new GameAnimation(context, new int[] { R.mipmap.img_person_hire_2_left_nv_1, R.mipmap.img_person_hire_2_left_nv_2 }, true);
            mHeroAnim[ANIM_RIGHT] = new GameAnimation(context, new int[] { R.mipmap.img_person_hire_2_right_nv_1, R.mipmap.img_person_hire_2_right_nv_2 }, true);
        } else if (personType == PERSON_TYPE_1) {
            mHeroAnim[ANIM_LEFT] = new GameAnimation(context, new int[] { R.mipmap.img_person_hire_3_left_nv_1, R.mipmap.img_person_hire_3_left_nv_2 }, true);
            mHeroAnim[ANIM_RIGHT] = new GameAnimation(context, new int[] { R.mipmap.img_person_hire_3_right_nv_1, R.mipmap.img_person_hire_3_right_nv_2 }, true);
        } else if (personType == PERSON_TYPE_2) {
            mHeroAnim[ANIM_LEFT] = new GameAnimation(context, new int[] { R.mipmap.img_person_hire_4_left_nv_1, R.mipmap.img_person_hire_4_left_nv_2 }, true);
            mHeroAnim[ANIM_RIGHT] = new GameAnimation(context, new int[] { R.mipmap.img_person_hire_4_right_nv_1, R.mipmap.img_person_hire_4_right_nv_2 }, true);
        } else if (personType == PERSON_TYPE_3) {
            mHeroAnim[ANIM_LEFT] = new GameAnimation(context, new int[] { R.mipmap.img_person_hire_5_left_nv_1, R.mipmap.img_person_hire_5_left_nv_2 }, true);
            mHeroAnim[ANIM_RIGHT] = new GameAnimation(context, new int[] { R.mipmap.img_person_hire_5_right_nv_1, R.mipmap.img_person_hire_5_right_nv_2 }, true);
        } else if (personType == PERSON_TYPE_4) {
            mHeroAnim[ANIM_LEFT] = new GameAnimation(context, new int[] { R.mipmap.img_person_hire_6_left_nv_1, R.mipmap.img_person_hire_6_left_nv_2 }, true);
            mHeroAnim[ANIM_RIGHT] = new GameAnimation(context, new int[] { R.mipmap.img_person_hire_6_right_nv_1, R.mipmap.img_person_hire_6_right_nv_2 }, true);
        }
    }

}
