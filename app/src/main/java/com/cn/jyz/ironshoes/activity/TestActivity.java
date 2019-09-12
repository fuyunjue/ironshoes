package com.cn.jyz.ironshoes.activity;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cn.jyz.ironshoes.R;
import com.cn.jyz.ironshoes.push.MqttService;
import com.cn.jyz.ironshoes.utils.DensityUtil;
import com.cn.jyz.ironshoes.view.SvgIcon;
import com.cn.jyz.ironshoes.view.SvgIconOnClick;
import com.cn.jyz.ironshoes.view.SvgLayout;
import com.ibm.mqtt.Mqtt;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends Activity {
	
	private LinearLayout OutLayout = null;
	private SvgLayout svgLayout = null;
	private MqqtBroadReceiver mqqtBroadReceiver= null;
	@Override
	protected void onCreate (@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_activity);
		
		//查找地图控件
		svgLayout = (SvgLayout)findViewById(R.id.powerFullLayout);
		//初始化地图控件 屏幕高度 - 底部或者顶部的一些高度去除
		int otherHeight = DensityUtil.px2dip(this,getResources().getDimensionPixelSize(R.dimen.dimen43));
		int mapHeight = DensityUtil.getDevicePx(this)[1] -  otherHeight;
		svgLayout.initOutLayout("transtation.svg",mapHeight,800,600,0xFF000000);
		//添加或者更新图标级
		List<SvgIcon> iconList = new ArrayList<SvgIcon>();
		//两个铁鞋
		iconList.add(new SvgIcon("绿色铁鞋",R.mipmap.index_tx_green,null,13,20,184,289));
		iconList.add(new SvgIcon("红色铁鞋",R.mipmap.index_tx_red,null,13,20,293,289));
		//3个机车
		for (int i=0;i<3;i++){
			SvgIcon icon = new SvgIcon(""+i, i>1?R.mipmap.index_jc_red : R.mipmap.index_jc_green,"079"+i,30,17,200+(i*30),292);
			iconList.add(icon);
		}
		svgLayout.AddOrRefIcons(iconList);

		//事件委托过来
		svgLayout.setOnClick(new SvgIconOnClick() {
			@Override
			public void onClick(SvgIcon icon, View view) {
				Toast.makeText(TestActivity.this, "点击："+view.getTag().toString(), Toast.LENGTH_SHORT).show();
			}
		});
		
		//服务器消息通知注册
		IntentFilter filter = new IntentFilter();
		filter.addAction(MqttService.NOTIFICATION_BROADCAST);
		mqqtBroadReceiver = new MqqtBroadReceiver();
		registerReceiver(mqqtBroadReceiver, filter);// 注册
	}
	
	//接收服务器的消息通知到界面
	private class MqqtBroadReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(TestActivity.this,"收到消息通知："+intent.getStringExtra("msg"), Toast.LENGTH_SHORT).show();
		}
	}
	@Override
	protected void onDestroy() {
		unregisterReceiver(mqqtBroadReceiver);
		mqqtBroadReceiver = null;
		super.onDestroy();
	}
}
