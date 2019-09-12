package com.cn.jyz.ironshoes.model;

import java.io.Serializable;

public class ModelMotorWithTiexieInfo extends ModelBase implements Serializable {
	
	private static final long serialVersionUID = -3388989080706787868L;
	
	private TiexieInfo motor;
	private TieXieDetail txL;   //左铁鞋信息
	private TieXieDetail txR;   //由铁鞋信息
	
	
	public TiexieInfo getMotor () {
		return motor;
	}
	
	public void setMotor (TiexieInfo motor) {
		this.motor = motor;
	}
	
	public TieXieDetail getTxL () {
		return txL;
	}
	
	public void setTxL (TieXieDetail txL) {
		this.txL = txL;
	}
	
	public TieXieDetail getTxR () {
		return txR;
	}
	
	public void setTxR (TieXieDetail txR) {
		this.txR = txR;
	}
	
	public class TiexieInfo {
		private String timeArrive;  //入库时间
		private String txInstall;  //铁鞋设防
		private String trainOrder;  //车次
		private String motorman;  //司机
		private String patrolTime;  //巡检时间
		private String rummager;  //坐标开始X
		private String trainNumber;  //车号
		private String region;  //配属局段
		
		
		public String getTimeArrive () {
			return timeArrive;
		}
		
		public void setTimeArrive (String timeArrive) {
			this.timeArrive = timeArrive;
		}
		
		public String getTxInstall () {
			return txInstall;
		}
		
		public void setTxInstall (String txInstall) {
			this.txInstall = txInstall;
		}
		
		public String getTrainOrder () {
			return trainOrder;
		}
		
		public void setTrainOrder (String trainOrder) {
			this.trainOrder = trainOrder;
		}
		
		public String getMotorman () {
			return motorman;
		}
		
		public void setMotorman (String motorman) {
			this.motorman = motorman;
		}
		
		public String getPatrolTime () {
			return patrolTime;
		}
		
		public void setPatrolTime (String patrolTime) {
			this.patrolTime = patrolTime;
		}
		
		public String getRummager () {
			return rummager;
		}
		
		public void setRummager (String rummager) {
			this.rummager = rummager;
		}
		
		public String getTrainNumber () {
			return trainNumber;
		}
		
		public void setTrainNumber (String trainNumber) {
			this.trainNumber = trainNumber;
		}
		
		public String getRegion () {
			return region;
		}
		
		public void setRegion (String region) {
			this.region = region;
		}
		
	}
	
	public class TieXieDetail {
		private String installationTime;    //安装时间
		private String tieXieCode;    //铁鞋编号
		private String patrolTimeQuantum;    //巡检时间
		private String loraState;    //Lora通信
		private String patrolPerson;    //巡检人员
		private String tieXieBattery;    //电量
		private String installationPerson;    //安装人员
		private String patrolResult;    //配属局段
		
		public String getInstallationTime () {
			return installationTime;
		}
		
		public void setInstallationTime (String installationTime) {
			this.installationTime = installationTime;
		}
		
		public String getTieXieCode () {
			return tieXieCode;
		}
		
		public void setTieXieCode (String tieXieCode) {
			this.tieXieCode = tieXieCode;
		}
		
		public String getPatrolTimeQuantum () {
			return patrolTimeQuantum;
		}
		
		public void setPatrolTimeQuantum (String patrolTimeQuantum) {
			this.patrolTimeQuantum = patrolTimeQuantum;
		}
		
		public String getLoraState () {
			return loraState;
		}
		
		public void setLoraState (String loraState) {
			this.loraState = loraState;
		}
		
		public String getPatrolPerson () {
			return patrolPerson;
		}
		
		public void setPatrolPerson (String patrolPerson) {
			this.patrolPerson = patrolPerson;
		}
		
		public String getTieXieBattery () {
			return tieXieBattery;
		}
		
		public void setTieXieBattery (String tieXieBattery) {
			this.tieXieBattery = tieXieBattery;
		}
		
		public String getInstallationPerson () {
			return installationPerson;
		}
		
		public void setInstallationPerson (String installationPerson) {
			this.installationPerson = installationPerson;
		}
		
		public String getPatrolResult () {
			return patrolResult;
		}
		
		public void setPatrolResult (String patrolResult) {
			this.patrolResult = patrolResult;
		}
	}
}
