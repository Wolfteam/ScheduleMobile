package com.wolfteam20.schedulemobile.data.network.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Efrain Bastidas on 1/13/2018.
 */

@SuppressLint("ParcelCreator")
@Parcelize
data class DisponibilidadDetailsDTO(
        var disponibilidad: List<DisponibilidadDTO>?,
        var horasAsignadas: Int,
        var horasACumplir: Int
) : Parcelable