package com.wolfteam20.schedulemobile.ui.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.octicons_typeface_library.Octicons;
import com.wolfteam20.schedulemobile.App;
import com.wolfteam20.schedulemobile.R;
import com.wolfteam20.schedulemobile.di.components.ActivityComponent;
import com.wolfteam20.schedulemobile.di.components.DaggerActivityComponent;
import com.wolfteam20.schedulemobile.di.modules.ActivityModule;
import com.wolfteam20.schedulemobile.ui.login.LoginActivity;
import com.wolfteam20.schedulemobile.utils.Constants;
import com.wolfteam20.schedulemobile.utils.NetworkUtilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Efrain.Bastidas on 1/5/2018.
 */

public abstract class BaseDrawerActivity extends AppCompatActivity
        implements BaseContractView,
        Drawer.OnDrawerItemClickListener {

    private ActivityComponent mActivityComponent;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    private List<Unbinder> mUnBinder =  new ArrayList<>();
    Drawer mDrawer;

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen()) {
            mDrawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(App.getApplication(this).getApplicationComponent())
                .build();
        setContentView(R.layout.base_activity);
        setUnBinder(ButterKnife.bind(this));

        setSupportActionBar(mToolbar);
        initNavigationDrawer();

        boolean isUserAdmin = getApplicationContext()
                .getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
                .getBoolean(Constants.PREF_IS_USER_ADMIN, false);

        if (!isUserAdmin)
            mDrawer.removeItems(3);
    }

    @Override
    protected void onDestroy() {
        for (Unbinder u : mUnBinder) {
            u.unbind();
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        //TODO: Arreglar el beta de que no se pueda presionar varias veces en el mismo item
        boolean closeDrawer = true;
        switch ((int) drawerItem.getIdentifier()) {
            case 1://Home
                setFragment(1);
                break;
            case 2://Disponibilidad
                setFragment(2);
                break;
            case 3:
                closeDrawer = false;
                break;
            case 4://Aulas
                Toast.makeText(view.getContext(), "Click en Aulas", Toast.LENGTH_SHORT).show();
                break;
            case 5://Materias
                Toast.makeText(view.getContext(), "Click en Materias", Toast.LENGTH_SHORT).show();
                break;
            case 6://Periodos
                Toast.makeText(view.getContext(), "Click en Periodos", Toast.LENGTH_SHORT).show();
                break;
            case 7://Profesores
                Toast.makeText(view.getContext(), "Click en Profesores", Toast.LENGTH_SHORT).show();
                break;
            case 8://Profesores Materias
                Toast.makeText(view.getContext(), "Click en Profesores Materias", Toast.LENGTH_SHORT).show();
                break;
            case 9://Secciones
                Toast.makeText(view.getContext(), "Click en Secciones", Toast.LENGTH_SHORT).show();
                break;
            case 10://Logout
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.cerrar_sesion)
                        .setMessage(R.string.cerrar_sesion_msg)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getApplicationContext()
                                        .getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
                                        .edit().putString("PREF_KEY_ACCESS_TOKEN", "").apply();
                                startActivity(LoginActivity.getIntent(getApplicationContext()));
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .create();
                dialog.show();
                break;
        }
        if (closeDrawer)
            mDrawer.closeDrawer();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void initNavigationDrawer() {
        AccountHeader headerResult = new AccountHeaderBuilder().withActivity(this)
                .withHeaderBackground(R.drawable.backgound_header)
                .addProfiles(
                        new ProfileDrawerItem().withName("Efrain Bastidas")
                                .withIcon(getResources().getDrawable(R.drawable.logo))
                ).build();

        //create the drawer and remember the `Drawer` result object
        mDrawer = new DrawerBuilder().withActivity(this).withToolbar(mToolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem().withIdentifier(1)
                                .withName(R.string.drawer_menu_home)
                                .withIcon(Octicons.Icon.oct_home)
                                .withIconColor(getResources().getColor(R.color.brownDarken4))
                                .withTextColor(getResources().getColor(R.color.brownDarken4))
                                .withSelectedTextColor(getResources().getColor(R.color.brownDarken4)),
                        new PrimaryDrawerItem().withIdentifier(2)
                                .withName(R.string.drawer_menu_dispnobilidad)
                                .withIcon(Octicons.Icon.oct_server)
                                .withIconColor(getResources().getColor(R.color.blueDarken4))
                                .withTextColor(getResources().getColor(R.color.blueDarken4))
                                .withSelectedTextColor(getResources().getColor(R.color.blueDarken4)),
                        new ExpandableDrawerItem()
                                .withIdentifier(3)
                                .withName(R.string.drawer_menu_editar_db)
                                .withIcon(Octicons.Icon.oct_database)
                                .withDescription("Editar Tablas de la db")
                                .withSelectable(false)
                                .withSubItems(
                                        new SecondaryDrawerItem().withIdentifier(4)
                                                .withName(R.string.drawer_menu_aulas),
                                        new SecondaryDrawerItem().withIdentifier(5)
                                                .withName(R.string.drawer_menu_materias),
                                        new SecondaryDrawerItem().withIdentifier(6)
                                                .withName(R.string.drawer_menu_periodos),
                                        new SecondaryDrawerItem().withIdentifier(7)
                                                .withName(R.string.drawer_menu_profesores),
                                        new SecondaryDrawerItem().withIdentifier(8)
                                                .withName(R.string.drawer_menu_profesores_materias),
                                        new SecondaryDrawerItem().withIdentifier(9)
                                                .withName(R.string.drawer_menu_secciones)
                                )
                                .withIconColor(getResources().getColor(R.color.greenDarken4))
                                .withTextColor(getResources().getColor(R.color.greenDarken4)),
                        new PrimaryDrawerItem().withIdentifier(11)
                                .withName(R.string.drawer_menu_about)
                                .withIcon(Octicons.Icon.oct_settings)
                                .withSelectable(false)
                                .withIconColor(getResources().getColor(R.color.greyDarken4))
                                .withTextColor(getResources().getColor(R.color.greyDarken4)),
                        new PrimaryDrawerItem().withIdentifier(10)
                                .withName(R.string.drawer_menu_logout)
                                .withIcon(Octicons.Icon.oct_sign_out)
                                .withSelectable(false)
                                .withIconColor(getResources().getColor(R.color.redDarken4))
                                .withTextColor(getResources().getColor(R.color.redDarken4))
                )
                .withAccountHeader(headerResult)
                .withHeaderDivider(true)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withTranslucentStatusBar(true)
                .withTranslucentNavigationBar(true)
                .withInnerShadow(true)
                .withOnDrawerItemClickListener(this)
                .build();
    }

    @Override
    public boolean isNetworkAvailable() {
        return NetworkUtilities.isNetworkAvailable(this);
    }

    @Override
    public void openActivityOnTokenExpire() {
        startActivity(LoginActivity.getIntent(this));
        finish();
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder.add(unBinder);
    }

    protected abstract void setFragment(int fragment);
}