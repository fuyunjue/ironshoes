package com.cn.jyz.ironshoes.model;

import java.io.Serializable;
import java.util.List;

/**
 * 作业记录
 */
public class ModelTaskRecord extends ModelBase implements Serializable {
	
	private static final long serialVersionUID = -4939090599483090115L;
	
	private List<TaskRecord> data;
	
	public List<TaskRecord> getData () {
		return data;
	}
	
	public void setData (List<TaskRecord> data) {
		this.data = data;
	}
	
	public class TaskRecord {
		private String executeDate;         //实施日期
		private String executeTime;         //实施时刻
		private String location;         //股道
		private String leftTxName;         //左铁鞋
		private String txNumber;         //车号
		private String rightTxName;         //右铁鞋
		private String executor;         //实施人
		private String executeSignature;         //实施签章
		private String examiner;         //检查人
		private String examineSignature;         //检查签章
		private String dismantleDate;         //防滑撤除日期
		private String dismantleTime;         //防滑撤除时间
		private String dismantleUser;         //撤除人
		private String dismantleSignature;         //防滑撤除签章
		private String patrolDate;         //巡检日期
		private String patrolTime;         //巡检时刻
		private String patrolResult;         //巡检结果
		private String patrolman;         //巡检人
		private String patrolSignature;         //巡检签章
		
		public String getExecuteDate () {
			return executeDate;
		}
		
		public void setExecuteDate (String executeDate) {
			this.executeDate = executeDate;
		}
		
		public String getExecuteTime () {
			return executeTime;
		}
		
		public void setExecuteTime (String executeTime) {
			this.executeTime = executeTime;
		}
		
		public String getLocation () {
			return location;
		}
		
		public void setLocation (String location) {
			this.location = location;
		}
		
		public String getLeftTxName () {
			return leftTxName;
		}
		
		public void setLeftTxName (String leftTxName) {
			this.leftTxName = leftTxName;
		}
		
		public String getTxNumber () {
			return txNumber;
		}
		
		public void setTxNumber (String txNumber) {
			this.txNumber = txNumber;
		}
		
		public String getRightTxName () {
			return rightTxName;
		}
		
		public void setRightTxName (String rightTxName) {
			this.rightTxName = rightTxName;
		}
		
		public String getExecutor () {
			return executor;
		}
		
		public void setExecutor (String executor) {
			this.executor = executor;
		}
		
		public String getExecuteSignature () {
			return executeSignature;
		}
		
		public void setExecuteSignature (String executeSignature) {
			this.executeSignature = executeSignature;
		}
		
		public String getExaminer () {
			return examiner;
		}
		
		public void setExaminer (String examiner) {
			this.examiner = examiner;
		}
		
		public String getExamineSignature () {
			return examineSignature;
		}
		
		public void setExamineSignature (String examineSignature) {
			this.examineSignature = examineSignature;
		}
		
		public String getDismantleDate () {
			return dismantleDate;
		}
		
		public void setDismantleDate (String dismantleDate) {
			this.dismantleDate = dismantleDate;
		}
		
		public String getDismantleTime () {
			return dismantleTime;
		}
		
		public void setDismantleTime (String dismantleTime) {
			this.dismantleTime = dismantleTime;
		}
		
		public String getDismantleUser () {
			return dismantleUser;
		}
		
		public void setDismantleUser (String dismantleUser) {
			this.dismantleUser = dismantleUser;
		}
		
		public String getDismantleSignature () {
			return dismantleSignature;
		}
		
		public void setDismantleSignature (String dismantleSignature) {
			this.dismantleSignature = dismantleSignature;
		}
		
		public String getPatrolDate () {
			return patrolDate;
		}
		
		public void setPatrolDate (String patrolDate) {
			this.patrolDate = patrolDate;
		}
		
		public String getPatrolTime () {
			return patrolTime;
		}
		
		public void setPatrolTime (String patrolTime) {
			this.patrolTime = patrolTime;
		}
		
		public String getPatrolResult () {
			return patrolResult;
		}
		
		public void setPatrolResult (String patrolResult) {
			this.patrolResult = patrolResult;
		}
		
		public String getPatrolman () {
			return patrolman;
		}
		
		public void setPatrolman (String patrolman) {
			this.patrolman = patrolman;
		}
		
		public String getPatrolSignature () {
			return patrolSignature;
		}
		
		public void setPatrolSignature (String patrolSignature) {
			this.patrolSignature = patrolSignature;
		}
	}
}
