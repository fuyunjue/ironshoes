package com.cn.jyz.ironshoes.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.cn.jyz.ironshoes.R;
import com.cn.jyz.ironshoes.application.AppApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by terence on 2018/6/7.
 */

public class UpdateAppManager {
	// 外存sdcard存放路径
	private static final String FILE_PATH = Environment.getExternalStorageDirectory() +"/" + "download" +"/";
	// 下载应用存放全路径
	private static final String FILE_NAME = FILE_PATH + "maple.apk";
	// 准备安装新版本应用标记
	private static final int INSTALL_TOKEN = 1;
	//Log日志打印标签
	private static final String TAG = "Update_log";
	
	private Activity mActivity;
//	//获取版本数据的地址
//	private String version_path = "https://www.maplefashion.com/main/apk/apk_version.xml";
	//获取新版APK的默认地址
	private String apk_path = "http://team.agiledev.com.cn:8080/WSS/apk/app.apk";
	// 下载应用的进度条
	private ProgressDialog progressDialog;
	
	//新版本号和描述语言
	private int update_versionCode;
	private String update_describe;
	
	public UpdateAppManager(Activity context) {
		this.mActivity = context;
	}
	
	
	
	public static final int REQUEST_EXTERNAL_STORAGE = 1;
	private static String[] PERMISSIONS_STORAGE = {
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE };
	
	public void verifyStoragePermissions(Activity activity) {
		int permission = ActivityCompat.checkSelfPermission(activity,
				Manifest.permission.WRITE_EXTERNAL_STORAGE);
		int permission1 = ActivityCompat.checkSelfPermission(activity,
				Manifest.permission.READ_EXTERNAL_STORAGE);
		if (permission != PackageManager.PERMISSION_GRANTED || permission1 != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
					REQUEST_EXTERNAL_STORAGE);
		} else {
			showDownloadDialog();
		}
	}
	
	/**
	 * 获取当前版本号
	 */
	private int getCurrentVersion() {
		try {
			
			PackageManager manager = mActivity.getPackageManager();
			PackageInfo info = manager.getPackageInfo(mActivity.getPackageName(), 0);
			
			Log.e(TAG, "当前版本名和版本号" + info.versionName + "--" + info.versionCode);
			
			return info.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
			
			Log.e(TAG, "获取当前版本号出错");
			return 0;
		}
	}
	
	/**
	 * 从服务器获得更新信息
	 */
	public void getUpdateMsg() {
		
		class mAsyncTask extends AsyncTask<String, Integer, String> {
			@Override
			protected String doInBackground(String... params) {

				MessageHelper helper = new MessageHelper(mActivity);
				return helper.sendPostGetVersion();// 使用http post
			}

			@Override
			protected void onPostExecute(String s) {             //回到主线程，更新UI

				if (s == null || s.trim().length() == 0) {
					return;
				}
				
				Log.e(TAG, "异步消息处理反馈--" + s);
				try {
					JSONObject object = new JSONObject(s);
					if (object == null)
						return;

					update_versionCode = object.getInt("version");
					update_describe = object.getString("desc");
					apk_path = object.getString("path");

					Log.e(TAG, "新版本号--" + update_versionCode);
					Log.e(TAG, "新版本描述--\n" + update_describe);
				} catch (JSONException e) {
					e.printStackTrace();
					return;
				}

				if (update_versionCode > getCurrentVersion()) {

					Log.e(TAG, "提示更新！");
					showNoticeDialog();
				} else {
					Log.e(TAG, "已是最新版本！");
				}
			}
		}

		new mAsyncTask().execute(AppApplication.getInstance().getMessageHelper().getUrls().get(MessageHelper.APK_VERSION_URL));
	}
	
	
	/**
	 * 显示提示更新对话框
	 */
	private void showNoticeDialog() {
		new AlertDialog.Builder(mActivity)
				.setTitle("检测到新版本！")
				.setMessage(update_describe)
				.setPositiveButton("下载", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						
						verifyStoragePermissions(mActivity);
						
					}
				}).setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).create().show();
	}
	
	/**
	 * 显示下载进度对话框
	 */
	public void showDownloadDialog() {
		progressDialog = new ProgressDialog(mActivity);
		progressDialog.setTitle("正在下载...");
		progressDialog.setCanceledOnTouchOutside(true);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		
		new downloadAsyncTask().execute();
	}
	
	/**
	 * 下载新版本应用
	 */
	private class downloadAsyncTask extends AsyncTask<Void, Integer, Integer> {
		
		@Override
		protected void onPreExecute() {
			Log.e(TAG, "执行至--onPreExecute");
			progressDialog.show();
		}
		
		@Override
		protected Integer doInBackground(Void... params) {
			
			Log.e(TAG, "执行至--doInBackground");
			
			URL url;
			HttpURLConnection connection = null;
			InputStream in = null;
			FileOutputStream out = null;
			try {
				url = new URL(apk_path);
				
				connection = (HttpURLConnection)url.openConnection();
				connection.setRequestProperty("Accept-Encoding", "identity");    // 加上这句话解决问题
				connection.connect();
				
				int status = connection.getResponseCode();
				if (status == 302) {
					String location = connection.getHeaderField("Location");
					url = new URL(location);
					
					connection = (HttpURLConnection)url.openConnection();
					connection.setRequestProperty("Accept-Encoding", "identity");    // 加上这句话解决问题
					connection.connect();
				}
				
				long fileLength = connection.getContentLength();
				
				in = connection.getInputStream();
				File file_path = new File(FILE_PATH);
				if (!file_path.exists()) {
					file_path.mkdir();
				}
				
				File apkFile = new File(FILE_NAME);
				if (apkFile.isFile()) {
					apkFile.delete();
				}
				
				out = new FileOutputStream(new File(FILE_NAME));//为指定的文件路径创建文件输出流
				byte[] buffer = new byte[1024 * 1024];
				int len = 0;
				long readLength = 0;
				
				Log.e(TAG, "执行至--readLength = 0");
				
				while ((len = in.read(buffer)) != -1) {
					
					out.write(buffer, 0, len);//从buffer的第0位开始读取len长度的字节到输出流
					readLength += len;
					
					int curProgress = (int) (((float) readLength / fileLength) * 100);
					
					Log.e(TAG, "当前下载进度：" + curProgress);
					
					publishProgress(curProgress);
					
					if (readLength >= fileLength) {
						
						Log.e(TAG, "执行至--readLength >= fileLength");
						break;
					}
				}
				
				out.flush();
				return INSTALL_TOKEN;
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				if (connection != null) {
					connection.disconnect();
				}
			}
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			
			Log.e(TAG, "异步更新进度接收到的值：" + values[0]);
			progressDialog.setProgress(values[0]);
		}
		
		@Override
		protected void onPostExecute(Integer integer) {
			mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run () {
					progressDialog.dismiss();//关闭进度条
				}
			});
			
			//安装应用
			installApp();
		}
	}
	
	/**
	 * 安装新版本应用
	 */
	public void installApp() {
		File appFile = new File(FILE_NAME);
		if (!appFile.exists() || !appFile.isFile()) {
			mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run () {
					ToastUtil.showLong(mActivity, "安裝文件不存在");
				}
			});
			return;
		}
		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		File apkFile = new File(appFile.getAbsolutePath());
		//兼容7.0
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			Uri contentUri = FileProvider.getUriForFile(mActivity, "com.cn.jyz.ironshoes.provider", apkFile);
			intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
			//兼容8.0
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				boolean hasInstallPermission = mActivity.getPackageManager().canRequestPackageInstalls();
				if (!hasInstallPermission) {
					mActivity.runOnUiThread(new Runnable() {
						@Override
						public void run () {
							ToastUtil.showLong(mActivity, R.string.string_install_unknow_apk_note);
						}
					});
					startInstallPermissionSettingActivity();
					return;
				}
			}
		} else {
			intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		
		if (mActivity.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
			mActivity.startActivity(intent);
		}
	}
	
	
	
	/**
	 * 跳转到设置-允许安装未知来源-页面
	 */
	@RequiresApi (api = Build.VERSION_CODES.O)
	private void startInstallPermissionSettingActivity() {
		Uri packageURI = Uri.parse("package:" + mActivity.getPackageName());
		//注意这个是8.0新API
		Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
		mActivity.startActivityForResult(intent, Constant.REQUEST_INSTALL_UNKNOW_APK_NOTE);
	}
}
