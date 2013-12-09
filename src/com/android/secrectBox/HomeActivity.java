package com.android.secrectBox;

import android.app.Activity;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.util.Log;
import android.widget.Scroller;
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
    private Button drawerSwitcherBtn;
    private RelativeLayout pageFontEl;

    //--ui controller
    private Scroller mScroller;

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
        initScroller();
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
        drawerSwitcherBtn = (Button)findViewById(R.id.DrawerSwitcher);
        pageFontEl = (RelativeLayout)findViewById(R.id.PageFront);
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

        navSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                common.goTo(HomeActivity.this, LocationSelectActivity.class);
            }
        });

        drawerSwitcherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                common.toast(HomeActivity.this, "clickDrawer");
                animateDrawer();
            }
        });
    }

    private void initScroller(){
        mScroller =  new Scroller(HomeActivity.this);
    }

    private String switchState = "on";
    private void animateDrawer(){
//      Animation translateAnimation = new TranslateAnimation(0.0f, 100.0f, 0.0f, 0.0f);
//      translateAnimation.setDuration(1000);
//      translateAnimation.
//      pageFontEl.startAnimation(translateAnimation);
//      pageFontEl.scrollBy(300, 0);
        int[] location = new int[2];
        int leftDelta;
        pageFontEl.getLocationOnScreen(location);
        if(switchState == "off"){
            leftDelta = 0;
            switchState = "on";
        } else{
            leftDelta = common.dip2px(HomeActivity.this, 130);
            switchState = "off";
        }
        pageFontEl.layout(leftDelta, 0, leftDelta + pageFontEl.getWidth(), 0 + pageFontEl.getHeight());
    }
}
