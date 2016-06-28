package com.android.audric.bonjourmadame.ui.fragment;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.audric.bonjourmadame.BonjourMadameApplication;
import com.android.audric.bonjourmadame.R;
import com.android.audric.bonjourmadame.Request.PostsLoader;
import com.android.audric.bonjourmadame.model.Post;
import com.android.audric.bonjourmadame.ui.Intents;
import com.android.audric.bonjourmadame.ui.activity.SimpleImageActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

public class ImageGridFragment extends Fragment
        implements
        LoaderManager.LoaderCallbacks<List<Post>> {

    protected AbsListView listView;


    protected void startImagePagerActivity(int position) {
        Intent intent = new Intent(getActivity(), SimpleImageActivity.class);
        intent.putExtra(Intents.FRAGMENT_INDEX, ImagePagerFragment.INDEX);
        intent.putExtra(Intents.IMAGE_POSITION, position);
        startActivity(intent);
    }
    private static final int GET_POST_ID = 1;

    public static final int INDEX = 0;
    private int currentPage = 0, postsPerPage = 20;
    private ImageAdapter imageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_image_grid, container, false);
        listView = (GridView) rootView.findViewById(R.id.grid);
        imageAdapter = new ImageAdapter(getActivity());
        listView.setAdapter(imageAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startImagePagerActivity(position);
            }
        });


        listView.setOnScrollListener(new EndlessScrollListener());

        return rootView;
    }

    private class EndlessScrollListener implements AbsListView.OnScrollListener {
        private int previousTotal = 0;
        private boolean loading = true;

        public EndlessScrollListener() {
        }



        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            if (!loading && (view.getLastVisiblePosition() + 1 >= totalItemCount)) {
                loadMorePosts();
                loading = true;
            }
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }
    }


    private class ImageAdapter extends BaseAdapter {

        private List<Post> posts;

        private LayoutInflater inflater;

        private DisplayImageOptions options;

        ImageAdapter(Context context) {
            posts = ((BonjourMadameApplication) ImageGridFragment.this.getActivity().getApplication()).getPosts();
            inflater = LayoutInflater.from(context);


            BitmapDisplayer displayer = new FadeInBitmapDisplayer(500) {

                @Override
                public void display(Bitmap bitmap, ImageAware imageAware,
                                    LoadedFrom loadedFrom) {
                    if (loadedFrom != LoadedFrom.MEMORY_CACHE) {
                        super.display(bitmap, imageAware, loadedFrom);
                    } else {
                        ((ImageView) imageAware.getWrappedView()).setImageBitmap(bitmap);
                    }
                }
            };

            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.ic_stub)
                    .showImageForEmptyUri(R.drawable.ic_empty)
                    .showImageOnFail(R.drawable.ic_error)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .displayer(displayer)
                    .build();



        }

        @Override
        public int getCount() {
            if(posts == null)
                return 0;
            return posts.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.item_grid_image, parent, false);
                holder = new ViewHolder();
                assert view != null;
                holder.imageView = (ImageView) view.findViewById(R.id.image);
                holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }


            ImageLoader.getInstance()
                    .displayImage(posts.get(position).getBiggestPictureUrl().getUrl(), holder.imageView, options, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {
                            holder.progressBar.setProgress(0);
                            holder.progressBar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                            holder.progressBar.setVisibility(View.GONE);

                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            holder.progressBar.setVisibility(View.GONE);

                        }

                    }, new ImageLoadingProgressListener() {
                        @Override
                        public void onProgressUpdate(String imageUri, View view, int current, int total) {
                            holder.progressBar.setProgress(Math.round(100.0f * current / total));
                        }
                    });

            return view;
        }
    }

    static class ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
    }


    public void loadMorePosts() {
        currentPage++;
        getActivity().getLoaderManager().restartLoader(GET_POST_ID, null, this);
    }

    @Override
    public Loader<List<Post>> onCreateLoader(int id, Bundle args) {
        return new PostsLoader(getActivity().getApplicationContext(), currentPage, postsPerPage);
    }

    @Override
    public void onLoadFinished(Loader<List<Post>> loader, List<Post> data) {
        if(data != null) {
            ((BonjourMadameApplication) getActivity().getApplication()).addPosts(data);
            listView.deferNotifyDataSetChanged();
            imageAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Post>> loader) {
        /* Nothing to do */
    }
}
