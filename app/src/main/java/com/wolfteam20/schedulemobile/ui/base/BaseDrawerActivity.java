package com.wolfteam20.schedulemobile.ui.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
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
import com.wolfteam20.schedulemobile.ui.disponibilidad.DispActivity;
import com.wolfteam20.schedulemobile.ui.editardb.EditarDBActivity;
import com.wolfteam20.schedulemobile.ui.editardb.EditarDBFragment;
import com.wolfteam20.schedulemobile.ui.home.HomeActivity;
import com.wolfteam20.schedulemobile.ui.login.LoginActivity;
import com.wolfteam20.schedulemobile.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Efrain.Bastidas on 1/5/2018.
 */

public abstract class BaseDrawerActivity extends MvpAppCompatActivity
        implements BaseViewContract,
        Drawer.OnDrawerItemClickListener {

    private ActivityComponent mActivityComponent;
    @BindView(R.id.toolbar) protected Toolbar mToolbar;
    private List<Unbinder> mUnBinder = new ArrayList<>();
    protected Drawer mDrawer;
    private final int DRAWER_DELAY = 210;

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
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(App.getApplication(this).getApplicationComponent())
                .build();
        super.onCreate(savedInstanceState);
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
        boolean closeDrawer = true;
        int item = (int) drawerItem.getIdentifier();
        switch (item) {
            case 1://Home
                new Handler().postDelayed(() -> {
                    startActivity(HomeActivity.getIntent(getApplicationContext()));
                    finish();
                }, DRAWER_DELAY);
                break;
            case 2://Disponibilidad
                new Handler().postDelayed(() -> {
                    startActivity(DispActivity.Companion.getIntent(getApplicationContext()));
                    finish();
                }, DRAWER_DELAY);
                break;
            case 3:
                closeDrawer = false;
                break;
            case 4://Aulas
            case 5://Materias
            case 6://Periodos
            case 7://Profesores
            case 8://Profesores Materias
            case 9://Secciones
            case 10://Usuarios
                int startPosition = item - 4;
                if (this instanceof EditarDBActivity) {
                    EditarDBFragment fragment = (EditarDBFragment) getSupportFragmentManager()
                            .findFragmentByTag("EDITARDB_FRAGMENT_TAG");
                    fragment.setCurrentItem(startPosition);
                    break;
                }

                new Handler().postDelayed(() -> {
                    Intent intent = EditarDBActivity.Companion.getIntent(getApplicationContext());
                    intent.putExtra("START_POSITION", startPosition);
                    startActivity(intent);
                    finish();
                }, DRAWER_DELAY);
                break;
            case 12://Logout
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.cerrar_sesion)
                        .setMessage(R.string.cerrar_sesion_msg)
                        .setPositiveButton("Si", (dialogInterface, i) -> {
                            getApplicationContext()
                                    .getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
                                    .edit().putString("PREF_KEY_ACCESS_TOKEN", "").apply();
                            startActivity(LoginActivity.getIntent(getApplicationContext()));
                            finish();
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
                                .withDescription(R.string.editardb_description)
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
                                                .withName(R.string.drawer_menu_secciones),
                                        new SecondaryDrawerItem().withIdentifier(10)
                                                .withName(R.string.drawer_menu_usuarios)
                                )
                                .withIconColor(getResources().getColor(R.color.greenDarken4))
                                .withTextColor(getResources().getColor(R.color.greenDarken4)),
                        new PrimaryDrawerItem().withIdentifier(11)
                                .withName(R.string.drawer_menu_about)
                                .withIcon(Octicons.Icon.oct_settings)
                                .withSelectable(false)
                                .withIconColor(getResources().getColor(R.color.greyDarken4))
                                .withTextColor(getResources().getColor(R.color.greyDarken4)),
                        new PrimaryDrawerItem().withIdentifier(12)
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
        mDrawer.getAdapter().withOnPreClickListener((v, adapter, item, position) -> {
            if (item.isSelected()) {
                mDrawer.closeDrawer();
                return true;
            }
            return false;
        });
    }

    @Override
    public void onError(String message) {
        if (message != null)
            showSnakBar(message);
        else
            showSnakBar(getString(R.string.error));
    }

    @Override
    public void onError(@StringRes int resId) {
        onError(getString(resId));
    }

    @Override
    public void openActivityOnTokenExpire() {
        startActivity(LoginActivity.getIntent(this));
        finish();
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder.add(unBinder);
    }

    @Override
    public void showMessage(String message) {
        if (message != null)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    private void showSnakBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        TextView textView = view.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }
}