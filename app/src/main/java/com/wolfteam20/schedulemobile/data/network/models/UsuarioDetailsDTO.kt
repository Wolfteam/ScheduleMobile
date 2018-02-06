package com.wolfteam20.schedulemobile.data.network.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Transient
import io.objectbox.relation.ToOne

/**
 * Created by Efrain Bastidas on 2/4/2018.
 */

@Entity
class UsuarioDetailsDTO {
    @Id(assignable = true)
    var cedula: Long = 0
    var username: String = ""
    var password: String = ""
    @Transient
    lateinit var profesor: ProfesorDTO
    lateinit var profesorDetails: ToOne<ProfesorDTO>
    @Transient
    lateinit var privilegios: PrivilegioDTO
    lateinit var privilegiosDetails: ToOne<PrivilegioDTO>
}