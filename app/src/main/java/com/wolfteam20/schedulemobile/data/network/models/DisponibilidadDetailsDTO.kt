package com.wolfteam20.schedulemobile.data.network.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index

/**
 * Created by Efrain Bastidas on 1/13/2018.
 */

@Entity
data class DisponibilidadDetailsDTO(
        @Id var id: Long = 0,
        @Index var cedula: Int = 0,
        var disponibilidad: MutableList<DisponibilidadDTO>? = null,
        var horasAsignadas: Int = 0,
        var horasACumplir: Int = 0
)