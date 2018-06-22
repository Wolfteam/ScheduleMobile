package com.wolfteam20.schedulemobile.data;

import com.wolfteam20.schedulemobile.data.db.DbHelperContract;
import com.wolfteam20.schedulemobile.data.preferences.PreferencesHelperContract;
import com.wolfteam20.schedulemobile.data.network.ApiScheduleContract;

/**
 * Created by Efrain.Bastidas on 1/4/2018.
 */

public interface DataManagerContract extends PreferencesHelperContract, ApiScheduleContract, DbHelperContract {
}
