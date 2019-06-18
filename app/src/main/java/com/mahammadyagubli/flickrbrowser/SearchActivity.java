package com.mahammadyagubli.flickrbrowser;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class SearchActivity extends BaseActivity {
    private static final String TAG ="SearchActivity: " ;
    private SearchView mSearchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar  toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("SearchActivity");
        }
       toolbar.inflateMenu(R.menu.menu_search);
        Log.d(TAG, "onCreate: starts");

        activiteToolbar(true);
        Log.d(TAG, "onCreate: ends");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: starts");
       getMenuInflater().inflate(R.menu.menu_search,menu);
        SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        mSearchView=(SearchView)menu.findItem(R.id.app_bar_search).getActionView();
        SearchableInfo searchableInfo=searchManager.getSearchableInfo(getComponentName());
        mSearchView.setSearchableInfo(searchableInfo);
      //  Log.d(TAG, "onCreateOptionsMenu: "+getComponentName().toShortString());

        Log.d(TAG, "onCreateOptionsMenu: hint is"+mSearchView.getQueryHint());
       // Log.d(TAG, "onCreateOptionsMenu:  searchable info is"+searchableInfo.toString());
        mSearchView.setIconified(false);
        Log.d(TAG, "onCreateOptionsMenu: returned "+true);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "onQueryTextSubmit: called");
                SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                sharedPreferences.edit().putString(Flicker_Query,s).apply();
               mSearchView.clearFocus();
                finish();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                finish();
                return false;
            }
        });
        return true;
    }

}
