package com.wolfteam20.schedulemobile.ui.disponibilidad

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.ui.base.BaseDrawerActivity
import kotlinx.android.synthetic.main.base_activity.*


/**
 * Created by Efrain Bastidas on 1/13/2018.
 */
class DispActivity : BaseDrawerActivity() {
    private val DISP_FRAGMENT_TAG = "DISP_FRAGMENT_TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.disponibilidad_activity)
        layoutInflater.inflate(R.layout.disponibilidad_activity, content_frame)
        var fragment: DispFragment? =
            supportFragmentManager.findFragmentByTag(DISP_FRAGMENT_TAG) as DispFragment?
        if (fragment == null && savedInstanceState == null) {
            fragment = DispFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.disponibilidad_activity, fragment, DISP_FRAGMENT_TAG)
                .commit()
        }
        mDrawer.setSelection(2, false)
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, DispActivity::class.java)
        }
    }
}