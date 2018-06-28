package com.jt.msi.alipayplugin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.jt.msi.paystander.PayInterfaceActivity;

import java.lang.reflect.Constructor;

/**
 * Created by MSI on 2018/6/5.
 */

public class ProxyActivity extends Activity {

    private String className;
    private PayInterfaceActivity payInterfaceActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        className = getIntent().getStringExtra("className");
        //class
        try {
            Class activityClass = getClassLoader().loadClass(className);
            Constructor constructor=activityClass.getConstructor(new Class[]{});
            Object instance = constructor.newInstance(new Object[]{});

            payInterfaceActivity = (PayInterfaceActivity) instance;
            payInterfaceActivity.attach(this);
            Bundle bundle=new Bundle();
            payInterfaceActivity.onCreate(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getClassLoader();
    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getResources();
    }

    @Override
    public void onStart() {
        super.onStart();
        payInterfaceActivity.onStart();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        payInterfaceActivity.onRestart();
    }

    @Override
    public void onResume() {
        super.onResume();
        payInterfaceActivity.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        payInterfaceActivity.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        payInterfaceActivity.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        payInterfaceActivity.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        payInterfaceActivity.onSaveInstanceState(outState);
    }

    @Override
    public void startActivity(Intent intent) {
        String className = intent.getStringExtra("className");
        Intent intent1=new Intent(this,ProxyActivity.class);
        intent1.putExtra("className",className);
        super.startActivity(intent1);
    }

    @Override
    public ComponentName startService(Intent intent) {
        String serviceName = intent.getStringExtra("serviceName");
        Intent intent1=new Intent(this,ProxyService.class);
        intent1.putExtra("serviceName",serviceName);
       return super.startService(intent1);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        IntentFilter newInterFilter=new IntentFilter();
        for (int i = 0; i < filter.countActions(); i++) {
            newInterFilter.addAction(filter.getAction(i));
        }
        return super.registerReceiver(new ProxyBroadCast(receiver.getClass().getName(),this), newInterFilter);
    }
}
