package cms.wxj.community.bean;

import java.util.List;

/**
 * 
 * 社区小人实体(机器人npc+雇佣小人) Created by 54966 on 2018/6/15.
 */

public class CommunityPersonBean {

	public static final int		PERSON_TYPE_NPC		= 0;	// npc小人

	public static final int		PERSON_TYPE_HIRE	= 1;	// 雇佣小人

	public int					mId;						// 自己的id

	public int					posX;

	public int					posY;

	public int					left;

	public int					top;

	public int					width;

	public int					height;

	public int					personType;					// 0:npc小人 1:雇佣小人

	public String				wxHeadicon;					// 微信头像

	public boolean				clicked;					// true:可点击 false:不可点击

	public boolean				showHeadView;				// true:显示小人头上view false:不显示

}
