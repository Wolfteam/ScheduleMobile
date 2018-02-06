package com.wolfteam20.schedulemobile.di.modules;

import android.app.Application;
import android.content.Context;

import com.wolfteam20.schedulemobile.App;
import com.wolfteam20.schedulemobile.data.network.models.AulaDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDTO;
import com.wolfteam20.schedulemobile.data.network.models.DisponibilidadDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.MateriaDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.PeriodoAcademicoDTO;
import com.wolfteam20.schedulemobile.data.network.models.ProfesorDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.ProfesorMateriaDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.SeccionesDetailsDTO;
import com.wolfteam20.schedulemobile.data.network.models.UsuarioDetailsDTO;
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
    Box<AulaDetailsDTO> provideAulaDetailsDTOBox(){
        return ((App)mContext).getBoxStore().boxFor(AulaDetailsDTO.class);
    }

    @Provides
    Box<DisponibilidadDTO> provideDisponibilidadDTOBox(){
        return ((App)mContext).getBoxStore().boxFor(DisponibilidadDTO.class);
    }

    @Provides
    Box<DisponibilidadDetailsDTO> provideDisponibilidadDetailsDTOBox(){
        return ((App)mContext).getBoxStore().boxFor(DisponibilidadDetailsDTO.class);
    }

    @Provides
    Box<MateriaDetailsDTO> provideMateriaDetailsDTOBox(){
        return ((App)mContext).getBoxStore().boxFor(MateriaDetailsDTO.class);
    }

    @Provides
    Box<PeriodoAcademicoDTO> providePeriodoAcademicoDTOBox(){
        return ((App)mContext).getBoxStore().boxFor(PeriodoAcademicoDTO.class);
    }

    @Provides
    Box<ProfesorDetailsDTO> provideProfesorDetailsDTOBox(){
        return ((App)mContext).getBoxStore().boxFor(ProfesorDetailsDTO.class);
    }

    @Provides
    Box<ProfesorMateriaDetailsDTO> provideProfesorMateriaDetailsDTOBox(){
        return ((App)mContext).getBoxStore().boxFor(ProfesorMateriaDetailsDTO.class);
    }

    @Provides
    Box<SeccionesDetailsDTO> provideSeccionesDetailsDTOBox(){
        return ((App)mContext).getBoxStore().boxFor(SeccionesDetailsDTO.class);
    }

    @Provides
    Box<UsuarioDetailsDTO> provideUsuarioDetailsDTOBox(){
        return ((App)mContext).getBoxStore().boxFor(UsuarioDetailsDTO.class);
    }
}
