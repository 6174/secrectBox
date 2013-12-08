package com.android.secrectBox;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 13-12-8.
 */
public class CreateActivity extends Activity {
    private Common common;
    private String LOGTAG;

    //--UI Elements
    private Button navBackBtn;

    @Override
    public void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_content);
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
        navBackBtn = (Button)findViewById(R.id.BackBtn);
    }

    private void initUIElementEvents(){
        navBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

