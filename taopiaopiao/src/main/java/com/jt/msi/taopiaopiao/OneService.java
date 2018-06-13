package com.jt.msi.taopiaopiao;

import android.util.Log;

/**
 * Created by MSI on 2018/6/13.
 */

public class OneService extends BaseService{
    int i=0;

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    Log.i("Jutao","run:"+i++);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
