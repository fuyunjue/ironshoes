package com.cn.jyz.ironshoes.view;

import android.widget.ImageView;
import android.widget.TextView;

import java.net.PortUnreachableException;

public class SvgIcon {
    private String IconTag = "";
    private int IconResourceId = 0;
    private int Width =0;
    private int Height =0;
    private int Left =0;
    private int Top =0;
    private String Text = null;
    public ImageView img = null;
    public TextView vText = null;
    
    public SvgIcon(String iconTag,int iconResourceId,String text,int width,int height,int left,int top){
        IconTag = iconTag;
        IconResourceId = iconResourceId;
        Width = width;
        Height = height;
        Left = left;
        Top = top;
        Text = text;
    }
    
    public String getIconTag(){
        return IconTag;
    }
    public int getIconResourceId(){
        return IconResourceId;
    }
    public int getWidth(){
        return Width;
    }
    public int getHeight(){
        return Height;
    }
    public int getLeft(){
        return Left;
    }
    public int getTop(){
        return Top;
    }
    public String getText(){
        return Text;
    }
}
