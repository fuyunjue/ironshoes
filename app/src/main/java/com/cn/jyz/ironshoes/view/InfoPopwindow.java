package com.cn.jyz.ironshoes.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cn.jyz.ironshoes.R;
import com.cn.jyz.ironshoes.model.ModelMotorWithTiexieInfo;
import com.cn.jyz.ironshoes.utils.ToastUtil;

public class InfoPopwindow extends PopupWindow {
	
	private Context mContext;
	private View mView;
	private ModelMotorWithTiexieInfo info;
	
	public InfoPopwindow (Activity context, ModelMotorWithTiexieInfo info) {
		super(context);
		this.mContext = context;
		this.info = info;
		initView(context);
		init();
	}
	
	private void initView(final Activity context) {
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = mInflater.inflate(R.layout.layout_pop_info, null);
		LinearLayout ll_info_close = (LinearLayout) mView.findViewById(R.id.ll_info_close);
		ll_info_close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//销毁弹出框
				dismiss();
				backgroundAlpha(context, 1f);
			}
		});
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
				dismiss();
			}
		});
	}
	
	/**
	 * 初始化显示内容
	 */
	void init() {
		if (null == info.getMotor()) {
			ToastUtil.showShort(mContext, "查询内容为空");
			return;
		}
		((TextView) mView.findViewById(R.id.tv_time_arrive)).setText(String.format(mContext.getResources().getString(R.string.tv_time_arrive), info.getMotor().getTimeArrive() + ""));
		((TextView) mView.findViewById(R.id.tv_tx_install)).setText(String.format(mContext.getResources().getString(R.string.tv_tx_install), info.getMotor().getTxInstall() + ""));
		((TextView) mView.findViewById(R.id.tv_patrol_time)).setText(String.format(mContext.getResources().getString(R.string.tv_patrol_time), info.getMotor().getPatrolTime() + ""));
		((TextView) mView.findViewById(R.id.tv_rummager)).setText(String.format(mContext.getResources().getString(R.string.tv_rummager_info), info.getMotor().getRummager() + ""));
		((TextView) mView.findViewById(R.id.tv_region)).setText(String.format(mContext.getResources().getString(R.string.tv_region), info.getMotor().getRegion() + ""));
		((TextView) mView.findViewById(R.id.tv_trainnumber)).setText(String.format(mContext.getResources().getString(R.string.tv_trainnumber), info.getMotor().getTrainNumber() + ""));
		((TextView) mView.findViewById(R.id.tv_trainorder)).setText(String.format(mContext.getResources().getString(R.string.tv_trainorder), info.getMotor().getTrainOrder() + ""));
		((TextView) mView.findViewById(R.id.tv_motorman)).setText(String.format(mContext.getResources().getString(R.string.tv_motorman), info.getMotor().getMotorman() + ""));
		
		String tmp = "-";
		
		ModelMotorWithTiexieInfo.TieXieDetail l =  info.getTxL();
		if (l != null) {
			//左铁鞋
			((TextView) mView.findViewById(R.id.tv_l_tiexiecode)).setText(String.format(mContext.getResources().getString(R.string.tv_xh), l.getTieXieCode() == null ? "-" : l.getTieXieCode()));
			((TextView) mView.findViewById(R.id.tv_l_installationtime)).setText(String.format(mContext.getResources().getString(R.string.tv_xh), l.getInstallationTime() == null ? "-" : l.getInstallationTime()));
			((TextView) mView.findViewById(R.id.tv_l_installationperson)).setText(String.format(mContext.getResources().getString(R.string.tv_xh), l.getInstallationPerson() == null ? "-" : l.getInstallationPerson()));
			((TextView) mView.findViewById(R.id.tv_l_patroltimequantum)).setText(String.format(mContext.getResources().getString(R.string.tv_xh), l.getPatrolTimeQuantum() == null ? "-" : l.getPatrolTimeQuantum()));
			((TextView) mView.findViewById(R.id.tv_l_patrolperson)).setText(String.format(mContext.getResources().getString(R.string.tv_xh), l.getPatrolPerson() == null ? "-" : l.getPatrolPerson()));
			((TextView) mView.findViewById(R.id.tv_l_patrolresult)).setText(String.format(mContext.getResources().getString(R.string.tv_xh), l.getPatrolResult() == null ? "-" : l.getPatrolResult()));
			((TextView) mView.findViewById(R.id.tv_l_tiexiebattery)).setText(String.format(mContext.getResources().getString(R.string.tv_xh), l.getTieXieBattery() == null ? "-" : l.getTieXieBattery()));
			((TextView) mView.findViewById(R.id.tv_l_lorastate)).setText(String.format(mContext.getResources().getString(R.string.tv_xh), l.getLoraState() == null ? "-" : l.getLoraState()));
		} else {
			((TextView) mView.findViewById(R.id.tv_l_tiexiecode)).setText(tmp);
			((TextView) mView.findViewById(R.id.tv_l_installationtime)).setText(tmp);
			((TextView) mView.findViewById(R.id.tv_l_installationperson)).setText(tmp);
			((TextView) mView.findViewById(R.id.tv_l_patroltimequantum)).setText(tmp);
			((TextView) mView.findViewById(R.id.tv_l_patrolperson)).setText(tmp);
			((TextView) mView.findViewById(R.id.tv_l_patrolresult)).setText(tmp);
			((TextView) mView.findViewById(R.id.tv_l_tiexiebattery)).setText(tmp);
			((TextView) mView.findViewById(R.id.tv_l_lorastate)).setText(tmp);
		}
		
		//右铁鞋
		ModelMotorWithTiexieInfo.TieXieDetail r =  info.getTxR();
		if (null != r) {
			((TextView) mView.findViewById(R.id.tv_r_tiexiecode)).setText(String.format(mContext.getResources().getString(R.string.tv_xh), r.getTieXieCode() == null ? "-" : r.getTieXieCode()));
			((TextView) mView.findViewById(R.id.tv_r_installationtime)).setText(String.format(mContext.getResources().getString(R.string.tv_xh), r.getInstallationTime() == null ? "-" : r.getInstallationTime()));
			((TextView) mView.findViewById(R.id.tv_r_installationperson)).setText(String.format(mContext.getResources().getString(R.string.tv_xh), r.getInstallationPerson() == null ? "-" : r.getInstallationPerson()));
			((TextView) mView.findViewById(R.id.tv_r_patroltimequantum)).setText(String.format(mContext.getResources().getString(R.string.tv_xh), r.getPatrolTimeQuantum() == null ? "-" : r.getPatrolTimeQuantum()));
			((TextView) mView.findViewById(R.id.tv_r_patrolperson)).setText(String.format(mContext.getResources().getString(R.string.tv_xh), r.getPatrolPerson() == null ? "-" : r.getPatrolPerson()));
			((TextView) mView.findViewById(R.id.tv_r_patrolresult)).setText(String.format(mContext.getResources().getString(R.string.tv_xh), r.getPatrolResult() == null ? "-" : r.getPatrolResult()));
			((TextView) mView.findViewById(R.id.tv_r_tiexiebattery)).setText(String.format(mContext.getResources().getString(R.string.tv_xh), r.getTieXieBattery() == null ? "-" : r.getTieXieBattery()));
			((TextView) mView.findViewById(R.id.tv_r_lorastate)).setText(String.format(mContext.getResources().getString(R.string.tv_xh), r.getLoraState() == null ? "-" : r.getLoraState()));
		} else {
				((TextView) mView.findViewById(R.id.tv_r_tiexiecode)).setText(tmp);
				((TextView) mView.findViewById(R.id.tv_r_installationtime)).setText(tmp);
				((TextView) mView.findViewById(R.id.tv_r_installationperson)).setText(tmp);
				((TextView) mView.findViewById(R.id.tv_r_patroltimequantum)).setText(tmp);
				((TextView) mView.findViewById(R.id.tv_r_patrolperson)).setText(tmp);
				((TextView) mView.findViewById(R.id.tv_r_patrolresult)).setText(tmp);
				((TextView) mView.findViewById(R.id.tv_r_tiexiebattery)).setText(tmp);
				((TextView) mView.findViewById(R.id.tv_r_lorastate)).setText(tmp);
		}
	}
	
	
	/**
	 * 设置添加屏幕的背景透明度
	 *
	 * @param bgAlpha
	 */
	public void backgroundAlpha(Activity context, float bgAlpha) {
		WindowManager.LayoutParams lp = context.getWindow().getAttributes();
		lp.alpha = bgAlpha;
		context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		context.getWindow().setAttributes(lp);
	}
}
