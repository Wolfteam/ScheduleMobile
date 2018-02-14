package com.wolfteam20.schedulemobile.di.modules;

import com.wolfteam20.schedulemobile.data.db.DbHelper;
import com.wolfteam20.schedulemobile.data.db.DbHelperContract;
import com.wolfteam20.schedulemobile.data.db.Repository.DisponibilidadDetailsRepository;
import com.wolfteam20.schedulemobile.data.db.Repository.DisponibilidadRepository;
import com.wolfteam20.schedulemobile.data.db.Repository.ProfesorRepository;
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDTO;
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO;

import dagger.Module;
import dagger.Provides;
import io.objectbox.Box;

/**
 * Created by Efrain.Bastidas on 1/17/2018.
 */

@Module(includes = ApplicationContextModule.class)
public class DbHelperModule {
    @Provides
    DbHelperContract provideDbHelper(DbHelper dbHelper) {
        return dbHelper;
    }

    @Provides
    DisponibilidadDetailsRepository provideDispDetailsRepository(Box<DisponibilidadDetailsDTO> box) {
        return new DisponibilidadDetailsRepository(box);
    }

    @Provides
    DisponibilidadRepository provideDispRepository(Box<DisponibilidadDTO> box) {
        return new DisponibilidadRepository(box);
    }

    @Provides
    ProfesorRepository provideProfesorRepository(Box<ProfesorDetailsDTO> box) {
        return new ProfesorRepository(box);
    }
}
