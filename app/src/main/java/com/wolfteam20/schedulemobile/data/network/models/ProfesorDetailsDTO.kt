package com.wolfteam20.schedulemobile.data.network.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Efrain.Bastidas on 1/12/2018.
 */
@SuppressLint("ParcelCreator")
@Parcelize
class ProfesorDetailsDTO(
    var cedula: Long = 0,
    var nombre: String = "Efrain",
    var apellido: String = "Bastidas",
    var prioridad: PrioridadProfesorDTO? = null
) : Parcelable