package com.wolfteam20.schedulemobile.data.network.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Transient
import io.objectbox.relation.ToOne

/**
 * Created by Efrain Bastidas on 2/4/2018.
 */

@Entity
class SeccionesDetailsDTO {
    @Id
    var id: Long = 0
    var numeroSecciones: Int = 0
    var cantidadAlumnos: Int = 0
    @Transient
    lateinit var materia: MateriaDetailsDTO
    lateinit var materiaDetails: ToOne<MateriaDetailsDTO>
    @Transient
    lateinit var periodoAcademico: PeriodoAcademicoDTO
    lateinit var periodoAcademicoDetails: ToOne<PeriodoAcademicoDTO>
}