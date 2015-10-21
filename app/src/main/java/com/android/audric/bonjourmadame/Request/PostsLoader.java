package com.android.audric.bonjourmadame.Request;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.text.TextUtils;
import android.util.Log;

import com.android.audric.bonjourmadame.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by audric on 21/10/15.
 */
public class PostsLoader extends AsyncTaskLoader<List<Post>> {
    private static String TAG = "PostsLoader";



    private static String JSON_META = "meta";
    private static String JSON_STATUS = "status";
    private static String JSON_RESPONSE = "response";
    private static String JSON_BLOG = "blog";
    private static String JSON_POSTS = "posts";


    /**
     * Stores away the application context associated with context.
     * Since Loaders can be used across multiple activities it's dangerous to
     * store the context directly; always use {@link #getContext()} to retrieve
     * the Loader's Context, don't use the constructor argument directly.
     * The Context returned by {@link #getContext} is safe to use across
     * Activity instances.
     *
     * @param context used to retrieve the application context.
     */
    public PostsLoader(Context context) {
        super(context);
        onContentChanged();
    }


    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public List<Post> loadInBackground() {
        String posts = RequestManager.getPosts();


        if (!TextUtils.isEmpty(posts)) {


            try {
                JSONObject root = new JSONObject(posts);
                if(root.getJSONObject(JSON_META).getInt(JSON_STATUS) == 200) {
                    JSONArray postsJSON = root.getJSONObject(JSON_RESPONSE).getJSONArray(JSON_POSTS);


                    List<Post> postList = new ArrayList<>();


                    for(int i = 0; i < postsJSON.length(); i++) {
                        JSONObject current = postsJSON.getJSONObject(i);
                        Post post = Post.fromJSON(current);
                        postList.add(post);
                    }

                    Log.e(TAG, "post size:" + postList.size());
                    return postList;

                }
                else {
                    Log.e(TAG, "Failed to find status or status is not 200");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        return null;
    }


}
