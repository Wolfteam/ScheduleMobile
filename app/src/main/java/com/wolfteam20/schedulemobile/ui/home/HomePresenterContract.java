package com.wolfteam20.schedulemobile.ui.home;

import com.wolfteam20.schedulemobile.di.scopes.ActivityScope;
import com.wolfteam20.schedulemobile.ui.base.BasePresenterContract;

import io.reactivex.ObservableEmitter;
import okhttp3.ResponseBody;

/**
 * Created by Efrain Bastidas on 1/6/2018.
 */

@ActivityScope
public interface HomePresenterContract extends BasePresenterContract{
    /**
     * Obtiene una planificacion en particular para luego salvarla
     * @param tipoPlanificacion Tipo de planificacion (1 Academica, 2 Aulas, 3 Horarios)
     */
    void getPlanificacion(int tipoPlanificacion);

    /**
     * Obtiene el periodo academico actual y actualiza la ui
     */
    void getCurrentPeriodo();

    /**
     * Verifica si tiene permisos de escritura, si no los tiene los pide.
     * Esto se usa para poder guardar el archivo de planificacion
     * @return True en caso de tenerlos
     */
    boolean isWritePermissionGranted();

    /**
     * Se encarga de guardar el archivo excel en la memoria
     * @param filename Nombre a darle al archivo
     * @param responseBody ResponseBody
     * @param emitter ObservableEmitter<String> Usado para luego llamar a los metodos que actualizan la ui
     */
    void saveFile(String filename, ResponseBody responseBody, ObservableEmitter<String> emitter);

    void subscribe();
}
