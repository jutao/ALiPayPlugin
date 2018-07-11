package com.jt.msi.alipayplugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import dalvik.system.DexClassLoader;

/**
 * Created by MSI on 2018/6/4.
 */

public class PluginManager {
    private PackageInfo packageInfo;

    private Resources resources;

    private DexClassLoader dexClassLoader;

    private Context context;

    private static final PluginManager ourInstance = new PluginManager();

    public static PluginManager getInstance() {
        return ourInstance;
    }

    private PluginManager() {
    }

    public void loadPath(Context context) {
        File fileDir = context.getDir("plugin", Context.MODE_PRIVATE);
        String name = "pluginb.apk";
        String filePath = new File(fileDir, name).getAbsolutePath();

        PackageManager packageManager = context.getPackageManager();
        packageInfo = packageManager.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);

        //activity 名字
        File dexOutFile = context.getDir("dex", Context.MODE_PRIVATE);
        dexClassLoader = new DexClassLoader(filePath, dexOutFile.getAbsolutePath(), null, context.getClassLoader());
        //resource
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, filePath);
            resources = new Resources(assetManager, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        parseReceivers(context, filePath);
    }

    private void parseReceivers(Context context, String path) {
        try {
            Class packageParserClass = Class.forName("android.content.pm.PackageParser");
            Method parsePackage = packageParserClass.getDeclaredMethod("parsePackage", File.class, int.class);
            Object instance = packageParserClass.newInstance();
            Object packageObj = parsePackage.invoke(instance, new File(path), PackageManager.GET_ACTIVITIES);
            Field receiverField = packageObj.getClass().getDeclaredField("receivers");
            //拿到广播集合
            List receivers = (List) receiverField.get(packageObj);

            Class<?> componentClass = Class.forName("android.content.pm.PackageParser$Component");
            Field intentsField = componentClass.getDeclaredField("intents");

            //generateActivityInfo方法

            Class<?> packageUserStateClass = Class.forName("android.content.pm.PackageUserState");
            Object packageUserState = packageUserStateClass.newInstance();
            Class<?> packageParser$ActivityClass = Class.forName("android.content.pm.PackageParser$Activity");
            Method generateActivityInfoMethod = packageParserClass.getDeclaredMethod("generateActivityInfo", packageParser$ActivityClass, int.class, packageUserStateClass, int.class);
            //userId
            Class<?> userHandle = Class.forName("android.os.UserHandle");
            Method getCallingUserIdMethod = userHandle.getDeclaredMethod("getCallingUserId");
            int userId = (int) getCallingUserIdMethod.invoke(null);


            for (Object receiver : receivers) {
                ActivityInfo activityInfo = (ActivityInfo) generateActivityInfoMethod.invoke(instance, receiver, 0, packageUserState, userId);
                BroadcastReceiver broadcastReceiver = (BroadcastReceiver) dexClassLoader.loadClass(activityInfo.name).newInstance();
                List<? extends IntentFilter> intents = (List<? extends IntentFilter>) intentsField.get(receiver);
                for (IntentFilter intentFilter : intents) {
                    context.registerReceiver(broadcastReceiver, intentFilter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ClassLoader getClassLoader() {
        return dexClassLoader;
    }

    public Resources getResources() {
        return resources;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
