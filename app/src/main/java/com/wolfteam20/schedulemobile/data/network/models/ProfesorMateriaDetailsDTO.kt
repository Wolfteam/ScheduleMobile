package com.wolfteam20.schedulemobile.data.network.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Transient
import io.objectbox.relation.ToOne

/**
 * Created by Efrain Bastidas on 2/4/2018.
 */

@Entity
class ProfesorMateriaDetailsDTO {
    @Id(assignable = true)
    var id: Long = 0
    @Transient
    lateinit var profesor: ProfesorDetailsDTO
    lateinit var profesorDetails: ToOne<ProfesorDetailsDTO>
    @Transient
    lateinit var materia: MateriaDetailsDTO
    lateinit var materiaDetails: ToOne<MateriaDetailsDTO>
}