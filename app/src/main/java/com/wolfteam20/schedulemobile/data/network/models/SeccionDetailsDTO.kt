package com.wolfteam20.schedulemobile.data.network.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Efrain Bastidas on 2/4/2018.
 */

@SuppressLint("ParcelCreator")
@Parcelize
class SeccionDetailsDTO(
    var numeroSecciones: Int = 0,
    var cantidadAlumnos: Int = 0,
    var materia: MateriaDetailsDTO,
    var periodoAcademico: PeriodoAcademicoDTO?
) : Parcelable