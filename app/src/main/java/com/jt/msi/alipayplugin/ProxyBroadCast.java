package com.jt.msi.alipayplugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jt.msi.paystander.PayInterfaceBroadcast;

import java.lang.reflect.Constructor;
import java.util.Observer;

/**
 * Created by MSI on 2018/6/29.
 */

public class ProxyBroadCast extends BroadcastReceiver {
    private String className;
    private  PayInterfaceBroadcast payInterfaceBroadcast;

    public ProxyBroadCast(String className,Context context) {
        this.className = className;
        try {
            Class loadClass=PluginManager.getInstance().getClassLoader().loadClass(className);
            Constructor<?> constructor = loadClass.getConstructor(new Class[]{});
            Object instance=constructor.newInstance(new Object[]{});
            payInterfaceBroadcast = (PayInterfaceBroadcast) instance;
            payInterfaceBroadcast.attach(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        payInterfaceBroadcast.onReceive(context,intent);
    }
}
