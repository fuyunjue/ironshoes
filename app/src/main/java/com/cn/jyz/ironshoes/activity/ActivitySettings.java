package com.cn.jyz.ironshoes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.cn.jyz.ironshoes.R;
import com.cn.jyz.ironshoes.application.AppApplication;
import com.cn.jyz.ironshoes.keeper.UserKeeper;
import com.cn.jyz.ironshoes.utils.MessageHelper;
import com.cn.jyz.ironshoes.utils.ToastUtil;
import com.cn.jyz.ironshoes.view.SuperEditText;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivitySettings extends AppCompatActivity {
	
	private Context mContext;
	
	private EditText et_port_mqtt;
	private EditText et_port;
	
	private EditText set_ip_mqtt;
	private SuperEditText set_ip;
	private CheckBox checkBox;

	private String ip = "";
	private String port = "";
	private String ip_mqtt = "";
	private String port_mqtt = "";
	
	@Override
	protected void onCreate (@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		mContext = this;
		
		if (AppApplication.getInstance().getMessageHelper().getUrls().get(MessageHelper.MQTT_IP).trim().length() > 0
				&& AppApplication.getInstance().getMessageHelper().getUrls().get(MessageHelper.MQTT_PORT).trim().length() > 0
				&& AppApplication.getInstance().getMessageHelper().getUrls().get(MessageHelper.BASE_URL).trim().length() > 0) {
			//TODO system配置文件里有配置，为debug模式，读取该配置，享有优先
			go2Login();
		} else if (UserKeeper.getStringValue(mContext, UserKeeper.IP) != null
				&& UserKeeper.getStringValue(mContext, UserKeeper.PORT) != null
					&& UserKeeper.getStringValue(mContext, UserKeeper.IP_MQTT) != null
						&& UserKeeper.getStringValue(mContext, UserKeeper.PORT_MQTT) != null) {
			go2Login();
		}
		
		et_port_mqtt = (EditText) findViewById(R.id.et_port_mqtt);
		et_port = (EditText) findViewById(R.id.et_port);
		set_ip_mqtt = (EditText) findViewById(R.id.set_ip_mqtt);
		set_ip = (SuperEditText) findViewById(R.id.set_ip);
		checkBox = (CheckBox) findViewById(R.id.checkBox);
		
		findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick (View view) {
				String es[] = set_ip.getSuperEditTextValue();
				
				for (String e : es) {
					if (e.length() == 0) {
						ToastUtil.showShort(mContext, "请输入IP");
						set_ip.requestFocus();
						return;
					}
					ip += e + ".";
				}
				
//				if (isIP(ip)) {
//					ToastUtil.showShort(mContext, "请输入正确的IP地址");
//					set_ip.requestFocus();
//					return;
//				}
				
				ip = ip.substring(0, ip.length() - 1);
				
				port = et_port.getText().toString();
				if (port.length() == 0) {
					ToastUtil.showShort(mContext, "请输入端口号");
					et_port.requestFocus();
					return;
				}
				
				//TODO 消息推送监听不做限制
				ip_mqtt = set_ip_mqtt.getText().toString().trim();
				if (ip_mqtt.length() == 0) {
					ToastUtil.showShort(mContext, "请输入推送监听服务器IP");
					set_ip_mqtt.requestFocus();
					return;
				}
//				String es_mqtt[] = set_ip_mqtt.getSuperEditTextValue();
//				for (String e : es_mqtt) {
//					if (e.length() == 0) {
//						ToastUtil.showShort(mContext, "请输入推送监听服务器IP");
//						set_ip_mqtt.requestFocus();
//						return;
//					}
//					ip_mqtt += e + ".";
//				}
//				ip_mqtt = ip_mqtt.substring(0, ip_mqtt.length() - 1);
				
				port_mqtt = et_port_mqtt.getText().toString();
				if (port_mqtt.length() == 0) {
					ToastUtil.showShort(mContext, "请输入推送监听服务器端口号");
					et_port_mqtt.requestFocus();
					return;
				}
				
					//判断正确，进入登录界面
					UserKeeper.SaveSharepreferenceByKey(mContext, UserKeeper.IP, ip);
					UserKeeper.SaveSharepreferenceByKey(mContext, UserKeeper.IP_MQTT, ip_mqtt);
					UserKeeper.SaveSharepreferenceByKey(mContext, UserKeeper.PORT_MQTT, port_mqtt);
					UserKeeper.SaveSharepreferenceByKey(mContext, UserKeeper.PORT, port);

					UserKeeper.SaveSharepreferenceByKey(mContext, "isLog", checkBox.isChecked()?"1":"0");
					
					go2Login();
			}
		});
	}
	
	/**
	 * 登录
	 */
	private void go2Login () {
		//设置参数
		Intent loginIntent = new Intent(mContext, ActivityLogin.class);
		startActivity(loginIntent);
		finish();
	}
	
	/**
	 * 判断是否为ip地址
	 *
	 * @param ip
	 * @return
	 */
	boolean isIpString(String ip) {
		boolean is = true;
		try {
			InetAddress ia = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			is = false;
		}
		return is;
	}
	
	public boolean isIP(String addr) {
		if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
			return false;
		}
		/**
		 * 判断IP格式和范围
		 */
		String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		Pattern pat = Pattern.compile(rexp);
		Matcher mat = pat.matcher(addr);
		boolean ipAddress = mat.find();
		//============对之前的ip判断的bug在进行判断
		if (ipAddress==true){
			String ips[] = addr.split("\\.");
			if(ips.length==4){
				try{
					for(String ip : ips){
						if(Integer.parseInt(ip)<0||Integer.parseInt(ip)>255){
							return false;
						}
					}
				}catch (Exception e){
					return false;
				}
				return true;
			}else{
				return false;
			}
		}
		return ipAddress;
	}
}
