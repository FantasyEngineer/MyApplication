package com.hjg.hjgapplife.activity.IPC;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.hjg.hjgapplife.IMyAidlInterface;

/**
 * Created by Administrator on 2017/11/2 0002.
 */

public class AIDLService extends Service {
    private IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int add(int a, int b) throws RemoteException {
            return a + b;
        }
    };

    public AIDLService() {
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
