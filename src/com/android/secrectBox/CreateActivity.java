package com.android.secrectBox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 13-12-8.
 */
public class CreateActivity extends Activity {
    final private int LOCATION_REQUEST_CODE = 0;
    private Common common;
    private String LOGTAG;

    //--UI Elements
    private Button navBackBtn;
    private TextView selectPositionBtn;

    @Override
    public void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_content);
        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.i(LOGTAG, "activitity result");
        switch (requestCode){
            case LOCATION_REQUEST_CODE:
                handlerLocationSelectResult(resultCode, data);
                break;
        }
    }

    private void init(){
        initCommon();
        initUIElements();
        initUIElementEvents();
    }

    private void initCommon(){
        common = Common.getInstance();
        LOGTAG = common.getTag();
    }

    private void initUIElements(){
        navBackBtn = (Button)findViewById(R.id.BackBtn);
        selectPositionBtn = (TextView)findViewById(R.id.PositionSelect);
    }

    private void initUIElementEvents(){
        navBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        selectPositionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("user_name", "6174");
                Intent intent = new Intent(CreateActivity.this, LocationSelectActivity.class);
                intent.putExtras(b);
                startActivityForResult(intent, LOCATION_REQUEST_CODE);
//                common.goTo(CreateActivity.this, LocationSelectActivity.class);
            }
        });
    }

    private void handlerLocationSelectResult(int resultCode, Intent intent){
        Bundle b = intent.getExtras();
        String userName = b.getString("user_name");
        common.toast(CreateActivity.this, "activity result, haha " + resultCode + userName);
    }
}

