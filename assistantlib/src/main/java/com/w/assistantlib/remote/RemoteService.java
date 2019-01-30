package com.w.assistantlib.remote;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.w.assistantlib.PieAssistant;
import com.w.assistantlib.remote.IAssistantTools.Stub;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kotlin.jvm.internal.Intrinsics;

public final class RemoteService extends Service {
    private RemoteCallbackList mRemoteCallbackList = new RemoteCallbackList<IRequestCallBack>();


    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    public IBinder onBind(@NotNull Intent intent) {
        return (IBinder) (new RemoteService.RomteBinder());
    }

    public boolean onUnbind(@NotNull Intent intent) {
        return super.onUnbind(intent);
    }

    public final class RomteBinder extends Stub {
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, @NotNull String aString, @NotNull Request aRequest) throws RemoteException {
        }

        @NotNull
        public String request(@Nullable final Request request, @Nullable final IRequestCallBack iRequestCallBack) {
            if (request != null) {
                if (iRequestCallBack != null) {
                    mRemoteCallbackList.register(iRequestCallBack);
                }

                int len = mRemoteCallbackList.beginBroadcast();
                if (PieAssistant.getRmoteServiceCallBack() != null) {
                    for (RemoteServiceCallBack rmoteServiceCallBack : PieAssistant.getRmoteServiceCallBack()) {
                        rmoteServiceCallBack.onCallBack(request, new RemoteServiceResponse() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    iRequestCallBack.onResponse(response);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
                mRemoteCallbackList.finishBroadcast();
                if (iRequestCallBack != null) {
                    mRemoteCallbackList.unregister(iRequestCallBack);
                }
            }

            return "";
        }
    }

    public interface RemoteServiceCallBack {
        public void onCallBack(@Nullable final Request request, @Nullable final RemoteServiceResponse iRequestCallBack);
    }

    public interface RemoteServiceResponse {
        public void onResponse(String response);
    }
}
