package com.example.lab.android.nuc.materialtest.Downalod;



//在此接口中定义各种方法
public interface DownloadListener{

    //用于通知当前下载进度
    void onProgress(int progress);

    //用于通知下载成功的事件
    void onSuccess();

    //用于通知下载失败的事件
    void onFailed();

    //用于通知下载暂停事件
    void onPause();

    //用于通知下载取消事件
    void onCanceled();
}
