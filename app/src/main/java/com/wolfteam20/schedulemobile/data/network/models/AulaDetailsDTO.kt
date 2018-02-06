package com.wolfteam20.schedulemobile.data.network.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Transient
import io.objectbox.relation.ToOne

/**
 * Created by Efrain Bastidas on 2/3/2018.
 */

@Entity
class AulaDetailsDTO {
    @Id(assignable = true)
    var idAula: Long = 0
    var nombreAula: String = "default"
    var capacidad: Int = 0
    @Transient
    lateinit var tipoAula: TipoAulaMateriaDTO
    lateinit var tipo: ToOne<TipoAulaMateriaDTO>
}