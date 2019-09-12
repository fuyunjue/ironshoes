package com.cn.jyz.ironshoes.skybeacon;

import java.util.ArrayList;
import java.util.List;

import com.cn.jyz.ironshoes.R;
import com.skybeacon.sdk.locate.SKYBeaconManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends Activity {
	private SharedPreferences mPreferences;
	private String passcodeStr;
	private EditText encryptKey;
	private Spinner spinnerKey;
	private List<String> mKeyList = null;
	private boolean firstIn = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_ble);

		mPreferences = getSharedPreferences("seekcyBeaconSDKDemo", MODE_PRIVATE);
		passcodeStr = mPreferences.getString("ENCRYPT_KEY", "");
		encryptKey = (EditText) findViewById(R.id.config_encrypt_key);
		encryptKey.setText(passcodeStr);
		if (passcodeStr != null && !passcodeStr.equals("")) {
			SKYBeaconManager.getInstance().setBroadcastKey(passcodeStr);
		}
		encryptKey.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				if (arg0.length() == 64 || arg0.length() == 0) {
					// 获得SharedPreferences 的Editor对象
					SharedPreferences.Editor editor = mPreferences.edit();
					// 修改数据
					editor.putString("ENCRYPT_KEY", String.valueOf(encryptKey.getText()));
					editor.commit();
					SKYBeaconManager.getInstance().setBroadcastKey(arg0.toString());
				}
			}
		});
		mKeyList = new ArrayList<String>();
		mKeyList.add("");
		mKeyList.add("");
		mKeyList.add("A5B51111222233334444579539C04181B2E3F58C232641D741D03EED5932409D");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_broadcast_id, R.id.spinner_broadcast_id_view, mKeyList);
		spinnerKey = (Spinner) findViewById(R.id.config_encrypt_key_spinner);
		spinnerKey.setAdapter(adapter);
		spinnerKey.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (!firstIn) {
					encryptKey.setText(mKeyList.get(arg2));
				} else {
					firstIn = false;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.bt_scan:
			SKYBeaconManager.getInstance().setBroadcastKey(passcodeStr);
			intent = new Intent(MainActivity.this, ScanActivity.class);
			startActivity(intent);
			break;
		case R.id.bt_monitor:
			SKYBeaconManager.getInstance().setBroadcastKey(passcodeStr);
			intent = new Intent(MainActivity.this, MonitorActivity.class);
			startActivity(intent);
			break;
		case R.id.bt_push:
			SKYBeaconManager.getInstance().setBroadcastKey(passcodeStr);
			intent = new Intent(MainActivity.this, PushActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
