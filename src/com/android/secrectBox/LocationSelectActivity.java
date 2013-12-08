package com.android.secrectBox;

import android.app.Activity;
import android.os.Bundle;
import com.tencent.mapapi.map.MapView;

/**
 * Created by Administrator on 13-12-8.
 */
public class LocationSelectActivity extends Activity{
    MapView mMapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_select_content);
        mMapView = (MapView) findViewById(R.id.mapview);
    }
}
