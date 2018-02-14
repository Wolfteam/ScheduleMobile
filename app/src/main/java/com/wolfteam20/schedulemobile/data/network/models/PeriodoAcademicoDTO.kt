package com.wolfteam20.schedulemobile.data.network.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Created by Efrain Bastidas on 2/4/2018.
 */

@SuppressLint("ParcelCreator")
@Parcelize
data class PeriodoAcademicoDTO(
    var idPeriodo: Long,
    var nombrePeriodo: String,
    var status: Boolean,
    var fechaCreacion: Date
) : Parcelable