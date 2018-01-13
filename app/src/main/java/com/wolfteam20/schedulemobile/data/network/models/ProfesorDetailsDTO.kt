package com.wolfteam20.schedulemobile.data.network.models

/**
 * Created by Efrain.Bastidas on 1/12/2018.
 */
data class ProfesorDetailsDTO(
        var cedula: Int,
        var nombre: String,
        var apellido: String,
        var prioridad : PrioridadProfesorDTO?
)