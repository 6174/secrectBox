package com.android.secrectBox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.util.Log;
import android.widget.Toast;

public class HomeActivity extends Activity {
    private Common common;
    private String LOGTAG;
    //--UI Elements
    //-Navigation Button groups
    private RelativeLayout navFindBtn;
    private RelativeLayout navCreateBtn;
    private RelativeLayout navUserSpaceBtn;
    private RelativeLayout navSettingBtn;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_content);
        init();
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
        navFindBtn = (RelativeLayout)findViewById(R.id.NavFindBtn);
        navCreateBtn = (RelativeLayout)findViewById(R.id.NavCreateBtn);
        navUserSpaceBtn = (RelativeLayout)findViewById(R.id.NavUserSpaceBtn);
        navSettingBtn = (RelativeLayout)findViewById(R.id.NavSettingBtn);
    }

    private void initUIElementEvents(){
        initNavBtnEvents();
    }

    private void initNavBtnEvents(){
        navFindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(LOGTAG, "clicked navFIndBtn");
                common.goTo(HomeActivity.this, FindActivity.class);
            }
        });

        navCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(LOGTAG, "clicked navCreateBtn");
//                toast("clicked navCreateBtn");
                common.goTo( HomeActivity.this,CreateActivity.class);
            }
        });
    }
}
