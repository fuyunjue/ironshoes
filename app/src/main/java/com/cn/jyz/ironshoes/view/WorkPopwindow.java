package com.cn.jyz.ironshoes.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.cn.jyz.ironshoes.R;

public class WorkPopwindow  extends PopupWindow {
	
	private View mView;
	
	public WorkPopwindow(Activity context, View.OnClickListener itemsOnClick) {
		super(context);
		initView(context, itemsOnClick);
	}
	
	private void initView(final Activity context, View.OnClickListener itemsOnClick) {
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = mInflater.inflate(R.layout.layout_pop_work, null);
		LinearLayout ll_work_close = (LinearLayout) mView.findViewById(R.id.ll_work_close);
		LinearLayout ll_ironshoes_install = (LinearLayout) mView.findViewById(R.id.ll_ironshoes_install);
		LinearLayout ll_ironshoes_check = (LinearLayout) mView.findViewById(R.id.ll_ironshoes_check);
		LinearLayout ll_ironshoes_delete = (LinearLayout) mView.findViewById(R.id.ll_ironshoes_delete);
		ll_work_close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//销毁弹出框
				dismiss();
				backgroundAlpha(context, 1f);
			}
		});
		//设置按钮监听
		ll_ironshoes_install.setOnClickListener(itemsOnClick);
		ll_ironshoes_check.setOnClickListener(itemsOnClick);
		ll_ironshoes_delete.setOnClickListener(itemsOnClick);
		//设置SelectPicPopupWindow的View
		this.setContentView(mView);
		//设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(WindowManager.LayoutParams.FILL_PARENT);
		//设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		//设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		//设置PopupWindow可触摸
		this.setTouchable(true);
		//设置非PopupWindow区域是否可触摸
//        this.setOutsideTouchable(false);
		//设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimUp);
		//实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x00000000);
		//设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		backgroundAlpha(context, 0.5f);//0.0-1.0
		this.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				backgroundAlpha(context, 1f);
			}
		});
	}
	
	
	/**
	 * 设置添加屏幕的背景透明度
	 *
	 * @param bgAlpha
	 */
	public static void backgroundAlpha(Activity context, float bgAlpha) {
		WindowManager.LayoutParams lp = context.getWindow().getAttributes();
		lp.alpha = bgAlpha;
		context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		context.getWindow().setAttributes(lp);
	}
	
	public View getmView () {
		return mView;
	}
}
