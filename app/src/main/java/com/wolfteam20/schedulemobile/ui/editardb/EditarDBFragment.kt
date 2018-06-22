package com.wolfteam20.schedulemobile.ui.editardb


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wolfteam20.schedulemobile.R
import com.wolfteam20.schedulemobile.ui.base.BaseDrawerActivity
import com.wolfteam20.schedulemobile.ui.base.BaseFragment
import com.wolfteam20.schedulemobile.ui.editardb.aula.AulasFragment
import com.wolfteam20.schedulemobile.ui.editardb.materia.MateriasFragment
import com.wolfteam20.schedulemobile.ui.editardb.periodos.PeriodosFragment
import com.wolfteam20.schedulemobile.ui.editardb.profesores.ProfesoresFragment
import com.wolfteam20.schedulemobile.ui.editardb.profesoresxmateria.ProfesorMateriaFragment
import com.wolfteam20.schedulemobile.ui.editardb.secciones.SeccionesFragment
import com.wolfteam20.schedulemobile.ui.editardb.usuarios.UsuariosFragment
import kotlinx.android.synthetic.main.editardb_fragment.*
import kotlinx.android.synthetic.main.editardb_fragment_common.*

/**
 * Created by Efrain Bastidas on 2/2/2018.
 */
class EditarDBFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.editardb_fragment, container, false)
    }


    override fun initLayout(view: View?, savedInstanceState: Bundle?) {
        val startPosition = activity?.intent?.extras?.getInt("START_POSITION", 0)
        editardb_fragment_tab_layout.setSelectedTabIndicatorColor(Color.WHITE)
        editardb_fragment_tab_layout.setupWithViewPager(editardb_fragment_view_pager)
        //editardb_fragment_view_pager.offscreenPageLimit = 2
        editardb_fragment_view_pager.adapter = PageAdapter(context!!, fragmentManager!!)
        editardb_fragment_view_pager.currentItem = startPosition!!
        editardb_fragment_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                baseDrawerActivity.setSelectionAtPosition(position + 4, false)
            }
        })
    }

    fun setCurrentItem(position: Int) {
        editardb_fragment_view_pager.currentItem = position
    }

    private class PageAdapter(
        context: Context,
        fragmentManager: FragmentManager
    ) : FragmentPagerAdapter(fragmentManager) {

        private val mContext = context
        private val FRAGMENT_PAGES = 7

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> AulasFragment()
                1 -> MateriasFragment()
                2 -> PeriodosFragment()
                3 -> ProfesoresFragment()
                4 -> ProfesorMateriaFragment()
                5 -> SeccionesFragment()
                else -> UsuariosFragment()
            }
        }

        override fun getCount(): Int {
            return FRAGMENT_PAGES
        }

        override fun getPageTitle(position: Int): CharSequence {
            return when (position) {
                0 -> mContext.resources.getString(R.string.drawer_menu_aulas)
                1 -> mContext.resources.getString(R.string.drawer_menu_materias)
                2 -> mContext.resources.getString(R.string.drawer_menu_periodos)
                3 -> mContext.resources.getString(R.string.drawer_menu_profesores)
                4 -> mContext.resources.getString(R.string.drawer_menu_profesores_materias)
                5 -> mContext.resources.getString(R.string.drawer_menu_secciones)
                else -> mContext.resources.getString(R.string.drawer_menu_usuarios)
            }
        }
    }
}