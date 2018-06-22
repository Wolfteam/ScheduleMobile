package com.wolfteam20.schedulemobile.utils;

/**
 * Created by Efrain.Bastidas on 1/4/2018.
 */

public class Constants {
    //Preferences constants
    public static final String PREFERENCE_NAME = "schedule_preferences";
    public static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";
    public static final String PREF_KEY_FULLNAME = "PREF_KEY_FULLNAME";
    public static final String PREF_KEY_USERNAME = "PREF_KEY_USERNAME";
    public static final String PREF_KEY_CEDULA = "PREF_KEY_CEDULA";
    public static final String PREF_IS_USER_ADMIN = "PREF_IS_USER_ADMIN";

    //JWT Constants
    static final String ROLE_CLAIM = "http://schemas.microsoft.com/ws/2008/06/identity/claims/role";
    static final String ROLE_ADMIN = "Administrador";
    static final String SECRET = "MyTokenSecretKey";
    static final String CLAIM_NAMEIDENTIFIER = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier";
    static final String CLAIM_GIVENNAME = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/givenname";
    static final String CLAIM_NAME = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name";
    static final String CLAIM_SURNAME = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/surname";

    //Permisos
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 10;

    //Nombre Archivos
    public static final String PLANIF_ACADE_FILENAME = "planificacionAcademica.xlsx";
    public static final String PLANIF_AULAS_FILENAME = "planificacionAulas.xlsx";
    public static final String PLANIF_HORAR_FILENAME = "planificacionHorario.xlsx";

    //Tags usados en editar db y editar db details
    public static final String ITEM_TAG = "ITEM";
    public static final String ITEM_ID_TAG = "ID";
    public static final String ITEM_POSITION_TAG = "POSITION";
    public static final String ITEM_OPERATION_TAG = "OPERATION";
    public static final String CURRENT_ITEM_TAG = "CURRENT_ITEM";
}
