package com.example.lab.android.nuc.materialtest.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lab.android.nuc.materialtest.Downalod.AutoInstall;
import com.example.lab.android.nuc.materialtest.FloatActionButton.DragFloatActionButton;
import com.example.lab.android.nuc.materialtest.Fruit.Fruit;
import com.example.lab.android.nuc.materialtest.Fruit.FruitAdapter;
import com.example.lab.android.nuc.materialtest.R;
import com.example.lab.android.nuc.materialtest.Service.DownloadService;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    //下载的服务
    private DownloadService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //向下转型
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private DrawerLayout mDrawerLayout;

    private static int VIBRAT = 1;

    //切换名字和邮箱
    private TextView usernameView;
    private TextView emailView;

    //切换头像
    private CircleImageView headImageView;

    //打开相册
    public static final int CHOOSE_PHOTO = 2;


    //封装列表中所有的水果
    private Fruit[] fruits = {new Fruit("Apple", R.drawable.apple_pic),new Fruit("Banana",R.drawable.banana_pic),
            new Fruit("Orange",R.drawable.orange_pic),new Fruit("Watermelon",R.drawable.watermelon_pic),
            new Fruit("Pear",R.drawable.pear_pic),new Fruit("Grape",R.drawable.grape_pic),
            new Fruit("Pineapple",R.drawable.pineapple_pic),new Fruit("Strawberry",R.drawable.strawberry_pic),
            new Fruit("Cherry",R.drawable.cherry_pic),new Fruit("Mango",R.drawable.mango_pic)};

    private List<Fruit> fruitList = new ArrayList<>();

    private FruitAdapter fruitAdapter;


    //下拉刷新列表
    private SwipeRefreshLayout swipeRefreshLayout;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        headImageView = (CircleImageView) findViewById(R.id.icon_image);

        usernameView = (TextView) findViewById(R.id.username);
        emailView = (TextView) findViewById(R.id.mail);

        //开启服务
        Intent intent_4 = new Intent(this,DownloadService.class);
        startService(intent_4);
        bindService(intent_4,connection,BIND_AUTO_CREATE);

        //打开应用显示通知栏
        notification();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){

            //调用actionBar的setDisplayHomeAsUpEnabled()方法让导航按钮显示出开
            actionBar.setDisplayHomeAsUpEnabled(true);

            //再设置一个导航按钮图标,默认是返回键,设置为菜单的样式
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        }
        navView.setCheckedItem(R.id.main_fruit);


        //设置菜单栏item的点击事件
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
                    mDrawerLayout.closeDrawers();
                }
//                我们可以在这个方法中写自己的逻辑，这里我们只是调用DrawerLayout的closeDrawers()方法将滑动菜单关闭就好
                switch (item.getItemId()){
                    case R.id.main_fruit:
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_call:
                        Intent call_intent = new Intent(MainActivity.this,CallActivity.class);
                        startActivity(call_intent);
                        break;
                    case R.id.nav_friends:
                        Intent task_intent = new Intent(MainActivity.this,FriendActivity.class);;
                        startActivity(task_intent);
                        break;
                    case R.id.nav_location:
                        Intent location_intent = new Intent(MainActivity.this,LocationActivity.class);
                        startActivity(location_intent);
                        break;
                    case R.id.nav_task:
                        Intent taskIntent = new Intent(MainActivity.this,TaskActivity.class);
                        startActivity(taskIntent);
                        break;
                    case R.id.nav_login:
                        Intent login_intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivityForResult(login_intent,1);
                        break;
                    case R.id.other:
                        Intent other_intent = new Intent(MainActivity.this,Main2Activity.class);
                        startActivityForResult(other_intent,1);
                        break;
                    default:
                }
                return true;
            }
        });
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"FAb clicked",Toast.LENGTH_SHORT).show();

                Snackbar.make(v,"Data deleted",Snackbar.LENGTH_SHORT)
                        .setAction("Unod", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this,"Data restored",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
        DragFloatActionButton fab_move = (DragFloatActionButton) findViewById(R.id.fab_move);
        fab_move.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        initFruits();
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        fruitAdapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(fruitAdapter);


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruit();
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    String username = msg.getData().getString("1");
                    String email = msg.getData().getString("2");
                    usernameView.setText(username);
                    emailView.setText(email);
                    break;
                default:
            }
        }
    };

    //更新水果的布局
    private void refreshFruit(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
//                    线程沉睡2 秒，否则刷新就立即结束了，从而看不到刷新过程
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
//                        通知数据发生了变化
                        fruitAdapter.notifyDataSetChanged();
//                        表示刷新事件结束，并隐藏进度条
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    //重复显示水果的名字50遍
    private void initFruits(){
        fruitList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }




    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.backup:
                String url = "http://shouji.360tpcdn.com/180205/be9c722e58ec42eb85ceef6325ee8619/com.kugou.android_8948.apk";
                downloadBinder.startDownload(url);
                break;
            case R.id.delete:
                downloadBinder.pauseDownload();
                break;
            case R.id.settings:
                downloadBinder.cancelDownload();
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
            default:
        }
        return true;
    }

    //点击事件的
    @Override
    public void onClick(View v) {
        if (downloadBinder == null){
            return;
        }
        switch (v.getId()){
            case R.id.username :case R.id.mail:
                Intent login_intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(login_intent);
                break;
            default:
        }
    }

    private void notification(){
        //对系统通知进行管理
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //点击通知的动作
        Intent intent = new Intent(this,MainActivity.class);
        //延迟的Intent
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("你的水果App")
                .setContentText("点击回到Fruit")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.apple_pic)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.banana_pic))
                .setContentIntent(pi)
                //设置手机默认的响铃、震动及指示灯闪烁
//                .setSound(Uri.parse(getPackageName() + "/" + R))
//                显示大图片
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture
                        (BitmapFactory.decodeResource(getResources(),R.drawable.cherry_pic)))
                .setStyle(new NotificationCompat.BigTextStyle().bigText
                        (Fruit.class.getName()))
                .build();
        manager.notify(1,notification);
//        Notification vibrateNotification = new NotificationCompat.Builder(this)
//                .setDefaults(NotificationCompat.DEFAULT_ALL)
//                .build();
//        if (VIBRAT == 1){
//            manager.notify(1,vibrateNotification);
//            VIBRAT = 2;
//        }
    }

    @Override
    public void onActivityReenter(final int resultCode, Intent data) {
        switch (resultCode){
            case 1:
                if (resultCode == RESULT_OK){
                    final String return_username = data.getStringExtra("username_return");
                    final String return_email = data.getStringExtra("email_return");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            message.what = 1;
                            bundle.putString("1",return_username);
                            bundle.putString("2",return_email);
                            message.setData(bundle);//mes利用Bundle传递数据
                            handler.sendMessage(message);//用activity中的handler发送消息
                        }
                    }).start();
                }
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //活动销毁解除绑定
        unbindService(connection);
    }
}
