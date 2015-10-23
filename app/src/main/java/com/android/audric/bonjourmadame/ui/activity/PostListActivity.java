package com.android.audric.bonjourmadame.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.android.audric.bonjourmadame.R;
import com.android.audric.bonjourmadame.model.Post;
import com.android.audric.bonjourmadame.ui.Intents;
import com.android.audric.bonjourmadame.ui.fragment.PostDetailFragment;
import com.android.audric.bonjourmadame.ui.fragment.PostListFragment;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An activity representing a list of Posts. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PostDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link PostListFragment} and the item details
 * (if present) is a {@link PostDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link PostListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class PostListActivity extends FragmentActivity
        implements PostListFragment.Callbacks,
        BMPostsListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private List<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        ArrayList<Post> postsIntent = getIntent().getParcelableArrayListExtra(Intents.POSTS);
        posts = Collections.unmodifiableList(postsIntent);
        setContentView(R.layout.activity_post_list);

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(getApplicationContext());
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(10 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());

        if (findViewById(R.id.post_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((PostListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.post_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link PostListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(int position) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putParcelable(Intents.POST, posts.get(position));
            PostDetailFragment fragment = new PostDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.post_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, PostDetailActivity.class);
            detailIntent.putExtra(Intents.POST, posts.get(position));
            startActivity(detailIntent);
        }
    }

    @Override
    public List<Post> getPosts() {
        return posts;
    }
}
