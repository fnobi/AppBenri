package com.fnobi.appbenri;

import android.support.v7.app.ActionBarActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {
    
    public static final String EXTRAS_REFRESH = "extras_refresh";
    
    private SearchAppActivityFragment mFragment;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
        Boolean isRefresh = intent.getBooleanExtra(EXTRAS_REFRESH, false);
        if (isRefresh) {
            finish();
            return;
        }
        
        setContentView(R.layout.appbenri_activity_main);

        if (savedInstanceState == null) {
            mFragment = new SearchAppActivityFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.container, mFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Boolean isRefresh = intent.getBooleanExtra(EXTRAS_REFRESH, false);
        if (isRefresh) {
            mFragment.setupListView();
        }
    }
}
