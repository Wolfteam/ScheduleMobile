package com.wolfteam20.schedulemobile.ui.editardb.details

import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.ui.base.BaseActivity
import com.wolfteam20.schedulemobile.ui.base.BaseFragment
import com.wolfteam20.schedulemobile.ui.editardb.aula.AulasFragment
import com.wolfteam20.schedulemobile.ui.editardb.details.aulas.AulasDetailsFragment
import kotlinx.android.synthetic.main.base_activity.*

/**
 * Created by Efrain.Bastidas on 10/2/2018.
 */
class EditarDBDetailsActivity : BaseActivity() {
    lateinit var mFragmentToLoad: BaseFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editardb_details_activity)
        val fragmentToLoad = intent.extras.getInt("Fragment_TO_LOAD",1)

        when (fragmentToLoad) {
            1 -> mFragmentToLoad = AulasDetailsFragment()
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
        fun getIntent(context: Context, fragment: Int, id: Long): Intent {
            return Intent(context, EditarDBDetailsActivity::class.java)
                .putExtra("Fragment_TO_LOAD", fragment)
                .putExtra("ID", id)
        }
    }
}