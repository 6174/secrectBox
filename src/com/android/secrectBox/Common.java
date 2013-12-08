package com.android.secrectBox;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Administrator on 13-12-8.
 */
public class Common {
    private String LOGTAG = "6174-secrectBox";
    private static Common ourInstance = new Common();
    public static Common getInstance() {
        return ourInstance;
    }
    private Common() {
    }

    public void goTo(Context context, Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(context, cls);
        context.startActivity(intent);
    }

    public void toast(Context context, String info){
        Toast toast;
        toast = Toast.makeText(context.getApplicationContext(),
                info, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    public String getTag(){
        return LOGTAG;
    }
}
