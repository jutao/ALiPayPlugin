package com.jt.msi.paystander;

import android.content.Context;
import android.content.Intent;

/**
 * Created by MSI on 2018/6/14.
 */

public interface PayInterfaceBroadcast {
    void attach(Context context);

    void onReceive(Context context, Intent intent);
}
