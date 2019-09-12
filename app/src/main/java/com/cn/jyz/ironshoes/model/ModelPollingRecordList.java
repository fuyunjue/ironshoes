package com.cn.jyz.ironshoes.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 巡检记录-数据封装后
 */
public class ModelPollingRecordList extends ModelBase implements Serializable {
	
	private static final long serialVersionUID = -6067877979956127653L;
	
	private String patrolDate;    //巡检时间
	private String patrolTimeFrame; //巡检时段
//	private String trainNumber; //机车车号
//	private String location;    //轨道号
	
	private List<ModelPollingRecord.PollingRecord> data = new ArrayList<>();
	
//	public String getTrainNumber () {
//		return trainNumber;
//	}
//
//	public void setTrainNumber (String trainNumber) {
//		this.trainNumber = trainNumber;
//	}
//
//	public String getLocation () {
//		return location;
//	}
//
//	public void setLocation (String location) {
//		this.location = location;
//	}
	
	public String getPatrolDate () {
		return patrolDate;
	}
	
	public void setPatrolDate (String patrolDate) {
		this.patrolDate = patrolDate;
	}
	
	public String getPatrolTimeFrame () {
		return patrolTimeFrame;
	}
	
	public void setPatrolTimeFrame (String patrolTimeFrame) {
		this.patrolTimeFrame = patrolTimeFrame;
	}
	
	public List<ModelPollingRecord.PollingRecord> getData () {
		return data;
	}
	
	public void setData (List<ModelPollingRecord.PollingRecord> data) {
		this.data = data;
	}
}
