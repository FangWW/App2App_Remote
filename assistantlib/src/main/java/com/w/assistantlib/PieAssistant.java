package com.w.assistantlib;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.w.assistantlib.remote.AssistantCallback;
import com.w.assistantlib.remote.IAssistantTools;
import com.w.assistantlib.remote.IRequestCallBack;
import com.w.assistantlib.remote.Request;
import com.w.assistantlib.remote.RemoteService;

import java.util.ArrayList;
import java.util.List;


public class PieAssistant {
    private static Application mApplication;
    private static Handler handler = new Handler();
    private static IAssistantTools iAssistantTools;
    private static List<RemoteService.RemoteServiceCallBack> mRemoteServiceCallBack;
    private static String mAppID;

    public static void initService(RemoteService.RemoteServiceCallBack remoteServiceCallBack) {
        if (mRemoteServiceCallBack == null) {
            mRemoteServiceCallBack = new ArrayList<RemoteService.RemoteServiceCallBack>();
        }
        mRemoteServiceCallBack.add(remoteServiceCallBack);
    }

    public static List<RemoteService.RemoteServiceCallBack> getRmoteServiceCallBack() {
        return mRemoteServiceCallBack;
    }

    /**
     * @param application
     * @param appID       服务端的appid
     */
    public static void initClient(Application application, String appID) {
        if (mApplication == null) {
            mApplication = application;
            mAppID = appID;
        }
        if (mApplication == null) {
            throw new IllegalStateException("forget PieAssistant.initClient() in Application ???");
        }
        Intent serviceIntent = new Intent(Constant.RESPOND_AIDL_MESSAGE);
        serviceIntent.setPackage(appID);
//            serviceIntent.setComponent(new ComponentName(, RemoteService.class.getCanonicalName()));
//            final Intent eintent = new Intent(achieveExplicitFromImplicitIntent(application, serviceIntent));
        application.bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        Log.d("ww", "bindService");
    }

    static ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iAssistantTools = IAssistantTools.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            iAssistantTools = null;
        }
    };

    public static void onDestroy() {
        if (mApplication != null)
            mApplication.unbindService(serviceConnection);
    }

    public static void request(Request request, AssistantCallback callback) {
        request(request, callback, 0);
    }

    private static void request(Request request, AssistantCallback callback, int count) {
        if (null != iAssistantTools) {
            if (TextUtils.isEmpty(request.method)) {
                throw new IllegalStateException("request.method is null");
            }
            if (request.version <= 0) {
                throw new IllegalStateException("request.version <= 0");
            }
            try {
                iAssistantTools.request(request, new IRequestCallBack.Stub() {

                    @Override
                    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

                    }

                    @Override
                    public void onResponse(String response) throws RemoteException {
//                        Log.e("www", "onResponse: " + response);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onResponse(response);
                            }
                        });
                    }

                    @Override
                    public void onFailure(String e) throws RemoteException {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFailure(new Exception(e));
                            }
                        });
                    }

                });
            } catch (RemoteException e) {
                e.printStackTrace();
                callback.onFailure(new Exception("The remote service " + e.toString()));
            }
        } else {
            if (count > 2) {
                callback.onFailure(new Exception("The remote service is not connected"));
                return;
            }
            Log.e("ww", "未连接上远端服务");
            initClient(mApplication, mAppID);
            count++;
            int finalCount = count;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    request(request, callback, finalCount);
                }
            }, 1000);
        }
    }

}
