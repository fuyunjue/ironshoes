package com.cn.jyz.ironshoes.skybeacon;

public class iBeaconView {
	public String mac = "";
	public int rssi = -1;
	public String detailInfo = "";
	public boolean isMultiIDs = false;
	public float weight = (float) 0.00;
	public boolean oadMode = false;

	public void reset(iBeaconView beacon) {
		this.mac = beacon.mac;
		this.rssi = beacon.rssi;
		this.detailInfo = beacon.detailInfo;
		this.isMultiIDs = beacon.isMultiIDs;
		this.weight = beacon.weight;
	}
}
