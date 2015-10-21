package com.android.audric.bonjourmadame.Request;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by audric on 21/10/15.
 */
public class RequestManager {

    private static String tumblrToken = "2qET0uQCcMsWWG5aSd6bDyxGnEalx045VFaiboVrd4YuCp1HIz";
    private static String baseURL = "https://api.tumblr.com/v2/blog/bonjourmadame.fr/";
    private static String postsURL = baseURL + "posts";


    private static final String getPostsUrlWithToken() {
        return postsURL + "?api_key=" + tumblrToken;
    }




    public static String getPosts() {
        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet get = new HttpGet(getPostsUrlWithToken());

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(get);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            // Oops
            e.printStackTrace();
        }
        finally {
            try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
        }
        return result;
    }





}
