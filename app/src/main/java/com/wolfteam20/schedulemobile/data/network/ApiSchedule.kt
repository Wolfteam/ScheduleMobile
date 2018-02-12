package com.wolfteam20.schedulemobile.data.network

import com.wolfteam20.schedulemobile.data.network.models.*
import com.wolfteam20.schedulemobile.di.qualifiers.ApplicationContext
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Efrain.Bastidas on 11/2/2018.
 */
class ApiSchedule @Inject constructor(@ApplicationContext api: ApiScheduleContract) :
    ApiScheduleContract {
    private val mApi = api

    override fun getToken(
        username: String?,
        password: String?,
        isMobile: Boolean?
    ): Observable<Response<TokenDTO>> {
        return mApi.getToken(username, password, isMobile)
    }

    override fun getCurrentPeriodoAcademico(): Observable<Response<PeriodoAcademicoDTO>> {
        return mApi.currentPeriodoAcademico
    }

    override fun getPlanificacionAcademica(): Observable<Response<ResponseBody>> {
        return mApi.planificacionAcademica
    }

    override fun getPlanificacionAulas(): Observable<Response<ResponseBody>> {
        return mApi.planificacionAulas
    }

    override fun getPlanificacionHorario(): Observable<Response<ResponseBody>> {
        return mApi.planificacionHorario
    }

    override fun getAllProfesores(): Observable<MutableList<ProfesorDetailsDTO>> {
        return mApi.allProfesores
    }

    override fun getProfesor(cedula: Int): Observable<ProfesorDetailsDTO> {
        return mApi.getProfesor(cedula)
    }

    override fun getDisponbilidad(cedula: Int): Observable<DisponibilidadDetailsDTO> {
        return mApi.getDisponbilidad(cedula)
    }

    override fun addDisponibilidad(disponibilidades: MutableList<DisponibilidadDTO>?): Observable<Response<ResponseBody>> {
        return mApi.addDisponibilidad(disponibilidades)
    }

    override fun getAllAulas(): Observable<MutableList<AulaDetailsDTO>> {
        return mApi.allAulas
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun removeAulas(idAulas: String?): Completable {
        return mApi.removeAulas(idAulas)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun removeAula(idAula: Long): Completable {
        return mApi.removeAula(idAula)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun addAula(aula: AulaDTO?): Completable {
        return mApi.addAula(aula)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun updateAula(idAula: Long, aula: AulaDTO?): Completable {
        return mApi.updateAula(idAula, aula)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun getAllMaterias(): Observable<MutableList<MateriaDetailsDTO>> {
        return mApi.allMaterias
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun getAllPeriodosAcademicos(): Observable<MutableList<PeriodoAcademicoDTO>> {
        return mApi.allPeriodosAcademicos
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun getAllProfesorMateria(): Observable<MutableList<ProfesorMateriaDetailsDTO>> {
        return mApi.allProfesorMateria
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun getAllSecciones(): Observable<MutableList<SeccionesDetailsDTO>> {
        return mApi.allSecciones
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun getAllUsuarios(): Observable<MutableList<UsuarioDetailsDTO>> {
        return mApi.allUsuarios
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }
}