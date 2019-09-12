package com.cn.jyz.ironshoes.utils;

import android.content.Context;
import android.content.res.Resources.NotFoundException;

import com.cn.jyz.ironshoes.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Properties;

public class MessageHelper {

	private HashMap<String , String> urls = new HashMap<String, String>();
	
	private static final String IMG_LOGIN_LOGO_URL = "IMG_LOGIN_LOGO_URL";
	public static final String APK_VERSION_URL = "APK_VERSION_URL";
	public static final String BASE_URL = "BASE_URL";
	public static final String MQTT_USERNAME = "MQTT_USERNAME";
	public static final String MQTT_PASSWORD = "MQTT_PASSWORD";
	public static final String MQTT_TOPIC = "MQTT_TOPIC";
	public static final String MQTT_IP = "MQTT_IP";
	public static final String MQTT_PORT = "MQTT_PORT";
	

	public MessageHelper (Context context) {
		try {
			Properties p = new Properties();
			p.load(context.getResources().openRawResource(R.raw.system));
			urls.put(IMG_LOGIN_LOGO_URL, p.getProperty(IMG_LOGIN_LOGO_URL));
			urls.put(APK_VERSION_URL, p.getProperty(APK_VERSION_URL));
			urls.put(BASE_URL, p.getProperty(BASE_URL));
			urls.put(MQTT_USERNAME, p.getProperty(MQTT_USERNAME));
			urls.put(MQTT_PASSWORD, p.getProperty(MQTT_PASSWORD));
			urls.put(MQTT_TOPIC, p.getProperty(MQTT_TOPIC));
			urls.put(MQTT_IP, p.getProperty(MQTT_IP));
			urls.put(MQTT_PORT, p.getProperty(MQTT_PORT));
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Http方式请求接口
	 * 
	 * @param key
	 * @return
	 */
	public String sendPost(String key) {
		try {
			HttpURLConnection httpcon = (HttpURLConnection) ((new URL(urls.get(key)).openConnection()));
			httpcon.setDoOutput(true);
			httpcon.setRequestProperty("Content-Type", "application/json");
			httpcon.setRequestProperty("Accept", "application/json");
			httpcon.setRequestProperty("contentType", "utf-8");  
			httpcon.setRequestMethod("POST");
			httpcon.connect();

			int status = httpcon.getResponseCode();
			if (status != 200) {
				throw new IOException("Post failed with error code " + status);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					httpcon.getInputStream() ,"utf-8"));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
			
			System.out.println(sb.toString());
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *
	 * @return
	 */
	public String sendPostGetWelComeImg() {
		try {

			String baseUrl = AppUtil.getRealUrl(urls.get(IMG_LOGIN_LOGO_URL));
			URL url = new URL(baseUrl);
			if (url == null) {
				return null;
			}
			URLConnection urlConnection = url.openConnection();
			if (urlConnection == null) {
				return null;
			}
			HttpURLConnection httpcon = (HttpURLConnection) (urlConnection);
			httpcon.setDoOutput(true);
			httpcon.setRequestProperty("Content-Type", "application/json");
			httpcon.setRequestProperty("Accept", "application/json");
			httpcon.setRequestProperty("contentType", "utf-8");
			httpcon.setConnectTimeout(3 * 1000);
			httpcon.setRequestMethod("GET");
			httpcon.connect();
			
			int status = httpcon.getResponseCode();
			if (status == 302) {
				String location = httpcon.getHeaderField("Location");
				url = new URL(location);
				urlConnection = url.openConnection();
				if (urlConnection == null) {
					return null;
				}
				httpcon = (HttpURLConnection) (urlConnection);
				httpcon.setDoOutput(true);
				httpcon.setInstanceFollowRedirects(false);
				httpcon.setRequestProperty("Content-Type", "application/json");
				httpcon.setRequestProperty("Accept", "application/json");
				httpcon.setRequestProperty("contentType", "utf-8");
				httpcon.setConnectTimeout(3 * 1000);
				httpcon.setRequestMethod("GET");
				httpcon.connect();
			} else if (status != 200) {
				throw new IOException("Get failed with error code " + status);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					httpcon.getInputStream() ,"utf-8"));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *
	 * @return
	 */
	public String sendPostGetVersion() {
		try {
			String baseUrl = AppUtil.getRealUrl(urls.get(APK_VERSION_URL));
			URL url = new URL(baseUrl);
			if (url == null) {
				return null;
			}
			URLConnection urlConnection = url.openConnection();
			if (urlConnection == null) {
				return null;
			}
			HttpURLConnection httpcon = (HttpURLConnection) (urlConnection);
			httpcon.setDoOutput(true);
			httpcon.setInstanceFollowRedirects(false);
			httpcon.setRequestProperty("Content-Type", "application/json");
			httpcon.setRequestProperty("Accept", "application/json");
			httpcon.setRequestProperty("contentType", "utf-8");
			httpcon.setConnectTimeout(3 * 1000);
			httpcon.setRequestMethod("GET");
			httpcon.connect();

			int status = httpcon.getResponseCode();
			if (status == 302) {
				String location = httpcon.getHeaderField("Location");
				url = new URL(location);
				urlConnection = url.openConnection();
				if (urlConnection == null) {
					return null;
				}
				httpcon = (HttpURLConnection) (urlConnection);
				httpcon.setDoOutput(true);
				httpcon.setInstanceFollowRedirects(false);
				httpcon.setRequestProperty("Content-Type", "application/json");
				httpcon.setRequestProperty("Accept", "application/json");
				httpcon.setRequestProperty("contentType", "utf-8");
				httpcon.setConnectTimeout(3 * 1000);
				httpcon.setRequestMethod("GET");
				httpcon.connect();
				
				
			} else if (status != 200) {
				throw new IOException("Post failed with error code " + status);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					httpcon.getInputStream() ,"utf-8"));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * @return the urls
	 */
	public HashMap<String, String> getUrls() {
		return urls;
	}

	
	
//	/**
//	 * 调用webservice
//	 * 
//	 * @param json
//	 * @return
//	 */
//	public String sendMsg(String json) {
//		try {
//			SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);
//			rpc.addProperty("arg0", json);
//
//			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//					SoapEnvelope.VER11);
//			envelope.dotNet = false;
//			envelope.encodingStyle = "UTF-8";
//			envelope.setOutputSoapObject(rpc);
//			new MarshalBase64().register(envelope);
//			HttpTransportSE aht = new HttpTransportSE(URL, 60 * 1000);
//
//			aht.call(SOAP_ACTION, envelope);
//			Object result = (Object) envelope.getResponse();
//			Log.d(TAG, result.toString());
//			return String.valueOf(result);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

}
