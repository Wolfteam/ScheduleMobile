package com.wolfteam20.schedulemobile.data.network.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Efrain.Bastidas on 1/12/2018.
 */
@Entity
data class PrioridadProfesorDTO(
    @Id(assignable = true) var id: Long,
    var codigoPrioridad: String,
    var horasACumplir: Int
)