package com.wolfteam20.schedulemobile.data.network.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Efrain Bastidas on 2/3/2018.
 */

@SuppressLint("ParcelCreator")
@Parcelize
data class MateriaDetailsDTO(
    var codigo: Long,
    var asignatura: String,
    var horasAcademicasTotales: Int,
    var horasAcademicasSemanales: Int,
    var carrera: CarreraDTO,
    var semestre: SemestreDTO,
    var tipoMateria: TipoAulaMateriaDTO
): Parcelable