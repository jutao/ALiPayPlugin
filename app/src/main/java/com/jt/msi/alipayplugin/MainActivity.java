package com.jt.msi.alipayplugin;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PluginManager.getInstance().setContext(this);
    }

    public void load(View view){
        loadPlugin();
    }

    private void loadPlugin() {
        File fileDir=this.getDir("plugin", Context.MODE_PRIVATE);
        String name="pluginb.apk";
        String filePath=new File(fileDir,name).getAbsolutePath();
        File file=new File(filePath);
        if(file.exists()){
            file.delete();
        }
        InputStream is=null;
        FileOutputStream os=null;
        try {
            is=new FileInputStream(new File(Environment.getExternalStorageDirectory(),name));
            os=new FileOutputStream(filePath);
            int len=0;
            byte[] buffer=new byte[1024];
            while ((len=is.read(buffer))!=-1){
                os.write(buffer,0,len);
            }
            File f=new File(filePath);
            if(f.exists()){
                Toast.makeText(this,"dex overwrite",Toast.LENGTH_LONG).show();
            }
            PluginManager.getInstance().loadPath(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void click(View view){
        Intent intent=new Intent(this,ProxyActivity.class);
        intent.putExtra("className",PluginManager.getInstance().getPackageInfo().activities[0].name);
        startActivity(intent);
    }


}
