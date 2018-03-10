package com.example.lab.android.nuc.materialtest.Downalod;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by 王浩 on 2018/3/10.
 */

public class AutoInstall {
    private static String mUrl;

    private static Context mcontext;

    private static String downloadUrl;

    public static void setUrl(String url){
        mUrl = url;
    }
    public static void setdownloadUrl(String urll){
        downloadUrl = urll;
    }
    /**
     * 安装
     * @param context
     *  接受外部传进来的context
     */

    public static void install(Context context){
        mcontext = context;
        //核心是以下代码
        Intent intent_5 = new Intent(Intent.ACTION_VIEW);
        intent_5.setDataAndType(Uri.fromFile(new File(mUrl)),
                "application/vnd.android.package-archive");
        mcontext.startActivity(intent_5);
    }



    public static String getdoanloadUrl(){
        return downloadUrl;
    }
}
