package com.cn.jyz.ironshoes.model;

import java.io.Serializable;

/**
 * Created by terence on 2018/4/5.
 */

public class ModelWelcomeImg implements Serializable {
	private static final long serialVersionUID = -4375581965465266654L;
	
	private String logo;
	
	public String getLogo () {
		return logo;
	}
	
	public void setLogo (String logo) {
		this.logo = logo;
	}
}
