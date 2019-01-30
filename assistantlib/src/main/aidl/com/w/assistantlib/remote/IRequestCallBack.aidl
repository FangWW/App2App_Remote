// IRequest.aidl
package com.w.assistantlib.remote;

// Declare any non-default types here with import statements

interface IRequestCallBack {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void onResponse(String response);

    void onFailure(String e);
}
