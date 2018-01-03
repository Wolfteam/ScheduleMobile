package com.wolfteam20.schedulemobile.ui.login;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.Toast;

import com.wolfteam20.schedulemobile.R;
import com.wolfteam20.schedulemobile.data.models.TokenDTO;
import com.wolfteam20.schedulemobile.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Efrain Bastidas on 12/31/2017.
 */

public class LoginActivity extends BaseActivity implements LoginContract.View {

    @BindView(R.id.username)
    EditText textViewUsername;
    @BindView(R.id.password)
    EditText textViewPassword;

    @Inject
    LoginContract.Presenter<LoginContract.View> mPresenter;
    //private static MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);
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
    public void showSuccess() {

    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void intentToEventActivity() {

    }

//    private String post(String url, String json) {
//        OkHttpClient client = new OkHttpClient();
//        RequestBody body = RequestBody.create(JSON, json);
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//        try {
//            Response response = client.newCall(request).execute();
//            return response.body().string();
//        }catch (IOException e){
//            return "Ocurrio un error";
//        }
//    }

//    private void getToken(String username, String password){
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.1.102/ScheduleAPI/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ScheduleApiService service = retrofit.create(ScheduleApiService.class);
//        Call<TokenDTO> repos = service.getToken(username, password);
//        repos.enqueue(new Callback<TokenDTO>() {
//            @Override
//            public void onResponse(Call<TokenDTO> call, retrofit2.Response<TokenDTO> response) {
//                if (response.isSuccessful()){
//                    String username = response.body().getUsername();
//                    Toast.makeText(getApplicationContext(), "Exito, usuario: " + username , Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TokenDTO> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "Ocurrio error", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//    }
//
//    public interface ScheduleApiService {
//        @FormUrlEncoded
//        @POST("token")
//        Call<TokenDTO> getToken(@Field("username") String username, @Field("password") String password);
//    }
}
