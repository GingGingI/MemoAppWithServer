package com.firebase.ginggingi.memoappwithserver.Interfaces;

import android.content.Context;

/**
 * Created by GingGingI on 2018-04-12.
 */

public interface SendDataModel {
    void InitData(String title, String content, Context context);
    void SendData();
    boolean ChkIsValidData(String s);
}
