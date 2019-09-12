package com.cn.jyz.ironshoes.skybeacon;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import com.cn.jyz.ironshoes.R;
import com.skybeacon.sdk.RangingBeaconsListener;
import com.skybeacon.sdk.ScanServiceStateCallback;
import com.skybeacon.sdk.locate.SKYBeacon;
import com.skybeacon.sdk.locate.SKYBeaconManager;
import com.skybeacon.sdk.locate.SKYBeaconMultiIDs;
import com.skybeacon.sdk.locate.SKYBeaconNearbyThreshold;
import com.skybeacon.sdk.locate.SKYRegion;

@SuppressLint("HandlerLeak")
public class PushActivity extends Activity {
	private final int UPDATE_LIST_VIEW = 1;
	private final int CLEAR_LIST_VIEW = 2;
	// listview
	private ListView listView;
	private LeDeviceListAdapter leDeviceListAdapter;
	// Timer timer;
	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case UPDATE_LIST_VIEW:
				leDeviceListAdapter.addDevice((iBeaconView) msg.obj);
				leDeviceListAdapter.notifyDataSetChanged();
				break;
			case CLEAR_LIST_VIEW:
				leDeviceListAdapter.clear();
				leDeviceListAdapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_push);
		SKYBeaconManager.getInstance().init(this);
		// SKYBeaconManager.getInstance().setCacheTimeMillisecond(2000);
		SKYBeaconManager.getInstance().setScanTimerIntervalMillisecond(1500);
		// 不设置默认－59
		SKYBeaconManager.getInstance().setPhoneMeasuredPower(-55);
		SKYBeaconManager.getInstance().setNearbyTriggerThreshold(SKYBeaconNearbyThreshold.NEAR_3_METER);
		// timer = new Timer();
		// timer.scheduleAtFixedRate(new TimerTask(), 10, 1500);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onStart();
		initListView();
		startRanging();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onStop();
		stopRanging();
	}

	private void initListView() {
		listView = (ListView) findViewById(R.id.listview_scan);
		leDeviceListAdapter = new LeDeviceListAdapter(this);
		listView.setAdapter(leDeviceListAdapter);
	}

	private void startRanging() {
		SKYBeaconManager.getInstance().startScanService(new ScanServiceStateCallback() {

			@Override
			public void onServiceDisconnected() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onServiceConnected() {
				// TODO Auto-generated method stub
				SKYBeaconManager.getInstance().startRangingBeacons(null);
			}
		});

		SKYBeaconManager.getInstance().setRangingBeaconsListener(new RangingBeaconsListener() {

			@Override
			public void onRangedNearbyBeacons(SKYRegion beaconRegion, List beaconList) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onRangedBeaconsMultiIDs(SKYRegion beaconRegion, List beaconMultiIDsList) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onRangedBeacons(SKYRegion beaconRegion, List beaconList) {
				// TODO Auto-generated method stub
				Message msg1 = new Message();
				msg1.what = CLEAR_LIST_VIEW;
				mHandler.sendMessage(msg1);
				for (int i = 0; i < beaconList.size(); i++) {
					iBeaconView beacon = new iBeaconView();
					beacon.mac = ((SKYBeacon) beaconList.get(i)).getDeviceAddress();
					beacon.rssi = ((SKYBeacon)beaconList.get(i)).getRssi();
					// System.out.println("RangingBeacons！！！！！！！！！！");
					beacon.isMultiIDs = false;
					beacon.weight = ((SKYBeacon)beaconList.get(i)).getPushWeight();
					beacon.detailInfo = ((SKYBeacon)beaconList.get(i)).getProximityUUID() + "\r\nMajor: " + String.valueOf(((SKYBeacon)beaconList.get(i)).getMajor()) + "\tMinir: "
							+ String.valueOf(((SKYBeacon)beaconList.get(i)).getMinor()) + "\r\n";
					beacon.detailInfo += "version: " + String.valueOf(((SKYBeacon)beaconList.get(i)).getHardwareVersion()) + "." + String.valueOf(((SKYBeacon)beaconList.get(i)).getFirmwareVersionMajor()) + "."
							+ String.valueOf(((SKYBeacon)beaconList.get(i)).getFirmwareVersionMinor());
					Message msg = new Message();
					msg.obj = beacon;
					msg.what = UPDATE_LIST_VIEW;
					mHandler.sendMessage(msg);
				}
			}
		});
	}

	private void stopRanging() {
		SKYBeaconManager.getInstance().stopScanService();
		SKYBeaconManager.getInstance().stopRangingBeasons(null);
	}

	// private class TimerTask extends java.util.TimerTask {
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// Message msg = new Message();
	// msg.what = CLEAR_LIST_VIEW;
	// mHandler.sendMessage(msg);
	// }
	// }
}
