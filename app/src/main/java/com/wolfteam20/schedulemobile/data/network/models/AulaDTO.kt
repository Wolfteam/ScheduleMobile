package com.wolfteam20.schedulemobile.data.network.models

/**
 * Created by Efrain Bastidas on 2/3/2018.
 */
data class AulaDTO(
    var idAula: Long = 0,
    var nombreAula: String,
    var capacidad: Int,
    var idTipo: Long
)