package com.wolfteam20.schedulemobile.data.network.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Efrain Bastidas on 2/4/2018.
 */
@Entity
data class PrivilegioDTO(@Id(assignable = true) var idPrivilegio: Long, var nombrePrivilegio: String)