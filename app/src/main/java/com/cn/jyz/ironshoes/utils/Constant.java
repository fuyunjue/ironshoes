package com.cn.jyz.ironshoes.utils;

/**
 * Created by terence on 2017/11/8.
 */
public class Constant {
	public static final String EVENT_REFRESH_LANGUAGE           = "EVENT_REFRESH_LANGUAGE";
	public static final String MY_USER_AGENT                    = "ironshoes_app";         //自定义webview agent
	
	public static final String REMENBER_PASSWPRD                = "true";                   //记住密码，用作对比
	public static final String WEBVIEW_ENCODING                 = "UTF-8";                  //页面编码
	public static final String WEBVIEW_OBJ_NAME                 = "ironshoesJsObject";   //自定义webview注入对象名称
	
	public static final String APP_PATH                         = "ironshoes";           //应用缓存目录
	public static final String APK_NAME                         = "ironshoes.apk";              //文件下载保存的文件名
	
	public static final Integer REQUEST_INSTALL_UNKNOW_APK_NOTE = 0x101;                    //适配8.0及以上安装未知来源应用授权请求码
	
	public static final long LOGIN_SITE_AUTH                    = 7 * 24 * 60 * 60 * 1000;  //app自动登录有效时间，7天
//	public static final long LOGIN_SITE_AUTH                    = 5 * 60 * 1000;            //测试
	public static final long LOGIN_SESSION                      = 20 * 60 * 1000;           //每次登录20分钟有效，过期需重新登录
//	public static final long LOGIN_SESSION                      = 2 * 60 * 1000;            //测试
	
	
	//登录
	public static final String API_LOGIN                      = "/WSApi/api/login";
	//巡检记录列表查询
	public static final String API_POLLINGRECORD              = "/WSApi/api/polling/polling";
	//作业记录列表查询
	public static final String API_UNSLIPLEVEL                = "/WSApi/api/operation/unSlipRegister";
	
	public static final String API_SEARCH                     = "/WSApi/api/searchPatrolTieXie";
	
	//获取所有站点
	public static final String API_STATIONS                   = "/WSApi/api/stations";
	
	//铁鞋安装-铁鞋设防
	public static final String INSTALL                        = "/WSApi/api/polling/install";
	
	//铁鞋卸载-铁鞋撤防
	public static final String UNINSTALL                      = "/WSApi/api/polling/uninstall";
	
	//巡检-铁鞋巡检
	public static final String CHECKMODEL                     = "/WSApi/api/polling/checkModel";
	
	//电子地图-地图股道接口
	public static final String TRACKINFO                      = "/WSApi/api/trackinfo";
	
	//电子地图-机车列表接口
	public static final String MOTORS                         = "/WSApi/api/motors";
	
	//查询铁鞋适用情况
	public static final String  USAGERECORD                   = "/WSApi/api/skate/usageRecord";
	
	//查询铁鞋适用情况
	public static final String TIEXIES                        = "/WSApi/api/tieXies";
	
	//电子地图-点击弹窗查看铁鞋信息
	public static final String  TIEXIE_INFO                   = "/WSApi/api/motorWithTieXieInfo";
	
	//电子地图-获取机车和铁鞋坐标位置接口（新）
	public static final String  MOTORANDTX                    = "/WSApi/api/motorAndTx";
	
	//所有铁鞋mac地址接口
	public static final String MAC                            = "/WSApi/api/tx/mac";
}
