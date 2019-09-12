package com.cn.jyz.ironshoes.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.jyz.ironshoes.R;
import com.cn.jyz.ironshoes.adapter.NoticeListAdapter;
import com.cn.jyz.ironshoes.application.AppApplication;
import com.cn.jyz.ironshoes.emodel.EResponseCode;
import com.cn.jyz.ironshoes.html.JsObject;
import com.cn.jyz.ironshoes.html.MyWebChromeClient;
import com.cn.jyz.ironshoes.model.HashTracksInfo;
import com.cn.jyz.ironshoes.model.ModelMotorWithTiexieInfo;
import com.cn.jyz.ironshoes.model.ModelMotors;
import com.cn.jyz.ironshoes.model.ModelMqtt;
import com.cn.jyz.ironshoes.model.ModelStation;
import com.cn.jyz.ironshoes.model.ModelTiexies;
import com.cn.jyz.ironshoes.model.ModelTrack;
import com.cn.jyz.ironshoes.model.ModelUserInfo;
import com.cn.jyz.ironshoes.model.ModelWorkResult;
import com.cn.jyz.ironshoes.push.MqttService;
import com.cn.jyz.ironshoes.utils.AppUtil;
import com.cn.jyz.ironshoes.utils.AsyncHttpHelper;
import com.cn.jyz.ironshoes.utils.Constant;
import com.cn.jyz.ironshoes.utils.DensityUtil;
import com.cn.jyz.ironshoes.utils.GsonTools;
import com.cn.jyz.ironshoes.utils.ToastUtil;
import com.cn.jyz.ironshoes.utils.UpdateAppManager;
import com.cn.jyz.ironshoes.view.InfoPopwindow;
import com.cn.jyz.ironshoes.view.SvgIcon;
import com.cn.jyz.ironshoes.view.SvgLayout;
import com.cn.jyz.ironshoes.view.TopMiddlePopup;
import com.cn.jyz.ironshoes.view.WorkPopwindow;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.github.jdsjlzx.util.WeakHandler;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.skybeacon.sdk.ConnectionStateCallback;
import com.skybeacon.sdk.RangingBeaconsListener;
import com.skybeacon.sdk.ScanServiceStateCallback;
import com.skybeacon.sdk.config.SKYBeaconCommunication;
import com.skybeacon.sdk.config.SKYBeaconConfigException;
import com.skybeacon.sdk.locate.SKYBeacon;
import com.skybeacon.sdk.locate.SKYBeaconManager;
import com.skybeacon.sdk.locate.SKYBeaconMultiIDs;
import com.skybeacon.sdk.locate.SKYRegion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;


public class MainActivityBackup extends AppCompatActivity implements View.OnClickListener {
	
//	static {
//		AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//	}
//
//	private static final int NEW_NOTICE         = 0x21;     //收到新预警
//	private static final int REQUEST_ENABLE_BT  = 0x22;     //蓝牙权限检查返回
//	private static final int DOWNLOAD_FINISH    = 0x23;
//	public static final int MAPCLICK            = 0x24;     //电子地图点击icon回传
//
//
//	private Context mContext;
//
//	private ModelUserInfo userInfo;
//
//	private UpdateAppManager manager;
//
//	private RelativeLayout rl_main_drawer;
//
//	private DrawerLayout drawer_layout;
//
//	private LinearLayout OutLayout = null;
//	private SvgLayout svgLayout = null;
//
//	private LinearLayout urm_top_ll;
//	private TextView tv_station;
//	private ImageView iv_check_stations;
//
//	public static int screenW, screenH;
//
//	private TopMiddlePopup middlePopup;
//	private WorkPopwindow workPop;
//	private InfoPopwindow infoPop;
//
//	//======================  预警信息列表  ======================//
//	/**服务器端一共多少条数据*/
//	private static final int TOTAL_COUNTER = 100;//如果服务器没有返回总数据或者总页数，这里设置为最大值比如10000，什么时候没有数据了根据接口返回判断
//
//	/**每一页展示多少条数据*/
//	private static final int REQUEST_COUNT = 10;
//
//	/**已经获取到多少条数据了*/
//	private static int mCurrentCounter = 0;
//
//	private LRecyclerView mRecyclerView = null;
//
//	private NoticeListAdapter mDataAdapter = null;
//
//	private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
//
//	private MqqtBroadReceiver mqqtBroadReceiver = null;
//
//	private ModelStation station = null;
//	private ModelStation.StationInfo currentStation = null;
//
//	//TODO 测试数据
//	private String levelmark = "兰州西整备场";
//
//
	@Override
	protected void onCreate (Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		mContext = this;
//		userInfo = AppApplication.getInstance().getUserInfo();
//
//		getScreenPixels();
//
//		initView();
//		initNoticeRecyclerView();
//
//		if (progressDialog == null || !progressDialog.isShowing())
//			progressDialog = AppUtil.getProgressDialog(mContext, getResources().getString(R.string.loading), false, false);
//
//		//TODO 加载站点信息
////		loadStations(userInfo.getLevelmark());
////		this.levelmark = userInfo.getLevelmark();
//		loadStations(levelmark);
//
////		服务器消息通知注册
//		IntentFilter filter = new IntentFilter();
//		filter.addAction(MqttService.NOTIFICATION_BROADCAST);
//		mqqtBroadReceiver = new MqqtBroadReceiver();
//		registerReceiver(mqqtBroadReceiver, filter);// 注册
//
//		/**
//		 * 检查版本更新
//		 */
////		manager=new UpdateAppManager(this);
////		manager.getUpdateMsg();//检查更新
//
////		GlideApp.with(mContext).load().into()
	}
//
//	/**
//	 * 加载站点信息
//	 *
//	 * @param levelmark 站场名称
//	 */
//	void loadStations(String levelmark) {
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("levelmark", levelmark);
//
//		AsyncHttpHelper.getInstance().get(mContext, Constant.API_STATIONS, params, new AsyncHttpResponseHandler() {
//
//			@Override
//			public void onFailure (final int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run () {
//						if (progressDialog != null && progressDialog.isShowing())
//							progressDialog.dismiss();
//
//						ToastUtil.showShort(mContext, arg0);
//					}
//				});
//			}
//
//
//			@Override
//			public void onSuccess (int arg0, Header[] arg1, byte[] arg2) {
//
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run () {
//						if (progressDialog != null && progressDialog.isShowing())
//							progressDialog.dismiss();
//					}
//				});
//
//				final ModelStation stations = GsonTools.getObjectData(new String(arg2), ModelStation.class);
//
//				if (stations.getCode() == EResponseCode.SUCCESS.getCode()) {
//					if (stations.getStations().size() > 0) {
//						//初始化站场下拉列表
//						currentStation = stations.getStations().get(0);
//						tv_station.setText(currentStation.getStationname() + "");
//
//						//加载该站场股道相关信息
//						loadTrackinfo(currentStation.getStationname(), currentStation.getVectorFilePath());
//
//						station = stations;
//
//					} else {
//						ToastUtil.showShort(mContext, "该站点无站场信息");
//					}
//
//					if (stations.getStations() != null && stations.getStations().size() > 1) {
//						iv_check_stations.setVisibility(View.VISIBLE);
//					} else {
//						iv_check_stations.setVisibility(View.INVISIBLE);
//					}
//				} else if (stations.getCode() == EResponseCode.WRONG.getCode()) {
//					runOnUiThread(new Runnable() {
//						@Override
//						public void run () {
//							ToastUtil.showShort(mContext, stations.getMsg());
//						}
//					});
//				}
//			}
//		});
//	}
//
//
//	private WebView webView;
//
//	/**
//	 * 加载SVG资源
//	 *
//	 * @param filePath
//	 * @param aspectratioX
//	 * @param aspectratioY
//	 * @param motors
//	 * @param tracks
//	 */
//	void initSVG(String filePath, float aspectratioX, float aspectratioY, ModelMotors motors, final ModelTrack tracks) {
//		//初始化地图控件 屏幕高度 - 底部或者顶部的一些高度去除
////		int otherHeight = DensityUtil.dip2px(mContext, mContext.getResources().getDimensionPixelSize(R.dimen.dimen49));
//
//		//展示VIEW的高度
//		int mapViewHeight = DensityUtil.getDevicePx(MainActivityBackup.this)[1] -  mContext.getResources().getDimensionPixelSize(R.dimen.dimen49);
//		//计算缩放比例
//		float mapRote = getMapRote(mapViewHeight, aspectratioY);
//		//计算展示VIEW的宽度
//		float mapViewWidth = aspectratioX * mapRote;
//		//加载电子轨道地图
////		svgLayout.initOutLayout(filePath, mapViewWidth, mapViewHeight, mapRote);
//
//		//添加机车
//		addSVGIcon(motors.getList(), mapRote);
//
////		//两个铁鞋
////		iconList.add(new SvgIcon("绿色铁鞋",R.mipmap.index_tx_green,null,13,20,184,289));
////		iconList.add(new SvgIcon("红色铁鞋",R.mipmap.index_tx_red,null,13,20,293,289));
//
//		webView = (WebView) findViewById(R.id.webView);
//		//支持javascript
//		webView.getSettings().setJavaScriptEnabled(true);
//		// 设置可以支持缩放
//		webView.getSettings().setSupportZoom(true);
//		// 设置出现缩放工具
//		webView.getSettings().setBuiltInZoomControls(true);
//		//扩大比例的缩放
//		webView.getSettings().setUseWideViewPort(true);
//		//不显示webview缩放按钮
//		webView.getSettings().setDisplayZoomControls(false);
//		//自适应屏幕
//		webView.getSettings().setLoadWithOverviewMode(true);
//		webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//		//获取设备状态栏高度
//		int height = 0;
//		int resourceId = getApplicationContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
//		if (resourceId > 0) {
//			height = getApplicationContext().getResources().getDimensionPixelSize(resourceId);
//		}
//
//		int svgHeightDip = DensityUtil.px2dip(mContext, mapViewHeight - height);
//		float svgRote = getMapRote(mapViewWidth, mapViewHeight - height);
//		int svgWidthDip = (int) (svgHeightDip * svgRote);
//		readHtmlFormAssets(filePath, (int)aspectratioX, (int)aspectratioY, motors, svgWidthDip / aspectratioY, svgHeightDip / aspectratioX, tracks);
//	}
//
//
//	/**
//	 * 以webview的方式加载SVG和机车图标
//	 * @param path
//	 * @param width
//	 * @param height
//	 * @param motors
//	 * @param wRote
//	 * @param hRote
//	 * @param tracks
//	 */
//	private void readHtmlFormAssets(final String path, final int width, final int height, final ModelMotors motors, final float wRote, final float hRote, final ModelTrack tracks) {
//		WebSettings webSettings = webView.getSettings();
//		webSettings.setJavaScriptEnabled(true);
//		webSettings.setDomStorageEnabled(true);
//		//设置可以访问文件
//		webSettings.setAllowFileAccess(true);
//       //设置支持缩放
//		webSettings.setBuiltInZoomControls(true);
//		webSettings.setSupportZoom(true);
//		webSettings.setUseWideViewPort(true); //可任意比例缩放
//		webSettings.setLoadWithOverviewMode(true);
//
//		webView.addJavascriptInterface(new JsObject(mContext, mHandler), Constant.WEBVIEW_OBJ_NAME);
//		webView.setWebChromeClient(new MyWebChromeClient(this, null));
//		webView.requestFocus();
//
//		webView.setWebViewClient(new WebViewClient() {
//
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				return false;
//			}
//
//			@Override
//			public void onPageFinished(WebView view, final String url) {
//
//				webView.loadUrl("javascript:createSvgTag('"+ path +"', " + width + ", " + height + ")");
//				assembleData(motors, tracks, wRote, hRote);
//			}
//
//			@Override
//			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//				super.onReceivedError(view, errorCode, description, failingUrl);
//			}
//		});
//
//		webView.loadUrl("file:///android_asset/svg.html");
//	}
//
//
//	void assembleData(ModelMotors motors, ModelTrack tracks, float wRote, float hRote) {
////		hRote = wRote;
//		ArrayList<HashTracksInfo> hashTracksInfos = new ArrayList<>();
//
//		//封装机车列表为Hash Hash<Integer, ArrayList<MotorInfo>> Hash<机车轨道, ArrayList<MotorInfo>>
//		HashMap<Integer, ArrayList<ModelMotors.MotorInfo>> motorHash = new HashMap<>();
//		ArrayList<ModelMotors.MotorInfo> motorList = motors.getList();
//		for (ModelMotors.MotorInfo mInfo : motorList) {
//			int trackId = Integer.parseInt(mInfo.getTrackId());
//			if (null == motorHash.get(trackId)) {
//				ArrayList<ModelMotors.MotorInfo> list = new ArrayList<>();
//				list.add(mInfo);
//				motorHash.put(trackId, list);
//			} else {
//				motorHash.get(trackId).add(mInfo);
//			}
//		}
//
//		//封装为Hash集合 Hash<String, List<MotorInfo>>  Hash<股道号, 机车(铁鞋)列表>
//		HashMap<Integer, ModelTrack.TrackInfo> hash = new HashMap<>();
//		ArrayList<ModelTrack.TrackInfo> tList = tracks.getTracks();
//		for (ModelTrack.TrackInfo tItem : tList) {
//			hash.put(Integer.parseInt(tItem.getTrackno()), tItem);
//		}
//
//		//遍历Hash
//	    Iterator iter = hash.entrySet().iterator();
//	    int defaultHeight = 0;
//		while (iter.hasNext()) {
//			Map.Entry entry = (Map.Entry) iter.next();
//
//			int key = Integer.parseInt(entry.getKey().toString());
//
//			if (null == motorHash.get(key)) {
//				//如果该铁轨无机车，不必要做其他逻辑了
//				continue;
//			}
//			ModelTrack.TrackInfo value = (ModelTrack.TrackInfo) entry.getValue();
//			ModelTrack.TrackInfo tmp = hash.get(key - 1);
//
//			HashTracksInfo hashTracksInfo = new HashTracksInfo();
//			//计算该股道width、height
//			hashTracksInfo.setTrackRealWidth((int) (wRote * (Float.parseFloat(value.getEndX()) - Float.parseFloat(value.getStartX()))));
//			int tHeight = defaultHeight;
//			if (tmp != null) {
//				tHeight = (int) (hRote * (Float.parseFloat(value.getStartY()) - Float.parseFloat(tmp.getStartY())));
//				defaultHeight = tHeight;
//			}
//			hashTracksInfo.setTrackRealHeight(tHeight);
//			hashTracksInfo.setTrackno(Integer.parseInt(value.getTrackno()));
//			hashTracksInfo.setTrackInfo(value);
//			hashTracksInfo.setList((motorHash.get((key))));
//
//			hashTracksInfos.add(hashTracksInfo);
//		}
//
//		//遍历集合，画机车、铁鞋
//		if (hashTracksInfos.size() > 0) {
//			for (HashTracksInfo item : hashTracksInfos) {
//				ArrayList<ModelMotors.MotorInfo> list = item.getList();
//				//该轨道的机车宽度平均值
//				//TODO 需要加上左右铁鞋两个空位?
//				int pWidth = item.getTrackRealWidth() / (Integer.parseInt(item.getTrackInfo().getCapacity()));
//				for (ModelMotors.MotorInfo info : list) {
//					//计算机车各项值
//					int type = 1;   //画小车
//					String tag = info.getCarId();
//					String text = info.getCarId();
//					int height = item.getTrackRealHeight();
//					int marginLeft = (int) (wRote * Float.parseFloat(item.getTrackInfo().getStartX())) + pWidth * info.getTrainIndex();
//					int width = pWidth;
//					int marginTop = (int) (hRote * Float.parseFloat(item.getTrackInfo().getStartY()) - height);
//
////					int height = item.getTrackRealHeight();
////					int marginLeft = (int) (wRote * Float.parseFloat(item.getTrackInfo().getStartX())) + pWidth * info.getTrainIndex();
////					int width = pWidth;
////					int marginTop = (int) (hRote * (Float.parseFloat(item.getTrackInfo().getStartY()) - Float.parseFloat(tracks.getOffsetTop())) - height);
//
////
////					int height = item.getTrackRealHeight();
////					int marginLeft = (int) (wRote * Float.parseFloat(item.getTrackInfo().getStartX())) + pWidth * info.getTrainIndex();
////					int width = pWidth;
////					int marginTop = (int) (hRote * (Float.parseFloat(item.getTrackInfo().getStartY())) - height);
//
//
//					Log.e("aaaa", "javascript:addSVGIcon(" + type + ",1," + tag + "," + text + "," + marginTop + "," + marginLeft + "," + width + "," + height + ");");
//					webView.loadUrl("javascript:addSVGIcon(" + type + ",1,'" + tag + "'," + text + "," + marginTop + "," + marginLeft + "," + width + "," + height + ");");
//				}
//			}
//		}
//	}
//
//	/**
//	 * 计算svg图缩放比例
//	 *
//	 * @param mapHeight 展示view高度
//	 * @param aspectratioY  svg图实际高度
//	 * @return
//	 */
//	float getMapRote (float mapHeight,  float aspectratioY) {
//		//比例等于svg图高/显示svg图的占高
//		return aspectratioY / mapHeight;
//	}
//
//	/**
//	 * 添加机车图标列表
//	 *
//	 * @param motors
//	 * @param mapRote
//	 */
//	void addSVGIcon(ArrayList<ModelMotors.MotorInfo> motors, float mapRote) {
//		//添加或者更新图标级
//		List<SvgIcon> iconList = new ArrayList<SvgIcon>();
//
//		for (ModelMotors.MotorInfo mInfo : motors) {
////			SvgIcon icon = new SvgIcon(
////					mInfo.getCarId(),
////					mInfo.getTrainIndex() > 1 ? R.mipmap.index_jc_red : R.mipmap.index_jc_green,
////					mInfo.getCarId(),
////					mapRote, mInfo);
////			iconList.add(icon);
//		}
//		svgLayout.AddOrRefIcons(iconList);
//
//		//事件委托过来
////		svgLayout.setOnClick(new SvgIconOnClick() {
////			@Override
////			public void onClick(SvgIcon icon, View view) {
////				//查询信息
////				if (icon != null) {
////					motorWithTieXieInfo(icon.getIconTag(), view);
////				}
////			}
////		});
//	}
//
//
//	/**
//	 * 加载机车列表
//	 *
//	 * @param levelmark
//	 * @param vectorFilePath
//	 * @param aspectratioX
//	 * @param aspectratioY
//	 * @param tracks
//	 */
//	void loadMotors (String levelmark, final String vectorFilePath, final float aspectratioX, final float aspectratioY, final ModelTrack tracks) {
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("levelmark", levelmark);
//
//		AsyncHttpHelper.getInstance().get(mContext, Constant.MOTORS, params, new AsyncHttpResponseHandler() {
//
//			@Override
//			public void onFailure (final int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run () {
//						if (progressDialog != null && progressDialog.isShowing())
//							progressDialog.dismiss();
//
//						ToastUtil.showShort(mContext, arg0);
//					}
//				});
//			}
//
//
//			@Override
//			public void onSuccess (int arg0, Header[] arg1, byte[] arg2) {
//
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run () {
//						if (progressDialog != null && progressDialog.isShowing())
//							progressDialog.dismiss();
//					}
//				});
//
//				final ModelMotors motors = GsonTools.getObjectData(new String(arg2), ModelMotors.class);
//
//				if (motors.getCode() == EResponseCode.SUCCESS.getCode()) {
//					initSVG(vectorFilePath, aspectratioX, aspectratioY, motors, tracks);
//				} else if (motors.getCode() == EResponseCode.WRONG.getCode()) {
//					runOnUiThread(new Runnable() {
//						@Override
//						public void run () {
//							ToastUtil.showShort(mContext, motors.getMsg());
//						}
//					});
//				}
//			}
//		});
//	}
//
//	/**
//	 * 加载站点股道相关信息
//	 *
//	 * @param levelmark
//	 * @param vectorFilePath
//	 */
//	void loadTrackinfo (final String levelmark, final String vectorFilePath) {
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("levelmark", levelmark);
//
//		AsyncHttpHelper.getInstance().get(mContext, Constant.TRACKINFO, params, new AsyncHttpResponseHandler() {
//
//			@Override
//			public void onFailure (final int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run () {
//						if (progressDialog != null && progressDialog.isShowing())
//							progressDialog.dismiss();
//
//						ToastUtil.showShort(mContext, arg0);
//					}
//				});
//			}
//
//
//			@Override
//			public void onSuccess (int arg0, Header[] arg1, byte[] arg2) {
//
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run () {
//						if (progressDialog != null && progressDialog.isShowing())
//							progressDialog.dismiss();
//					}
//				});
//
//				final ModelTrack tracks = GsonTools.getObjectData(new String(arg2), ModelTrack.class);
//
//				if (tracks.getCode() == EResponseCode.SUCCESS.getCode())
//					loadMotors(levelmark, vectorFilePath, Integer.parseInt(tracks.getAspectratioX()), Integer.parseInt(tracks.getAspectratioY()), tracks);
//				else if (tracks.getCode() == EResponseCode.WRONG.getCode()) {
//					runOnUiThread(new Runnable() {
//						@Override
//						public void run () {
//							ToastUtil.showShort(mContext, tracks.getMsg());
//						}
//					});
//				}
//			}
//		});
//	}
//
//	/**
//	 * 预警消息列表
//	 */
//	void initNoticeRecyclerView() {
//		mRecyclerView = (LRecyclerView) findViewById(R.id.list);
//
//		mDataAdapter = new NoticeListAdapter(this);
//		mLRecyclerViewAdapter = new LRecyclerViewAdapter(mDataAdapter);
//		mRecyclerView.setAdapter(mLRecyclerViewAdapter);
//
//		DividerDecoration divider = new DividerDecoration.Builder(this)
//				.setHeight(R.dimen.dimen1)
//				.setPadding(R.dimen.dimen1)
//				.setColorResource(R.color.split)
//				.build();
//
//		//mRecyclerView.setHasFixedSize(true);
//		mRecyclerView.addItemDecoration(divider);
//
//		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//		mRecyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
//		mRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
//		mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
//		//禁用下拉刷新功能
//		mRecyclerView.setPullRefreshEnabled(false);
//		//是否禁用自动加载更多功能,false为禁用, 默认开启自动加载更多功能
//		mRecyclerView.setLoadMoreEnabled(false);
//
//		mRecyclerView.setLScrollListener(new LRecyclerView.LScrollListener() {
//
//			@Override
//			public void onScrollUp() {
//			}
//
//			@Override
//			public void onScrollDown() {
//			}
//
//
//			@Override
//			public void onScrolled(int distanceX, int distanceY) {
//			}
//
//			@Override
//			public void onScrollStateChanged(int state) {
//
//			}
//
//		});
//	}
//
//	private void addItems(ArrayList<ModelMqtt> list) {
//		mDataAdapter.addAll(list);
//		mCurrentCounter += list.size();
//	}
//
//	void initView() {
//
//		drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
//		rl_main_drawer = (RelativeLayout) findViewById(R.id.rl_main_drawer);
//
//		findViewById(R.id.ll_logout).setOnClickListener(this);
//		findViewById(R.id.ll_work).setOnClickListener(this);
//		findViewById(R.id.iv_drawer_menu).setOnClickListener(this);
//		findViewById(R.id.iv_record).setOnClickListener(this);
//		findViewById(R.id.iv_search).setOnClickListener(this);
//		urm_top_ll = (LinearLayout) findViewById(R.id.urm_top_ll);
//		urm_top_ll.setOnClickListener(this);
//		tv_station = (TextView) findViewById(R.id.tv_station);
//		iv_check_stations = (ImageView) findViewById(R.id.iv_check_stations);
//
//		//抽屉滑出宽度为屏幕三分之二
//		ViewGroup.LayoutParams vgLayoutParams = rl_main_drawer.getLayoutParams();
//		vgLayoutParams.width = screenW / 4 * 3;
//		vgLayoutParams.height = screenH;
//		rl_main_drawer.setLayoutParams(vgLayoutParams);
//
//		//查找地图控件
//		svgLayout = (SvgLayout)findViewById(R.id.powerFullLayout);
//
//
//		((TextView) findViewById(R.id.tv_user_chinesename)).setText(String.format(getResources().getString(R.string.tv_user_chinesename), userInfo == null ? "" : userInfo.getChinesename()));
//		((TextView) findViewById(R.id.tv_user_mobile)).setText(String.format(getResources().getString(R.string.tv_user_mobile), userInfo == null ? "" : userInfo.getMobile()));
//	}
//
	@Override
	public void onClick (View view) {
//		switch (view.getId()) {
//			case R.id.iv_drawer_menu:
//				toggle();
//				break;
//			case R.id.iv_record:
//				Intent recordIntent = new Intent(mContext, ActivityRecord.class);
//				startActivity(recordIntent);
//				break;
//			case R.id.iv_search:
//				Intent searchIntent = new Intent(mContext, ActivitySearch.class);
//				startActivity(searchIntent);
//				break;
//			case R.id.urm_top_ll:
//				setPopup(0);
//				middlePopup.show(findViewById(R.id.rl_main_title));
//				break;
//			case R.id.ll_work:
//				workPop = null;
//
//				clickDoWork(view);
//				break;
//			case R.id.ll_logout:
//				//清除登录信息
//				this.userInfo = null;
//				AppApplication.getInstance().setUserInfo(null);
//				Intent loginIntent = new Intent(mContext, ActivityLogin.class);
//				startActivity(loginIntent);
//				finish();
//				break;
//				default:
//					break;
//		}
	}
//
//	/**
//	 * 点击铁鞋作业
//	 * @param view
//	 */
//	void clickDoWork(final View view) {
//		//检查应用权限
//		if (!checkBluetoothPermission()) {
//			ActivityCompat.requestPermissions(MainActivityBackup.this,
//					new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_ENABLE_BT );
//		} else {
//			//检查蓝牙是否已打开
//			if (checkBluetoothEnabled()) {
//
//				startScan(view);
//			} else {
//				new AlertDialog.Builder(MainActivityBackup.this)
//						.setTitle(R.string.app_name)
//						.setMessage("蓝牙功能尚未打开，是否打开蓝牙")
//						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								dialog.dismiss();
//								if (turnOnBluetooth()) {
//									Toast tst = Toast.makeText(MainActivityBackup.this, "打开蓝牙成功！", Toast.LENGTH_SHORT);
//									tst.show();
//								} else {
//									Toast tst = Toast.makeText(MainActivityBackup.this, "打开蓝牙失败！", Toast.LENGTH_SHORT);
//									tst.show();
//								}
//							}
//						})
//						.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								dialog.dismiss();
//							}
//						}).show();
//			}
//		}
//	}
//
//	/**
//	 * 检查蓝牙是否打开
//	 * @return
//	 */
//	boolean checkBluetoothEnabled() {
//		BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
//		BluetoothAdapter mBluetoothAdapter = bluetoothManager.getAdapter();
//
//		return mBluetoothAdapter.isEnabled();
//	}
//
//	//为弹出窗口实现监听类
//	private View.OnClickListener itemsOnClick = new View.OnClickListener() {
//
//		public void onClick(View v) {
//			Object o = workPop.getmView().findViewById(R.id.tv_device).getTag(R.string.tag_tv);
//
//			if (o == null) {
//				ToastUtil.showShort(mContext, "设备扫描中，请稍等");
//				return;
//			}
//			workPop.getmView().findViewById(R.id.tv_device).setTag(R.string.tag_tv, null);
//
//			workPop.dismiss();
//			workPop.backgroundAlpha(MainActivityBackup.this, 1f);
//
//			SKYBeacon device = (SKYBeacon) o;
//			switch (v.getId()) {
//				case R.id.ll_ironshoes_delete:
//					doWork(device, "撤防", workIds[0]);
//					break;
//				case R.id.ll_ironshoes_check:
//					doWork(device, "巡检", workIds[1]);
//					break;
//				case R.id.ll_ironshoes_install:
//					doWork(device, "设防", workIds[2]);
//					break;
//				default:
//					break;
//			}
//		}
//
//	};
//
//	private  int[] workIds = new int[] {1, 2, 3};   //撤防，巡检，设防
//
//	/**
//	 * 执行作业
//	 * @param device
//	 * @param title
//	 */
//	void doWork(SKYBeacon device, final String title, int workId) {
////		final String deviceName = device.getDeviceName();
//		final String deviceName = "TKY5-1";   //TODO 测试数据
//
//		if (workIds[0] == workId) {
//			//通过设备号查询信息
//			checkSN(deviceName, workId, title, deviceName);
//		} else if (workIds[1] == workId) {
//			//通过设备号查询信息
//			checkSN(deviceName, workId, title, deviceName);
//		} else if (workIds[2] == workId) {
//			showWorkPop(deviceName, deviceName, title, workId, null);
//		}
//	}
//
//	/**
//	 * 根据设备号查询该设备当前状态
//	 *
//	 * @param sn
//	 * @param workId
//	 * @param title
//	 * @param bm
//	 */
//	void checkSN(final String sn, final int workId, final String title, final String bm) {
//		if (progressDialog == null || !progressDialog.isShowing())
//			progressDialog = AppUtil.getProgressDialog(mContext, getResources().getString(R.string.loading), false, false);
//
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("levelmark", this.levelmark);
//		params.put("txno", sn);   //铁鞋编号
//
//		AsyncHttpHelper.getInstance().get(mContext, Constant.TIEXIES, params, new AsyncHttpResponseHandler() {
//
//			@Override
//			public void onFailure (final int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run () {
//						if (progressDialog != null && progressDialog.isShowing())
//							progressDialog.dismiss();
//
//						ToastUtil.showShort(mContext, arg0);
//					}
//				});
//			}
//
//
//			@Override
//			public void onSuccess (int arg0, Header[] arg1, byte[] arg2) {
//
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run () {
//						if (progressDialog != null && progressDialog.isShowing())
//							progressDialog.dismiss();
//					}
//				});
//
//				final ModelTiexies tiexies = GsonTools.getObjectData(new String(arg2), ModelTiexies.class);
//				if (tiexies.getCode() == EResponseCode.SUCCESS.getCode()) {
//					showWorkPop(sn, bm, title, workId, tiexies);
//				} else if (tiexies.getCode() == EResponseCode.WRONG.getCode()) {
//					runOnUiThread(new Runnable() {
//						@Override
//						public void run () {
//							ToastUtil.showShort(mContext, tiexies.getMsg());
//						}
//					});
//				}
//			}
//		});
//	}
//
//	private ProgressDialog progressDialog;
//	private PopupWindow doWorkPop = null;
//	private View doWorkView = null;
//
//	/**
//	 *
//	 * @param sn    铁鞋编码
//	 * @param bm
//	 * @param title
//	 * @param workId
//	 * @param tiexie
//	 */
//	void showWorkPop(final String sn, String bm, final String title, int workId, ModelTiexies tiexie) {
//
//		if (null == doWorkPop) {
//			Integer viewId = null;
//			if (workId == workIds[0]) {
//				viewId = R.layout.layout_pop_work_cf;
//			} else if (workId == workIds[1]) {
//				viewId = R.layout.layout_pop_work_xj;
//			} else if (workId == workIds[2]) {
//				viewId = R.layout.layout_pop_work_sf;
//			}
//
//			doWorkView = LayoutInflater.from(mContext).inflate(viewId, null);
//
//			doWorkPop = new PopupWindow(doWorkView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//			// 设置PopupWindow是否能响应外部点击事件
//			doWorkPop.setOutsideTouchable(false);
//			// 设置PopupWindow是否能响应点击事件
//			doWorkPop.setTouchable(true);
//
//			//设置SelectPicPopupWindow弹出窗体可点击
//			doWorkPop.setFocusable(true);
//			//实例化一个ColorDrawable颜色为半透明
//			ColorDrawable dw = new ColorDrawable(0x00000000);
//			//设置SelectPicPopupWindow弹出窗体的背景
//			doWorkPop.setBackgroundDrawable(dw);
//			WorkPopwindow.backgroundAlpha(this, 0.5f);//0.0-1.0
//			doWorkPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
//
//				@Override
//				public void onDismiss() {
//					WorkPopwindow.backgroundAlpha(MainActivityBackup.this, 1f);
//					doWorkPop = null;
//				}
//			});
//		}
//
//		//TODO 填充内容
//		((TextView) doWorkView.findViewById(R.id.tv_work_sn)).setText(String.format(getResources().getString(R.string.tv_device_sn), sn));
//		((TextView) doWorkView.findViewById(R.id.tv_current_user)).setText(AppApplication.getInstance().getUserInfo().getChinesename());
//
//		if (workId == workIds[0]) {//撤防
//			if (tiexie != null && tiexie.getList().size() > 0) {
//				ModelTiexies.TiexieInfo tInfo = tiexie.getList().get(0);
//				doWorkView.findViewById(R.id.text_view_dowork).setTag(R.string.tag_tv_track, tInfo);
//				((TextView) doWorkView.findViewById(R.id.tv_work_bm)).setText(String.format(getResources().getString(R.string.tv_device_bm), tInfo.getTxno()));
//				((TextView) doWorkView.findViewById(R.id.tv_work_gdh)).setText(String.format(getResources().getString(R.string.tv_device_gdh), tInfo.getTrackId()));
//				((TextView) doWorkView.findViewById(R.id.tv_work_txwz)).setText(String.format(getResources().getString(R.string.tv_device_txwz), tInfo.getTxName()));
//			}
//		} else if (workId == workIds[1]) {//巡检
//			if (tiexie != null && tiexie.getList().size() > 0) {
//				ModelTiexies.TiexieInfo tInfo = tiexie.getList().get(0);
//				doWorkView.findViewById(R.id.text_view_dowork).setTag(R.string.tag_tv_track, tInfo);
//				((TextView) doWorkView.findViewById(R.id.tv_work_bm)).setText(String.format(getResources().getString(R.string.tv_device_bm), tInfo.getTxno()));
//				((TextView) doWorkView.findViewById(R.id.tv_work_gdh)).setText(String.format(getResources().getString(R.string.tv_device_gdh), tInfo.getTrackId()));
//				((TextView) doWorkView.findViewById(R.id.tv_work_txwz)).setText(String.format(getResources().getString(R.string.tv_device_txwz), tInfo.getTxName()));
//			}
//		} else if (workId == workIds[2]) {//设防
//			//设防
//			((TextView) doWorkView.findViewById(R.id.tv_work_bm)).setText(String.format(getResources().getString(R.string.tv_device_bm), bm));
//			((TextView) doWorkView.findViewById(R.id.tv_work_gdh)).setText(String.format(getResources().getString(R.string.tv_device_gdh), ""));
//			((TextView) doWorkView.findViewById(R.id.tv_work_jcch)).setText(String.format(getResources().getString(R.string.tv_device_jcch), ""));
//			((TextView) doWorkView.findViewById(R.id.tv_work_txwz)).setText(String.format(getResources().getString(R.string.tv_device_txwz), ""));
//		}
//
//		doWorkView.findViewById(R.id.text_view_cancel).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick (View view) {
//				doWorkPop.dismiss();
//				doWorkPop = null;
//			}
//		});
//
//		doWorkView.findViewById(R.id.text_view_dowork).setTag(R.string.tag_tv, workId);
//		doWorkView.findViewById(R.id.text_view_dowork).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick (View view) {
//				int workId = Integer.parseInt(view.getTag(R.string.tag_tv).toString());
//				//填充内容
//				if (workId == workIds[0]) {
//					ModelTiexies.TiexieInfo tInfo = (ModelTiexies.TiexieInfo) view.getTag(R.string.tag_tv_track);
//					//撤防
//					uninstall(tInfo.getTxno(), AppApplication.getInstance().getUserInfo().getUserid());
//				} else if (workId == workIds[1]) {
//					ModelTiexies.TiexieInfo tInfo = (ModelTiexies.TiexieInfo) view.getTag(R.string.tag_tv_track);
//					//巡检
//					checkModel(sn, tInfo.getTrackId(), AppApplication.getInstance().getUserInfo().getUserid());
//				} else if (workId == workIds[2]) {
//					//设防
//					String et_jcch = ((EditText) doWorkView.findViewById(R.id.et_jcch)).getText().toString().trim();
//					if (et_jcch.length() == 0) {
//						ToastUtil.showShort(mContext, "请输入机车车号");
//						return;
//					}
//					Spinner spinner_gdh = (Spinner) doWorkView.findViewById(R.id.spinner_gdh);
//					//请求设防接口
//					install(sn, spinner_gdh.getSelectedItem().toString(), AppApplication.getInstance().getUserInfo().getUserid());
//				}
//
//				doWorkPop.dismiss();
//				doWorkPop = null;
//			}
//		});
//
//		doWorkPop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
//	}
//
//
//	void toggle() {
//		int drawerLockMode = drawer_layout.getDrawerLockMode(GravityCompat.START);
//		if (drawer_layout.isDrawerVisible(GravityCompat.START)
//				&& (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_OPEN)) {
//			drawer_layout.closeDrawer(GravityCompat.START);
//		} else if (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
//			drawer_layout.openDrawer(GravityCompat.START);
//		}
//	}
//
//	@Override
//	public void onBackPressed () {
//		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//		if (drawer.isDrawerOpen(GravityCompat.START)) {
//			drawer.closeDrawer(GravityCompat.START);
//		} else {
//			super.onBackPressed();
//		}
//	}
//
//	/**
//	 * 弹窗点击事件
//	 */
//	private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
//
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//			middlePopup.dismiss();
//			currentStation = station.getStations().get(position);
//			tv_station.setText(currentStation.getStationname() + "");
//
//			//TODO 切换电子地图，刷新整个地图
//		}
//	};
//
//	/**
//	 * 设置弹窗
//	 *
//	 * @param type
//	 */
//	private void setPopup(int type) {
//		ArrayList<String> stationNames = new ArrayList();
//		for (ModelStation.StationInfo s : station.getStations()) {
//			stationNames.add(s.getStationname());
//		}
//		middlePopup = new TopMiddlePopup(mContext, screenW, screenH, onItemClickListener, stationNames, type);
//	}
//
//	@Override
//	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//		switch (requestCode) {
//			case UpdateAppManager.REQUEST_EXTERNAL_STORAGE:
//				if (grantResults.length > 0
//						&& grantResults[0] == PackageManager.PERMISSION_GRANTED
//						&& grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//					// 权限请求成功的操作
//					manager.showDownloadDialog();
//				} else {
//
//					// 权限请求失败的操作
//				}
//				break;
//			case REQUEST_ENABLE_BT:
//				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//					clickDoWork(findViewById(R.id.ll_work));
//				} else {
//					Toast.makeText(MainActivityBackup.this, "蓝牙权限未开启,请设置", Toast.LENGTH_SHORT).show();
//				}
//				break;
//		}
//	}
//
//	/**
//	 * 应用蓝牙权限
//	 */
//	boolean checkBluetoothPermission() {
//		if (Build.VERSION.SDK_INT >= 23) {
//			//校验是否已具有模糊定位权限
//			if (ContextCompat.checkSelfPermission(MainActivityBackup.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//				return false;
//			} else {
//				//权限已打开
//				return true;
//			}
//		} else {
//			//小于23版本直接使用
//			return true;
//		}
//	}
//
//	/**
//	 * 执行扫描
//	 */
//	void startScan(View view) {
//
//		workPop = new WorkPopwindow(MainActivityBackup.this, itemsOnClick);
//		workPop.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//		workPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
//
//			@Override
//			public void onDismiss() {
//				turnOffBle();
//			}
//		});
//
//		workPop.getmView().findViewById(R.id.ll_tmp).setVisibility(View.VISIBLE);
//
//		SKYBeaconManager.getInstance().init(mContext);
//		SKYBeaconManager.getInstance().setCacheTimeMillisecond(2000);
//		SKYBeaconManager.getInstance().setScanTimerIntervalMillisecond(1000);
//		startRanging();
//	}
//
//	/**
//	 * 关闭蓝牙所有操作
//	 */
//	private void turnOffBle() {
//		//停止扫描
//		stopRanging();
//		if (skyBeaconCommunication != null) {
//			skyBeaconCommunication.disconnect();
//		}
//		workPop.backgroundAlpha(MainActivityBackup.this, 1f);
//		workPop = null;
//	}
//
//	private SKYBeaconCommunication skyBeaconCommunication;
//
//	/**
//	 * 开始扫描蓝牙
//	 */
//	private void startRanging() {
//		SKYBeaconManager.getInstance().startScanService(new ScanServiceStateCallback() {
//
//			@Override
//			public void onServiceDisconnected() {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onServiceConnected() {
//				// TODO Auto-generated method stub
//				SKYBeaconManager.getInstance().startRangingBeacons(null);
//			}
//		});
//
//		SKYBeaconManager.getInstance().setRangingBeaconsListener(new RangingBeaconsListener() {
//
//			@Override
//			public void onRangedNearbyBeacons(SKYRegion beaconRegion, List beaconList) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onRangedBeaconsMultiIDs(SKYRegion beaconRegion, List beaconMultiIDsList) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onRangedBeacons(SKYRegion beaconRegion, List beaconList) {
//				if (beaconList.size() >= 1) {
//					//取最近一个设备
//					final String deviceAddress = ((SKYBeacon) beaconList.get(0)).getDeviceAddress();
//					//执行连接设备
//					runOnUiThread(new Runnable() {
//						@Override
//						public void run () {
//							//获取到mac地址，执行连接设备获取deviceName
//							((TextView) workPop.getmView().findViewById(R.id.tv_device)).setText(String.format(mContext.getResources().getString(R.string.tv_device_connect), deviceAddress));
////							workPop.getmView().findViewById(R.id.tv_device).setTag(R.string.tag_tv, d);
//							//停止扫描
//							stopRanging();
//
//							//执行连接设备
//							connectBeacon(deviceAddress);
//						}
//					});
//				}
//			}
//		});
//	}
//
//	/**
//	 * 停止扫描
//	 */
//	private void stopRanging() {
//		SKYBeaconManager.getInstance().stopScanService();
//		SKYBeaconManager.getInstance().stopRangingBeasons(null);
//	}
//
//	/**
//	 * 连接蓝牙设备
//	 *
//	 * @param deviceAddress
//	 */
//	public void connectBeacon(String deviceAddress) {
//		ConnectionStateCallback connectionStateCallback = new ConnectionStateCallback() {
//
//			@Override
//			public void onDisconnected() {
//				turnOffBle();
//			}
//
//			@Override
//			public void onConnectedSuccess(SKYBeaconMultiIDs skyBeaconMultiIDs) {
//				Log.e("aaaaa" , "deviceName: " + skyBeaconMultiIDs.getDeviceName());
//
//				skyBeaconCommunication.disconnect();
//			}
//
//			@Override
//			public void onConnectedSuccess(final SKYBeacon skyBeacon) {
//				final SKYBeacon skyBeaconTmp = skyBeacon;
//				Log.e("bbbbb" , "deviceName: " + skyBeacon.getDeviceName());
//				final String deviceName = skyBeacon.getDeviceName();
//				// TODO Auto-generated method stub
////				runOnUiThread(new Runnable() {
////
////					@Override
////					public void run() {
////						// TODO Auto-generated method stub
////						stateInfo.setText("connect success! singleBeacon");
////						setView(skyBeaconTmp);
////					}
////				});
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run () {
//						((TextView) workPop.getmView().findViewById(R.id.tv_device)).setText(String.format(mContext.getResources().getString(R.string.tv_ble_connect), deviceName));
//						workPop.getmView().findViewById(R.id.tv_device).setTag(R.string.tag_tv, skyBeacon);
//					}
//				});
//
//				skyBeaconCommunication.disconnect();
//			}
//
//			@Override
//			public void onConnectedFailed(SKYBeaconConfigException skyBeaconConfigException) {
//				String exceptionInfo = skyBeaconConfigException.getMessage() + String.valueOf(skyBeaconConfigException.getCode());
//				ToastUtil.showShort(mContext, exceptionInfo);
//
//				turnOffBle();
//			}
//		};
//		skyBeaconCommunication = new SKYBeaconCommunication(this);
//		SKYBeacon skyBeaconConnect = new SKYBeacon(deviceAddress);
//		skyBeaconCommunication.connect(skyBeaconConnect, connectionStateCallback);
//	}
//
//
//	/**
//	 * 强制开启当前 Android 设备的 Bluetooth
//	 * @return true：强制打开 Bluetooth　成功　false：强制打开 Bluetooth 失败
//	 */
//	public static boolean turnOnBluetooth() {
//		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//
//		if (bluetoothAdapter != null) {
//			return bluetoothAdapter.enable();
//		}
//
//		return false;
//	}
//
//	@Override
//	protected void onDestroy() {
//		unregisterReceiver(mqqtBroadReceiver);
//		mqqtBroadReceiver = null;
//		super.onDestroy();
//	}
//
//
//
//	//接收服务器的消息通知到界面
//	private class MqqtBroadReceiver extends BroadcastReceiver {
//		@Override
//		public void onReceive(Context context, Intent intent) {
//
//			ModelMqtt task = GsonTools.getObjectData(intent.getStringExtra("msg"), ModelMqtt.class);
//			if (null == task)
//				return;
//
//			Log.e("aaaaaa", intent.getStringExtra("msg"));
//
//			Message msg = mHandler.obtainMessage();
//			msg.obj = task;
//			msg.what = NEW_NOTICE;
//			mHandler.sendMessage(msg);
//		}
//	}
//
//	/**
//	 * 获取屏幕的宽和高
//	 */
//	public void getScreenPixels() {
//		DisplayMetrics metrics = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(metrics);
//		screenW = metrics.widthPixels;
//		screenH = metrics.heightPixels;
//	}
//
//
//	//WeakHandler必须是Activity的一个实例变量.原因详见：http://dk-exp.com/2015/11/11/weak-handler/
//	private WeakHandler mHandler = new WeakHandler() {
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			switch (msg.what) {
//
//				case NEW_NOTICE:
//					ArrayList<ModelMqtt> newList = new ArrayList<>();
//						newList.add((ModelMqtt) msg.obj);
//					addItems(newList);
//
//					mRecyclerView.refreshComplete(REQUEST_COUNT);
//
//					break;
//				case MAPCLICK:
//					Object tag = msg.obj;
//					if (tag != null) {
//						motorWithTieXieInfo(tag.toString());
//					} else {
//						ToastUtil.showShort(mContext, "查找不到该信息");
//					}
//					break;
//				default:
//					break;
//			}
//		}
//	};
//
//
//	/**
//	 * 作业 - 巡检
//	 * @param txno
//	 * @param trackId
//	 * @param userId
//	 */
//	void checkModel(String txno, String trackId, String userId) {
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("trackId", trackId);
//		params.put("txno", txno);
//		params.put("userId", userId);
//
//		if (progressDialog == null || !progressDialog.isShowing())
//			progressDialog = AppUtil.getProgressDialog(mContext, getResources().getString(R.string.working), false, false);
//
//		AsyncHttpHelper.getInstance().post(mContext, Constant.CHECKMODEL, params, new AsyncHttpResponseHandler() {
//
//			@Override
//			public void onFailure (final int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run () {
//						if (progressDialog != null && progressDialog.isShowing())
//							progressDialog.dismiss();
//
//						ToastUtil.showShort(mContext, arg0);
//					}
//				});
//			}
//
//
//			@Override
//			public void onSuccess (int arg0, Header[] arg1, byte[] arg2) {
//
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run () {
//						if (progressDialog != null && progressDialog.isShowing())
//							progressDialog.dismiss();
//					}
//				});
//
//				final ModelWorkResult result = GsonTools.getObjectData(new String(arg2), ModelWorkResult.class);
//
//				if (result.getCode() == EResponseCode.SUCCESS.getCode()) {
//					runOnUiThread(new Runnable() {
//						@Override
//						public void run () {
//							ToastUtil.showShort(mContext, "执行巡检成功");
//							//TODO 作业完成，是否需要执行刷新地图
//						}
//					});
//				} else if (result.getCode() == EResponseCode.WRONG.getCode()) {
//					runOnUiThread(new Runnable() {
//						@Override
//						public void run () {
//							ToastUtil.showShort(mContext, result.getMsg());
//						}
//					});
//				}
//			}
//		});
//	}
//
//
//	/**
//	 * 作业 - 设防（安装）
//	 * @param txno
//	 * @param trackId
//	 * @param userId
//	 */
//	void install(String txno, String trackId, String userId) {
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("trackId", trackId);
//		params.put("txno", txno);
//		params.put("userId", userId);
//
//		runOnUiThread(new Runnable() {
//			@Override
//			public void run () {
//				if (progressDialog == null || !progressDialog.isShowing())
//					progressDialog = AppUtil.getProgressDialog(mContext, getResources().getString(R.string.working), false, false);
//			}
//		});
//
//		AsyncHttpHelper.getInstance().post(mContext, Constant.INSTALL, params, new AsyncHttpResponseHandler() {
//
//				@Override
//				public void onFailure (final int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//
//					runOnUiThread(new Runnable() {
//						@Override
//						public void run () {
//							if (progressDialog != null && progressDialog.isShowing())
//								progressDialog.dismiss();
//
//							ToastUtil.showShort(mContext, "操作失败，请重试");
//						}
//					});
//				}
//
//
//				@Override
//				public void onSuccess (int arg0, Header[] arg1, byte[] arg2) {
//
//					runOnUiThread(new Runnable() {
//						@Override
//						public void run () {
//							if (progressDialog != null && progressDialog.isShowing())
//								progressDialog.dismiss();
//						}
//					});
//
//					final ModelWorkResult result = GsonTools.getObjectData(new String(arg2), ModelWorkResult.class);
//
//					runOnUiThread(new Runnable() {
//						@Override
//						public void run () {
//							if (result.getCode() == EResponseCode.SUCCESS.getCode()) {
//								ToastUtil.showShort(mContext, "执行设防成功");
//								//TODO 作业完成，是否需要执行刷新地图
//
//							} else if (result.getCode() == EResponseCode.WRONG.getCode()) {
//								ToastUtil.showShort(mContext, result.getMsg());
//							}
//						}
//					});
//				}
//			});
//	}
//
//	/**
//	 * 作业 - 撤防（卸载）
//	 * @param txno
//	 * @param userId
//	 */
//	void uninstall(String txno, String userId) {
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("txno", txno);
//		params.put("userId", userId);
//
//		runOnUiThread(new Runnable() {
//			@Override
//			public void run () {
//				if (progressDialog == null || !progressDialog.isShowing())
//					progressDialog = AppUtil.getProgressDialog(mContext, getResources().getString(R.string.working), false, false);
//			}
//		});
//
//		AsyncHttpHelper.getInstance().post(mContext, Constant.UNINSTALL, params, new AsyncHttpResponseHandler() {
//
//			@Override
//			public void onFailure (final int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run () {
//						if (progressDialog != null && progressDialog.isShowing())
//							progressDialog.dismiss();
//
//						ToastUtil.showShort(mContext, "操作失败，请重试");
//					}
//				});
//			}
//
//
//			@Override
//			public void onSuccess (int arg0, Header[] arg1, byte[] arg2) {
//
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run () {
//						if (progressDialog != null && progressDialog.isShowing())
//							progressDialog.dismiss();
//					}
//				});
//
//				final ModelWorkResult result = GsonTools.getObjectData(new String(arg2), ModelWorkResult.class);
//
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run () {
//						if (result.getCode() == EResponseCode.SUCCESS.getCode()) {
//							ToastUtil.showShort(mContext, "执行设防成功");
//							//TODO 作业完成，是否需要执行刷新地图
//
//						} else if (result.getCode() == EResponseCode.WRONG.getCode()) {
//							ToastUtil.showShort(mContext, result.getMsg());
//						}
//					}
//				});
//			}
//		});
//	}
//
//	/**
//	 * 点击电子地图-弹窗显示内容
//	 *
//	 * @param carId
//	 */
//	void motorWithTieXieInfo(String carId) {
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("carId", 15);
//
//		runOnUiThread(new Runnable() {
//			@Override
//			public void run () {
//				if (progressDialog == null || !progressDialog.isShowing())
//					progressDialog = AppUtil.getProgressDialog(mContext, getResources().getString(R.string.loading), false, false);
//			}
//		});
//
//		AsyncHttpHelper.getInstance().get(mContext, Constant.TIEXIE_INFO, params, new AsyncHttpResponseHandler() {
//
//			@Override
//			public void onFailure (final int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run () {
//						if (progressDialog != null && progressDialog.isShowing())
//							progressDialog.dismiss();
//
//						ToastUtil.showShort(mContext, "操作失败，请重试");
//					}
//				});
//			}
//
//
//			@Override
//			public void onSuccess (int arg0, Header[] arg1, byte[] arg2) {
//
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run () {
//						if (progressDialog != null && progressDialog.isShowing())
//							progressDialog.dismiss();
//					}
//				});
//
//				final ModelMotorWithTiexieInfo result = GsonTools.getObjectData(new String(arg2), ModelMotorWithTiexieInfo.class);
//
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run () {
//						if (result.getCode() == EResponseCode.SUCCESS.getCode()) {
//							if (result.getMotor() == null) {
//								ToastUtil.showShort(mContext, "未查询到相关信息");
//								return;
//							}
//
//							infoPop = new InfoPopwindow(MainActivityBackup.this, result);
//							infoPop.showAtLocation(webView,
//									Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//
//						} else if (result.getCode() == EResponseCode.WRONG.getCode()) {
//							ToastUtil.showShort(mContext, result.getMsg());
//						}
//					}
//				});
//			}
//		});
//	}
}