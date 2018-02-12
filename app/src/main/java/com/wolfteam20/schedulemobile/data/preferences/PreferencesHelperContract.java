package com.wolfteam20.schedulemobile.data.preferences;


import android.content.Context;

/**
 * Created by Efrain.Bastidas on 1/4/2018.
 */

public interface PreferencesHelperContract {
    /**
     * Obtiene el contexto de con el cual se intancio las SharedPreferences
     *
     * @return Context
     */
    Context getContext();

    /**
     * Guarda el token de acceso en SharedPreferences
     *
     * @param token Token dado por ScheduleAPI
     */
    void storeAccessToken(String token);

    /**
     * Guarda los datos del usuario asociados al token
     *
     * @param token Token de autenticacion
     */
    void storeUser(String token);

    /**
     * Obtiene el token de acceso
     *
     * @return String Token dado por ScheduleAPI
     */
    String getToken();

    /**
     * Indica si el usuario logeado es admin
     *
     * @return True en caso de serlo
     */
    boolean isUserAdmin();

    /**
     * Obtiene la cedula del usuario autenticado
     *
     * @return int Cedula del usuario
     */
    int getCedula();

    /**
     * Obtiene el nombre completo del usuario autenticado
     *
     * @return String Nombre completo del usuario
     */
    String getFullname();

    /**
     * Obtiene el usernmae del usuario autenticado
     *
     * @return String Username del usuario
     */
    String getUsername();
}
