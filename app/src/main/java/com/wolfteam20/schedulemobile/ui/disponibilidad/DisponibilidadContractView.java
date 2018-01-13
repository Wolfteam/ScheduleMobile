package com.wolfteam20.schedulemobile.ui.disponibilidad;

import android.support.annotation.NonNull;

import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO;
import com.wolfteam20.schedulemobile.ui.base.BaseContractView;

import java.util.List;

/**
 * Created by Efrain.Bastidas on 1/4/2018.
 */

public interface DisponibilidadContractView extends BaseContractView {

    void hideLoading();

    void showError(String error);

    void showLoading();

    void showProfesores(@NonNull List<ProfesorDetailsDTO> profesores);
}
