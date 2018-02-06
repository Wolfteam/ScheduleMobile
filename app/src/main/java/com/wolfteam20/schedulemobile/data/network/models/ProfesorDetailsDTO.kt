package com.wolfteam20.schedulemobile.data.network.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Transient
import io.objectbox.relation.ToOne

/**
 * Created by Efrain.Bastidas on 1/12/2018.
 */
@Entity
class ProfesorDetailsDTO {
    @Id(assignable = true)
    var cedula: Long = 0
    var nombre: String = "Efrain"
    var apellido: String = "Bastidas"
    @Transient
    var prioridad: PrioridadProfesorDTO? = null
    lateinit var prioridadDetails: ToOne<PrioridadProfesorDTO>
}