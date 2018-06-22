package com.wolfteam20.schedulemobile.data.network.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Efrain.Bastidas on 1/12/2018.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class PrioridadProfesorDTO(
    var id: Long,
    var codigoPrioridad: String,
    var horasACumplir: Int
) : Parcelable