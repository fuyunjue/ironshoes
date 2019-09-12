package com.cn.jyz.ironshoes.html;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.cn.jyz.ironshoes.activity.MainActivity;
import com.github.jdsjlzx.util.WeakHandler;

public class JsObject {
	private Context activity;
	private WeakHandler mHandler;
	
	public static final String CLICK_DATA_TYPE = "data_type";
	public static final String CLICK_DATA_FID = "data_fid";
	public static final String CLICK_DATA_INDEX = "data_index";
	public static final String CLICK_DATA_TRACKID = "data_trackid";
	
	
	public JsObject (Context activity, WeakHandler handler){
		this.activity = activity;
		this.mHandler = handler;
	}
	
	@JavascriptInterface
	public void doToast(String toast) {
		Toast.makeText(activity, toast + "", Toast.LENGTH_SHORT).show();
	}
	
	@JavascriptInterface
	public void iconClick(int type, int fid, int index, int trackId) {
		Message msg = mHandler.obtainMessage();
		msg.what = MainActivity.MAPCLICK;
		Bundle n = new Bundle();
		n.putInt(CLICK_DATA_TYPE, type);
		n.putInt(CLICK_DATA_FID, fid);
		n.putInt(CLICK_DATA_INDEX, index);
		n.putInt(CLICK_DATA_TRACKID, trackId);
		msg.setData(n);
		mHandler.sendMessage(msg);
	}
	
}
