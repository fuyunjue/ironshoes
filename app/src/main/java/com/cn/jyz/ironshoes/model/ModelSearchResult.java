package com.cn.jyz.ironshoes.model;

import java.io.Serializable;
import java.util.List;

public class ModelSearchResult extends ModelBase implements Serializable {
	
	private static final long serialVersionUID = -2528537181954099884L;
	
	private List<SearchInfo> data;
	
	public List<SearchInfo> getData () {
		return data;
	}
	
	public void setData (List<SearchInfo> data) {
		this.data = data;
	}
	
	public class SearchInfo {
		private String patrolman;//	String	巡检人
		private String orientation;//	String	安装位置
		private String removePeriodTime;//	String	撤防时间
		private String removePerson;//	String	撤防作业人员
		private String movePeriodTime;//	String	设防时间
		private String moveGroup;//	String	设防班组
		private String patrolTime;//	String	巡检时间
		private String location;//	String	股道
		private String trainNumber;//	String	机车车号
		private String txCode;//	Array	铁鞋Code
		private String patrolResult;//	String	检查结果
		private String movePerson;//	String	设防作业人员
		
		public String getPatrolman () {
			return patrolman;
		}
		
		public void setPatrolman (String patrolman) {
			this.patrolman = patrolman;
		}
		
		public String getOrientation () {
			return orientation;
		}
		
		public void setOrientation (String orientation) {
			this.orientation = orientation;
		}
		
		public String getRemovePeriodTime () {
			return removePeriodTime;
		}
		
		public void setRemovePeriodTime (String removePeriodTime) {
			this.removePeriodTime = removePeriodTime;
		}
		
		public String getRemovePerson () {
			return removePerson;
		}
		
		public void setRemovePerson (String removePerson) {
			this.removePerson = removePerson;
		}
		
		public String getMovePeriodTime () {
			return movePeriodTime;
		}
		
		public void setMovePeriodTime (String movePeriodTime) {
			this.movePeriodTime = movePeriodTime;
		}
		
		public String getMoveGroup () {
			return moveGroup;
		}
		
		public void setMoveGroup (String moveGroup) {
			this.moveGroup = moveGroup;
		}
		
		public String getPatrolTime () {
			return patrolTime;
		}
		
		public void setPatrolTime (String patrolTime) {
			this.patrolTime = patrolTime;
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
		
		public String getPatrolResult () {
			return patrolResult;
		}
		
		public void setPatrolResult (String patrolResult) {
			this.patrolResult = patrolResult;
		}
		
		public String getMovePerson () {
			return movePerson;
		}
		
		public void setMovePerson (String movePerson) {
			this.movePerson = movePerson;
		}
	}
}
