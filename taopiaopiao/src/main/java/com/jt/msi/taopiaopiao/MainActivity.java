package com.jt.msi.taopiaopiao;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (that == null) {
                    startActivity(new Intent(MainActivity.this, SecondActivity.class));
                } else {
                    startActivity(new Intent(that, SecondActivity.class));
                    startService(new Intent(that, OneService.class));
                }
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.jt.msi.taopiaopiao.MainActivity");
                registerReceiver(new MyReceiver(), intentFilter);
            }
        });
        findViewById(R.id.btn_send_cast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction("com.jt.msi.taopiaopiao.MainActivity");
                sendBroadcast(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
