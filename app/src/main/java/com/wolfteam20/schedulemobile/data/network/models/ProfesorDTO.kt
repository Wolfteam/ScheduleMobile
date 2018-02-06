package com.wolfteam20.schedulemobile.data.network.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Efrain.Bastidas on 1/12/2018.
 */
@Entity
data class ProfesorDTO(
        @Id(assignable = true) var cedula: Long,
        var nombre: String,
        var apellido: String,
        var idPrioridad : Int
)
