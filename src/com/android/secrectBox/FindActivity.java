package com.android.secrectBox;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import cn.trinea.android.common.view.DropDownListView;
import cn.trinea.android.common.view.DropDownListView.OnDropDownListener;

/**
 * Created by zhutou on 13-12-7.
 */
public class FindActivity extends Activity {
    private Common common;
    private String LOGTAG;
    //--UI Elements
    private Button navBackBtn;
    private DropDownListView treasureListEl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_conetnt);
        init();
    }
//    private void nullFUnc(){
//
//    }
    private void init(){
        initCommon();
        initUIElements();
        initUIElementEvents();
    }

    private void initCommon(){
        common = Common.getInstance();
        LOGTAG = common.getTag();
    }

    private void  initUIElements(){
        navBackBtn = (Button)findViewById(R.id.NavBackBtn);
        initListView();
    }

    private void initListView(){
        treasureListEl = (DropDownListView)findViewById(R.id.TreasureList);
        TreasureListAdapter adapter = new TreasureListAdapter();
        treasureListEl.setAdapter(adapter);
    }

    private void initUIElementEvents(){
        navBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(LOGTAG, "click navBackBtn");
                finish();
            }
        });
        treasureListEl.setOnDropDownListener(new OnDropDownListener() {

            @Override
            public void onDropDown() {
//                new GetDataTask(true).execute();
            }
        });

        // set on bottom listener
        treasureListEl.setOnBottomListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                new GetDataTask(false).execute();
            }
        });
    }

    class TreasureListAdapter extends BaseAdapter{
        @Override
        public int getCount(){
            return 16;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = getLayoutInflater();
            TreasureViewHolder holder = null;
            if(view == null){
                view = inflater.inflate(R.layout.find_el_treasure_list_item,  null);
                holder = new TreasureViewHolder(view);
                view.setTag(holder);
            }else{
                holder = (TreasureViewHolder)view.getTag();
            }
            holder.getTreasureInfoEl().setText("Treasure Text set by grogram");
            return view;
        }
    }

    class TreasureViewHolder{
        private View base;
        private TextView treasureInfoEl = null;
        private Button goBtn = null;
        public TreasureViewHolder(View base){
            this.base = base;
        }
        public TextView getTreasureInfoEl(){
            if(treasureInfoEl == null){
                treasureInfoEl = (TextView)base.findViewById(R.id.TreasureText);
            }
            return treasureInfoEl;
        }
    }
}
