package com.wolfteam20.schedulemobile.data.network.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

/**
 * Created by Efrain Bastidas on 2/4/2018.
 */

@Entity
data class PeriodoAcademicoDTO(
    @Id(assignable = true) var idPeriodo: Long,
    var nombrePeriodo: String,
    var status: Boolean,
    var fechaCreacion: Date)