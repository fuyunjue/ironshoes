package com.cn.jyz.ironshoes.model;

/**
 * 缓存svg图上机车、铁鞋坐标位置信息
 */
public class ViewSvgIcon {
	private ModelMotorsAndTx.MotorAndTxInfo info;
	
	private int trainIndex; //序号	机车或铁鞋在股道上的序号
	private int iconWidth;  //icon显示的宽度
	private int iconHeight; //icon显示的高度
	private int marginLeft; //icon左边距
	private int marginTop;  //icon上边距
	
	
	public int getTrainIndex () {
		return trainIndex;
	}
	
	public void setTrainIndex (int trainIndex) {
		this.trainIndex = trainIndex;
	}
	
	public ModelMotorsAndTx.MotorAndTxInfo getInfo () {
		return info;
	}
	
	public void setInfo (ModelMotorsAndTx.MotorAndTxInfo info) {
		this.info = info;
	}
	
	public int getIconWidth () {
		return iconWidth;
	}
	
	public void setIconWidth (int iconWidth) {
		this.iconWidth = iconWidth;
	}
	
	public int getIconHeight () {
		return iconHeight;
	}
	
	public void setIconHeight (int iconHeight) {
		this.iconHeight = iconHeight;
	}
	
	public int getMarginLeft () {
		return marginLeft;
	}
	
	public void setMarginLeft (int marginLeft) {
		this.marginLeft = marginLeft;
	}
	
	public int getMarginTop () {
		return marginTop;
	}
	
	public void setMarginTop (int marginTop) {
		this.marginTop = marginTop;
	}
}
