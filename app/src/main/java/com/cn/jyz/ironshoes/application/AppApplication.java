package com.cn.jyz.ironshoes.application;


import android.app.Application;

import com.cn.jyz.ironshoes.model.ModelUserInfo;
import com.cn.jyz.ironshoes.push.MqttService;
import com.cn.jyz.ironshoes.utils.MessageHelper;
import com.cn.jyz.ironshoes.utils.SysAppCrashHandler;

public class AppApplication extends Application {

    private static AppApplication mInstance;
    
    private ModelUserInfo userInfo;
    
    private String baseUrl = "";
    
    private String mqttServerIp = "";
    private String mqttServerPort = "";
    
    private MessageHelper messageHelper;

    public static AppApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        SysAppCrashHandler handler = SysAppCrashHandler.getInstance();
        handler.init(getApplicationContext());
        Thread.setDefaultUncaughtExceptionHandler(handler);
        super.onCreate();
        mInstance = this;
    
        messageHelper = new MessageHelper(mInstance);
    
//        if (AppApplication.getInstance().getMessageHelper().getUrls().get(MessageHelper.MQTT_IP).trim().length() > 0
//                && AppApplication.getInstance().getMessageHelper().getUrls().get(MessageHelper.MQTT_PORT).trim().length() > 0
//                && AppApplication.getInstance().getMessageHelper().getUrls().get(MessageHelper.BASE_URL).trim().length() > 0) {
//            //TODO system配置文件里有配置，为debug模式，读取该配置，享有优先
//            this.mqttServerIp = messageHelper.getUrls().get(MessageHelper.MQTT_IP);
//            this.mqttServerPort = messageHelper.getUrls().get(MessageHelper.MQTT_PORT);
//            this.baseUrl = messageHelper.getUrls().get(MessageHelper.BASE_URL);
//
//            //启动mqtt监听
//            AppApplication.getInstance().StartPushService();
//        } else if (UserKeeper.getStringValue(mInstance, UserKeeper.IP) != null
//                && UserKeeper.getStringValue(mInstance, UserKeeper.PORT) != null
//                && UserKeeper.getStringValue(mInstance, UserKeeper.IP_MQTT) != null
//                && UserKeeper.getStringValue(mInstance, UserKeeper.PORT_MQTT) != null) {
//            //TODO 经由服务IP配置配置过的，动态读取该配置，不享有优先
//            this.baseUrl = UserKeeper.getStringValue(mInstance, UserKeeper.IP) + ":" + UserKeeper.getStringValue(mInstance, UserKeeper.PORT);
//            this.mqttServerIp = UserKeeper.getStringValue(mInstance, UserKeeper.IP_MQTT);
//            this.mqttServerPort = UserKeeper.getStringValue(mInstance, UserKeeper.PORT_MQTT);
//
//            //手动启动mqtt服务
//            AppApplication.getInstance().StartPushService();
//        }
    }
    public void StartPushService(){
        MqttService.actionStart(getApplicationContext());
    }
    
    public ModelUserInfo getUserInfo () {
        return userInfo;
    }
    
    public void setUserInfo (ModelUserInfo userInfo) {
        this.userInfo = userInfo;
    }
    
    public String getBaseUrl () {
        return baseUrl;
    }
    
    public void setBaseUrl (String baseUrl) {
        this.baseUrl = baseUrl;
    }
    
    public String getMqttServerIp () {
        return mqttServerIp;
    }
    
    public void setMqttServerIp (String mqttServerIp) {
        this.mqttServerIp = mqttServerIp;
    }
    
    public String getMqttServerPort () {
        return mqttServerPort;
    }
    
    public void setMqttServerPort (String mqttServerPort) {
        this.mqttServerPort = mqttServerPort;
    }
    
    public MessageHelper getMessageHelper () {
        return messageHelper;
    }
}
