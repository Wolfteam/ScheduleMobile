package com.wolfteam20.schedulemobile.ui.editardb

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.ui.base.BaseActivity
import com.wolfteam20.schedulemobile.ui.base.BaseFragment
import com.wolfteam20.schedulemobile.ui.editardb.aula.details.AulasDetailsFragment

/**
 * Created by Efrain.Bastidas on 10/2/2018.
 */
class EditarDBDetailsActivity : BaseActivity() {
    private lateinit var mFragmentToLoad: BaseFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editardb_details_activity)
        val fragmentToLoad = intent.extras.getInt("Fragment_TO_LOAD",1)

        when (fragmentToLoad) {
            1 -> mFragmentToLoad =
                    AulasDetailsFragment()
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.editardb_details_activity_frame, mFragmentToLoad)
            .commit()
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