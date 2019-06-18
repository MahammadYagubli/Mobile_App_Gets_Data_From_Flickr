package com.mahammadyagubli.flickrbrowser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements getFlickerJsonData.OnDataAvailable, RecycleItemClickListener.OnRecyclerClickListener {

    private static final String TAG = "MainActivity";
private FlickerRecyclerViewAdapter mFlickerRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activiteToolbar(true);
      Toolbar  toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Main Page");
        }
        toolbar.setSubtitle("Test Subtitle");
        toolbar.inflateMenu(R.menu.menu_main);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycleView) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFlickerRecyclerViewAdapter=new FlickerRecyclerViewAdapter(new ArrayList<Photo>(),this);
        recyclerView.setAdapter(mFlickerRecyclerViewAdapter);
       recyclerView.addOnItemTouchListener(new RecycleItemClickListener(this,recyclerView,this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    // GetRawData getrawData=new GetRawData();
     //   getrawData.execute("https://api.flickr.com/services/feeds/photos_public.gne?tags=beuauty&tagmode=any&format=json&nojsoncallback=1");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume starts");
        super.onResume();
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String querryResult=sharedPreferences.getString(Flicker_Query,"");
        if(querryResult.length()>0){
            getFlickerJsonData getFlickrJsonData = new getFlickerJsonData(this, "https://api.flickr.com/services/feeds/photos_public.gne", "en-us", true);
            getFlickrJsonData.executeOnSameThread(querryResult);
        }
        else {
            getFlickerJsonData getFlickrJsonData = new getFlickerJsonData(this, "https://api.flickr.com/services/feeds/photos_public.gne", "en-us", true);
            getFlickrJsonData.executeOnSameThread("Love");

        }

        //  getFlickrJsonData.execute("android,nougat");
        Log.d(TAG, "onResume ends");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_search) {
           Intent intent=new Intent(this,SearchActivity.class);
           startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void OnDataAvailable(List<Photo> data, DownloadStatus status){
        if(status==DownloadStatus.OK){
            Log.d(TAG,"OnDataAvailable: data is "+status);
 mFlickerRecyclerViewAdapter.loadNewData(data);
   }
else {

            Log.d(TAG, "OnDataAvailable: failed with status"+status);
        }
        Log.d(TAG, "OnDataAvailable: Ends");
    }


    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, "onItemClick:  starts");
        Toast.makeText(MainActivity.this," Normal tap at position: " +position ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Log.d(TAG, "onItemLongClick:  starts");
        Intent intent=new Intent(this, PhotoDetailActivity.class);
        intent.putExtra(PHOTO_TRANSFER,mFlickerRecyclerViewAdapter.getPhoto(position));
        startActivity(intent);

         }
}
