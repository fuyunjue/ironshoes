package com.cn.jyz.ironshoes.utils;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.Map;

import cz.msebera.android.httpclient.entity.StringEntity;

public class AsyncHttpHelper {
	
	private static AsyncHttpHelper instance;
	
	/**
	 * 定义一个异步网络client 默认超时未10秒 当超过，默认重连次数为5次 默认最大连接数为10个 　　
	 */
	private static AsyncHttpClient client = new AsyncHttpClient();
	
	static {
		client.setTimeout(10000);//设置超时的时间
	}
	
	
	private AsyncHttpHelper() { }
	
//
//	/**
//	 * HTTP GET METHODs -- 存在异常或者请求超时情况下。回调返回值将是空字符串
//	 *
//	 * @param url
//	 *            请求的url
//	 * @param callback
//	 *            请求完毕后回调的方法
//	 */
//	public void get(Context context, String url,
//	                final AsyncHttpResponseHandler callback) {
//		httpRequest(context, url, null, callback, EHttpMethod.GET);
//	}
	
	
	public void get(Context context, String url, Map<String, Object> params,
	                final AsyncHttpResponseHandler callback) {
		httpRequest(context, url, params, callback, EHttpMethod.GET);
	}
	
	
//	/**
//	 * HTTP POST METHODs -- 存在异常或者请求超时情况下，回调返回值将是空字符串
//	 *
//	 * @param url
//	 *            请求的url
//	 * @param callback
//	 *            请求完毕后回调的方法
//	 */
//	public void post(Context context, String url,
//	                 final AsyncHttpResponseHandler callback) {
//		httpRequest(context, url, null, callback, EHttpMethod.POST);
//	}
	
	
	public void post(Context context, String url, Map<String, Object> params,
	                 final AsyncHttpResponseHandler callback) {
		httpRequest(context, url, params, callback, EHttpMethod.POST);
	}
	/**
	 *
	 * @描写叙述:构建一个单例类
	 * @方法名: getInstance
	 * @return
	 * @返回类型 AndroidAsyncHttpHelper
	 * @创建人 John
	 * @创建时间 2015年8月14日上午10:52:21
	 * @改动人 John
	 * @改动时间 2015年8月14日上午10:52:21
	 * @改动备注
	 * @since
	 * @throws
	 */
	public static AsyncHttpHelper getInstance() {
		if (null == instance) {
			synchronized (AsyncHttpHelper.class) {
				if (null == instance) {
					instance = new AsyncHttpHelper();
				}
			}
		}
		return instance;
	}
	
	private void httpRequest(Context context, String api,
	                         Map<String, Object> params,
	                         final AsyncHttpResponseHandler callback, EHttpMethod method) {
		/* 推断网络状态 */
		if (!AppUtil.isNetWorking(context)) {
			String str = "暂无网络";
			callback.sendFailureMessage(40, null, str.getBytes(), null);
			return;
		}
		/* 得到请求參数 */
		JSONObject jo = new JSONObject();
		RequestParams requestParams = new RequestParams();
		try {
			if (null != params && params.size() > 0) {
				for (String key : params.keySet()) {
					requestParams.put(key, params.get(key));
					jo.put(key, params.get(key));
					Log.e("Async",key+":"+params.get(key));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//动态拼装url
		String url = AppUtil.getRealUrl(api);
		Log.e("Async","Url:"+url);
		switch (method) {
			case GET:
				client.get(context, url, requestParams, callback);
				break;
			
			case POST:
				StringEntity entity = new StringEntity(jo.toString(),"utf-8");
				client.post(context, url, entity, "application/json", callback);
				break;
			
			
			default:
				break;
		}
	}
	/**
	 *
	 * @类描写叙述：请求的类型
	 * @项目名称：
	 * @包名：
	 * @类名称：EHttpMethod
	 * @创建人：John
	 * @创建时间：2015年8月14日上午10:55:44
	 * @改动人：John
	 * @改动时间：2015年8月14日上午10:55:44
	 * @改动备注：
	 * @version v1.0
	 * @see [nothing]
	 * @bug [nothing]
	 *
	 */
	public enum EHttpMethod {
		GET, POST
	}
	/**
	 *
	 * @类描写叙述：请求处理的类型
	 * @项目名称
	 * @包名：
	 * @类名称：EResponseHandlerType
	 * @创建人：John
	 * @创建时间：2015年8月14日上午10:56:09
	 * @改动人：John
	 * @改动时间：2015年8月14日上午10:56:09
	 * @改动备注：
	 * @version v1.0
	 * @see [nothing]
	 * @bug [nothing]
	 *
	 */
	public enum EResponseHandlerType {
		Text, Json
	}
}
