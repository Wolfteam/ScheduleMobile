package com.wolfteam20.schedulemobile.ui.home;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

public class HomeFragment extends BaseFragment implements HomeContractView, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    HomeContractPresenter<HomeContractView> mPresenter;

    @BindView(R.id.home_fragment_swipe_to_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.home_fragment_periodo_actual) TextView mPeriodoActual;

    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;
    private boolean hasWriteAccess = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, view);
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
    public void onBtnPlanificacionClick(View view) {
        requestWritePermission();
        //TODO: Arreglar esta validacion que no ta funcando
        if (!hasWriteAccess) {
            Toast.makeText(getContext(), "Debe dar permisos de escritura", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()) {
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

    public void requestWritePermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)
                    getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle(getResources().getString(R.string.permisos_title))
                        .setMessage(getResources().getString(R.string.permisos_write_msg))
                        .setPositiveButton("Ok", null)
                        .create();
                dialog.show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity) getContext(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            }
            hasWriteAccess = false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    hasWriteAccess = true;
                } else {
                    hasWriteAccess = false;
                }
                break;
        }
    }

    @Override
    protected void initLayout(View view, Bundle savedInstanceState) {
        mPresenter.onAttach(this);
        mPresenter.subscribe();
    }

    @Override
    public void hideLoading() {
        if (mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);
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
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
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