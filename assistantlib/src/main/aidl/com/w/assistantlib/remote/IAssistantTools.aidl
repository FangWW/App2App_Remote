// IRomteAidlInterface.aidl
package com.w.assistantlib.remote;
import com.w.assistantlib.remote.Request;
import com.w.assistantlib.remote.IRequestCallBack;

// Declare any non-default types here with import statements

interface IAssistantTools {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString,in Request request);

    String request(in Request request,in IRequestCallBack iRequestCallBack);

}
