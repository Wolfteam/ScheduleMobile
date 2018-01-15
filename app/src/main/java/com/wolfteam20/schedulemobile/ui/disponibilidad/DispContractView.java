package com.wolfteam20.schedulemobile.ui.disponibilidad;

import android.support.annotation.NonNull;
import android.view.View;

import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO;
import com.wolfteam20.schedulemobile.ui.base.BaseContractView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Efrain.Bastidas on 1/4/2018.
 */

public interface DispContractView extends BaseContractView {

    void hideLoading();

    void onBtnDiaClick(@NotNull View view);

    void onBtnGuardarCambiosClick();

    void showDetailsFragment(DisponibilidadDetailsDTO disponibilidad, int idDia);

    void showError(String error);

    void showLoading();

    /**
     * Muestra los profesores en el dropdown
     * @param profesores List<ProfesorDetailsDTO>
     */
    void showProfesores(@NonNull List<ProfesorDetailsDTO> profesores);

    /**
     * Actualiza los textview con los valores correspondientes
     *
     * @param horasACumplir  Horas a cumplir del prof. seleccionado
     * @param horasRestantes Horas restantes a asignar del prof. seleccionado
     */
    void updateHoras(int horasACumplir, int horasRestantes);
}
