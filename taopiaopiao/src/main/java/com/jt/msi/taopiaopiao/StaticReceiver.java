package com.jt.msi.taopiaopiao;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by MSI on 2018/7/8.
 */

public class StaticReceiver extends BroadcastReceiver{
    static final String ACTION="com.jt.msi.alipayplugin.plugin_action";
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"我是插件，收到宿主消息",Toast.LENGTH_LONG).show();
        context.sendBroadcast(new Intent(ACTION));
    }
}
