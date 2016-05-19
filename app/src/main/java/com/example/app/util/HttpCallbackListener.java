package com.example.app.util;

/**
 * Created by chen on 2016/4/20.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
