package com.cn.jyz.ironshoes.push;

import java.lang.reflect.Method;
import java.util.Locale;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDefaultFilePersistence;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.internal.MemoryPersistence;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.provider.Settings.Secure;
import android.util.Log;

import com.cn.jyz.ironshoes.R;
import com.cn.jyz.ironshoes.activity.ActivityLogin;
import com.cn.jyz.ironshoes.application.AppApplication;
import com.cn.jyz.ironshoes.model.ModelMqtt;
import com.cn.jyz.ironshoes.utils.GsonTools;
import com.cn.jyz.ironshoes.utils.MessageHelper;


public class MqttService extends Service implements MqttCallback {
    //握手的账号、密码
    //TODO 改为配置文件，在system.properties文件
//    private String userName = "admin";
//    private String passWord = "admin";
    //订阅的频道名
//    private static final String TOPIC = "bizalarm"; // Device ID Format, add any prefix you'd like
    //本设备的连接名称
    private static final String DEVICE_ID_FORMAT = "%s_%s"; // Device ID Format, add any prefix you'd like
    
    //监听服务器ip、端口
//    private String MQTT_BROKER = "10.21.101.212"; // Broker URL or IP Address
//    private int MQTT_PORT = 1883; // Broker Port
//    private String MQTT_BROKER = null; // Broker URL or IP Address
//    private int MQTT_PORT; // Broker Port
    
    
    
    public static final String DEBUG_TAG = "MqttService"; // Debug TAG

    private static final String MQTT_THREAD_NAME = "MqttService[" + DEBUG_TAG + "]"; // Handler Thread ID


    public static final int MQTT_QOS_0 = 0; // QOS Level 0 ( Delivery Once no confirmation )
    public static final int MQTT_QOS_1 = 1; // QOS Level 1 ( Delevery at least Once with confirmation )
    public static final int MQTT_QOS_2 = 2; // QOS Level 2 ( Delivery only once with confirmation with handshake )

    private static final int MQTT_KEEP_ALIVE = 240000; // KeepAlive Interval in MS
    private static final String MQTT_KEEP_ALIVE_TOPIC_FORAMT = "/users/%s/keepalive"; // Topic format for KeepAlives
    private static final byte[]     MQTT_KEEP_ALIVE_MESSAGE = { 0 }; // Keep Alive message to send
    private static final int MQTT_KEEP_ALIVE_QOS = MQTT_QOS_0; // Default Keepalive QOS

    private static final boolean MQTT_CLEAN_SESSION = true; // Start a clean session?

    private static final String MQTT_URL_FORMAT = "tcp://%s:%s"; // URL Format normally don't change

    private static final String ACTION_START  = DEBUG_TAG + ".START"; // Action to start
    private static final String ACTION_STOP   = DEBUG_TAG + ".STOP"; // Action to stop
    private static final String ACTION_KEEPALIVE= DEBUG_TAG + ".KEEPALIVE"; // Action to keep alive used by alarm manager
    private static final String ACTION_RECONNECT= DEBUG_TAG + ".RECONNECT"; // Action to reconnect


    // Note: There is a 23 character limit you will get
    // An NPE if you go over that limit
    private boolean mStarted = false;   // Is the Client started?
    private String mDeviceId;       // Device ID, Secure.ANDROID_ID
    private Handler mConnHandler;     // Seperate Handler thread for networking

    private MqttDefaultFilePersistence mDataStore; // Defaults to FileStore
    private MemoryPersistence mMemStore; // On Fail reverts to MemoryStore
    private MqttConnectOptions mOpts; // Connection Options

    private MqttTopic mKeepAliveTopic; // Instance Variable for Keepalive topic

    private MqttClient mClient; // Mqtt Client

    private AlarmManager mAlarmManager; // Alarm manager to perform repeating tasks
    private ConnectivityManager mConnectivityManager; // To check for connectivity changes

    private static int 				notificationId = 0;
    private NotificationManager mNotifMan;


    public static int mqttState = 0;
    public static String mqttStateStr = "初始化";

    /**
     * Start MQTT Client
     * @param ctx context to start the service with
     * @return void
     */
    public static void actionStart(Context ctx) {
        Intent i = new Intent(ctx, MqttService.class);
        i.setAction(ACTION_START);
        ctx.startService(i);
    }
    /**
     * Stop MQTT Client
     * @param ctx context to start the service with
     * @return void
     */
    public static void actionStop(Context ctx) {
            Intent i = new Intent(ctx,MqttService.class);
            i.setAction(ACTION_STOP);
            ctx.startService(i);
    }
    /**
     * Send a KeepAlive Message
     * @param ctx context to start the service with
     * @return void
     */
    public static void actionKeepalive(Context ctx) {
            Intent i = new Intent(ctx,MqttService.class);
            i.setAction(ACTION_KEEPALIVE);
            ctx.startService(i);
    }

    /**
     * Initalizes the DeviceId and most instance variables
     * Including the Connection Handler, Datastore, Alarm Manager
     * and ConnectivityManager.
     */
    @Override
    public void onCreate() {
            super.onCreate();

            mDeviceId = String.format(DEVICE_ID_FORMAT,
                    AppApplication.getInstance().getMessageHelper().getUrls().get(MessageHelper.MQTT_TOPIC),
                    Secure.getString(getContentResolver(), Secure.ANDROID_ID));
            
            if (mDeviceId.length() > 23) {
                mDeviceId = mDeviceId.substring(0, 22);
            }

            HandlerThread thread = new HandlerThread(MQTT_THREAD_NAME);
            thread.start();

            mConnHandler = new Handler(thread.getLooper());

            try {
                mDataStore = new MqttDefaultFilePersistence(getCacheDir().getAbsolutePath());
            } catch (MqttPersistenceException e) {
                e.printStackTrace();
                mDataStore = null;
                mMemStore = new MemoryPersistence();
            }

            mOpts = new MqttConnectOptions();
            mOpts.setCleanSession(MQTT_CLEAN_SESSION);
            // Do not set keep alive interval on mOpts we keep track of it with alarm's
    
            //TODO 执行握手，设置账号密码
            // 用户名
            mOpts.setUserName(AppApplication.getInstance().getMessageHelper().getUrls().get(MessageHelper.MQTT_USERNAME));
            // 密码
            mOpts.setPassword(AppApplication.getInstance().getMessageHelper().getUrls().get(MessageHelper.MQTT_PASSWORD).toCharArray());

            mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            mConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            mNotifMan = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }

    /**
     * Service onStartCommand
     * Handles the action passed via the Intent
     *
     * @return START_REDELIVER_INTENT
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
            super.onStartCommand(intent, flags, startId);

            String action = intent.getAction();

            Log.i(DEBUG_TAG,"Received action of " + action);

            if (action == null) {
                Log.i(DEBUG_TAG,"Starting service with no action\n Probably from a crash");
            } else {
                if(action.equals(ACTION_START)) {
                    Log.i(DEBUG_TAG,"Received ACTION_START");
                    start();
                } else if(action.equals(ACTION_STOP)) {
                    stop();
                } else if(action.equals(ACTION_KEEPALIVE)) {
                    keepAlive();
                } else if(action.equals(ACTION_RECONNECT)) {
                    if(isNetworkAvailable()) {
                        reconnectIfNecessary();
                    }
                }
            }

            return START_REDELIVER_INTENT;
    }

    /**
     * Attempts connect to the Mqtt Broker
     * and listen for Connectivity changes
     * via ConnectivityManager.CONNECTVITIY_ACTION BroadcastReceiver
     */
    private synchronized void start() {
            if (mStarted) {
                Log.i(DEBUG_TAG,"Attempt to start while already started");
                return;
            }

            if (hasScheduledKeepAlives()) {
                stopKeepAlives();
            }

            connect();
            try {
                registerReceiver(mConnectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            }
            catch (Exception ex){

            }
    }
    /**
     * Attempts to stop the Mqtt client
     * as well as halting all keep alive messages queued
     * in the alarm manager
     */
    private synchronized void stop() {
        if (!mStarted) {
            Log.i(DEBUG_TAG,"Attemtpign to stop connection that isn't running");
            return;
        }

        if (mClient != null) {
            mConnHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        mClient.disconnect();

                        sendMqqtStateMessage(2,"断开连接");
                    } catch (MqttException ex) {
                        ex.printStackTrace();
                    }
                    
                    mClient = null;
                    mStarted = false;

                    stopKeepAlives();
                    }
            });
        }

        unregisterReceiver(mConnectivityReceiver);
    }
    
    /**
     * Connects to the broker with the appropriate datastore
     */
    private synchronized void connect() {
        sendMqqtStateMessage(0,"正在连接");

        try {
            //TODO mqtt监听服务ip、端口号
            String url = String.format(Locale.US, MQTT_URL_FORMAT, AppApplication.getInstance().getMqttServerIp(), AppApplication.getInstance().getMqttServerPort());
            Log.i(DEBUG_TAG,"Connecting with URL: " + url);
    
            if (mDataStore != null) {
                Log.i(DEBUG_TAG,"Connecting with DataStore");
                mClient = new MqttClient(url, mDeviceId, mDataStore);
            } else {
                Log.i(DEBUG_TAG,"Connecting with MemStore");
                mClient = new MqttClient(url, mDeviceId, mMemStore);
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }

        mConnHandler.post(new Runnable() {
            
            @Override
            public void run() {
                try {
                    mClient.connect(mOpts);

                    //设置订阅频道
                    mClient.subscribe(AppApplication.getInstance().getMessageHelper().getUrls().get(MessageHelper.MQTT_TOPIC), 0);

                    mClient.setCallback(MqttService.this);

                    mStarted = true; // Service is now connected

                    Log.i(DEBUG_TAG,"Successfully connected and subscribed starting keep alives");

                    sendMqqtStateMessage(1,"保持连接");

                    startKeepAlives();
                } catch(MqttException e) {
                    sendMqqtStateMessage(2,"连接失败");
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * Schedules keep alives via a PendingIntent
     * in the Alarm Manager
     */
    private void startKeepAlives() {
            Intent i = new Intent();
            i.setClass(this, MqttService.class);
            i.setAction(ACTION_KEEPALIVE);
            PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
            mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                       System.currentTimeMillis() + MQTT_KEEP_ALIVE,
                                       MQTT_KEEP_ALIVE, pi);
    }
    /**
     * Cancels the Pending Intent
     * in the alarm manager
     */
    private void stopKeepAlives() {
            Intent i = new Intent();
            i.setClass(this, MqttService.class);
            i.setAction(ACTION_KEEPALIVE);
            PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
            mAlarmManager.cancel(pi);
    }
    /**
     * Publishes a KeepALive to the topic
     * in the broker
     */
    private synchronized void keepAlive() {
            if(isConnected()) {
                    try {
                            sendKeepAlive();
                            sendMqqtStateMessage(1,"保持连接");

                            return;
                    } catch(MqttConnectivityException ex) {
                            ex.printStackTrace();
                            reconnectIfNecessary();
                    } catch(MqttPersistenceException ex) {
                            ex.printStackTrace();
                            stop();
                    } catch(MqttException ex) {
                            ex.printStackTrace();
                            stop();
                    }
            }
    }
    /**
     * Checkes the current connectivity
     * and reconnects if it is required.
     */
    private synchronized void reconnectIfNecessary() {
            if(mStarted && mClient == null) {
                    connect();
            }
    }
    /**
     * Query's the NetworkInfo via ConnectivityManager
     * to return the current connected state
     * @return boolean true if we are connected false otherwise
     */
    private boolean isNetworkAvailable() {
            NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();

            return (info == null) ? false : info.isConnected();
    }
    /**
     * Verifies the client State with our local connected state
     * @return true if its a match we are connected false if we aren't connected
     */
    private boolean isConnected() {
            if(mStarted && mClient != null && !mClient.isConnected()) {
                    Log.i(DEBUG_TAG,"Mismatch between what we think is connected and what is connected");
            }

            if(mClient != null) {
                    return (mStarted && mClient.isConnected()) ? true : false;
            }

            return false;
    }
    /**
     * Receiver that listens for connectivity chanes
     * via ConnectivityManager
     */
    private final BroadcastReceiver mConnectivityReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                    Log.i(DEBUG_TAG,"Connectivity Changed...");
            }
    };
    /**
     * Sends a Keep Alive message to the specified topic
     * @return MqttDeliveryToken specified token you can choose to wait for completion
     */
    private synchronized MqttDeliveryToken sendKeepAlive()
    throws MqttConnectivityException, MqttPersistenceException, MqttException {
            if(!isConnected())
                    throw new MqttConnectivityException();

            if(mKeepAliveTopic == null) {
                    mKeepAliveTopic = mClient.getTopic(
                            String.format(Locale.US, MQTT_KEEP_ALIVE_TOPIC_FORAMT,mDeviceId));
            }

//            Log.i(DEBUG_TAG,"Sending Keepalive to " + MQTT_BROKER);

            MqttMessage message = new MqttMessage(MQTT_KEEP_ALIVE_MESSAGE);
            message.setQos(MQTT_KEEP_ALIVE_QOS);

            return mKeepAliveTopic.publish(message);
    }
    /**
     * Query's the AlarmManager to check if there is
     * a keep alive currently scheduled
     * @return true if there is currently one scheduled false otherwise
     */
    private synchronized boolean hasScheduledKeepAlives() {
            Intent i = new Intent();
            i.setClass(this, MqttService.class);
            i.setAction(ACTION_KEEPALIVE);
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_NO_CREATE);

            return (pi != null) ? true : false;
    }


    @Override
    public IBinder onBind(Intent arg0) {
            return null;
    }
    /**
     * Connectivity Lost from broker
     */
    @Override
    public void connectionLost(Throwable arg0) {
            stopKeepAlives();

            mClient = null;

            if(isNetworkAvailable()) {
                    reconnectIfNecessary();
            }
    }
    /**
     * Publish Message Completion
     */
    @Override
    public void deliveryComplete(MqttDeliveryToken arg0) {

    }
    /**
     * Received Message from broker
     */
    @Override
    public void messageArrived(MqttTopic topic, MqttMessage message)
    throws Exception {
        String msg = new String(message.getPayload(),"GBK");
        Log.i(DEBUG_TAG,"  Topic:\t" + topic.getName() +
                  "  Message:\t" + msg +
                  "  QoS:\t" + message.getQos());
        try {
            final ModelMqtt task = GsonTools.getObjectData(msg, ModelMqtt.class);
            //消息栏通知
            showNotification(task.getContent());
        }
        catch (Exception ex){

        }
        //android事件通知
        sendBroadCastMessage(msg);
    }
    public static String NOTIFICATION_BROADCAST = "com.cn.jyz.ironshoes.MqqtBroadCost";
    public void sendBroadCastMessage(String text){
        Intent intent = new Intent();
        intent.setAction(NOTIFICATION_BROADCAST);
        intent.putExtra("msg", text);
        this.sendOrderedBroadcast(intent, null); //有序广播发送
    }
    //发送mqtt状态信息
    public void sendMqqtStateMessage(int state,String stateStr){
        mqttState = state;
        mqttStateStr = stateStr;

        Intent intent = new Intent();
        intent.setAction(NOTIFICATION_BROADCAST);
        intent.putExtra("mqttState", mqttState);
        intent.putExtra("mqttStateStr", mqttStateStr);
        Log.e("MqttService","mqttState:"+mqttState+" mqttStateStr:"+mqttStateStr);
        this.sendOrderedBroadcast(intent, null); //有序广播发送
    }

    private void showNotification(String text) {
        Notification n = new Notification();

        //n.flags |= Notification.FLAG_SHOW_LIGHTS;
        n.flags |= Notification.FLAG_AUTO_CANCEL;
        n.defaults=Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND;
        n.ledARGB = Color.BLUE;
        n.ledOnMS =5000; //闪光时间，毫秒

        n.icon = R.mipmap.ic_launcher;
        n.when = System.currentTimeMillis();

        // Simply open the parent activit
        PendingIntent pi = PendingIntent.getActivity(this, 0,
                new Intent(this, ActivityLogin.class), 0);

        String mTitle = getResources().getString(R.string.app_name);
        if (Build.VERSION.SDK_INT <16) {
            Class clazz = n.getClass();
            try {
                Method m2 = clazz.getDeclaredMethod("setLatestEventInfo", Context.class,CharSequence.class,CharSequence.class,PendingIntent.class);
                m2.invoke(n, this, mTitle,text, pi);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        else
        {
            n = new Notification.Builder(this)
                    .setAutoCancel(true)
                    .setContentTitle(mTitle)
                    .setContentText(text)
                    .setContentIntent(pi)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND)
                    .build();
        }
        notificationId++;
        mNotifMan.notify(notificationId, n);
    }

    /**
     * MqttConnectivityException Exception class
     */
    private class MqttConnectivityException extends Exception {
    private static final long serialVersionUID = -7385866796799469420L;
    }
}
