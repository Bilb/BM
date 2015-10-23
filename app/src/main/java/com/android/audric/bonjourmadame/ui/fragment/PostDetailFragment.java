package com.android.audric.bonjourmadame.ui.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.audric.bonjourmadame.R;
import com.android.audric.bonjourmadame.model.Post;
import com.android.audric.bonjourmadame.ui.Intents;
import com.android.audric.bonjourmadame.ui.activity.PostDetailActivity;
import com.android.audric.bonjourmadame.ui.activity.PostListActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * A fragment representing a single Post detail screen.
 * This fragment is either contained in a {@link PostListActivity}
 * in two-pane mode (on tablets) or a {@link PostDetailActivity}
 * on handsets.
 */
public class PostDetailFragment extends Fragment {
    /**
     * The dummy content this fragment is presenting.
     */
    private Post post;


    private ImageLoadingListener fadeInListener = new ImageLoadingListener() {
        @Override
        public void onLoadingStarted(String imageUri, View view) {
            showView(getView().findViewById(R.id.progressBar), true);
        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            showView(getView().findViewById(R.id.progressBar), false);
        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            showView(getView().findViewById(R.id.progressBar), false);
            FadeInBitmapDisplayer.animate(view, 500);
        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {
            showView(getView().findViewById(R.id.progressBar), false);
        }
    };

    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .build();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PostDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(Intents.POST)) {
            post = getArguments().getParcelable(Intents.POST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (post != null) {
            ImageView imv = (ImageView) rootView.findViewById(R.id.post_detail);
            ImageLoader.getInstance().displayImage(post.getBiggestPictureUrl().getUrl(),imv, options, fadeInListener );
        }

        return rootView;
    }

    private void showView(View v, boolean show) {
        v.setVisibility((show == true?View.VISIBLE:View.INVISIBLE));
    }
}
