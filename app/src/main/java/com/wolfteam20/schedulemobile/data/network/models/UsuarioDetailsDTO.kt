package com.wolfteam20.schedulemobile.data.network.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Efrain Bastidas on 2/4/2018.
 */

@SuppressLint("ParcelCreator")
@Parcelize
class UsuarioDetailsDTO(
    var cedula: Long = 0,
    var username: String = "",
    var password: String = "",
    var profesor: ProfesorDTO?,
    var privilegios: PrivilegioDTO
) : Parcelable
