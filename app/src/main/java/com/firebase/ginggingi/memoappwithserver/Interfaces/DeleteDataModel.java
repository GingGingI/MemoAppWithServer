package com.firebase.ginggingi.memoappwithserver.Interfaces;

import android.content.Context;

/**
 * Created by GingGingI on 2018-05-11.
 */

public interface DeleteDataModel {

    void InitData(int idx, Context context);
    void DeleteData();
    boolean ChkIsDataExist(String s);

}
