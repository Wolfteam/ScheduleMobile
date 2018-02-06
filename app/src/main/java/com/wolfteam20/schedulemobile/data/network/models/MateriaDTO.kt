package com.wolfteam20.schedulemobile.data.network.models

/**
 * Created by Efrain Bastidas on 2/3/2018.
 */
data class MateriaDTO(
    var codigo: Int,
    var asignatura: String,
    var horasAcademicasTotales: Int,
    var horasAcademicasSemanales: Int,
    var idSemestre: Int,
    var idTipo: Int,
    var idCarrera: Int
)