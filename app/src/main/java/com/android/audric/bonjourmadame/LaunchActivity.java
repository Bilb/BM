package com.android.audric.bonjourmadame;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.audric.bonjourmadame.Request.PostsLoader;
import com.android.audric.bonjourmadame.model.Post;

import java.util.List;


public class LaunchActivity
        extends ActionBarActivity implements
        LoaderManager.LoaderCallbacks<List<Post>> {

    private static final String TAG = "LaunchActivity";


    private static final int GET_POST_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);


        getLoaderManager().initLoader(GET_POST_ID, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launch, menu);
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

        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<List<Post>> onCreateLoader(int id, Bundle args) {
        return new PostsLoader(getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<List<Post>> loader, List<Post> data) {
        Log.e(TAG, "load finished");
    }

    @Override
    public void onLoaderReset(Loader<List<Post>> loader) {
        /* Nothing to do */
    }
}
