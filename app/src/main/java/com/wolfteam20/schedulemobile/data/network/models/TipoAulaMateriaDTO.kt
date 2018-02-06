package com.wolfteam20.schedulemobile.data.network.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Efrain Bastidas on 2/3/2018.
 */

@Entity
data class TipoAulaMateriaDTO(
    @Id(assignable = true) var idTipo: Long,
    var nombreTipo: String
)