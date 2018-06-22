package com.wolfteam20.schedulemobile.data.network.models

/**
 * Created by Efrain.Bastidas on 12/2/2018.
 */
data class UsuarioDTO(
    val cedula: Long,
    val username: String,
    val password: String,
    val idPrivilegio: Long = 1
)