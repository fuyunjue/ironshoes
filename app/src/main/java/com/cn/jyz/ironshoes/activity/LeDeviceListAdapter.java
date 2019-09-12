package com.cn.jyz.ironshoes.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cn.jyz.ironshoes.R;
import com.cn.jyz.ironshoes.ibeacon.iBeaconView;

public class LeDeviceListAdapter extends BaseAdapter {

	// Adapter for holding devices found through scanning.

	private ArrayList<iBeaconView> mLeDevices;
	// private Map<String, Integer> mDeviceState = new HashMap<String,
	// Integer>();
	private LayoutInflater mInflator;
	private Activity mContext;
	private Object lock;

	private List<Integer> mCheckList = null;

	public LeDeviceListAdapter(Activity c) {
		super();
		mContext = c;
		mLeDevices = new ArrayList<iBeaconView>();
		mInflator = mContext.getLayoutInflater();
		lock = new Object();
	}

	@SuppressLint("UseValueOf")
	public void setCheckall(boolean isCheckall) {
		if (mCheckList == null) {
			mCheckList = new ArrayList<Integer>();
		}
		if (isCheckall) {
			for (int i = 0; i < mLeDevices.size(); i++) {
				mCheckList.add(new Integer(i));
			}
		} else {
			mCheckList.clear();
		}
	}

	public void addDevice(iBeaconView device) {
		if (device == null) {
			return;
		}

		synchronized (lock) {
			for (int i = 0; i < mLeDevices.size(); i++) {
				String btAddress = mLeDevices.get(i).mac;
				if (btAddress.equals(device.mac)) {
					mLeDevices.get(i).reset(device);
					return;
				}
			}
			mLeDevices.add(device);
			//排序
			Collections.sort(mLeDevices, new Comparator<iBeaconView>() {
				@Override
				public int compare(iBeaconView u1, iBeaconView u2) {
					double diff = u1.Distance - u2.Distance;
					if (diff * 100 > 0) {
						return 1;
					}else if (diff * 100 < 0) {
						return -1;
					}
					return 0; //相等为0
				}
			});
			
		}
	}

	// public void setDeivceState(int position, int state) {
	// mDeviceState.put(mLeDevices.get(position), state);
	// }

	public iBeaconView getDevice(int position) {
		return mLeDevices.get(position);
	}

	public void clear() {
		synchronized (lock) {
			mLeDevices.clear();
		}
		if (mCheckList != null) {
			mCheckList.clear();
		}
	}

	@Override
	public int getCount() {
		return mLeDevices.size();
	}

	@Override
	public Object getItem(int i) {
		return mLeDevices.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		ViewHolder viewHolder;
		// General ListView optimization code.
		if (view == null) {
			view = mInflator.inflate(R.layout.listview_scan, null);
			viewHolder = new ViewHolder();
			viewHolder.deviceText = (TextView) view.findViewById(R.id.device_text);
			viewHolder.deviceRssi = (TextView) view.findViewById(R.id.device_rssi);
			viewHolder.deviceVersion = (TextView) view.findViewById(R.id.device_version);
			viewHolder.deviceDetail = (TextView) view.findViewById(R.id.device_detail);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		iBeaconView beacon = mLeDevices.get(i);
		viewHolder.deviceText.setText(beacon.mac);

		if (beacon.weight != 0) {
			viewHolder.deviceVersion.setText(String.valueOf(beacon.weight));
		} else {
			if (beacon.isMultiIDs) {
				viewHolder.deviceVersion.setText("多");
			} else {
				viewHolder.deviceVersion.setText("单");
			}
		}
		viewHolder.deviceDetail.setText(beacon.detailInfo);
		viewHolder.deviceRssi.setText(String.valueOf(beacon.rssi));
		return view;
	}

	class ViewHolder {
		TextView deviceText;
		TextView deviceRssi;
		TextView deviceVersion;
		TextView deviceDetail;
	}
}
