package com.wolfteam20.schedulemobile.di.modules;

import android.app.Application;
import android.content.Context;

import com.wolfteam20.schedulemobile.App;
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDTO;
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDetailsDTO;
import com.wolfteam20.schedulemobile.di.qualifiers.ApplicationContext;
import com.wolfteam20.schedulemobile.di.scopes.ApplicationScope;

import dagger.Module;
import dagger.Provides;
import io.objectbox.Box;

/**
 * Created by Efrain.Bastidas on 1/2/2018.
 */
@Module
public class ApplicationContextModule {

    private final Application mContext;

    public ApplicationContextModule(Application mContext) {
        this.mContext = mContext;
    }

    @Provides
    @ApplicationScope
    @ApplicationContext
    Context provideContext() {
        return mContext;
    }

    @Provides
    Box<DisponibilidadDTO> provideDisponibilidadDTOBox(){
        return ((App)mContext).getBoxStore().boxFor(DisponibilidadDTO.class);
    }

    @Provides
    Box<DisponibilidadDetailsDTO> provideDisponibilidadDetailsDTOBox(){
        return ((App)mContext).getBoxStore().boxFor(DisponibilidadDetailsDTO.class);
    }
}
