package com.wolfteam20.schedulemobile.data.preferences;

/**
 * Created by Efrain.Bastidas on 1/4/2018.
 */

public interface PreferencesHelperContract {
    /**
     * Guarda el token de acceso en SharedPreferences
     * @param token Token dado por ScheduleAPI
     */
    void storeAccessToken(String token);

    /**
     * Obtiene el token de acceso
     * @return String Token dado por ScheduleAPI
     */
    String getToken();

    /**
     * SGuarda la informacion del usuario autenticado
     * @param user Usuario autenticado
     */
    //void storeUser(User user)

    /**
     * Obtiene el usernmae del usuario autenticado
     * @return String
     */
    String getUsername();

    /**
     * Obtiene el nombre completo del usuario autenticado
     * @return String?
     */
    String getFullname();
}
