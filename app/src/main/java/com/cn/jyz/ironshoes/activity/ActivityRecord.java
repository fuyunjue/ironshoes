package com.cn.jyz.ironshoes.activity;

import android.content.Context;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.jyz.ironshoes.R;
import com.cn.jyz.ironshoes.adapter.FragementRecordAdapter;
import com.cn.jyz.ironshoes.recyclerview.bean.RecordItem;
import com.cn.jyz.ironshoes.recyclerview.view.ListFragment;

import java.util.ArrayList;
import java.util.List;

public class ActivityRecord extends AppCompatActivity implements View.OnClickListener {
	
	private Context mContext;
	
	private ViewPager viewPager;
	private ArrayList<View> pageList;
	private TextView titleInspection;
	private TextView titleTask;
	private ImageView indicator; //滚动指示器
	private int offset = 0; //滚动条初始偏移量
	private int currIndex = 0; //当前页编号
	private int one; //一倍滚动量
	
	private ImageView iv_back;
	
	private LinearLayout linearLayout;
	private TextView title_task;
	
	
	
	@Override
	protected void onCreate (@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);
		mContext = this;
		
		initView();
	}
	void initView() {
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		//开始绑定按钮
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		titleInspection = (TextView) findViewById(R.id.title_inspection);
		titleTask = (TextView) findViewById(R.id.title_task);
		indicator = (ImageView) findViewById(R.id.indicator);
		
		linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
		title_task = (TextView) findViewById(R.id.title_task);
		
		List<String> titles = new ArrayList<>();
		titles.add("作业记录");
		titles.add("巡检记录");
		
		List<Fragment> fragments = new ArrayList<>();
		fragments.add(new ListFragment(mContext, RecordItem.TASK));
		fragments.add(new ListFragment(mContext, RecordItem.INSPECTION));
		
		FragementRecordAdapter fragementAdapter = new FragementRecordAdapter(getSupportFragmentManager(), fragments, titles);
		viewPager.setAdapter(fragementAdapter);
		
		viewPager.setCurrentItem(0);
		viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
		
		//监听文字被点击
		titleInspection.setOnClickListener(this);
		titleTask.setOnClickListener(this);
		
		InitScrollbar();
	}
	
	public void InitScrollbar() {
		linearLayout.post(new Runnable() {
			@Override
			public void run () {
				int lLeft = linearLayout.getLeft();
				int lWidth = linearLayout.getWidth();
				int rLeft = title_task.getLeft();
				int rWidth = title_task.getWidth();
				
				//初始化滚动条的位置
				offset = rWidth / 4 + lLeft;
				
				Matrix matrix = new Matrix();
				matrix.setScale(170f/lWidth, 200f/linearLayout.getHeight());
				matrix.postTranslate(offset, 0);
				indicator.setImageMatrix(matrix);
				//计算出切换一个界面时，滚动条的位移量
				one = rLeft;
				
				System.out.println(one + ".");
			}
		});
	}
	
	@Override
	public void onClick (View view) {
		switch (view.getId()) {
			case R.id.iv_back:
				finish();
				break;
			case R.id.title_inspection:
				//当点击“全部”标签时，调用左分页的加载
				viewPager.setCurrentItem(0);
				break;
			case R.id.title_task:
				//当点击“新增”标签时，调用右分页的加载
				viewPager.setCurrentItem(1);
				break;
				default:
					break;
		}
	}
	
	//页面滚动监听器功能，实现标签页左右滑动切换效果
	public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
		
		@Override
		public void onPageSelected(int index) {
			Animation animation = null;
			switch (index) {
				case 0:
//					titleInspection.setTextColor(0xff000000);//0x表示整型，ff表示透明度为0，最后的6位数字表示颜色，必须这样写，不能省略
//					titleTask.setTextColor(0xff8e8e8e);
					//该情况下，指示器从右向左移动
					animation = new TranslateAnimation(one, 0, 0, 0);
					//以上四个参数分别代表开始点离x坐标差值，结束点离x坐标差值；开始点离y坐标差值，结束点离y坐标差值
					break;
				case 1:
//					titleInspection.setTextColor(0xff000000);
//					titleTask.setTextColor(0xff8e8e8e);
					//该情况下，指示器从左向右移动
					animation = new TranslateAnimation(0, one, 0, 0);
					break;
			}
			currIndex = index;//使当前页编号等于该值，0表示“全部”页，1表示“新增”页
			animation.setFillAfter(true);//将此值设置为true表示指示器将停止在最终移动到的位置上
			//动画持续时间，单位为毫秒
			animation.setDuration(200);
			//开始执行动画
			indicator.startAnimation(animation);
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
		
	}
}
