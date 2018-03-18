# 基于Material support 的 Fruit app
## 一个关于水果的应用
## 最佳的UI体验—————Material Design 实战
|、、、主页面(MainActivtity.java) 、、、| 、、、水果界面(FruitActivity.java)、、、|
|:--:|:--:|
|<div align=center><img width="290" height="505" src="https://github.com/wanghao15536870732/MaterialTest/blob/61841dbee110b41cd8c50c9d0ec8c4f271e28827/app/src/main/res/drawable-xhdpi/Main.gif"/></div> |<div align=center><img width="290" height="505" src="https://github.com/wanghao15536870732/MaterialTest/blob/61841dbee110b41cd8c50c9d0ec8c4f271e28827/app/src/main/res/drawable-xhdpi/Login.gif"/></div> |
## 
|、、、、、电话簿界面(CallActivity.java)、、、、、|、、、、、好友界面(FriendsActivity.java)、、、、、|
|:--:|:--:|
|<div align=center><img width="290" height="505"                    src="https://github.com/wanghao15536870732/MaterialTest/blob/61841dbee110b41cd8c50c9d0ec8c4f271e28827/app/src/main/res/drawable-xhdpi/Call.gif"/></div> |<div align=center><img width="290" height="505" src="https://github.com/wanghao15536870732/MaterialTest/blob/61841dbee110b41cd8c50c9d0ec8c4f271e28827/app/src/main/res/drawable-xhdpi/Friend.gif"/></div> |
## 
| 、位置的界面(LocationActivity.java)(Baidu地图的Map)、 | 、、、 谈话的界面(TaskActivity.java)  、、、   |
|:--:|:--:|
|<div align=center><img width="290" height="505"                    src="https://github.com/wanghao15536870732/MaterialTest/blob/61841dbee110b41cd8c50c9d0ec8c4f271e28827/app/src/main/res/drawable-xhdpi/5.png"/></div> |<div align=center><img width="290" height="505" src="https://github.com/wanghao15536870732/MaterialTest/blob/61841dbee110b41cd8c50c9d0ec8c4f271e28827/app/src/main/res/drawable-xhdpi/6.png"/></div> |
## 首先是MainActivity的页面
## MainActivity的布局代码如下：
```java

<!--滑动菜单DrawLayout-->

<android.support.v4.widget.DrawerLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/drawer_layout"
    android:layout_height="match_parent"
    android:layout_width="match_parent" >

    <!--CoordinatorLayout、、、加强版的FrameLayout-->
    <!--跟FrameLayout不同的是它可以监控所有子布局的各种事件-->
    <android.support.design.widget.CoordinatorLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <!--为防止RecyclerView将TolBar遮住-->
        <!--可以将TolBar嵌套到AppBarLayout当中-->
        <!--并给RecyclerView指定一个布局行为-->
        <android.support.design.widget.AppBarLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">


            <android.support.v7.widget.Toolbar
                android:id="@+id/toolbar"
                android:layout_width="match_parent"
                android:layout_height="?attr/actionBarSize"
                android:background="?attr/colorPrimary"
                android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"
                app:popupTheme="@style/ThemeOverlay.AppCompat.Light"
                app:layout_scrollFlags="scroll|enterAlways|snap"/>
            <!--AppBarLayout还可以实现-->
            <!--scroll ：当RecyclerView向上滚动的时候，ToolBar会跟着一起向上滚动并实现隐藏
            enterAlways ：当RecyclerView向下滚动的时候，Toolbar会跟着一起向下滚动并重新显示
            snap ：当ToolBar还没有隐藏或者显示的时候，会根据当前滚动的距离，
            自动选择时显示还是隐藏-->
        </android.support.design.widget.AppBarLayout>


        <android.support.design.widget.FloatingActionButton
            android:id="@+id/fab"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="bottom|end"
            android:layout_margin="16dp"
            app:srcCompat="@drawable/ic_done"
            android:elevation="8dp"/>
        <com.example.lab.android.nuc.materialtest.FloatActionButton.DragFloatActionButton
            android:id="@+id/fab_move"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="bottom|start"
            android:layout_margin="16sp"
            app:srcCompat="@drawable/ic_add"/>

        <!--添加SwipeRefreshLayout,进行下拉刷新的功能-->
        <!--RecyclerView的布局添加到SwipeRefreshLayout,因此，
        Recycler布局行为要移到SwipeRefreshLayout里面才行-->
        <android.support.v4.widget.SwipeRefreshLayout
            android:id="@+id/swipe_refresh"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layout_behavior="@string/appbar_scrolling_view_behavior">

            <android.support.v7.widget.RecyclerView
                android:id="@+id/recycler_view"
                android:layout_width="match_parent"
                android:layout_height="match_parent" />
        </android.support.v4.widget.SwipeRefreshLayout>
    </android.support.design.widget.CoordinatorLayout>

    <!--<TextView-->
        <!--android:layout_width="match_parent"-->
        <!--android:layout_height="match_parent"-->
        <!--android:layout_gravity="start"-->
        <!--android:text="This is menu"-->
        <!--android:textSize="30sp"-->
        <!--android:background="#FFF"/>-->

    <android.support.design.widget.NavigationView
        android:id="@+id/nav_view"
        android:layout_height="match_parent"
        android:layout_width="match_parent"
        android:layout_gravity="start"
        app:menu="@menu/nav_menu"
        app:headerLayout="@layout/nav_header" />

</android.support.v4.widget.DrawerLayout>
```
## 简化一下就是：
```java

<android.support.v4.widget.DrawerLayout

    <android.support.design.widget.CoordinatorLayout

        <android.support.design.widget.AppBarLayout

            <android.support.v7.widget.Toolbar/>

        </android.support.design.widget.AppBarLayout>

	//两个FloatActionButton

        <android.support.design.widget.FloatingActionButton/>

        <com.example.lab.android.nuc.materialtest.FloatActionButton.DragFloatActionButton/>

        //用SwipeRefreshLayout嵌套一个RecyclerView

        <android.support.v4.widget.SwipeRefreshLayout">

            <android.support.v7.widget.RecyclerView />

        </android.support.v4.widget.SwipeRefreshLayout>

    </android.support.design.widget.CoordinatorLayout>

    <!--<TextView-->/>-->
	//菜单栏的实现

    <android.support.design.widget.NavigationView/>

</android.support.v4.widget.DrawerLayout>

```

## 滑动菜单的设置(在res中新建一个menu的包，再新建一个nav_menu.xml用于设置item项的图标和内容)```java```

### nav_menu.xml(每个item用Group分开是为了在每个item之间显示分割线)
```java
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <!--用Group将item分开，相邻item之间可以显示分割线-->
    <group android:id="@+id/one" android:checkableBehavior="single">

        <item
            android:id="@+id/main_fruit"
            android:icon="@drawable/ic_bookmark"
            android:title="Fruit" />
    </group>
    <group android:id="@+id/two" android:checkableBehavior="single">
        <item
            android:id="@+id/nav_login"
            android:icon="@drawable/ic_login"
            android:title="Login"/>
    </group>
    <group android:id="@+id/three" android:checkableBehavior="single">
        <item
            android:id="@+id/nav_call"
            android:icon="@drawable/ic_call"
            android:title="Call"/>
    </group>
    <group android:id="@+id/four" android:checkableBehavior="single">
        <item
            android:id="@+id/nav_friends"
            android:icon="@drawable/ic_friends"
            android:title="Friends"/>
    </group>
    <group android:checkableBehavior="single">
        <item
            android:id="@+id/nav_location"
            android:icon="@drawable/ic_location"
            android:title="Location"/>
    </group>
    <group android:id="@+id/six" android:checkableBehavior="single">
        <item
            android:id="@+id/nav_task"
            android:icon="@drawable/ic_task"
            android:title="Task" />
    </group>
    <group android:id="@+id/five" android:checkableBehavior="single">
        <item
            android:id="@+id/other"
            android:icon="@drawable/apple_pic"
            android:title="Other"/>
    </group>
</menu>
```

### MainActivity.java：

```java
   //获取滑动菜单的实例
    private DrawerLayout mDrawerLayout;
     //获取菜单的的实例
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        final NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        //调用getSupportActionBar()方法让导航按钮显示出来
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

                //如果已经拉到左边，则关闭
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
```

## 至于向下滑动刷新(MainActivity.java):
```java
    //下拉刷新列表
    private SwipeRefreshLayout swipeRefreshLayout;
    
    //通过findViewById找到swipeRefreshLayout的实例
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        //设置下拉刷新进度条的颜色为绿色
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruit();
            }
        });
        
         //更新水果的布局
    private void refreshFruit(){
        //开启一个新的线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                 //   线程沉睡2 秒，否则刷新就立即结束了，从而看不到刷新过程
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
        
```

## 登录界面的布局(activity_login.xml)
```java
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical"
android:layout_width="match_parent"
android:layout_height="match_parent">

<include layout="@layout/login_title"/>

<LinearLayout
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="40dp">
</LinearLayout>

<RelativeLayout
    android:layout_width="match_parent"
    android:layout_height="100dp"
    android:background="@drawable/shape">
    <!--该开源项目是用来轻松实现图片圆形化的共能-->
    <de.hdodenhof.circleimageview.CircleImageView
        android:id="@+id/icon_image"
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:layout_alignParentLeft="true"
        android:layout_alignParentStart="true"
        android:layout_alignParentTop="true"
        android:layout_marginLeft="57dp"
        android:layout_marginStart="57dp"
        android:src="@drawable/img_x" />

    <ImageView
        android:id="@+id/edit_view"
        android:layout_width="25dp"
        android:layout_height="25dp"
        android:layout_alignParentTop="true"
        android:layout_toLeftOf="@+id/take_picture"
        android:layout_toStartOf="@+id/take_picture"
        app:srcCompat="@drawable/ic_stay_primary_portrait_black_24dp" />

    <Button
        android:id="@+id/take_picture"
        android:layout_width="wrap_content"
        android:layout_height="40dp"
        android:layout_alignParentEnd="true"
        android:layout_alignParentRight="true"
        android:layout_alignParentTop="true"
        android:layout_marginEnd="17dp"
        android:layout_marginRight="17dp"
        android:text="点击拍照切换头像"
        android:textSize="10sp" />

    <ImageView
        android:id="@+id/take_form_album"
        android:layout_width="25dp"
        android:layout_height="25dp"
        android:layout_below="@+id/take_picture"
        android:layout_marginTop="12dp"
        android:layout_toLeftOf="@+id/take_picture"
        android:layout_toStartOf="@+id/take_picture"
        app:srcCompat="@drawable/ic_picture"/>

    <Button
        android:id="@+id/take_album"
        android:layout_width="wrap_content"
        android:layout_height="40dp"
        android:layout_toRightOf="@+id/take_form_album"
        android:layout_below="@+id/take_picture"
        android:text="相册选择头像"
        android:textSize="10sp" />
</RelativeLayout>


<LinearLayout
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="40dp">
</LinearLayout>

<LinearLayout
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="60dp"
    android:background="@drawable/shape_2">

    <TextView
        android:layout_width="90dp"
        android:layout_height="wrap_content"
        android:textSize="18sp"
        android:layout_gravity="center_vertical"
        android:text="Username:"/>
    <EditText
        android:id="@+id/username"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:layout_gravity="center_vertical"/>

</LinearLayout>

<LinearLayout
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="60dp"
    android:background="@drawable/shape">

    <TextView
        android:layout_width="90dp"
        android:layout_height="wrap_content"
        android:layout_gravity="center_vertical"
        android:text="Email:"
        android:textSize="18sp" />

    <EditText
        android:id="@+id/mail"
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="1"
        android:layout_gravity="center_vertical"/>
    <ImageView
        android:id="@+id/mail_Visibility"
        android:layout_width="40dp"
        android:layout_height="40dp"
        app:srcCompat="@drawable/ic_visibility" />
</LinearLayout>
<LinearLayout
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <CheckBox
        android:id="@+id/remember_password"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="18sp"
        android:textStyle="bold"
        android:text="Remember password"/>
</LinearLayout>

<LinearLayout
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="30dp">
</LinearLayout>
<Button
    android:id="@+id/login"
    android:layout_width="match_parent"
    android:layout_height="60dp"
    android:background="@drawable/shape"
    android:text="Login"
    android:textAllCaps="false"
    android:textColor="#0f0e0e"
    android:textSize="18sp"
    android:textStyle="bold" />
</LinearLayout>

```
## 简言之：(activity_login.xml):
```java
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android">

<include layout="@layout/login_title"/>

<LinearLayout>

</LinearLayout>

<RelativeLayout
    <de.hdodenhof.circleimageview.CircleImageView
        />

    <ImageView
         />

    <Button
       />

    <ImageView
      "/>

    <Button
         />
</RelativeLayout>


<LinearLayout
    
</LinearLayout>


<LinearLayout>

    <TextView/>

    <EditText/>

</LinearLayout>


<LinearLayout>

    <TextView/>

    <EditText"/>

    <ImageView />

</LinearLayout>

<LinearLayout>

    <CheckBox />

    <TextView/>

</LinearLayout>


<LinearLayout
    
</LinearLayout>


<Button />

</LinearLayout>

```

|动态演示|本人|
|:--:|:--:|
|<div align=center><img width="290" height="505" src="https://github.com/wanghao15536870732/MaterialTest/blob/master/app/src/main/res/drawable/show.gif"/></div>|   enem. . .凑合  <div align=center><img width="150" height="150" src="https://github.com/HeTingwei/ReadmeLearn/blob/master/avatar1.jpg"/></div> |

### 还有解决RecyclerView遮住Toolber的问题<br>请访问[博客](http://blog.csdn.net/qq_41005535/article/details/79368989)<br>或着[简书](https://www.jianshu.com/p/257364867831)
#### 点击每一种水果后就会跳转到FruitActivity上面，并且利用<br>可折叠标题栏[<android.support.design.widget.CollapsingToolbarLayout](http://blog.csdn.net/IT_51888_liang/article/details/70214143)<br>卡片式布局[<android.support.design.widget.CoordinatorLayout](http://blog.csdn.net/bigggfish/article/details/53585783)<br>悬浮按钮[<android.support.design.widget.FloatingActionButton](http://blog.csdn.net/chen_xi_hao/article/details/74347023)<br>形成精美的布局页面<br>

## 演示一下：
<div align=center><img width="290" height="505" src="https://github.com/wanghao15536870732/MaterialTest/blob/master/app/src/main/res/drawable/show_1.gif"/></div>

## 对了，还有[如何充分利用手机状态栏空间](http://blog.csdn.net/qq_41005535/article/details/79376481)就像这张图片一样：

<div align=center><img width="290" height="505" src="https://github.com/wanghao15536870732/UIBestPractice/blob/bcd2b99740b0f9a3bbe69e5b96ea5aa592a0dc68/app/src/main/res/drawable/apple.png"/></div>


## 最后完全演示一下：
<div align=center><img width="290" height="505" src="https://github.com/wanghao15536870732/coolweather/blob/7bebae3b164b8f37dbc3b72ccf79ff199bd1c1ba/app/src/main/res/drawable/Fruit.gif"/></div>
