package com.llthx.netunit;

import okhttp3.Response;

public interface NetUnitResponseCallback {
    default void onStart(){};
    default void onFailure(String reason){};
    default void onSuccess(String json){};
    default void onEnd(){};
}
