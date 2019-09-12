package com.cn.jyz.ironshoes.model;

import java.io.Serializable;
import java.util.List;

/**
 * 巡检记录
 */
public class ModelPollingRecord extends ModelBase implements Serializable {
	private static final long serialVersionUID = -4713309030192733931L;
	
	private List<PollingRecord> data;
	
	public List<PollingRecord> getData () {
		return data;
	}
	
	public void setData (List<PollingRecord> data) {
		this.data = data;
	}
	
	public class PollingRecord {
		private String patrolDate;//1月9日",
		private String patrolTimeFrame;// "8:00至11:00",
		private String patrolman;//"王春华",
		private String location;//"5",
		private String trainNumber; //机车车号
		private String txCode;      //铁鞋编号
		private String txStatus;    //铁鞋状态
		private String txPatrolTime;    //巡检时间
		private String patrolResult;    //巡检结果
		private String rummager;    //检查人
		private String rummagerResult;  //检查结果
		
//		private String rightTxCode;//"TYTK5-2",
//		private String leftTxCode;//TYK5-1",
//		private String rightTxStatus;//1",
//		private int rummagerResult;// 1,
//		private String leftTxPatrolTime;// "8:11",
//		private String leftTxStatus;//"1",
//		private String rightTxPatrolTime;// "8:11",
//		private String rummager;//"韩振",
//		private int id;//1,
//		private String txNumber;// "HXD1C-0061",
//		private String patrolResult;// "1"
		
		
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
		
		public String getPatrolman () {
			return patrolman;
		}
		
		public void setPatrolman (String patrolman) {
			this.patrolman = patrolman;
		}
		
		public String getLocation () {
			return location;
		}
		
		public void setLocation (String location) {
			this.location = location;
		}
		
		public String getTrainNumber () {
			return trainNumber;
		}
		
		public void setTrainNumber (String trainNumber) {
			this.trainNumber = trainNumber;
		}
		
		public String getTxCode () {
			return txCode;
		}
		
		public void setTxCode (String txCode) {
			this.txCode = txCode;
		}
		
		public String getTxStatus () {
			return txStatus;
		}
		
		public void setTxStatus (String txStatus) {
			this.txStatus = txStatus;
		}
		
		public String getTxPatrolTime () {
			return txPatrolTime;
		}
		
		public void setTxPatrolTime (String txPatrolTime) {
			this.txPatrolTime = txPatrolTime;
		}
		
		public String getPatrolResult () {
			return patrolResult;
		}
		
		public void setPatrolResult (String patrolResult) {
			this.patrolResult = patrolResult;
		}
		
		public String getRummager () {
			return rummager;
		}
		
		public void setRummager (String rummager) {
			this.rummager = rummager;
		}
		
		public String getRummagerResult () {
			return rummagerResult;
		}
		
		public void setRummagerResult (String rummagerResult) {
			this.rummagerResult = rummagerResult;
		}
	}
}
