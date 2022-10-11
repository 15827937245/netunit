package com.llthx.netunit;

import okhttp3.Response;

public interface NetUnitResponseCallback {
    void onStart();
    void onFailure();
    void onSuccess(Response response);
    void onEnd();
}
