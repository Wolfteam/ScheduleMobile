package com.wolfteam20.schedulemobile.ui.editardb

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.ui.base.BaseActivity
import com.wolfteam20.schedulemobile.ui.base.BaseFragment
import com.wolfteam20.schedulemobile.ui.editardb.aula.details.AulaDetailsFragment
import com.wolfteam20.schedulemobile.ui.editardb.base.ItemDetailsBaseFragment
import com.wolfteam20.schedulemobile.ui.editardb.materia.details.MateriaDetailsFragment
import com.wolfteam20.schedulemobile.ui.editardb.periodos.details.PeriodoDetailsFragment
import com.wolfteam20.schedulemobile.ui.editardb.profesores.details.ProfesorDetailsFragment
import com.wolfteam20.schedulemobile.ui.editardb.profesoresxmateria.details.ProfesorMateriaDetailsFragmnet
import com.wolfteam20.schedulemobile.ui.editardb.secciones.details.SeccionDetailsFragment
import com.wolfteam20.schedulemobile.ui.editardb.usuarios.details.UsuarioDetailsFragment

/**
 * Created by Efrain.Bastidas on 10/2/2018.
 */
class EditarDBDetailsActivity : BaseActivity() {
    private val EDITARDB_DETAILS_FRAGMENT_TAG = "EDITARDB_DETAILS_FRAGMENT_TAG"
    private lateinit var mFragmentToLoad: BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editardb_details_activity)

        val fragmentToLoad = intent.extras.getInt("Fragment_TO_LOAD", 1)
        val fragment: ItemDetailsBaseFragment? =
                supportFragmentManager.findFragmentByTag(EDITARDB_DETAILS_FRAGMENT_TAG) as ItemDetailsBaseFragment?

        if (fragment == null && savedInstanceState == null){
            mFragmentToLoad = when (fragmentToLoad) {
                1 -> AulaDetailsFragment()
                2 -> MateriaDetailsFragment()
                3 -> PeriodoDetailsFragment()
                4 -> ProfesorDetailsFragment()
                5 -> ProfesorMateriaDetailsFragmnet()
                6 -> SeccionDetailsFragment()
                7 -> UsuarioDetailsFragment()
                else -> throw Exception("Estas tratando de instanciar un fragment que no ha sido agregado")
            }
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.editardb_details_activity_frame, mFragmentToLoad)
                .commit()
        }
    }

    override fun onBackPressed() {
        finish()
    }

    companion object {
        /**
         * Obtiene un intent de EditarDBDetailsActivity, guardando en el mismo
         * el [fragment] a inflar, el [id] del objeto correspondiente (valido cuando se va editar)
         * y la [position] del item en la recycler view (valido cuando se va editar)
         */
        fun getIntent(context: Context, fragment: Int, id: Long = 0, position: Int = 0): Intent {
            return Intent(context, EditarDBDetailsActivity::class.java)
                .putExtra("Fragment_TO_LOAD", fragment)
                .putExtra("ID", id)
                .putExtra("POSITION", position)
        }
    }
}