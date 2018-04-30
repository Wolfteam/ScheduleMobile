package com.wolfteam20.schedulemobile.ui.editardb.base

import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.ui.base.BaseActivity
import com.wolfteam20.schedulemobile.ui.base.BaseFragment
import kotlinx.android.synthetic.main.editardb_details_common_progress_bar.*
import kotlinx.android.synthetic.main.editardb_details_common_toolbar.*

/**
 * Created by Efrain.Bastidas on 15/2/2018.
 */
abstract class ItemDetailsBaseFragment : BaseFragment(), ItemDetailsBaseViewContract {

    protected var mMenuItemsEnabled: Boolean = false

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        val id = baseActivity.intent.extras.getLong("ID", 0)
        //Add mode
        if (id == 0L)
            inflater?.inflate(R.menu.editardb_details_add_menu, menu)
        //Edit mode
        else
            inflater?.inflate(R.menu.editardb_details_edit_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        val menuSize = menu?.size()!!
        for (i in 0..menuSize) {
            when (menuSize) {
                2 -> {
                    if (i != 0 && i < menuSize)
                        menu.getItem(i)?.isEnabled = mMenuItemsEnabled
                }
                3 -> {
                    if (i != 1 && i < menuSize)
                        menu.getItem(i)?.isEnabled = mMenuItemsEnabled
                }
            }
        }
    }

    override fun showLoading() {
        editardb_details_common_progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        editardb_details_common_progress_bar.visibility = View.GONE
    }

    override fun finishActivity(operation: Int, position: Int, item: Parcelable?) {
        baseActivity.intent.putExtra("OPERATION", operation)
        baseActivity.intent.putExtra("POSITION", position)
        baseActivity.intent.putExtra("ITEM", item)
        baseActivity.setResult(BaseActivity.RESULT_OK, baseActivity.intent)
        baseActivity.onBackPressed()
    }
}