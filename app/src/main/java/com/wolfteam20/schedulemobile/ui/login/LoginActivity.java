package com.wolfteam20.schedulemobile.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wolfteam20.schedulemobile.R;
import com.wolfteam20.schedulemobile.data.services.ScheduleService;
import com.wolfteam20.schedulemobile.ui.base.BaseActivity;
import com.wolfteam20.schedulemobile.ui.home.HomeActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Efrain Bastidas on 12/31/2017.
 */

public class LoginActivity extends BaseActivity implements LoginContractView {

    @BindView(R.id.editTextUsername) EditText textViewUsername;
    @BindView(R.id.editTextPassword) EditText textViewPassword;
    @BindView(R.id.loginProgressBar) ProgressBar mProgressBar;
    @BindView(R.id.btnSignIn) Button mBtnSignin;

    @Inject
    LoginContractPresenter<LoginContractView> mPresenter;
    @Inject
    ScheduleService mScheduleService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);
//        mScheduleService = App.getApplication(this)
//                .getApplicationComponent()
//                .getScheduleService();
        mPresenter.onAttach(this);
    }

    @OnClick(R.id.btnSignIn)
    public void login() {
        boolean isNetworkAvailble = isNetworkAvailable();
        if (!isNetworkAvailble){
            showError("No hay red disponible.");
            return;
        }
        String username = textViewUsername.getText().toString();
        String password = textViewPassword.getText().toString();
        mPresenter.onLoginClick(username, password);
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        setEnabledViews(false);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.INVISIBLE);
        setEnabledViews(true);
    }

    @Override
    public void showSuccess(String msg) {
        Toast.makeText(this, "Listo!!! " + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void intentToEventActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void setEnabledViews(boolean enabled){
        textViewPassword.setEnabled(enabled);
        textViewUsername.setEnabled(enabled);
        mBtnSignin.setEnabled(enabled);
    }
}
