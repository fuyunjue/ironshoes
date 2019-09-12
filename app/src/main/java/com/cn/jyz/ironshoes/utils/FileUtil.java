package com.cn.jyz.ironshoes.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class FileUtil {
	
	/**
	 * 判断文件是否存在
	 *
	 * @param httpPath
	 * @return
	 */
	public static Boolean existHttpPath (String httpPath) {
		try {
			URL httpurl = new URL(new URI(httpPath).toASCIIString());
			URLConnection urlConnection = httpurl.openConnection();
			Long TotalSize = Long.parseLong(urlConnection.getHeaderField("Content-Length"));
			
			if (TotalSize <= 0) {
				return false;
			}
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	/**
	 * 获取网络图片文件流
	 * @param destUrl
	 * @return
	 */
	public static InputStream getInputStream(String destUrl) {
		InputStream inputStream = null;
		URLConnection urlConnection = null;
		URL url = null;
		try {
			url = new URL(destUrl);
			urlConnection = url.openConnection();
			inputStream = urlConnection.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
	}
}
