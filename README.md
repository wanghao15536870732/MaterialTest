# MaterialTest
## 最佳的UI体验—————Material Design 实战
## 一个关于水果的应用
|动态演示|本人|
|:--:|:--:|
|<div align=center><img width="290" height="505" src="https://github.com/wanghao15536870732/MaterialTest/blob/master/app/src/main/res/drawable/show.gif"/></div>|   enem. . .凑合  <div align=center><img width="150" height="150" src="https://github.com/HeTingwei/ReadmeLearn/blob/master/avatar1.jpg"/></div> |

### 还有解决RecyclerView遮住Toolber的问题<br>请访问[本人de博客](http://blog.csdn.net/qq_41005535/article/details/79368989)<br>或着[本人de简书](https://www.jianshu.com/p/257364867831)
#### 点击每一种水果后就会跳转到FruitActivity上面，并且利用<br>可折叠标题栏[<android.support.design.widget.CollapsingToolbarLayout](http://blog.csdn.net/IT_51888_liang/article/details/70214143)<br>卡片式布局[<android.support.design.widget.CoordinatorLayout](http://blog.csdn.net/bigggfish/article/details/53585783)<br>悬浮按钮[<android.support.design.widget.FloatingActionButton](http://blog.csdn.net/chen_xi_hao/article/details/74347023)<br>形成精美的布局页面<br>
## 布局代码如下：
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
## 演示一下：
<div align=center><img width="290" height="505" src="https://github.com/wanghao15536870732/MaterialTest/blob/master/app/src/main/res/drawable/show_1.gif"/></div>

## 对了，还有[如何充分利用手机状态栏空间](http://blog.csdn.net/qq_41005535/article/details/79376481)就像这张图片一样：

<div align=center><img width="290" height="505" src="https://github.com/wanghao15536870732/UIBestPractice/blob/bcd2b99740b0f9a3bbe69e5b96ea5aa592a0dc68/app/src/main/res/drawable/apple.png"/></div>


## 最后完全演示一下：
<div align=center><img width="290" height="505" src="https://github.com/wanghao15536870732/coolweather/blob/7bebae3b164b8f37dbc3b72ccf79ff199bd1c1ba/app/src/main/res/drawable/Fruit.gif"/></div>
