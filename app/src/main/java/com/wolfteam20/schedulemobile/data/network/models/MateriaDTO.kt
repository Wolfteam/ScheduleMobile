package com.wolfteam20.schedulemobile.data.network.models

/**
 * Created by Efrain Bastidas on 2/3/2018.
 */
data class MateriaDTO(
    var codigo: Long,
    var asignatura: String,
    var horasAcademicasTotales: Int,
    var horasAcademicasSemanales: Int,
    var idSemestre: Long,
    var idTipo: Long,
    var idCarrera: Long
)