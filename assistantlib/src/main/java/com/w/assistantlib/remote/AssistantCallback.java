package com.w.assistantlib.remote;

/**
 * <p>
 * FangJW
 */
public interface AssistantCallback {

    void onResponse(String response);

    void onFailure(Throwable e);
}
