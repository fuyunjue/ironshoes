package com.cn.jyz.ironshoes.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 站场信息
 */
public class ModelStation extends ModelBase implements Serializable {
	
	
	private static final long serialVersionUID = 126228887479084829L;
	
	private ArrayList<StationInfo> stations;    //站场集合
	
	public ArrayList<StationInfo> getStations () {
		return stations;
	}
	
	public void setStations (ArrayList<StationInfo> stations) {
		this.stations = stations;
	}
	
	public class StationInfo {
		public String stationno;//站场编号	返回站场编号，如1
		public String stationname;//站场名称	返回站场名称
		public String vectorFilePath;//矢量文件路径url
		
		
		public String getStationno () {
			return stationno;
		}
		
		public void setStationno (String stationno) {
			this.stationno = stationno;
		}
		
		public String getStationname () {
			return stationname;
		}
		
		public void setStationname (String stationname) {
			this.stationname = stationname;
		}
		
		public String getVectorFilePath () {
			return vectorFilePath;
		}
		
		public void setVectorFilePath (String vectorFilePath) {
			this.vectorFilePath = vectorFilePath;
		}
	}
}
