package com.wolfteam20.schedulemobile.ui.editardb.details.aulas

import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO
import com.wolfteam20.schedulemobile.ui.base.BaseViewContract
import com.wolfteam20.schedulemobile.ui.editardb.details.EditarDBDetailsViewContract

/**
 * Created by Efrain.Bastidas on 10/2/2018.
 */
interface AulasDetailsViewContract : EditarDBDetailsViewContract {
    fun showItem(aula: AulaDetailsDTO)
}