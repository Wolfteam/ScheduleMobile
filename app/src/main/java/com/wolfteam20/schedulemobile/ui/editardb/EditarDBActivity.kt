package com.wolfteam20.schedulemobile.ui.editardb

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.ui.base.BaseDrawerActivity
import kotlinx.android.synthetic.main.base_activity.*

/**
 * Created by Efrain Bastidas on 2/2/2018.
 */
class EditarDBActivity : BaseDrawerActivity() {
    private val EDITARDB_FRAGMENT_TAG = "EDITARDB_FRAGMENT_TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.editardb_activity)
        layoutInflater.inflate(R.layout.editardb_activity, content_frame)
        var fragment: EditarDBFragment? =
            supportFragmentManager.findFragmentByTag(EDITARDB_FRAGMENT_TAG) as EditarDBFragment?
        if (fragment == null && savedInstanceState == null) {
            fragment = EditarDBFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.editardb_activity_frame, fragment, EDITARDB_FRAGMENT_TAG)
                .commit()
        }
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, EditarDBActivity::class.java)
        }
    }
}