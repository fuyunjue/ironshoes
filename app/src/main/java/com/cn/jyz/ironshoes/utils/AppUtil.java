package com.cn.jyz.ironshoes.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.cn.jyz.ironshoes.application.AppApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by terence on 2017/9/4.
 */

public class AppUtil {
	
	/**
	 * 接口请求地址配置为动态拼装，这里控制真实组装后的Url
	 * @param api
	 * @return
	 */
	public static String getRealUrl (String api) {
		String baseUrl;
		String sysBaseUrl = AppApplication.getInstance().getMessageHelper().getUrls().get(MessageHelper.BASE_URL);
		if (sysBaseUrl.trim().length() == 0) {
			baseUrl = "http://" + AppApplication.getInstance().getBaseUrl() + api;
		} else {
			baseUrl = sysBaseUrl + api;
		}
		
		return baseUrl;
	}

		public static boolean isNetWorking(final Context context)
		{
			boolean flag = checkNet(context);
			if (!flag)
			{
				Toast.makeText(context, "应用当前处于离线操作状态", Toast.LENGTH_SHORT).show();
			}
			return flag;
		}
		/**
		 *
		 * @描写叙述:推断是否有网络连接
		 * @方法名: checkNet
		 * @param context
		 * @return
		 * @返回类型 boolean
		 * @创建人 John
		 * @创建时间 2015年8月14日上午10:59:54
		 * @改动人 John
		 * @改动时间 2015年8月14日上午10:59:54
		 * @改动备注
		 * @since
		 * @throws
		 */
		private static boolean checkNet(Context context) {


			if (context != null) {
				ConnectivityManager mConnectivityManager = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo mNetworkInfo = mConnectivityManager
						.getActiveNetworkInfo();
				if (mNetworkInfo != null) {
					return mNetworkInfo.isAvailable();
				}
			}
			return false;
		}
		/**
		 *
		 * @param context
		 * @return 推断WIFI网络是否可用
		 */
		public boolean isWifiConnected(Context context) {
			if (context != null) {
				ConnectivityManager mConnectivityManager = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo mWiFiNetworkInfo = mConnectivityManager
						.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				if (mWiFiNetworkInfo != null) {
					return mWiFiNetworkInfo.isAvailable();
				}
			}
			return false;
		}


		/**
		 *
		 * @param context
		 * @return 推断MOBILE网络是否可用
		 */
		public boolean isMobileConnected(Context context) {
			if (context != null) {
				ConnectivityManager mConnectivityManager = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo mMobileNetworkInfo = mConnectivityManager
						.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				if (mMobileNetworkInfo != null) {
					return mMobileNetworkInfo.isAvailable();
				}
			}
			return false;
		}


		/**
		 *
		 * @param context
		 * @return 获取当前网络连接的类型信息
		 */
		public static int getConnectedType(Context context) {
			if (context != null) {
				ConnectivityManager mConnectivityManager = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo mNetworkInfo = mConnectivityManager
						.getActiveNetworkInfo();
				if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
					return mNetworkInfo.getType();
				}
			}
			return -1;
		}
    
    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
    
    
    /**
     * 获取屏幕宽、高度
     * @param context
     * @return
     */
    public static int[] getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return new int[]{outMetrics.widthPixels, outMetrics.heightPixels};
    }
    
    /**
     * 获取安装在本机上该应用的版本号
     * @return
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "v0";
        }

    }

    /**
     * 判断service是否已启动
     * @param className
     * @return true为已启动，false为未启动
     */
    public static boolean isServiceWorked(String className , Context context) {
        ActivityManager myManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager.getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString().equals(className)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 初始化登录信息（退出登录、退出当前应用时执行该方法）
     * @param context
     */
    public static void clearLoginUser(Context context) {
//        AppApplication.getInstance().setLoginUser(null);
//		AppApplication.getInstance().setActive(false);
//		UserKeeper.SaveSharepreferenceByKey(context.getApplicationContext(), UserKeeper.login_user, "false");
    }

    /**
     * 程序是否在前台运行
     *
     * @return true：在前台      false：在后台
     */
    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)  {
            return false;
        }
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
                return true;
        }
        return false;
    }

    /**
     * 是否有可用网络 移动
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = cm.getActiveNetworkInfo();
	    return network != null;
    }

    /**
     * Wifi是否可用
     */
    public static boolean isWifiEnable(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }

    /**
     * 验证手机号是否符合大陆的标准格式
     * @param mobiles
     * @return
     */
    public static boolean isMobileNumberValid(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


    /**
     * 获取设备唯一uuid
     *
     * @param context
     * @return
     */
    public static String getdeviceUuid (Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId+"-KP";
    }
    
    /**
     * 初始化进度条
     *
     * @param context
     * @param b1
     * @param b2
     * @return
     */
    public static ProgressDialog getProgressDialog(Context context, String msg, boolean b1, boolean b2) {
        return ProgressDialog.show(context, "", msg, b1, b2);
    }

    /**
     * 判断字符串是否为空
     *
     * @param s
     * @return
     */
    public static boolean IsEmptyOrNullString(String s) {
        return (s == null) || (s.trim().length() == 0);
    }

	/**
	 * 判断service是否已经运行
	 * 必须判断uid,因为可能有重名的Service,所以要找自己程序的Service,不同进程只要是同一个程序就是同一个uid,个人理解android系统中一个程序就是一个用户
	 * 用pid替换uid进行判断强烈不建议,因为如果是远程Service的话,主进程的pid和远程Service的pid不是一个值,在主进程调用该方法会导致Service即使已经运行也会认为没有运行
	 * 如果Service和主进程是一个进程的话,用pid不会出错,但是这种方法强烈不建议,如果你后来把Service改成了远程Service,这时候判断就出错了
	 *
	 * @param className Service的全名,例如PushService.class.getName()
	 * @return true:Service已运行 false:Service未运行
	 */
	public static boolean isServiceExisted(Context ctx,String className) {
		ActivityManager am = (ActivityManager)ctx.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = am.getRunningServices(Integer.MAX_VALUE);
		int myUid = android.os.Process.myUid();
		for (ActivityManager.RunningServiceInfo runningServiceInfo : serviceList) {
			Log.e("isServiceExisted",runningServiceInfo.service.getClassName());
			if (runningServiceInfo.uid == myUid && runningServiceInfo.service.getClassName().equals(className)) {
				return true;
			}
		}
		return false;
	}
}
