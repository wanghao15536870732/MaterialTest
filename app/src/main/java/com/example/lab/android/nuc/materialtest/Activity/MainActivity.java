package com.example.lab.android.nuc.materialtest.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lab.android.nuc.materialtest.DragFloatActionButton;
import com.example.lab.android.nuc.materialtest.Fruit;
import com.example.lab.android.nuc.materialtest.FruitAdapter;
import com.example.lab.android.nuc.materialtest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private DrawerLayout mDrawerLayout;


    //切换头像
    private ImageView headImageView;

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

        headImageView = (ImageView) findViewById(R.id.icon_image);

        //点击头像调用相册选图片进行显示
        headImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },1);
                }
                else{

                }
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setCheckedItem(R.id.main_fruit);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                我们可以在这个方法中写自己的逻辑，这里我们只是调用DrawerLayout的closeDrawers()方法将滑动菜单关闭就好
                switch (item.getItemId()){
                    case R.id.main_fruit:
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_call:

                        break;
                    case R.id.nav_friends:

                        break;
                    case R.id.nav_location:
                        Intent location_intent = new Intent(MainActivity.this,LocationActivity.class);
                        startActivity(location_intent);
                        break;
                    case R.id.nav_task:
                        break;
                    default:
                }
                return true;
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

                    }else {
                        //4.4版本以下的使用下面的方法进行处理

                    }
                }
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
}
