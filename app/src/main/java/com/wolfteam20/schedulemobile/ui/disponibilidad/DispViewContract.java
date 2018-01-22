package com.wolfteam20.schedulemobile.ui.disponibilidad;

import android.support.annotation.NonNull;
import android.view.View;

import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO;
import com.wolfteam20.schedulemobile.ui.base.BaseViewContract;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Efrain.Bastidas on 1/4/2018.
 */

public interface DispViewContract extends BaseViewContract {

    void hideLoading();

    void onBtnDiaClick(@NotNull View view);

    void onBtnGuardarCambiosClick();

    void startDetailsActivity(int idDia);

    void showLoading();

    /**
     * Muestra la lista de profesores en el dropdown
     * @param profesores List<ProfesorDetailsDTO>
     */
    void showProfesores(@NonNull List<ProfesorDetailsDTO> profesores);

    /**
     * Actualiza los textview con los valores correspondientes
     * @param horasACumplir  Horas a cumplir del prof. seleccionado
     * @param horasRestantes Horas restantes a asignar del prof. seleccionado
     */
    void updateHoras(int horasACumplir, int horasRestantes);
}
