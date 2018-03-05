# MaterialTest
## 最佳的UI体验—————Material Design 实战\
|动态演示|本人|
|:--:|:--:|
|<div align=center><img width="290" height="505" src="https://github.com/wanghao15536870732/MaterialTest/blob/master/app/src/main/res/drawable/show.gif"/></div>|   enem. . .凑合  <div align=center><img width="150" height="150" src="https://github.com/HeTingwei/ReadmeLearn/blob/master/avatar1.jpg"/></div> |

### 还有解决RecyclerView遮住Toolber的问题<br>请访问[本人de博客](http://blog.csdn.net/qq_41005535/article/details/79368989)<br>或着[本人de简书](https://www.jianshu.com/p/257364867831)
#### 点击每一种水果后就会跳转到FruitActivity上面，并且利用<br>可折叠标题栏[<android.support.design.widget.CollapsingToolbarLayout](http://blog.csdn.net/IT_51888_liang/article/details/70214143)<br>卡片式布局[<android.support.design.widget.CoordinatorLayout](http://blog.csdn.net/bigggfish/article/details/53585783)<br>悬浮按钮[<android.support.design.widget.FloatingActionButton](http://blog.csdn.net/chen_xi_hao/article/details/74347023)<br>形成精美的布局页面<br>
## 布局代码如下：
```java
<android.support.design.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true">

    <android.support.design.widget.AppBarLayout
        android:id="@+id/appBar"
        android:layout_width="match_parent"
        android:layout_height="250dp"
        android:fitsSystemWindows="true">
        <android.support.design.widget.CollapsingToolbarLayout
            android:id="@+id/collapsing_toolbar"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"
            app:contentScrim="?attr/colorPrimary"
            app:layout_scrollFlags="scroll|exitUntilCollapsed"
            android:fitsSystemWindows="true">

            <ImageView
                android:id="@+id/fruit_image_view"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:scaleType="centerCrop"
                app:layout_collapseMode="parallax"
                android:fitsSystemWindows="true"/>
            <android.support.v7.widget.Toolbar
                android:id="@+id/toolbar"
                android:layout_width="match_parent"
                android:layout_height="?attr/actionBarSize"
                app:layout_collapseMode="pin"/>
        </android.support.design.widget.CollapsingToolbarLayout>
    </android.support.design.widget.AppBarLayout>

    <android.support.v4.widget.NestedScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_behavior="@string/appbar_scrolling_view_behavior">

        <LinearLayout
            android:orientation="vertical"
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

            <android.support.v7.widget.CardView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginBottom="15dp"
                android:layout_marginLeft="15dp"
                android:layout_marginRight="15dp"
                android:layout_marginTop="35dp"
                app:cardCornerRadius="4dp">

            <TextView
                android:id="@+id/fruit_content_text"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_margin="10dp" />

            </android.support.v7.widget.CardView>
        </LinearLayout>
    </android.support.v4.widget.NestedScrollView>

    <android.support.design.widget.FloatingActionButton
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="16dp"
        android:src="@drawable/ic_comment"
        app:layout_anchor="@id/appBar"
        app:layout_anchorGravity="bottom|end"/>


</android.support.design.widget.CoordinatorLayout>
```
## 演示一下：
<div align=center><img width="290" height="505" src="https://github.com/wanghao15536870732/MaterialTest/blob/master/app/src/main/res/drawable/show_1.gif"/></div>

## 对了，还有[如何充分利用手机状态栏空间](http://blog.csdn.net/qq_41005535/article/details/79376481)就像这张图片一样：

<div align=center><img width="290" height="505" src="https://github.com/wanghao15536870732/UIBestPractice/blob/bcd2b99740b0f9a3bbe69e5b96ea5aa592a0dc68/app/src/main/res/drawable/apple.png"/></div>


## 最后完全演示一下：
<div align=center><img width="290" height="505" src="https://github.com/wanghao15536870732/coolweather/blob/7bebae3b164b8f37dbc3b72ccf79ff199bd1c1ba/app/src/main/res/drawable/Fruit.gif"/></div>
