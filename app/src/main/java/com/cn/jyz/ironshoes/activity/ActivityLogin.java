package com.cn.jyz.ironshoes.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cn.jyz.ironshoes.R;
import com.cn.jyz.ironshoes.application.AppApplication;
import com.cn.jyz.ironshoes.emodel.EResponseCode;
import com.cn.jyz.ironshoes.keeper.UserKeeper;
import com.cn.jyz.ironshoes.model.ModelUserInfo;
import com.cn.jyz.ironshoes.model.ModelWelcomeImg;
//import com.cn.jyz.ironshoes.svg.GlideApp;
import com.cn.jyz.ironshoes.utils.AppUtil;
import com.cn.jyz.ironshoes.utils.AsyncHttpHelper;
import com.cn.jyz.ironshoes.utils.Constant;
import com.cn.jyz.ironshoes.utils.GsonTools;
import com.cn.jyz.ironshoes.utils.MessageHelper;
import com.cn.jyz.ironshoes.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * 登錄頁
 * 
 * @author Terence
 *
 */
public class ActivityLogin extends Activity implements OnClickListener {
	
	private Activity mActivity;
	
	private ProgressDialog progressDialog;
	
	private CheckBox cb_remenber_login;
	
	private EditText et_pwd;
	private EditText et_phone;
	
	private ImageView iv_login_logo;   //logo为动态加载
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mActivity = this;

		initView();
		initMain();
		
		if (AppApplication.getInstance().getMessageHelper().getUrls().get(MessageHelper.MQTT_IP).trim().length() > 0
				&& AppApplication.getInstance().getMessageHelper().getUrls().get(MessageHelper.MQTT_PORT).trim().length() > 0
				&& AppApplication.getInstance().getMessageHelper().getUrls().get(MessageHelper.BASE_URL).trim().length() > 0) {
			//TODO system配置文件里有配置，为debug模式，读取该配置，享有优先
			AppApplication.getInstance().setMqttServerIp(AppApplication.getInstance().getMessageHelper().getUrls().get(MessageHelper.MQTT_IP));
			AppApplication.getInstance().setMqttServerPort(AppApplication.getInstance().getMessageHelper().getUrls().get(MessageHelper.MQTT_PORT));
			AppApplication.getInstance().setBaseUrl(AppApplication.getInstance().getMessageHelper().getUrls().get(MessageHelper.BASE_URL));
			
			//启动mqtt监听
			AppApplication.getInstance().StartPushService();
		} else if (UserKeeper.getStringValue(mActivity, UserKeeper.IP) != null
				&& UserKeeper.getStringValue(mActivity, UserKeeper.PORT) != null
				&& UserKeeper.getStringValue(mActivity, UserKeeper.IP_MQTT) != null
				&& UserKeeper.getStringValue(mActivity, UserKeeper.PORT_MQTT) != null) {
			//TODO 经由服务IP配置配置过的，动态读取该配置，不享有优先
			AppApplication.getInstance().setBaseUrl(UserKeeper.getStringValue(mActivity, UserKeeper.IP) + ":" + UserKeeper.getStringValue(mActivity, UserKeeper.PORT));
			AppApplication.getInstance().setMqttServerIp(UserKeeper.getStringValue(mActivity, UserKeeper.IP_MQTT));
			AppApplication.getInstance().setMqttServerPort(UserKeeper.getStringValue(mActivity, UserKeeper.PORT_MQTT));
			
			//手动启动mqtt服务
			AppApplication.getInstance().StartPushService();
		} else {
			new AlertDialog.Builder(ActivityLogin.this)
					.setTitle(R.string.app_name)
					.setMessage("应用未配置服务器IP和端口")
					.setPositiveButton("去配置", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							Intent settingIntent = new Intent(mActivity, ActivitySettings.class);
							startActivity(settingIntent);
						}
					})
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							finish();
						}
					}).show();
		}

		if (Build.VERSION.SDK_INT >= 23) {
			int REQUEST_CODE_CONTACT = 101;
			String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
			//验证是否许可权限
			for (String str : permissions) {
				if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
					//申请权限
					this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
				}
			}
		}
	}
	
	void initView() {
		findViewById(R.id.btn_login).setOnClickListener(this);
		
		iv_login_logo = findViewById(R.id.iv_login_logo);
		cb_remenber_login = findViewById(R.id.cb_remenber_login);
		et_phone = findViewById(R.id.et_phone);
		et_pwd = findViewById(R.id.et_pwd);
	}
	
	void initMain() {
		String phone = UserKeeper.getStringValue(mActivity, UserKeeper.MOBILE);
		String remenber_login = UserKeeper.getStringValue(mActivity, UserKeeper.CB_REMENBER_PASSWORD);
		
		if (phone != null) {
			et_phone.setText(phone);
		}
		if (null != remenber_login && remenber_login.equals(Constant.REMENBER_PASSWPRD)) {
			String password = UserKeeper.getStringValue(mActivity, UserKeeper.PASSWORD);
			et_pwd.setText(password);
			cb_remenber_login.setChecked(true);
		}
		
		
		String imageResource = UserKeeper.getStringValue(mActivity, UserKeeper.IMAGE_RESOURCE);
		if (imageResource != null) {
			ModelWelcomeImg img = new Gson().fromJson(imageResource, new TypeToken<ModelWelcomeImg>() {}.getType());
			if (img != null) {
				String imageUrl = img.getLogo();
//				GlideApp.with(mActivity).load(imageUrl).into(iv_login_logo);
				Glide.with(mActivity).load(imageUrl).into(iv_login_logo);
			}
		}
		//TODO 动态更新logo链接地址配置为动态拼装
		new synWelcomeImgTask().execute();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_login:
				
				String phone = et_phone.getText().toString().trim();
				String password = et_pwd.getText().toString().trim();
				
				if (phone.length() == 0) {
					ToastUtil.showLong(this, R.string.phone_not_null);
				} else if (password.length() == 0) {
					ToastUtil.showLong(this, R.string.password_not_null);
				} else {
					//校验手机号码合法性
	//				if (AppUtil.isMobileNumberValid(phone)) {
	//
	//				}
					if (progressDialog == null || !progressDialog.isShowing())
						progressDialog = AppUtil.getProgressDialog(mActivity, getResources().getString(R.string.login_loading), false, false);
					doLogin(phone, password);
				}
				break;
			case R.id.iv_login_logo:
				
				break;
				default:
					break;
		}
	}
	
	void doLogin(String phone, String pwd) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("mobile", phone);
		params.put("password", pwd);
		
		AsyncHttpHelper.getInstance().post(mActivity, Constant.API_LOGIN, params, new AsyncHttpResponseHandler() {

					@Override
					public void onFailure (final int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
						UserKeeper.SaveSharepreferenceByKey(mActivity, UserKeeper.MOBILE, et_phone.getText().toString().trim());

						runOnUiThread(new Runnable() {
							@Override
							public void run () {
								if (progressDialog != null && progressDialog.isShowing())
									progressDialog.dismiss();

								ToastUtil.showShort(mActivity, "请求失败，请重试");
							}
						});
					}


					@Override
					public void onSuccess (int arg0, Header[] arg1, byte[] arg2) {
						UserKeeper.SaveSharepreferenceByKey(mActivity, UserKeeper.MOBILE, et_phone.getText().toString().trim());

						runOnUiThread(new Runnable() {
							@Override
							public void run () {
								if (progressDialog != null && progressDialog.isShowing())
									progressDialog.dismiss();
							}
						});

						final ModelUserInfo userInfo = GsonTools.getObjectData(new String(arg2), ModelUserInfo.class);
						if (userInfo.getCode() == EResponseCode.SUCCESS.getCode()) {

							if (cb_remenber_login.isChecked()) {
								UserKeeper.SaveSharepreferenceByKey(mActivity, UserKeeper.CB_REMENBER_PASSWORD, Constant.REMENBER_PASSWPRD);
								UserKeeper.SaveSharepreferenceByKey(mActivity, UserKeeper.PASSWORD, et_pwd.getText().toString().trim());
							}
							
							AppApplication.getInstance().setUserInfo(userInfo);
							
							Intent newIntent = new Intent(mActivity, MainActivity.class);
							startActivity(newIntent);
							finish();
						} else if (userInfo.getCode() == EResponseCode.WRONG.getCode()) {
							runOnUiThread(new Runnable() {
								@Override
								public void run () {
									ToastUtil.showShort(mActivity, userInfo.getMsg());
								}
							});
						}
					}
				});
	}
	
	private class synWelcomeImgTask extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... params) {
			MessageHelper helper = new MessageHelper(mActivity);
			return helper.sendPostGetWelComeImg();
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
				return;
			}
			
			UserKeeper.SaveSharepreferenceByKey(mActivity, UserKeeper.IMAGE_RESOURCE, result);
		}
	}
}
