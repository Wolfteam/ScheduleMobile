package com.wolfteam20.schedulemobile.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.wolfteam20.schedulemobile.R;
import com.wolfteam20.schedulemobile.ui.base.BaseDrawerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Efrain.Bastidas on 1/5/2018.
 */

public class HomeActivity extends BaseDrawerActivity {

    @BindView(R.id.content_frame) FrameLayout mContendedor;

    final String HOME_FRAGMENT_TAG = "HOME_FRAGMENT_TAG";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getLayoutInflater().inflate(R.layout.home_activity, mContendedor);

        HomeFragment fragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HOME_FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new HomeFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.home_activity, fragment, HOME_FRAGMENT_TAG)
                    .commit();
        }
    }

    @NonNull
    public static Intent getIntent(Context context){
        return new Intent(context, HomeActivity.class);
    }
}