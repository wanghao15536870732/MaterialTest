package com.example.lab.android.nuc.materialtest.Downalod;

import android.os.AsyncTask;
import android.os.Environment;

import com.baidu.location.b.e;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//AsyncTask的三个泛型参数，第一个参数表示在执行AsyncTask的时候需要传入一个字符串数据给后台任务
//第二个参数指定为Integer，表示使用整型数据来作为进度的显示单位
//第三个参数在指定为Integer，表使用整型数据来反馈执行结果
public class DownloadTask extends AsyncTask<String,Integer,Integer> {

    //定义四个整型常量分别表示下载成功的成功、失败、暂停和取消
    private static final int TYPE_SUCCESS = 0;
    private static final int TYPE_FAILED = 1;
    private static final int TYPE_PAUSE = 2;
    private static final int TYPE_CANCELED = 3;

    private int lastProgress;
    //定义一个下载的监听事件
    private DownloadListener listener;
    //取消事件为假
    private boolean isCanceled = false;
    //暂停时间为假
    private boolean isPaused = false;

    public DownloadTask(DownloadListener listener) {
        this.listener = listener;
    }

    //该方法用于在后台执行下载的逻辑
    @Override
    protected Integer doInBackground(String... params) {
        //建立输入流
        InputStream is = null;
        RandomAccessFile savedFile = null;
        //创建文件路径
        File file = null;
        try{
            long downloadLength = 0;//记录已下载的文件长度

            //先从参数中获取了下载的URL地址
            String downloadUrl = params[0];

            //再根据下载的URL地址解析出文件下载的文件名
            //在file路径的前面加上"/"
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            AutoInstall.setdownloadUrl(downloadUrl);
            //包的名是固定的

            //将制定文件放到SD的download目录中，即Environment.DIRECTORY_DOWNLOADS目录下
            String directory = Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DOWNLOADS).getPath();

            //文件的路径即为:包的名 + "/" + 文件的名字
            file = new File(directory + fileName);
            //先判断一下Download文件夹下是否已经存在文件夹
            if (file.exists()){
                //如果文件存在,下载的进度等于文件的字节
                downloadLength = file.length();
            }
            //用Long整型保存获取待下载文件的长度
            long contentLength = getContentLength(downloadUrl);
            //如果下载的进度为0，则返回下载失败的通知
            if (contentLength == 0){
                return TYPE_FAILED;
            } else if (contentLength == downloadLength){
                //如果已下载的字节等于文件的总字节，则表明下载成功了
                return TYPE_SUCCESS;
            }
            //用OkHttp来发送一条请求
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    //添加了一个Header.断点下载，指定从哪一个字节下载
                    .addHeader("RANGE" , "bytes=" + downloadLength  + "-")
                    .url(downloadUrl)
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null){
                //字节流
                is = response.body().byteStream();
                savedFile = new RandomAccessFile(file,"rw");
                savedFile.seek(downloadLength);//跳过已下载过的字节
                byte [] b = new byte[1024];
                int total = 0;
                //记录不断下载时的长度
                int len;
                while ((len = is.read(b)) != - 1){
                    if (isCanceled){
                        return TYPE_CANCELED;
                    }else if (isPaused){
                        return TYPE_PAUSE;
                    }else {
                        //没有被打断,进度条一直增加
                        total += len;
                        savedFile.write(b,0,len);
                        //计算下载的百分比
                        int progress = (int) ((total + downloadLength) * 100 / contentLength);
                        publishProgress(progress);
                    }
                }
                //下载成功,关闭网络请求
                response.body().close();
                return TYPE_SUCCESS;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                //关闭打开的is和savedFile
                if (is != null) {
                    is.close();
                }
                if (savedFile != null) {
                    savedFile.close();
                }
                //如果取消下载，并且此时文件存在
                if (isCanceled && file != null) {
                    //删除不必要的文件
                    file.delete();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }


    //该方法用于在通知栏显示下载进度
    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress > lastProgress){
            listener.onProgress(progress);
            lastProgress = progress;
        }
    }

    //该方法用于通知最后的结果
    @Override
    protected void onPostExecute(Integer integer) {
        switch (integer){
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_PAUSE:
                listener.onPause();
                break;
            case TYPE_CANCELED:
                listener.onCanceled();
                break;
            default:
                break;
        }
    }

    //暂停下载
    public void pauseDownload(){
        isPaused = true;
    }

    //停止下载
    public void cancelDownload(){
        isCanceled = true;
    }

    //记录下载的长度
    private long getContentLength(String downloadUrl)throws IOException{
        //先创建了一个OkHttpClient的实例
        OkHttpClient client = new OkHttpClient();
        //创建一个Request对象发送网络请求
        Request request = new Request.Builder()
                //设置网络地址
                .url(downloadUrl)
                .build();
        //利用newCall()方法来创建一个Call对象,并调用他的execute()方法,
        // 发送请求并获去返回的数据
        Response response = client.newCall(request).execute();
        //如果网络返回的数据为null，捉着是下载成功
        if (response != null ||  response.isSuccessful()){
            long contentLength = response.body().contentLength();
            response.close();
            return  contentLength;
        }
        return 0;
    }
}