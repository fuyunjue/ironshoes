package com.cn.jyz.ironshoes.html;

import android.app.Activity;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.cn.jyz.ironshoes.utils.ToastUtil;


public class MyWebChromeClient extends WebChromeClient {
	private Activity activity;
	private ProgressBar progressBarWebview;
	
	public MyWebChromeClient (Activity activity, ProgressBar progressBarWebview){
		super();
		this.activity = activity;
		this.progressBarWebview = progressBarWebview;
	}
	
	@Override
    public boolean onJsAlert(WebView view, String url, final String message, JsResult result) {
		this.activity.runOnUiThread(new Runnable() {
			@Override
			public void run () {
				ToastUtil.showShort(activity, message + "");
			}
		});
		result.cancel();
        return true;
    }
    
	@Override
    public boolean onJsConfirm(WebView view, String url, final String message, final JsResult result) {
		
		this.activity.runOnUiThread(new Runnable() {
			@Override
			public void run () {
				ToastUtil.showShort(activity, message + "");
			}
		});
		result.cancel();
		return true;
    }
    
	@Override
	public void onProgressChanged(WebView view, int newProgress) {
		if (null != progressBarWebview) {
			if (newProgress == 100){
				progressBarWebview.setVisibility(View.GONE);//加载完网页进度条消失
			} else {
				progressBarWebview.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
				progressBarWebview.setProgress(newProgress);//设置进度值
			}
		}
	}
}
