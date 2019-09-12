package com.cn.jyz.ironshoes.ibeacon;


public class iBeaconView {
	public String mac = "";
	public int rssi = -1;
	public String detailInfo = "";
	public boolean isMultiIDs = false;
	public float weight = (float) 0.00;
	public boolean oadMode = false;
	public double Distance = 0.00;
	
	public void reset(iBeaconView beacon) {
		this.mac = beacon.mac;
		this.rssi = beacon.rssi;
		this.detailInfo = beacon.detailInfo;
		this.isMultiIDs = beacon.isMultiIDs;
		this.weight = beacon.weight;
	}
}

