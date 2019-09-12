package com.cn.jyz.ironshoes.skybeacon;

import com.cn.jyz.ironshoes.R;
import com.skybeacon.sdk.OADProcessCallback;
import com.skybeacon.sdk.config.SKYBeaconConfigException;
import com.skybeacon.sdk.config.SKYBeaconOAD;
import com.skybeacon.sdk.locate.SKYBeacon;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class OADActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.ti.ble.sensortag.MESSAGE";
	public static final String FW_CUSTOM_DIRECTORY = Environment.DIRECTORY_DOWNLOADS;
	public static final int FILE_ACTIVITY_REQ = 0;

	private String deviceAddress = "";
	private boolean oadMode = false;
	String filePath = "";

	TextView deviceInfoView;
	TextView imageModeView;
	TextView imageSwitchView;
	Button imageSwitchBt;
	Button fileSelectBt;
	TextView fileSelectView;
	Button upgradeBt;
	TextView upgradeView;
	SKYBeaconOAD skyBeaconOAD;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_oad);
		deviceAddress = getIntent().getExtras().getString("deviceAddress");
		oadMode = getIntent().getExtras().getBoolean("oadMode");

		deviceInfoView = (TextView) findViewById(R.id.device_info);
		imageModeView = (TextView) findViewById(R.id.image_mode_view);
		imageSwitchView = (TextView) findViewById(R.id.image_mode_switch_info);
		imageSwitchBt = (Button) findViewById(R.id.image_mode_switch);
		fileSelectBt = (Button) findViewById(R.id.file_select);
		fileSelectView = (TextView) findViewById(R.id.file_select_view);
		upgradeBt = (Button) findViewById(R.id.upgrade_bt);
		upgradeView = (TextView) findViewById(R.id.upgrade_infos);

		deviceInfoView.setText(deviceAddress);
		if (oadMode) {
			imageModeView.setText("ImageA");
		} else {
			imageModeView.setText("ImageB");
		}
		// skyBeaconOAD = new SKYBeaconOAD(this);
		// skyBeaconOAD.setOnOADProcessCallback(onOadProcessCallback);
	}

	OADProcessCallback onOadProcessCallback = new OADProcessCallback() {

		@Override
		public void onProcessCallback(final long fileSize, final long writeSize, final int percent) {
			// TODO Auto-generated method stub
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					upgradeView.setText(String.valueOf(fileSize) + "\r\n" + String.valueOf(writeSize) + "\r\n" + String.valueOf(percent));

				}
			});
		}

		@Override
		public void onOADSuccess(final long fileSize, final long writeSize) {
			// TODO Auto-generated method stub
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					upgradeBt.setText("升级");
					upgradeView.setText(String.valueOf(fileSize) + "\r\n" + String.valueOf(writeSize) + "\r\n升级成功");
				}
			});
		}

		@Override
		public void onOADFailed(final long fileSize, final long writeSize, final int errorCode, final String errorMsg) {
			// TODO Auto-generated method stub
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					upgradeBt.setText("升级");
					upgradeView.setText(String.valueOf(fileSize) + "\r\n" + String.valueOf(writeSize) + "\r\n" + String.valueOf(errorCode) + "\r\n" + errorMsg);

				}
			});
		}

		@Override
		public void onImageSwitch(boolean isInOADMode, boolean isSwitchSuccess, final int errorCode, final String errorMsg) {
			// TODO Auto-generated method stub
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					imageSwitchBt.setText("切换");
				}
			});
			if (isInOADMode) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						imageModeView.setText("iMageA");
					}
				});
			} else {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						imageModeView.setText("iMageB");
					}
				});
			}

			if (isSwitchSuccess) {
				oadMode = !oadMode;
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						imageSwitchView.setText("switch success");
					}
				});
			} else {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						imageSwitchView.setText("switch failed：" + String.valueOf(errorCode));
					}
				});
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Check which request we're responding to
		if (requestCode == FILE_ACTIVITY_REQ) {
			// Make sure the request was successful
			if (resultCode == RESULT_OK) {
				filePath = data.getStringExtra(FileActivity.EXTRA_FILENAME);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						fileSelectView.setText(filePath);
					}
				});
			}
		}
	}

	public void onClick(View v) {
		boolean result = false;
		SKYBeacon skyBeacon = new SKYBeacon(deviceAddress);
		switch (v.getId()) {
		case R.id.image_mode_switch:
			skyBeaconOAD = new SKYBeaconOAD(this);
			skyBeaconOAD.setOnOADProcessCallback(onOadProcessCallback);
			// TODO Auto-generated method stub
			if (oadMode) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						imageSwitchBt.setText("切换中");
					}
				});
				result = skyBeaconOAD.setBeaconMode(skyBeacon, "123456");
			} else {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						imageSwitchBt.setText("切换中");
					}
				});
				result = skyBeaconOAD.setOADMode(skyBeacon, "123456");
			}
			break;
		case R.id.file_select:
			// TODO Auto-generated method stub
			Intent i = new Intent(this, FileActivity.class);
			i.putExtra(EXTRA_MESSAGE, FW_CUSTOM_DIRECTORY);
			startActivityForResult(i, FILE_ACTIVITY_REQ);
			break;
		case R.id.upgrade_bt:
			// TODO Auto-generated method stub
			skyBeaconOAD = new SKYBeaconOAD(this);
			skyBeaconOAD.setOnOADProcessCallback(onOadProcessCallback);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					upgradeBt.setText("升级中");
					upgradeView.setText("");
				}
			});

			result = skyBeaconOAD.oadProcess(skyBeacon, filePath);
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		skyBeaconOAD.disconnect();
	}
}
