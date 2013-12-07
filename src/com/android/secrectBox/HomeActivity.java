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
    final private String LOGTAG = "6174-homeActivity";
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
        initUIElements();
        initUIElementEvents();
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
                Intent intent = new Intent();
                intent.setClass(HomeActivity.this, FindActivity.class);
                startActivity(intent);
            }
        });

        navCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(LOGTAG, "clicked navCreateBtn");
                toast("clicked navCreateBtn");
            }
        });
    }

    private void toast(String info){
        Toast toast;
        toast = Toast.makeText(getApplicationContext(),
                info, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
