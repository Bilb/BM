package com.android.audric.bonjourmadame;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

import com.android.audric.bonjourmadame.Request.PostsLoader;
import com.android.audric.bonjourmadame.model.Post;
import com.android.audric.bonjourmadame.ui.activity.SimpleImageActivity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by audric on 06/12/15.
 */
public class BonjourMadameApplication extends Application
{
    private static final int GET_POST_ID = 1;

    private List<Post> posts = null;


    @Override
    public void onCreate() {
        super.onCreate();

        initImageLoader(getApplicationContext());
    }


    public void addPosts(List<Post> posts) {
        if (this.posts == null)
            this.posts = new ArrayList<>();

        this.posts.addAll(posts);
    }


    public List<Post> getPosts() {
        return posts;
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(context);


        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }







}

