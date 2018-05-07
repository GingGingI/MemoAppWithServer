package com.firebase.ginggingi.memoappwithserver.Interfaces;

import com.firebase.ginggingi.memoappwithserver.MainActivity;

public interface GetJsonModel {
    void InitDatas(String url, MainActivity mActivity);
    void ParseJsonFromURL();
    void GiveJsonArr();
}
