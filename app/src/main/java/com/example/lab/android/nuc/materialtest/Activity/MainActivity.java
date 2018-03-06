package com.example.lab.android.nuc.materialtest.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab.android.nuc.materialtest.FloatActionButton.DragFloatActionButton;
import com.example.lab.android.nuc.materialtest.Fruit.Fruit;
import com.example.lab.android.nuc.materialtest.Fruit.FruitAdapter;
import com.example.lab.android.nuc.materialtest.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private DrawerLayout mDrawerLayout;


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

        Button change_view = (Button) findViewById(R.id.change_headPhoto);
        //点击切换头像按钮调用相册选图片进行显示
//        change_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(MainActivity.this,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    },1);
//                }
//                else{
//                    openAlbum();
//                }
//            }
//        });

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
                    default:
                }
                return true;
            }
        });

        //登陆按钮的点击事件
        usernameView.setOnClickListener(this);
        emailView.setOnClickListener(this);

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


    //通过储存的授权打开相册
    private void openAlbum(){
        //使用Intent调用储存权限
        Intent intent = new Intent("android.intent.action.GET_CONTEXT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CHOOSE_PHOTO:
                if (requestCode == RESULT_OK){
                    //判断手机版本号
                    if (Build.VERSION.SDK_INT >= 19){
                        //4.4版本以上的使用下面的方法进行处理
                        handleImageOnkitakat(data);
                    }else {
                        //4.4版本以下的使用下面的方法进行处理
                        handleImageBeforeKitkat(data);
                    }
                }
        }
    }

    //4.4版本以上,选择相册中的图片不在返回图片真是的Uri了
    @TargetApi(19)
    private void handleImageOnkitakat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
            //如果是Document类型的Uri,则通过document id 进行处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.provider.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.provider.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("contnet://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){

            //如果是content类型的Uri,则使用普通方式处理
            imagePath = getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){

            //如果是file类型的Uri,直接获取图片路径即可
            imagePath = uri.getPath();
        }
        diaplayImage(imagePath);
    }

    //4.4版本以下的，选择相册的图片返回真实的Uri
    private void handleImageBeforeKitkat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        diaplayImage(imagePath);
    }


    private String getImagePath(Uri uri,String selection){
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null){
            //如果是从第一个开始查起的
            if (cursor.moveToFirst()){
                //获取储存下的所有图片
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            //关闭查找
            cursor.close();
        }
        return path;
    }

    //该方法将图片显示出来
    private void diaplayImage(String Imagepath){
        if (Imagepath != null){
            //利用BitmapFactory的decodeFile()方法解析图片
            Bitmap bitmap = BitmapFactory.decodeFile(Imagepath);
            headImageView.setImageBitmap(bitmap);
        }else{
            Toast.makeText(this, "failed to find imagePath", Toast.LENGTH_SHORT).show();
        }
    }


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
                Toast.makeText(this,"You clicked Backup",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this,"You clicked Delete",Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this,"You clicked Settings",Toast.LENGTH_SHORT).show();
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
        switch (v.getId()){
            case R.id.username :case R.id.mail:
                Intent login_intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(login_intent);
                break;
            default:
        }
    }
}
