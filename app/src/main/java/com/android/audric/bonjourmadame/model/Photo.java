package com.android.audric.bonjourmadame.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by audric on 21/10/15.
 */
public class Photo {

    private static final String URL_JSON = "url";
    private static final String URL_WIDTH = "width";
    private static final String URL_HEIGHT = "height";

    private String url;

    private int width, height;


    public static Photo fromJSON(JSONObject current) throws JSONException {
        Photo photo = new Photo();
        photo.url = current.getString(URL_JSON);
        photo.width = current.getInt(URL_WIDTH);
        photo.height = current.getInt(URL_HEIGHT);

        return photo;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "url='" + url + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo = (Photo) o;

        if (width != photo.width) return false;
        if (height != photo.height) return false;
        return !(url != null ? !url.equals(photo.url) : photo.url != null);

    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + width;
        result = 31 * result + height;
        return result;
    }
}
