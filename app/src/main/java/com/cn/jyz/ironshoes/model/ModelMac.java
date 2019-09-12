package com.cn.jyz.ironshoes.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelMac extends ModelBase implements Serializable {

	private ArrayList<MacInfo> data;
	
	public ArrayList<MacInfo> getData () {
		return data;
	}
	
	public void setData (ArrayList<MacInfo> data) {
		this.data = data;
	}
	
	public class MacInfo {
		private String txMac;      //数量
		private String txCode; //报警内容
		private String txName;  //报警设备
		private String txSite;   //提醒方式，默认为default震动
		private String txValue; //站场名称
		
		public String getTxMac () {
			return txMac;
		}
		
		public void setTxMac (String txMac) {
			this.txMac = txMac;
		}
		
		public String getTxCode () {
			return txCode;
		}
		
		public void setTxCode (String txCode) {
			this.txCode = txCode;
		}
		
		public String getTxName () {
			return txName;
		}
		
		public void setTxName (String txName) {
			this.txName = txName;
		}
		
		public String getTxSite () {
			return txSite;
		}
		
		public void setTxSite (String txSite) {
			this.txSite = txSite;
		}
		
		public String getTxValue () {
			return txValue;
		}
		
		public void setTxValue (String txValue) {
			this.txValue = txValue;
		}
	}
}
