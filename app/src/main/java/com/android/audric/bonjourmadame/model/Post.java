package com.android.audric.bonjourmadame.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by audric on 21/10/15.
 */
public class Post {
    private static final String TAG = "POST";

    private static final String ID_JSON = "id";
    private static final String POST_URL_JSON = "post_url";
    private static final String TIMESTAMP_JSON = "timestamp";
    private static final String CAPTION_JSON = "caption";
    private static final String PHOTOS_JSON = "photos";
    private static final String ALT_SIZES_JSON = "alt_sizes";


    private double id;

    private String postUrl, caption ;
    private int published;

    private List<Photo> photos;

    public static Post fromJSON(JSONObject current) throws JSONException {

        Post post = new Post();
        post.id = current.getDouble(ID_JSON);
        post.postUrl = current.getString(POST_URL_JSON);
        post.caption = current.getString(CAPTION_JSON);
        post.published = current.getInt(TIMESTAMP_JSON);
        post.photos = Post.fromJson(current.getJSONArray(PHOTOS_JSON));


        return post;
    }

    private static List<Photo> fromJson(JSONArray array) throws JSONException {
        List<Photo> photos = new ArrayList<>();

        JSONArray altSizeArray = array.getJSONObject(0).getJSONArray(ALT_SIZES_JSON);

        for( int i = 0; i < altSizeArray.length() ; i++) {
            Photo photo = Photo.fromJSON(altSizeArray.getJSONObject(i));
            photos.add(photo);
            Log.i(TAG, "Adding a photo: " + photo);
        }

        return photos;
    }


    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", postUrl='" + postUrl + '\'' +
                ", caption='" + caption + '\'' +
                ", published=" + published +
                ", photos=" + photos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (Double.compare(post.id, id) != 0) return false;
        if (published != post.published) return false;
        if (postUrl != null ? !postUrl.equals(post.postUrl) : post.postUrl != null) return false;
        if (caption != null ? !caption.equals(post.caption) : post.caption != null) return false;
        return !(photos != null ? !photos.equals(post.photos) : post.photos != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(id);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (postUrl != null ? postUrl.hashCode() : 0);
        result = 31 * result + (caption != null ? caption.hashCode() : 0);
        result = 31 * result + published;
        result = 31 * result + (photos != null ? photos.hashCode() : 0);
        return result;
    }
}
