package com.jt.msi.taopiaopiao;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jt.msi.paystander.PayInterfaceActivity;

/**
 * Created by MSI on 2018/6/3.
 */

public class BaseActivity extends Activity implements PayInterfaceActivity {
    protected Activity that;

    @Override
    public void attach(Activity proxyActivity) {
        this.that = proxyActivity;
    }

    @Override
    public void setContentView(View view) {
        if (that != null) {
            that.setContentView(view);
        } else {
            super.setContentView(view);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if (that != null) {
            that.setContentView(layoutResID);
        } else {
            super.setContentView(layoutResID);
        }
    }

    @Override
    public <T extends View> T findViewById(int id) {
        if (that != null) {
            return that.findViewById(id);
        } else {
            return super.findViewById(id);
        }
    }

    @Override
    public Intent getIntent() {
        if (that != null) {
            return that.getIntent();
        } else {
            return super.getIntent();
        }
    }

    @Override
    public ClassLoader getClassLoader() {
        if (that != null) {
            return that.getClassLoader();
        } else {
            return super.getClassLoader();
        }

    }


    @NonNull
    @Override
    public LayoutInflater getLayoutInflater() {
        if (that != null) {
            return that.getLayoutInflater();
        } else {
            return super.getLayoutInflater();
        }
    }

    @Override
    public void startActivity(Intent intent) {
//        ProxyActivity --->className
        if(that==null){
            super.startActivity(intent);
        }else {
            Intent m = new Intent();
            m.putExtra("className", intent.getComponent().getClassName());
            that.startActivity(m);
        }

    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        if (that != null) {
            return that.getApplicationInfo();
        } else {
            return super.getApplicationInfo();
        }
    }

    @Override
    public Window getWindow() {
        if (that != null) {
            return that.getWindow();
        } else {
            return super.getWindow();
        }
    }

    @Override
    public WindowManager getWindowManager() {
        if (that != null) {
            return that.getWindowManager();
        } else {
            return super.getWindowManager();
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if(that==null){
            super.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        if(that==null){
            super.onStart();
        }
    }

    @Override
    public void onRestart() {
        if(that==null){
            super.onRestart();
        }
    }

    @Override
    public void onResume() {
        if(that==null){
            super.onResume();
        }
    }

    @Override
    public void onPause() {
        if(that==null){
            super.onPause();
        }
    }

    @Override
    public void onStop() {
        if(that==null){
            super.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if(that==null){
            super.onDestroy();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(that==null){
            super.onSaveInstanceState(outState);
        }
    }
}
