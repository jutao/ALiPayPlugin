package com.jt.msi.taopiaopiao;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.jt.msi.paystander.PayInterfaceBroadcast;

/**
 * Created by MSI on 2018/6/25.
 */

public class MyReceiver extends BroadcastReceiver implements PayInterfaceBroadcast{
    @Override
    public void attach(Context context) {
        Toast.makeText(context,"上下文绑定成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"收到广播",Toast.LENGTH_LONG).show();
    }
}
