package com.wolfteam20.schedulemobile.utils;

/**
 * Created by Efrain.Bastidas on 1/4/2018.
 */

public class Constants {
    //Preferences constants
    public static  final String PREFERENCE_NAME = "schedule_preferences";
    public static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";
    public static final String PREF_KEY_FULLNAME= "PREF_KEY_FULLNAME";
    public static final String PREF_KEY_USERNAME = "PREF_KEY_USERNAME";
    public static final String PREF_IS_USER_ADMIN = "PREF_IS_USER_ADMIN";

    //JWT Constants
    public static final String ROLE_CLAIM = "http://schemas.microsoft.com/ws/2008/06/identity/claims/role";
    public static final String ROLE_ADMIN = "Administrador";
    public static final String SECRET = "MyTokenSecretKey";

    //Permisos
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 10;

    //Nombre Archivos
    public static final String PLANIF_ACADE_FILENAME = "planificacionAcademica.xlsx";
    public static final String PLANIF_AULAS_FILENAME = "planificacionAulas.xlsx";
    public static final String PLANIF_HORAR_FILENAME = "planificacionHorario.xlsx";
}
