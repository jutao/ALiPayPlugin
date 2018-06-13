package com.jt.msi.alipayplugin;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jt.msi.paystander.PayInterfaceService;

import java.lang.reflect.Constructor;

/**
 * Created by MSI on 2018/6/13.
 */

public class ProxyService extends Service{
    String serviceName;
    private PayInterfaceService payInterfaceService;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        init(intent);
        return null;
    }

    private void init(Intent intent) {
        serviceName=intent.getStringExtra("serviceName");
        try {
            Class<?> serviceClass = PluginManager.getInstance().getClassLoader().loadClass(serviceName);
            Constructor<?> constructor = serviceClass.getConstructor(new Class[]{});
            Object instance = constructor.newInstance(new Object[]{});
            payInterfaceService = (PayInterfaceService) instance;
            payInterfaceService.attach(this);
            Bundle bundle=new Bundle();
            payInterfaceService.onCreate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * onStartCommand 开始服务
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (payInterfaceService == null) {
            init(intent);
        }
        return payInterfaceService.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        payInterfaceService.onDestroy();
        super.onDestroy();

    }

    @Override
    public void onLowMemory() {
        payInterfaceService.onLowMemory();
        super.onLowMemory();
    }


    @Override
    public boolean onUnbind(Intent intent) {
        payInterfaceService.onUnbind(intent);
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        payInterfaceService.onRebind(intent);
        super.onRebind(intent);
    }
}
