package com.firebase.ginggingi.memoappwithserver.Interfaces;

import android.content.Context;

/**
 * Created by GingGingI on 2018-05-07.
 */

public interface UpdateDataModel {

    void InitData(int idx, String title, String content, Context context);
    void UpdateData();
    boolean ChkIsValidData(String s);
}
