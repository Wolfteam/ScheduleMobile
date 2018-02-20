package com.wolfteam20.schedulemobile.data.network.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Efrain.Bastidas on 1/12/2018.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class ProfesorDTO(
    var cedula: Long,
    var nombre: String,
    var apellido: String,
    var idPrioridad: Long
):Parcelable
