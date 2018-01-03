package com.wolfteam20.schedulemobile.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.Toast;

import com.wolfteam20.schedulemobile.R;
import com.wolfteam20.schedulemobile.data.services.ScheduleService;
import com.wolfteam20.schedulemobile.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Efrain Bastidas on 12/31/2017.
 */

public class LoginActivity extends BaseActivity implements LoginContractView {

    @BindView(R.id.username)
    EditText textViewUsername;
    @BindView(R.id.password)
    EditText textViewPassword;

    @Inject
    LoginContractPresenter<LoginContractView> mPresenter;
    @Inject
    ScheduleService mScheduleService;
    //private static MediaType JSON = MediaType.parse("application/json; charset=utf-8");

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
        String username = textViewUsername.getText().toString();
        String password = textViewPassword.getText().toString();
        mPresenter.onLoginClick(username, password);
    }

    @Override
    public void showLoading() {
        Toast.makeText(this, "Autenticando...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        Toast.makeText(this, "Listo!!!", Toast.LENGTH_SHORT).show();
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

    }
}
