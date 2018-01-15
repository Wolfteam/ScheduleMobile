package com.wolfteam20.schedulemobile.data.network.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Efrain Bastidas on 1/13/2018.
 */

@SuppressLint("ParcelCreator")
@Parcelize
data class DisponibilidadDTO(
        var idDia: Int,
        var idHoraInicio: Int,
        var idHoraFin: Int,
        var idPeriodo: Int
) : Parcelable
