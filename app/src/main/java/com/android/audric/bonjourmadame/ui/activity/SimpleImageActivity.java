package com.android.audric.bonjourmadame.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.android.audric.bonjourmadame.R;
import com.android.audric.bonjourmadame.ui.Intents;
import com.android.audric.bonjourmadame.ui.fragment.ImageGridFragment;
import com.android.audric.bonjourmadame.ui.fragment.ImagePagerFragment;


/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class SimpleImageActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int frIndex = getIntent().getIntExtra(Intents.FRAGMENT_INDEX, 0);
        Fragment fr;
        String tag;
        int titleRes;
        switch (frIndex) {
            default:

            case ImageGridFragment.INDEX:
                tag = ImageGridFragment.class.getSimpleName();
                fr = getSupportFragmentManager().findFragmentByTag(tag);
                if (fr == null) {
                    fr = new ImageGridFragment();
                }
                titleRes = R.string.ac_name_image_grid;
                break;

            case ImagePagerFragment.INDEX:
                tag = ImagePagerFragment.class.getSimpleName();
                fr = getSupportFragmentManager().findFragmentByTag(tag);
                if (fr == null) {
                    fr = new ImagePagerFragment();
                    fr.setArguments(getIntent().getExtras());
                }
                titleRes = R.string.ac_name_image_pager;
                break;

        }

        setTitle(titleRes);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fr, tag).commit();
    }
}
