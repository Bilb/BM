package com.android.audric.bonjourmadame.ui.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.audric.bonjourmadame.BonjourMadameApplication;
import com.android.audric.bonjourmadame.R;
import com.android.audric.bonjourmadame.model.Post;
import com.android.audric.bonjourmadame.ui.Intents;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;


public class ImagePagerFragment extends Fragment {

    public static final int INDEX = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_image_pager, container, false);
        ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);
        pager.setAdapter(new ImageAdapter(getActivity()));
        pager.setCurrentItem(getArguments().getInt(Intents.IMAGE_POSITION, 0));
        return rootView;
    }

    private class ImageAdapter extends PagerAdapter {

        private List<Post> posts;

        private LayoutInflater inflater;
        private DisplayImageOptions options;

        ImageAdapter(Context context) {
            posts = ((BonjourMadameApplication) ImagePagerFragment.this.getActivity().getApplication()).getPosts();
            inflater = LayoutInflater.from(context);

            options = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.ic_empty)
                    .showImageOnFail(R.drawable.ic_error)
                    .resetViewBeforeLoading(true)
                    .cacheOnDisk(true)
                    .imageScaleType(ImageScaleType.NONE_SAFE)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .considerExifParams(true)
                    .displayer(new FadeInBitmapDisplayer(300))
                    .build();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            if(posts == null)
                return 0;
            return posts.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
            assert imageLayout != null;
            ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
            final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);

            ImageLoader.getInstance().displayImage(posts.get(position).getBiggestPictureUrl().getUrl(),
                    imageView, options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    spinner.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    String message = null;
                    switch (failReason.getType()) {
                        case IO_ERROR:
                            message = "Input/Output error";
                            break;
                        case DECODING_ERROR:
                            message = "Image can't be decoded";
                            break;
                        case NETWORK_DENIED:
                            message = "Downloads are denied";
                            break;
                        case OUT_OF_MEMORY:
                            message = "Out Of Memory error";
                            break;
                        case UNKNOWN:
                            message = "Unknown error";
                            break;
                    }
                    Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();

                    spinner.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    spinner.setVisibility(View.GONE);
                }
            });

            view.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }
}
