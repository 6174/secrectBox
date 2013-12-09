package com.android.secrectBox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.*;
import com.baidu.platform.comapi.basestruct.GeoPoint;

/**
 * Created by Administrator on 13-12-8.
 */
public class LocationSelectActivity extends Activity{


    //--common part
    private Common common;
    private String LOGTAG;
    public static final String strKey = "SogUkjo6I8Ujev7GSFDVi5Oy";

    //--ui elements
    Button requestLocationBtn;

    //--map objects
    MyLocationMapView mMapView = null;
    MKMapViewListener mMapListener = null;
    BMapManager mBMapManager = null;
    MapController mMapController = null;

    // location objects
    LocationClient mLocClient;
    LocationData locData = null;
    MyLocationListenner myListener;
    locationOverlay myLocationOverlay = null;
    boolean isRequest = false;
    boolean isNeedLocationAtInitialTime = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 使用地图sdk前需先初始化BMapManager.
         * BMapManager是全局的，可为多个MapView共用，它需要地图模块创建前创建，
         * 并在地图地图模块销毁后销毁，只要还有地图模块在使用，BMapManager就不应该销毁
         */
        mBMapManager = new BMapManager(this);
        mBMapManager.init(strKey, null);
        /**
         * 由于MapView在setContentView()中初始化,所以它需要在BMapManager初始化之后
         */
        setContentView(R.layout.location_select_content);
        init();
    }

    private void init(){
        initCommon();
        initUIElements();
        initMap();
        initUIEvents();
    }

    private void initCommon(){
        common = Common.getInstance();
        LOGTAG = common.getTag();
    }

    private void initUIElements(){
        requestLocationBtn = (Button)findViewById(R.id.RequestLocationBtn);
        mMapView = (MyLocationMapView)findViewById(R.id.bmapView);
    }

    private void initUIEvents(){
        requestLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(LOGTAG, "clicked request location btn");
                requestLocation();
            }
        });
    }

    private void initMap(){
        initMapController();
//        initMapPos();
        initMapListener();
        initLocation();
        requestLocation();
    }

    private void initMapController(){
        mMapController = mMapView.getController();
        mMapController.enableClick(true);
        mMapController.setZoom(14);
        mMapView.setBuiltInZoomControls(true);
    }
    /**
     * 将地图移动至指定点
     * 使用百度经纬度坐标，可以通过http://api.map.baidu.com/lbsapi/getpoint/index.html查询地理坐标
     * 如果需要在百度地图上显示使用其他坐标系统的位置，请发邮件至mapapi@baidu.com申请坐标转换接口
     */
    private void initMapPos(){
        GeoPoint p ;
        double cLat = 115.403350;// 39.945 ;
        double cLon = 39.044865; // ;
//        locData.latitude = 115.403350;
//        locData.longitude = 39.044865;
        //设置中心点为天安门
        p = new GeoPoint((int)(cLat * 1E6), (int)(cLon * 1E6));
        mMapController.setCenter(p);
    }

    private void initMapListener(){
        /**
         *  MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
         */
        mMapListener = new MKMapViewListener() {
            /**
             * 在此处理地图移动完成回调
             * 缩放，平移等操作完成后，此回调被触发
             */
            @Override
            public void onMapMoveFinish() {
            }

            /**
             * 在此处理底图poi点击事件
             * 显示底图poi名称并移动至该点
             * 设置过： mMapController.enableClick(true); 时，此回调才能被触发
             *
             */
            @Override
            public void onClickMapPoi(MapPoi mapPoiInfo) {
                String title = "";
                if (mapPoiInfo != null){
                    title = mapPoiInfo.strText;
                    Toast.makeText(LocationSelectActivity.this,title,Toast.LENGTH_SHORT).show();
                    mMapController.animateTo(mapPoiInfo.geoPt);
                }
            }
            /**
             *  当调用过 mMapView.getCurrentMap()后触发
             */
            @Override
            public void onGetCurrentMap(Bitmap b) {
            }
            @Override
            public void onMapAnimationFinish() {
            }
            @Override
            public void onMapLoadFinish() {
            }
        };
        mMapView.regMapViewListener(mBMapManager, mMapListener);
    }

    private void initLocation(){

        Log.i(LOGTAG, "initLocation");
        //定位初始化
        mLocClient = new LocationClient(getApplicationContext());

        myListener = new MyLocationListenner();
        mLocClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//打开gps
        option.setCoorType("bd09ll");     //设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        mLocClient.requestLocation();

        //定位图层初始化
        myLocationOverlay = new locationOverlay(mMapView);

        //设置定位数据
        locData = new LocationData();
        myLocationOverlay.setData(locData);
        //添加定位图层
        mMapView.getOverlays().add(myLocationOverlay);
        myLocationOverlay.enableCompass();

        //修改定位数据后刷新图层生效
        mMapView.refresh();
    }

    private void requestLocation(){
        isRequest = true;
        mLocClient.requestLocation();
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.i("6174-haha", "onRecieveLoaction");
            if (location == null){
                return ;
            }
            logLocation(location);
            setLocData(location);
            animateToLocData();
            Log.i(LOGTAG, "on recieveLoation and map refreshed");
        }

        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null){
                return ;
            }
        }

        public void setLocData(BDLocation location){
            locData.latitude = location.getLatitude();
            locData.longitude = location.getLongitude();
            //如果不显示定位精度圈，将accuracy赋值为0即可
            locData.accuracy = location.getRadius();
            // 此处可以设置 locData的方向信息, 如果定位 SDK 未返回方向信息，用户可以自己实现罗盘功能添加方向信息。
            locData.direction = location.getDerect();
            //更新定位数据
            myLocationOverlay.setData(locData);
            //更新图层数据执行刷新后生效
            mMapView.refresh();
        }

        public void animateToLocData(){
            //是手动触发请求或首次定位时，移动到定位点
            if (isRequest || isNeedLocationAtInitialTime){
                //移动地图到定位点
                Log.d("LocationOverlay", "receive location, animate to it");
                mMapController.animateTo(new GeoPoint((int)(locData.latitude * 1e6), (int)(locData.longitude *  1e6)));
                isRequest = false;
                myLocationOverlay.setLocationMode(MyLocationOverlay.LocationMode.FOLLOWING);
            }
            //首次定位完成
            isNeedLocationAtInitialTime = false;
        }

        public void logLocation(BDLocation location){
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation){
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
            }
            Log.e(LOGTAG, sb.toString());
        }
    }

    //继承MyLocationOverlay重写dispatchTap实现点击处理
    public class locationOverlay extends MyLocationOverlay{

        public locationOverlay(MapView mapView) {
            super(mapView);
            // TODO Auto-generated constructor stub
        }
        @Override
        protected boolean dispatchTap() {
            // TODO Auto-generated method stub
            //处理点击事件,弹出泡泡
//            popupText.setBackgroundResource(R.drawable.popup);
//            popupText.setText("我的位置");
//            pop.showPopup(BMapUtil.getBitmapFromView(popupText),
//                    new GeoPoint((int)(locData.latitude*1e6), (int)(locData.longitude*1e6)),
//                    8);
            return true;
        }

    }

    /**
     *  MapView的生命周期与Activity同步
     */
    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mLocClient != null){
            mLocClient.stop();
        }
        mMapView.destroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMapView.onRestoreInstanceState(savedInstanceState);
    }

}




// 常用事件监听，用来处理通常的网络错误，授权验证错误等
class MyGeneralListener implements MKGeneralListener {

    @Override
    public void onGetNetworkState(int iError) {
        if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
//            Toast.makeText(DemoApplication.getInstance().getApplicationContext(), "您的网络出错啦！",
//                    Toast.LENGTH_LONG).show();
        }
        else if (iError == MKEvent.ERROR_NETWORK_DATA) {
//            Toast.makeText(DemoApplication.getInstance().getApplicationContext(), "输入正确的检索条件！",
//                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onGetPermissionState(int iError) {
        //非零值表示key验证未通过
        if (iError != 0) {
            //授权Key错误：
//            Toast.makeText(DemoApplication.getInstance().getApplicationContext(),
//                    "请在 DemoApplication.java文件输入正确的授权Key,并检查您的网络连接是否正常！error: "+iError, Toast.LENGTH_LONG).show();
//            DemoApplication.getInstance().m_bKeyRight = false;
        }
        else{
//            DemoApplication.getInstance().m_bKeyRight = true;
//            Toast.makeText(DemoApplication.getInstance().getApplicationContext(),
//                    "key认证成功", Toast.LENGTH_LONG).show();
        }
    }
}


/**
 * 继承MapView重写onTouchEvent实现泡泡处理操作
 * @author hejin
 *
 */
class MyLocationMapView extends MapView{
    static PopupOverlay   pop  = null;//弹出泡泡图层，点击图标使用
    public MyLocationMapView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    public MyLocationMapView(Context context, AttributeSet attrs){
        super(context,attrs);
    }
    public MyLocationMapView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (!super.onTouchEvent(event)){
            //消隐泡泡
            if (pop != null && event.getAction() == MotionEvent.ACTION_UP)
                pop.hidePop();
        }
        return true;
    }
}