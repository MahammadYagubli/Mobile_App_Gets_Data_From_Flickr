package com.mahammadyagubli.flickrbrowser;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;




public class BaseActivity extends AppCompatActivity{
    private static final String TAG = "BaseActivity";
    static final String Flicker_Query="FLICKER_QUERRY";
    static final String PHOTO_TRANSFER="FLICKER_QUERRY";

    void activiteToolbar(boolean enableHome){
        Log.d(TAG, "activiteToolbar: starts");
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
            if(toolbar!=null){
                setSupportActionBar(toolbar);
                actionBar=getSupportActionBar();

            }
        }
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(enableHome);
        }
    }



}
