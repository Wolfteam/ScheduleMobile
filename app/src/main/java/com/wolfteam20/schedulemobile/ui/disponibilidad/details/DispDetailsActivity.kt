package com.wolfteam20.schedulemobile.ui.disponibilidad.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.ui.base.BaseActivity

/**
 * Created by Efrain.Bastidas on 1/15/2018.
 */
class DispDetailsActivity : BaseActivity() {
    private val DISP_FRAGMENT_TAG = "DISP_FRAGMENT_TAG"
    private val DISP_DETAILS_FRAGMENT_TAG = "DISP_DETAILS_FRAGMENT_TAG"
    private var mDetailsFragment: DispDetailsFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.disponibilidad_activity)

        mDetailsFragment = supportFragmentManager.findFragmentByTag(DISP_DETAILS_FRAGMENT_TAG) as DispDetailsFragment?
        if (mDetailsFragment == null) {
            mDetailsFragment = DispDetailsFragment()
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.disponibilidad_activity, mDetailsFragment, DISP_DETAILS_FRAGMENT_TAG)
                    .commit()
        }
    }

    override fun onBackPressed() {
        intent.putExtra("Edited", true)
        setResult(RESULT_OK, intent)
        finish()
        super.onBackPressed()
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, DispDetailsActivity::class.java)
        }
    }
}