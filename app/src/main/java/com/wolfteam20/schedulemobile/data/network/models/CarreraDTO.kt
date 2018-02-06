package com.wolfteam20.schedulemobile.data.network.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Efrain Bastidas on 2/3/2018.
 */

@Entity
data class CarreraDTO(@Id(assignable = true) var idCarrera: Long, var nombreCarrera: String)