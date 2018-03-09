package com.example.lab.android.nuc.materialtest.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lab.android.nuc.materialtest.Call.Contacts;
import com.example.lab.android.nuc.materialtest.Call.ContactsAdapter;
import com.example.lab.android.nuc.materialtest.R;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class CallActivity extends AppCompatActivity {

    private List<Contacts> contactList = new ArrayList<>();

    ContactsAdapter adapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);


        //建立ListView的实例
        ListView listView = (ListView) findViewById(R.id.contacts_view);
        adapter = new ContactsAdapter(CallActivity.this,
                R.layout.contact_item,contactList);
        listView.setAdapter(adapter);
        initContact();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contacts contacts = contactList.get(position);
//                Toast.makeText(CallActivity.this, contacts.getContactName() + contacts.getContactNumber(), Toast.LENGTH_SHORT).show();
                Intent delIntent = new Intent(Intent.ACTION_DIAL);
                delIntent.setData(Uri.parse("tel:" + contacts.getContactNumber()));
                startActivity(delIntent);
            }
        });
    }

    private void initContact() {
        if (ContextCompat.checkSelfPermission(CallActivity.this, Manifest.permission.
                READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CallActivity.this, new String[]{Manifest.
                    permission.READ_CONTACTS}, 1);
        } else {
            readContacts();
        }
    }

    //查询联系人数据
    private void readContacts(){
        Cursor cursor = null;
        try{
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,null,null,null);
            if (cursor != null){
                while (cursor.moveToNext()){
                    //获取联系人姓名
                    Contacts contacts = new Contacts(R.drawable.mango_pic);
                    String displayName = cursor.getString(cursor.getColumnIndex
                            (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex
                            (ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contacts.setContactName(displayName);
                    contacts.setContactNumber(number);
                    contactList.add(contacts);
                }
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //如果cursor存在
            if (cursor != null){
                cursor.close();
            }
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


//    ArrayAdapter<String> adapter;
//
//    List<String> contactsList = new ArrayList<>();
//
//    Contacts contacts = new Contacts();
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_call);
//
//        //设置ListView的点击事件
//        ListView contactsView = (ListView) findViewById(R.id.contacts_view);
//
//
//        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contactsList);
//        //将adpter设置进去
//        contactsView.setAdapter(adapter);
//
//        //申请电话本的权限
//        if (ContextCompat.checkSelfPermission(CallActivity.this, Manifest.permission.
//                READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(CallActivity.this,new String[]{Manifest.
//                    permission.READ_CONTACTS},1);
//        }else{
//            readContacts();
//        }
//    }
//
//
//    //查询联系人数据
//    private void readContacts(){
//        Cursor cursor = null;
//        try{
//            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                    null,null,null,null);
//            if (cursor != null){
//                while (cursor.moveToNext()){
//                    //获取联系人姓名
//                    String displayName = cursor.getString(cursor.getColumnIndex
//                            (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                    String number = cursor.getString(cursor.getColumnIndex
//                            (ContactsContract.CommonDataKinds.Phone.NUMBER));
//                    contacts.setContactName(displayName);
//                    contacts.setContactNumber(number);
//                    contactsList.add(displayName + "\n" + number);
//                }
//                adapter.notifyDataSetChanged();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            //如果cursor存在
//            if (cursor != null){
//                cursor.close();
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode){
//            case 1:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    readContacts();
//                }else {
//                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            default:
//        }
//    }

}
