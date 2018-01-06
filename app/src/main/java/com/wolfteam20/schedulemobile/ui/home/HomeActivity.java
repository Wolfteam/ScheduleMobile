package com.wolfteam20.schedulemobile.ui.home;

import android.os.Bundle;
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

    @BindView(R.id.content_frame) protected FrameLayout mContendedor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getLayoutInflater().inflate(R.layout.home_activity, mContendedor);
    }
}
