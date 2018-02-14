package com.wolfteam20.schedulemobile.di.components;

import com.wolfteam20.schedulemobile.di.modules.ActivityModule;
import com.wolfteam20.schedulemobile.di.scopes.ActivityScope;
import com.wolfteam20.schedulemobile.ui.disponibilidad.details.DispDetailsFragment;
import com.wolfteam20.schedulemobile.ui.disponibilidad.DispFragment;
import com.wolfteam20.schedulemobile.ui.editardb.EditarDBFragment;
import com.wolfteam20.schedulemobile.ui.editardb.aula.AulasFragment;
import com.wolfteam20.schedulemobile.ui.editardb.aula.details.AulasDetailsFragment;
import com.wolfteam20.schedulemobile.ui.editardb.materia.MateriasFragment;
import com.wolfteam20.schedulemobile.ui.editardb.periodos.PeriodosFragment;
import com.wolfteam20.schedulemobile.ui.editardb.profesores.ProfesoresFragment;
import com.wolfteam20.schedulemobile.ui.editardb.profesoresxmateria.ProfesorMateriaFragment;
import com.wolfteam20.schedulemobile.ui.editardb.secciones.SeccionesFragment;
import com.wolfteam20.schedulemobile.ui.editardb.usuarios.UsuariosFragment;
import com.wolfteam20.schedulemobile.ui.home.HomeFragment;
import com.wolfteam20.schedulemobile.ui.login.LoginActivity;

import dagger.Component;

/**
 * Created by Efrain Bastidas on 1/2/2018.
 */

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(LoginActivity loginActivity);

    void inject(HomeFragment homeFragment);

    void inject(DispFragment dispFragment);

    void inject(DispDetailsFragment disponibilidadDetailsFragment);

    void inject(EditarDBFragment editarDBFragment);

    void inject(AulasFragment aulasFragment);

    void inject(MateriasFragment materiasFragment);

    void inject(PeriodosFragment periodosFragment);

    void inject(ProfesoresFragment profesoresFragment);

    void inject(ProfesorMateriaFragment profesorMateriaFragment);

    void inject(SeccionesFragment seccionesFragment);

    void inject(UsuariosFragment usuariosFragment);

    void inject(AulasDetailsFragment aulasDetailsFragment);
}