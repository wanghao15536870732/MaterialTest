package com.example.lab.android.nuc.materialtest.Activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab.android.nuc.materialtest.Call.Contacts;
import com.example.lab.android.nuc.materialtest.Call.SlideCutListView;
import com.example.lab.android.nuc.materialtest.R;

import java.util.ArrayList;
import java.util.List;

public class FriendActivity extends AppCompatActivity implements View.OnClickListener, SlideCutListView.RemoveListener {


    private static final String URI_SMS_LOG = "content://sms/";

    private SlideCutListView slideCutListView;
    
    private ArrayAdapter<String> adapter;
    
    private List<String> dataSourceList = new ArrayList<String>();

    private List<Contacts> dataSourceList_1 = new ArrayList<Contacts>();
    private Button change_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
//        change_view = (Button) findViewById(R.id.diaplay_apk);
//        //点击切换头像按钮调用相册选图片进行显示
//        change_view.setOnClickListener(this);

//        readSmslog();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS},1);
        }else {
            readContacts();
        }
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.diaplay_apk:
//                String fileName = AutoInstall.getdoanloadUrl().substring(AutoInstall.getdoanloadUrl().lastIndexOf("/"));
//                String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
//                AutoInstall.setUrl(directory + fileName);
//                AutoInstall.install(FriendActivity.this);
//        }
    }
    
    //查询联系人数据
    private void readContacts(){
        Cursor cursor_1 = null;
        Cursor cursor_2 = null;
        slideCutListView = (SlideCutListView) findViewById(R.id.slideCutListView);
        slideCutListView.setRemoveListener(this);
        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.list_item,dataSourceList);
        slideCutListView.setAdapter(adapter);
        try{
            cursor_1 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,null,null,null);
            cursor_2 = getContentResolver().query(Uri.parse(URI_SMS_LOG),new String[]{"_id", "address","person", "body", "date", "type"},
                    null, null, "date desc");
            if (cursor_1 != null){
                while (cursor_1.moveToNext()){
                    //获取联系人姓名
                    Contacts contacts = new Contacts(R.drawable.strawberry_pic);
                    String displayName = cursor_1.getString(cursor_1.getColumnIndex
                            (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor_1.getString(cursor_1.getColumnIndex
                            (ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contacts.setContactName(displayName);
                    contacts.setContactNumber(number);
                    dataSourceList.add(displayName + "\n" + number);
                    dataSourceList_1.add(contacts);
                }
                adapter.notifyDataSetChanged();
            }
            if (cursor_2 != null){
                while (cursor_2.moveToNext()){
                    //获取联系人id
                    String id = cursor_2.getString(cursor_2.getColumnIndex("_id"));

                    String address = cursor_2.getString(cursor_2.getColumnIndex("address"));

                    //获取联系人手机号

                    String name = cursor_2.getString(cursor_2.getColumnIndex("person"));


                    //获取短信内容
                    String body = cursor_2.getColumnName(cursor_2.getColumnIndex("body"));
                    //获取日期
                    String date = cursor_2.getColumnName(cursor_2.getColumnIndex("date"));
                    String type = cursor_2.getColumnName(cursor_2.getColumnIndex("type"));
                    //判断消息类型
                    String sms_type = "0".equals(type)?"待发消息":("1".equals(type)?"接受的消息":"已发送的消息");
                    dataSourceList.add("短信Id:" + id +  "发件人:" + name + "发件人电话:" + address + "信息内容:"
                            + body + "信息日期:" + date + "短信读取状态:" + sms_type);
                }
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //如果cursor存在
            if (cursor_1 != null){
                cursor_1.close();
            }
        }
        slideCutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contacts contacts = dataSourceList_1.get(position);
                Toast.makeText(FriendActivity.this,contacts.getContactName() + "\n" + contacts.getContactNumber(), Toast.LENGTH_SHORT).show();
            }
        });
        
    }

    private void readSmslog(){
        Cursor cursor_2 = null;
        try{
            cursor_2 = getContentResolver().query(Uri.parse(URI_SMS_LOG),new String[]{"_id", "address", "body", "date", "type"},
                    null, null, "_id desc limit 1");
            if (cursor_2 != null){
                cursor_2.moveToFirst();
                if (cursor_2.getCount() > 0){
                    String id = cursor_2.getString(cursor_2.getColumnIndex("_id"));
                    String address = cursor_2.getString(cursor_2.getColumnIndex("address"));
                    String body = cursor_2.getColumnName(cursor_2.getColumnIndex("body"));
                    String date = cursor_2.getColumnName(cursor_2.getColumnIndex("date"));
                    String type = cursor_2.getColumnName(cursor_2.getColumnIndex("type"));
                    String sms_type = "0".equals(type)?"待发消息":("1".equals(type)?"接受的消息":"已发送的消息");
                    dataSourceList.add(id+ ":" + address + ":" + body + ":" + date + ":" + sms_type);
                }
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor_2 != null){
                cursor_2.close();
            }
        }
    }

    @Override
    public void removeItem(SlideCutListView.RemoveDirection direction, int position) {
        Contacts contacts_6 = dataSourceList_1.get(position);
        adapter.remove(adapter.getItem(position));
        switch (direction){
            case RIGHT:
                Toast.makeText(this, "向右删除了联系人：" + contacts_6.getContactName(), Toast.LENGTH_SHORT).show();
                break;
            case LEFT:
                Toast.makeText(this, "向左删除了电话号码：" + contacts_6.getContactNumber(), Toast.LENGTH_SHORT).show();
                break;
            default:
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    readContacts();
                }else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
    
    //获取所有动态添加的Item,找到控件的id,获取数据
    private void printData(){
        for (int i = 0; i < slideCutListView.getChildCount(); i++) {
            View childAt = slideCutListView.getChildAt(i);
        }
    }
}
