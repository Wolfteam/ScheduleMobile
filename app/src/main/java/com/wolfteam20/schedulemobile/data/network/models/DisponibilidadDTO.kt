package com.wolfteam20.schedulemobile.data.network.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index

/**
 * Created by Efrain Bastidas on 1/13/2018.
 */

//@SuppressLint("ParcelCreator")
//@Parcelize
@Entity
data class DisponibilidadDTO(
        @Id var id: Long = 0,
        @Index var cedula: Int = 0,
        var idDia: Int,
        var idHoraInicio: Int,
        var idHoraFin: Int,
        var idPeriodo: Int = -1
) //: Parcelable
