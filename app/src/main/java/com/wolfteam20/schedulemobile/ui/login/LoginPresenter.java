package com.wolfteam20.schedulemobile.ui.login;

import com.wolfteam20.schedulemobile.data.models.TokenDTO;
import com.wolfteam20.schedulemobile.data.services.ScheduleService;
import com.wolfteam20.schedulemobile.ui.base.BasePresenter;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Efrain Bastidas on 1/2/2018.
 */

public class LoginPresenter<V extends LoginContractView>
        extends BasePresenter<V>
        implements LoginContractPresenter<V> {

    private ScheduleService mScheduleService;

    @Inject
    public LoginPresenter(ScheduleService scheduleService) {
        mScheduleService = scheduleService;
    }

    @Override
    public void onLoginClick(String username, String password) {
        mScheduleService.getToken(username, password).enqueue(new Callback<TokenDTO>() {
            @Override
            public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {
                if (response.isSuccessful()) {
                    String username = response.body().getUsername();
                    TokenDTO token = response.body();
                    getView().showSuccess("Autenticado el usuario: " + username + ". Su Token fue creado: " + token.getCreateDate());
                }else{
                    getView().showError("Usuario o clave invalidas");
                }
            }

            @Override
            public void onFailure(Call<TokenDTO> call, Throwable t) {
                getView().showError("Ocurrio un error");
            }
        });
    }


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
}