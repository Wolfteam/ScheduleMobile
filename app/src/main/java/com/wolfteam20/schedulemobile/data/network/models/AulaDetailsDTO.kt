package com.wolfteam20.schedulemobile.data.network.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Efrain Bastidas on 2/3/2018.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class AulaDetailsDTO(
    var idAula: Long = 0,
    var nombreAula: String,
    var capacidad: Int,
    var tipoAula: TipoAulaMateriaDTO
):Parcelable