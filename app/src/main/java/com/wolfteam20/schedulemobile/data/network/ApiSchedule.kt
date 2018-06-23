package com.wolfteam20.schedulemobile.data.network

import com.wolfteam20.schedulemobile.data.network.models.*
import com.wolfteam20.schedulemobile.di.qualifiers.ApplicationContext
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
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
        username: String,
        password: String,
        isMobile: Boolean,
        currentDate: String
    ): Observable<Response<TokenDTO>> {
        return mApi.getToken(username, password, isMobile, currentDate)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun getCurrentPeriodoAcademico(): Observable<Response<PeriodoAcademicoDTO>> {
        return mApi.currentPeriodoAcademico
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun getPlanificacionAcademica(): Observable<Response<ResponseBody>> {
        return mApi.planificacionAcademica
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun getPlanificacionAulas(): Observable<Response<ResponseBody>> {
        return mApi.planificacionAulas
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun getPlanificacionHorario(): Observable<Response<ResponseBody>> {
        return mApi.planificacionHorario
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun getAllProfesores(): Observable<MutableList<ProfesorDetailsDTO>> {
        return mApi.allProfesores
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun getProfesor(cedula: Int): Observable<ProfesorDetailsDTO> {
        return mApi.getProfesor(cedula)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun getDisponbilidad(cedula: Int): Observable<DisponibilidadDetailsDTO> {
        return mApi.getDisponbilidad(cedula)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun addDisponibilidad(disponibilidades: MutableList<DisponibilidadDTO>): Observable<Response<ResponseBody>> {
        return mApi.addDisponibilidad(disponibilidades)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun getAllCarreras(): Single<MutableList<CarreraDTO>> {
        return mApi.allCarreras
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun getAllTipoAulaMateria(): Single<MutableList<TipoAulaMateriaDTO>> {
        return mApi.allTipoAulaMateria
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun getAllPrivilegios(): Single<MutableList<PrivilegioDTO>> {
        return mApi.allPrivilegios
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun getAllSemestres(): Single<MutableList<SemestreDTO>> {
        return mApi.allSemestres
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun getAllAulas(): Observable<MutableList<AulaDetailsDTO>> {
        return mApi.allAulas
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun removeAulas(idAulas: String): Completable {
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

    override fun addAula(aula: AulaDTO): Completable {
        return mApi.addAula(aula)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun updateAula(idAula: Long, aula: AulaDTO): Completable {
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

    override fun removeMaterias(codigos: String): Completable {
        return mApi.removeMaterias(codigos)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun removeMateria(codigo: Long): Completable {
        return mApi.removeMateria(codigo)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun addMateria(materia: MateriaDTO): Completable {
        return mApi.addMateria(materia)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun updateMateria(codigo: Long, materia: MateriaDTO): Completable {
        return mApi.updateMateria(codigo, materia)
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

    override fun removePeriodosAcademicos(idPeriodos: String): Completable {
        return mApi.removePeriodosAcademicos(idPeriodos)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun removePeriodoAcademico(idPeriodo: Long): Completable {
        return mApi.removePeriodoAcademico(idPeriodo)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun addPeriodoAcademico(periodo: PeriodoAcademicoDTO): Completable {
        return mApi.addPeriodoAcademico(periodo)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun updatePeriodoAcademico(
        idPeriodo: Long,
        periodo: PeriodoAcademicoDTO
    ): Completable {
        return mApi.updatePeriodoAcademico(idPeriodo, periodo)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun getAllPrioridades(): Single<MutableList<PrioridadProfesorDTO>> {
        return mApi.allPrioridades
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }


    override fun removeProfesores(cedulas: String): Completable {
        return mApi.removeProfesores(cedulas)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun removeProfesor(cedula: Long): Completable {
        return mApi.removeProfesor(cedula)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun addProfesor(profesor: ProfesorDTO): Completable {
        return mApi.addProfesor(profesor)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun updateProfesor(cedula: Long, profesor: ProfesorDTO): Completable {
        return mApi.updateProfesor(cedula, profesor)
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

    override fun removeProfesorMateria(ids: String): Completable {
        return mApi.removeProfesorMateria(ids)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun removeProfesorMateria(id: Long): Completable {
        return mApi.removeProfesorMateria(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun addProfesorMateria(relacion: ProfesorMateriaDTO): Completable {
        return mApi.addProfesorMateria(relacion)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun updateProfesorMateria(id: Long, relacion: ProfesorMateriaDTO): Completable {
        return mApi.updateProfesorMateria(id, relacion)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun getAllSecciones(): Observable<MutableList<SeccionDetailsDTO>> {
        return mApi.allSecciones
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun removeSecciones(cedulas: String): Completable {
        return mApi.removeSecciones(cedulas)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun removeSeccion(codigo: Long): Completable {
        return mApi.removeSeccion(codigo)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun addSeccion(seccion: SeccionDTO): Completable {
        return mApi.addSeccion(seccion)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun updateSeccion(codigo: Long, seccion: SeccionDTO): Completable {
        return mApi.updateSeccion(codigo, seccion)
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

    override fun removeUsuarios(cedulas: String): Completable {
        return mApi.removeUsuarios(cedulas)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun removeUsuario(cedula: Long): Completable {
        return mApi.removeUsuario(cedula)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun addUsuario(usuario: UsuarioDTO): Completable {
        return mApi.addUsuario(usuario)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }

    override fun updateUsuario(cedula: Long, usuario: UsuarioDTO): Completable {
        return mApi.updateUsuario(cedula, usuario)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
    }
}