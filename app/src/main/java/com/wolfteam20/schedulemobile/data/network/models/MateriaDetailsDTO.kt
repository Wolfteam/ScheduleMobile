package com.wolfteam20.schedulemobile.data.network.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Transient
import io.objectbox.relation.ToOne

/**
 * Created by Efrain Bastidas on 2/3/2018.
 */

@Entity
class MateriaDetailsDTO {
    @Id(assignable = true)
    var codigo: Long = 0
    var asignatura: String = "default"
    var horasAcademicasTotales: Int = 0
    var horasAcademicasSemanales: Int = 0
    @Transient
    lateinit var carrera: CarreraDTO
    lateinit var carreraDetails: ToOne<CarreraDTO>
    @Transient
    lateinit var semestre: SemestreDTO
    lateinit var semestreDetails: ToOne<SemestreDTO>
    @Transient
    lateinit var tipoMateria: TipoAulaMateriaDTO
    lateinit var tipo: ToOne<TipoAulaMateriaDTO>
}