package com.example.lab.android.nuc.materialtest.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.lab.android.nuc.materialtest.R;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    //调用摄像头选择头像
    private static final int TAKE_PHOTO = 1;

    //调用相册进行选择头像
    private static final int CHOOSE_PHOTO = 2;

    private PopupWindow popupWindow;

    private View popupView;


    private CircleImageView circleImageView;
    //创建Uri对象
    private Uri imageUri;

    //调用摄像头选择头像
    private Button take_picture;
    //调用相册进行选择头像
    private Button take_album;

    private static int FLAG = 1;

    private EditText usernameEdit;
    private EditText emailEdit;

    private Button titleButton;
    private Button homeButton;
    private Button loginButton;


    private TextView usernameText;
    private TextView emailText;

    private ImageView show_password;
    private CheckBox rememberPass;

    //通过SharedPreferences储存实现记住密码功能
    private SharedPreferences pref;

    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //调用摄像头选择头像
        take_picture = (Button) findViewById(R.id.take_picture);
        //调用相册进行选择头像
        take_album = (Button) findViewById(R.id.take_album);

        usernameText = (TextView) findViewById(R.id.username);
        emailText = (TextView) findViewById(R.id.mail);

        //获取了SharePreferences对象
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        usernameEdit = (EditText) findViewById(R.id.username);
        emailEdit = (EditText) findViewById(R.id.mail);

        circleImageView = (CircleImageView) findViewById(R.id.icon_image);
        show_password = (ImageView) findViewById(R.id.mail_Visibility);
        rememberPass = (CheckBox) findViewById(R.id.remember_password);
        titleButton = (Button) findViewById(R.id.title_back_1);
        homeButton = (Button) findViewById(R.id.title_home);
        loginButton = (Button) findViewById(R.id.login);
        titleButton.setOnClickListener(this);
        homeButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        show_password.setOnClickListener(this);
        take_picture.setOnClickListener(this);
        take_album.setOnClickListener(this);

//        if ()

        //调用SharePreferences的getBoolean()方法获取remember_password这个键值，一开始默认的为false
        boolean isRemember = pref.getBoolean("remember_password",false);
        if (isRemember){
            //将账号和密码都设置文本框中
            String username = pref.getString("username","");
            String email = pref.getString("mail","");
            usernameEdit.setText(username);
            emailEdit.setText(email);
            //将CheckBox状态设置为勾选
            rememberPass.setChecked(true);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_1:
                finish();
                break;
            case R.id.title_home:

                break;
            case R.id.login:
                String username = usernameEdit.getText().toString();
                String email = emailEdit.getText().toString();
                editor = pref.edit();
                //检查复选框是否被选中
                if (rememberPass.isChecked()){
                    editor.putBoolean("remember_password",true);
                    editor.putString("username",username);
                    editor.putString("mail",email);
                }else {
                    editor.clear();
                }
                editor.apply();
                Intent intent = new Intent();
                intent.putExtra("username_return",username);
                intent.putExtra("email_return",email);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.mail_Visibility:
                if (FLAG == 1) {
                    show_password.setImageResource(R.drawable.ic_visibility_off);
                    FLAG = 0;
                } else {
                    show_password.setImageResource(R.drawable.ic_visibility);
                    FLAG = 1;
                }
                break;
            case R.id.take_picture:
                //创建File对象,用于储存拍照后的照片
                //getExternalCacheDir()用于得到该照片当前缓存的位置
                File outputImage = new File(getExternalCacheDir(),"take_image.jpg");
                try{
                    if (outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                //如果Android版本高于7.0
                if (Build.VERSION.SDK_INT >= 24){
                    //调用FileProvider的getUriForFile()方法将照片解析成Uri对象
                    imageUri = FileProvider.getUriForFile(LoginActivity.this,
                            "com.example.lab.android.nuc.materialtest",outputImage);
                }else {
                    imageUri = Uri.fromFile(outputImage);
                }

                //启动相机程序
                Intent intent_1 = new Intent("android.media.action.IMAGE_CAPTURE");
                intent_1.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent_1,TAKE_PHOTO);
                break;
            case R.id.take_album:
                //申请储存权限
                if (ContextCompat.checkSelfPermission(LoginActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(LoginActivity.this,new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },2);
                }else{
                    openAlbum();
                }
                break;
            default:
        }
    }

    //通过储存的授权打开相册
    private void openAlbum(){
        //使用Intent调用储存权限
        Intent intent_2 = new Intent("android.intent.action.GET_CONTENT");
        intent_2.setType("image/*");
        startActivityForResult(intent_2,CHOOSE_PHOTO);//打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 2:
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

            //调用相机拍照片作为头像

            case TAKE_PHOTO:
                if (resultCode == RESULT_OK){
                    try{
                        //将照片显示出来
                        Bitmap bitmap_1 = BitmapFactory.decodeStream(getContentResolver().
                                openInputStream(imageUri));
                        circleImageView.setImageBitmap(bitmap_1);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;

            //调用相册选择合适的照片作为头像

            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK){
                    //判断手机版本号
                    if (Build.VERSION.SDK_INT >= 19){
                        //4.4版本以上的使用下面的方法进行处理
                        handleImageOnKitKat(data);
                    }else {
                        //4.4版本以下的使用下面的方法进行处理
                        handleImageBeforeKitkat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    //4.4版本以上,选择相册中的图片不在返回图片真是的Uri了
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
            //如果是Document类型的Uri,则通过document id 进行处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){

            //如果是content类型的Uri,则使用普通方式处理
            imagePath = getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){

            //如果是file类型的Uri,直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    //4.4版本以下的，选择相册的图片返回真实的Uri
    private void handleImageBeforeKitkat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
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
        //返回路径
        return path;
    }

    //该方法将图片显示出来
    private void displayImage(String Imagepath){
        if (Imagepath != null){
            //利用BitmapFactory的decodeFile()方法解析图片
            Bitmap bitmap_2 = BitmapFactory.decodeFile(Imagepath);
            circleImageView.setImageBitmap(bitmap_2);
        }else{
            Toast.makeText(this, "failed to find imagePath", Toast.LENGTH_SHORT).show();
        }
    }
    //    private void showPayPwdDialog() {
//        if (popupWindow == null) {
//            popupView = View.inflate(LoginActivity.this, R.layout.activity_login, null);
//            popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,
//                    WindowManager.LayoutParams.MATCH_PARENT);
//            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//                @Override
//                public void onDismiss() {
//                    BrightnessManager.lightOn(LoginActivity.this);
//                    etPayPwd.setText("");
//                }
//            });
//            popupWindow.setBackgroundDrawable(new BitmapDrawable());
//            popupWindow.setFocusable(true);
//            popupWindow.setOutsideTouchable(true);
//
//            ToggleButton togglePwd = (ToggleButton) popupView.findViewById(R.id.togglePwd);
//            etPayPwd = (EditText) popupView.findViewById(R.id.etPayPwd);
//            togglePwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked) {
//                        //如果选中，显示密码
//                        etPayPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                    } else {
//                        //否则隐藏密码
//                        etPayPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                    }
//                }
//            });
//
//            popupView.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    popupWindow.dismiss();
//                    BrightnessManager.lightOn(MainActivity.this);
//                }
//            });
//
//            popupView.findViewById(R.id.tvConfirm).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String payPwd = etPayPwd.getText().toString();
//                    popupWindow.dismiss();
//                    BrightnessManager.lightOn(MainActivity.this);
//                    if (!TextUtils.isEmpty(payPwd)) {
//                        Toast.makeText(MainActivity.this, "提现请求提交成功！", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//
//        if (popupWindow.isShowing()) {
//            popupWindow.dismiss();
//            BrightnessManager.lightOn(MainActivity.this);
//        }
//        popupWindow.showAtLocation(MainActivity.this.findViewById(R.id.),
//                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//    }
}
