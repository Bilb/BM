package com.android.audric.bonjourmadame.ui.activity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.audric.bonjourmadame.BonjourMadameApplication;
import com.android.audric.bonjourmadame.R;
import com.android.audric.bonjourmadame.Request.PostsLoader;
import com.android.audric.bonjourmadame.model.Post;
import com.android.audric.bonjourmadame.ui.Intents;

import java.util.ArrayList;
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

        Button retryButton = (Button) findViewById(R.id.retryButton);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showView(findViewById(R.id.progressBar), true);
                showView(findViewById(R.id.retryButton), false);
                getLoaderManager().restartLoader(GET_POST_ID, null, LaunchActivity.this);
            }
        });
    }


    @Override
    public Loader<List<Post>> onCreateLoader(int id, Bundle args) {
        return new PostsLoader(getApplicationContext(), 0, 20);
    }

    @Override
    public void onLoadFinished(Loader<List<Post>> loader, List<Post> data) {
        if(data != null) {
            ((BonjourMadameApplication) getApplication()).addPosts(data);
            Intent i = new Intent(this, SimpleImageActivity.class);
            startActivity(i);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(), R.string.download_bm_posts_error,
                    Toast.LENGTH_LONG).show();
            showView(findViewById(R.id.progressBar), false);
            showView(findViewById(R.id.retryButton), true);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Post>> loader) {
        /* Nothing to do */
    }

    private void showView(View v, boolean show) {
        v.setVisibility((show ? View.VISIBLE : View.INVISIBLE));
    }
}
