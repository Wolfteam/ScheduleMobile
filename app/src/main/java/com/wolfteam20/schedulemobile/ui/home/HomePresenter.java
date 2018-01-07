package com.wolfteam20.schedulemobile.ui.home;

import com.wolfteam20.schedulemobile.data.preferences.PreferencesHelperContract;
import com.wolfteam20.schedulemobile.data.services.ScheduleService;
import com.wolfteam20.schedulemobile.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by Efrain Bastidas on 1/6/2018.
 */

public class HomePresenter<V extends HomeContractView>
        extends BasePresenter<V>
        implements HomeContractPresenter<V>{

    private final ScheduleService mScheduleService;
    private final PreferencesHelperContract mPreferencesHelper;

    @Inject
    public HomePresenter(ScheduleService scheduleService, PreferencesHelperContract preferencesHelper) {
        mScheduleService = scheduleService;
        mPreferencesHelper = preferencesHelper;
    }

    @Override
    public void subscribe() {
        //obten la data del periodo actual y quizas setea valores por defecto en
        //el navdrawer
    }
}
