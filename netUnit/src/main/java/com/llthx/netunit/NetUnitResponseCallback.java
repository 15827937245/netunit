package com.llthx.netunit;

import okhttp3.Response;

public interface NetUnitResponseCallback {
    void onStart();
    void onFailure(String reason);
    void onSuccess(String json);
    void onEnd();
}
