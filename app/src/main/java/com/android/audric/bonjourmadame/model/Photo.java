package com.android.audric.bonjourmadame.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;

/**
 * Created by audric on 21/10/15.
 */
public class Photo implements Parcelable {

    private static final String URL_JSON = "url";
    private static final String URL_WIDTH = "width";
    private static final String URL_HEIGHT = "height";

    private String url;

    private int width, height;


    public Photo() {

    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeInt(width);
        dest.writeInt(height);
    }


    public static class PhotoComparator implements Comparator<Photo> {
        @Override
        public int compare(Photo o1, Photo o2) {
            return (new Integer(o1.width)).compareTo(o2.width);
        }
    }

    public static final Parcelable.Creator<Photo> CREATOR
            = new Parcelable.Creator<Photo>() {
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    private Photo(Parcel in) {
        url = in.readString();
        width = in.readInt();
        height = in.readInt();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getUrl() {
        return url;
    }


}
