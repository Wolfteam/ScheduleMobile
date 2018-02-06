package com.wolfteam20.schedulemobile.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.wolfteam20.schedulemobile.R;
import com.wolfteam20.schedulemobile.ui.base.BaseFragment;
import com.wolfteam20.schedulemobile.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Efrain Bastidas on 1/7/2018.
 */

public class HomeFragment extends BaseFragment implements HomeViewContract, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    @InjectPresenter
    HomePresenter mPresenter;

    @BindView(R.id.home_fragment_swipe_to_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.home_fragment_periodo_actual) TextView mPeriodoActual;
    private Button mButtonClicked;

    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;

    @ProvidePresenter
    HomePresenter provideHomePresenter() {
        getActivityComponent().inject(this);
        return mPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        setUnBinder(ButterKnife.bind(this, view));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onRefresh() {
        mPeriodoActual.setText("");
        mPresenter.getCurrentPeriodo();
    }

    @OnClick({R.id.btnPlanificacionAcademica, R.id.btnPlanificacionAulas, R.id.btnPlanificacionHorario})
    @Override
    public void onBtnPlanificacionClick(Button button) {
        mButtonClicked = button;
        if (!mPresenter.isWritePermissionGranted()) {
            return;
        }
        switch (button.getId()) {
            case R.id.btnPlanificacionAcademica:
                mPresenter.getPlanificacion(1);
                break;
            case R.id.btnPlanificacionAulas:
                mPresenter.getPlanificacion(2);
                break;
            case R.id.btnPlanificacionHorario:
                mPresenter.getPlanificacion(3);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onBtnPlanificacionClick(mButtonClicked);
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.permisos_write_msg), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void initLayout(View view, Bundle savedInstanceState) {
    }

    @Override
    public void hideLoading() {
        if (mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void requestWritePermission() {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                Constants.MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void showRequestWritePermissionExplanation() {
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.permisos_title))
                .setMessage(getResources().getString(R.string.permisos_write_msg))
                .setPositiveButton("Ok", null)
                .create();
        dialog.show();
    }

    @Override
    public void showDownloadProgressIndicator() {
        mNotifyManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(getContext(), "Algo");
        mBuilder.setContentTitle("Descargando archivos")
                .setContentText("Descarga en progreso");
        //TODO: Ver como diablos muestro el icono en LOLLIPOP
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBuilder.setColor(getResources().getColor(R.color.greyDarken1));
            mBuilder.setSmallIcon(R.drawable.ic_stat_icon_app);
        } else {
            mBuilder.setSmallIcon(R.drawable.icon_app_high_res);
            //mBuilder.setSmallIcon(R.drawable.icon)
        }
        // Sets an activity indicator for an operation of indeterminate length
        mBuilder.setProgress(0, 0, true);
        // Issues the notification
        mNotifyManager.notify(1, mBuilder.build());
    }

    @Override
    public void showCurrentPeriodo(String periodo) {
        mPeriodoActual.setText(periodo);
    }

    @Override
    public void showLoading() {
        if (!mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void stopDownloadProgressIndicator() {
        // When the loop is finished, updates the notification
        mBuilder.setContentText("Descarga completada")
                // Removes the progress bar
                .setProgress(0, 0, false);
        mNotifyManager.notify(1, mBuilder.build());
    }
}
