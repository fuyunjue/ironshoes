package com.cn.jyz.ironshoes.skybeacon;

import java.text.SimpleDateFormat;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.cn.jyz.ironshoes.R;
import com.skybeacon.sdk.RangingBeaconsListener;
import com.skybeacon.sdk.ScanServiceStateCallback;
import com.skybeacon.sdk.decryption.DecryptProcess;
import com.skybeacon.sdk.locate.SKYBeacon;
import com.skybeacon.sdk.locate.SKYBeaconManager;
import com.skybeacon.sdk.locate.SKYRegion;
import com.skybeacon.sdk.utils.DataConvertUtils;

@SuppressLint({ "HandlerLeak", "SimpleDateFormat" })
public class ScanActivity extends Activity {
	private final int UPDATE_LIST_VIEW = 1;
	// listview
	private ListView listView;
	private LeDeviceListAdapter leDeviceListAdapter;

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case UPDATE_LIST_VIEW:
				leDeviceListAdapter.addDevice((iBeaconView) msg.obj);
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
		setContentView(R.layout.activity_scan);
		SKYBeaconManager.getInstance().init(this);
		SKYBeaconManager.getInstance().setCacheTimeMillisecond(2000);
		SKYBeaconManager.getInstance().setScanTimerIntervalMillisecond(1000);
		// 不设置默认－59
		// SKYBeaconManager.getInstance().setPhoneMeasurePower(-60);
		// SKYBeaconManager.getInstance().setNearbyTriggerThreshold(SKYBeaconNearbyThreshold.NEAR_1_METER);
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
		// TODO ListAdapter的notify更新必须减慢，不然影响点击事件
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				ListView lview = (ListView) arg0;
				final iBeaconView beacon = (iBeaconView) lview.getItemAtPosition(arg2);
				final String deviceAddress = beacon.mac;

				if (beacon.isMultiIDs) {
					Intent intent = new Intent(ScanActivity.this, ConfigMultiIDsActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("deviceAddress", deviceAddress);
					intent.putExtras(bundle);
					startActivity(intent);
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(ScanActivity.this);
					builder.setIcon(R.mipmap.ic_launcher);
					builder.setTitle("选择操作");
					// 指定下拉列表的显示数据
					final String[] cities = { "配置参数", "空中升级" };
					// 设置一个下拉的列表选择项
					builder.setItems(cities, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							if (arg1 == 0) {
								Intent intent = new Intent(ScanActivity.this, ConfigSingleIDActivity.class);
								Bundle bundle = new Bundle();
								bundle.putString("deviceAddress", deviceAddress);
								intent.putExtras(bundle);
								startActivity(intent);
							} else {
								Intent intent = new Intent(ScanActivity.this, OADActivity.class);
								Bundle bundle = new Bundle();
								bundle.putString("deviceAddress", deviceAddress);
								bundle.putBoolean("oadMode", beacon.oadMode);
								intent.putExtras(bundle);
								startActivity(intent);
							}
						}
					});
					builder.show();
				}
			}
		});
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

				// TODO Auto-generated method stub
				// System.out.println("RangingBeacons！！！！！！！！！！");
				for (int i = 0; i < beaconList.size(); i++) {
					// if (beaconList.get(i).getHardwareVersion() == 5 ||
					// beaconList.get(i).getHardwareVersion() == 8) {
					iBeaconView beacon = new iBeaconView();
					SKYBeacon tmp = (SKYBeacon) beaconList.get(i);
					beacon.mac = ((SKYBeacon) beaconList.get(i)).getDeviceAddress();
					beacon.rssi = ((SKYBeacon) beaconList.get(i)).getRssi();
					beacon.oadMode = ((SKYBeacon) beaconList.get(i)).isOADMode();
					// if (beacon.mac.equals("19:18:FC:03:BA:B7")) {
					SKYBeacon tmpTest = new SKYBeacon((SKYBeacon) beaconList.get(i));
					byte[] major = DataConvertUtils.intToByteArrayTwo(tmpTest.getMajor());
					byte[] minor = DataConvertUtils.intToByteArrayTwo(tmpTest.getMinor());
					byte[] mac = DataConvertUtils.macStringToBytes(tmpTest.getDeviceAddress());
					DecryptProcess.getMajorMinorMacV3(major, minor, mac);
					tmpTest.setMajor(DataConvertUtils.byte2ToInt(major));
					tmpTest.setMinor(DataConvertUtils.byte2ToInt(minor));
					tmpTest.setDeviceAddressDecrypt(DataConvertUtils.bytesToMacString(mac));
					// if (tmpTest.getDeviceModel().equals("S4LR_RTC")) {
					System.out.println("RangingBeacons！！！！！！！！！！");
					beacon.isMultiIDs = false;
					beacon.detailInfo += "Mac解密后" + tmpTest.getDeviceAddressDecrypt() + "\r\n";
					beacon.detailInfo += "设备型号：" + tmpTest.getDeviceModel() + "\r\n";
					beacon.detailInfo += tmpTest.getProximityUUID() + "\r\nMajor: " + String.valueOf(tmpTest.getMajor()) + "\tMinir: " + String.valueOf(tmpTest.getMinor()) + "\r\n";
					beacon.detailInfo += "version: " + String.valueOf(tmpTest.getHardwareVersion()) + "." + String.valueOf(tmpTest.getFirmwareVersionMajor()) + "."
							+ String.valueOf(tmpTest.getFirmwareVersionMinor()) + "\r\n";
					if (tmpTest.getIsOAD() == 1) {
						beacon.detailInfo += "是否支持OAD：是\r\n";
					} else {
						beacon.detailInfo += "是否支持OAD：否\r\n";
					}
					if (tmpTest.isOADMode()) {
						beacon.detailInfo += "是否处于OAD模式下：是\r\n";
					} else {
						beacon.detailInfo += "是否处于OAD模式下：否\r\n";
						if (tmpTest.getiBeaconDate() != null) {
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String dateString = formatter.format(tmpTest.getiBeaconDate());
							beacon.detailInfo += "beacon时间：" + dateString + "  毫秒时间戳：" + String.valueOf(tmpTest.getiBeaconMillisecond()) + "\r\n";
						}
					}
					if (tmpTest.isEncrypted() == 1) {
						beacon.detailInfo += "是否防蹭用：是\r\n";
					} else {
						beacon.detailInfo += "是否防蹭用：否\r\n";
					}
					if (tmpTest.isLocked() == 1) {
						beacon.detailInfo += "是否防篡改：是\r\n";
					} else {
						beacon.detailInfo += "是否防篡改：否\r\n";
					}
					beacon.detailInfo += "电量：" + String.valueOf(tmpTest.getBattery()) + "\r\n";
					beacon.detailInfo += "距离：" + String.valueOf(tmpTest.getDistance()) + "\r\n";
					beacon.detailInfo += "设备名称：" + ((SKYBeacon) beaconList.get(i)).getDeviceName();
					Message msg = new Message();
					msg.obj = beacon;
					msg.what = UPDATE_LIST_VIEW;
					mHandler.sendMessage(msg);
					// }
					// }
					// }
				}

			}
		});
	}

	private void stopRanging() {
		SKYBeaconManager.getInstance().stopScanService();
		SKYBeaconManager.getInstance().stopRangingBeasons(null);
	}
}
