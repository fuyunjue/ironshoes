package com.cn.jyz.ironshoes.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGImageView;
import com.cn.jyz.ironshoes.R;
import android.util.DisplayMetrics;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class SvgLayout extends RelativeLayout {
	// 屏幕宽高
	private int screenHeight;
	private int screenWidth;
	private long lastMultiTouchTime;// 记录多点触控缩放后的时间
	private ScaleGestureDetector mScaleGestureDetector = null;
	// private View view;
	private int downX;//手指按下的x坐标值
	private int downY;//手指按下的y坐标值
	private boolean needToHandle=true;
	private int moveX;
	private int moveY;
	private float scale;//缩放比例
	private float preScale = 1f;// 默认前一次缩放比例为1
	private Context ctx = null;
	
	//实际图片与地图尺寸比例
	private float mapRote = 0;
	//地图的高和宽
	private float mapWidth=0,mapHeight=0;
	//地图移动变量
	private float lastx=0,lasty=0;
	//图标集合
	private Map<String,SvgIcon> iconMap = null;
	//事件委托
	private SvgIconOnClick click = null;
	
	public SvgLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}
	public SvgLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public SvgLayout(Context context) {
		super(context);
		init(context);
	}
	private void init(Context context) {
		ctx = context;
	}
	
	public void initOutLayout(String svgPath,int mMapHeight,float  svgWidth,float svgHeight,int BgColor){
		try {
			//缩放控件初始化
			mScaleGestureDetector = new ScaleGestureDetector(ctx,new ScaleGestureListener());
			
			//设备屏幕参数获取
			DisplayMetrics dm = getResources().getDisplayMetrics();
			Log.e("initOutLayout","设备屏幕:"+(dm.widthPixels)+","+dm.heightPixels);
			screenWidth = dm.widthPixels;
			screenHeight = mMapHeight;
			Log.e("initOutLayout","控件高宽:"+screenWidth+","+screenHeight);
			
			//计算设置地图的整体高宽
			mapRote = mMapHeight/svgHeight;
			mapWidth = mapRote*svgWidth;
			mapHeight = mMapHeight;
			Log.e("initOutLayout","地图高宽:"+mapWidth+","+mapHeight);
			LinearLayout.LayoutParams layoutParams = new  LinearLayout.LayoutParams((int)mapWidth,(int)mapHeight);
			this.setLayoutParams(layoutParams);
			
			//Svg地图加载
			SVG svg = SVG.getFromAsset(getResources().getAssets(), svgPath);
			SVGImageView view = new SVGImageView(ctx);
			view.setSVG(svg);
			view.setBackgroundColor(BgColor);
			this.addView(view,new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
			
			//this.addView(CreateIconJc("001",400,292));
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void  AddOrRefIcons(List<SvgIcon> list){
		if(iconMap==null) iconMap = new HashMap<String, SvgIcon>();
		Map<String,String> delMaps = new HashMap<String, String>();
		for(int i=0;i<list.size();i++){
			SvgIcon icon = list.get(i);
			if(iconMap.containsKey(icon.getIconTag())){
				//更新
				SvgIcon mapIcon = iconMap.get(icon.getIconTag());
				UpdateIcon(mapIcon);
			}
			else{
				//新增图标
				SvgIcon mapIcon = AddIcon(icon);
				iconMap.put(mapIcon.getIconTag(),mapIcon);
			}
			delMaps.put(icon.getIconTag(),"");
		}
		
		//不在List中的控件进行删除处理
		Set<Map.Entry<String, SvgIcon>> set = iconMap.entrySet();
		for (Map.Entry<String, SvgIcon> me : set) {
			if(!delMaps.containsKey(me.getKey())){
				this.removeView(me.getValue().img);
				this.removeView(me.getValue().vText);
				iconMap.remove(me);
			}
		}
	}
	OnClickListener svgIconListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			try {
				SvgIcon icon = iconMap.get(view.getTag().toString());
				Log.e("svgIconListener", "点击：" + view.getTag().toString());
				if (click != null) click.onClick(icon, view);
			}
			catch (Exception ex){
				ex.printStackTrace();
			}
		}
	};
	public void  setOnClick(SvgIconOnClick mClick){
		click = mClick;
	}
	
	public SvgIcon AddIcon(SvgIcon icon){
		ImageView img = new ImageView(ctx);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)(icon.getWidth()*mapRote),(int)(icon.getHeight()*mapRote));
		layoutParams.leftMargin=(int)(icon.getLeft()*mapRote);
		layoutParams.topMargin=(int)(icon.getTop()*mapRote);
		img.setLayoutParams(layoutParams);//设置图片宽高
		img.setImageResource(icon.getIconResourceId()); //图片资源
		img.setTag(icon.getIconTag());
		this.addView(img);
		img.setOnClickListener(svgIconListener);
		icon.img = img;
		
		if(icon.getText()!=null && icon.getText().length()>0){
			//增加文本显示
			TextView tv = new TextView(ctx);
			RelativeLayout.LayoutParams txtParams = new RelativeLayout.LayoutParams((int)(icon.getWidth()*mapRote*0.6),(int)(icon.getHeight()*mapRote*0.5));
			txtParams.leftMargin=(int)(icon.getLeft()*mapRote + icon.getWidth()*mapRote*0.41);
			txtParams.topMargin=(int)(icon.getTop()*mapRote +  icon.getHeight()*mapRote * 0.1);
			tv.setLayoutParams(txtParams);//设置图片宽高
			tv.setText(icon.getText());
			tv.setTag(icon.getIconTag());
			tv.setTextSize((int)(icon.getHeight()*mapRote*0.2));
			tv.setTextColor(0xFFFFFFFF);
			this.addView(tv);
			
			icon.vText = tv;
			tv.setOnClickListener(svgIconListener);
		}
		return icon;
	}
	public SvgIcon UpdateIcon(SvgIcon icon){
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)(icon.getWidth()*mapRote),(int)(icon.getHeight()*mapRote));
		layoutParams.leftMargin=(int)(icon.getLeft()*mapRote);
		layoutParams.topMargin=(int)(icon.getTop()*mapRote);
		icon.img.setLayoutParams(layoutParams);//设置图片宽高
		icon.img.setImageResource(icon.getIconResourceId()); //图片资源
		if(icon.getText()!=null && icon.getText().length()>0) icon.vText.setText(icon.getText());
		return  icon;
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		try {
			super.onInterceptTouchEvent(ev);
			return false;
		}
		catch (Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int pointerCount = event.getPointerCount(); // 获得多少点
		if (pointerCount > 1) {
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					needToHandle=false;
					break;
				case MotionEvent.ACTION_MOVE:
					break;
				case MotionEvent.ACTION_POINTER_2_UP://第二个手指抬起的时候
					lastMultiTouchTime = System.currentTimeMillis();
					needToHandle=true;
					break;
				default:
					break;
			}
			//让mScaleGestureDetector处理触摸事件
			return mScaleGestureDetector.onTouchEvent(event);
		} else {
			long currentTimeMillis = System.currentTimeMillis();
			Log.e("currentTimeMillis","currentTimeMillis:"+(currentTimeMillis - lastMultiTouchTime)+" "+needToHandle+" "+preScale);
			if (currentTimeMillis - lastMultiTouchTime > 200 && needToHandle) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						lastx = event.getX();
						lasty = event.getY();
						break;
					case MotionEvent.ACTION_MOVE:
						final float xDistance = event.getX() - lastx;
						final float yDistance = event.getY() - lasty;
						int left, right, top, bottom;
						//当水平或者垂直滑动距离大于10,才算拖动事件
						if (Math.abs(xDistance) > 5 || Math.abs(yDistance) > 5) {
							
							left = (int) (getLeft() + xDistance);
							//left = left>0?0:left;
							//left = left<(screenWidth-width)?(screenWidth-width):left;
							right = left + (int) mapWidth;
							
							top = (int) (getTop() + yDistance);
							//top = top>0?0:top;
							//top = top<(screenHeight-height)?(screenHeight-height):top;
							bottom = top + (int) mapHeight;
							
							//执行拖动
							this.layout(left, top, right, bottom);
						}
						break;
					case MotionEvent.ACTION_UP:
						setPressed(false);
						break;
					case MotionEvent.ACTION_CANCEL:
						setPressed(false);
						break;
				}
				return true;
			}
		}
		return false;
	}
	public class ScaleGestureListener implements
			ScaleGestureDetector.OnScaleGestureListener {
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			float previousSpan = detector.getPreviousSpan();// 前一次双指间距
			float currentSpan = detector.getCurrentSpan();// 本次双指间距
			Log.e("onScale","================onScale:"+detector.getScaleFactor()+" dis:"+(previousSpan - currentSpan));
			
			if (currentSpan < previousSpan) {
				// 缩小
				scale = preScale - (previousSpan - currentSpan) / 1000 * preScale;
			} else {
				// 放大
				scale = preScale + (currentSpan - previousSpan) / 1000 * preScale;
			}
			scale = preScale * detector.getScaleFactor();
			if(scale<0.6) scale = (float)0.6;
			if(scale>2) scale = (float) 2;
			
			SvgLayout.this.setScaleX(scale);
			SvgLayout.this.setScaleY(scale);
			return false;
		}
		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			return true;
		}
		@Override
		public void onScaleEnd(ScaleGestureDetector detector) {
			preScale = scale;// 记录本次缩放比例
			lastMultiTouchTime = System.currentTimeMillis();// 记录双指缩放后的时间
		}
	}
}

