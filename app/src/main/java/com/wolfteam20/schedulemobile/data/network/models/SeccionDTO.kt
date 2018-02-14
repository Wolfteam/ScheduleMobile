package com.wolfteam20.schedulemobile.data.network.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Efrain.Bastidas on 12/2/2018.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class SeccionDTO(
    val codigo: Long,
    val numeroSecciones: Int,
    val cantidadAlumnos: Int,
    val idPeriodo: Int = 0
): Parcelable