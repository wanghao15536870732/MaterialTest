package com.example.lab.android.nuc.materialtest.Activity;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lab.android.nuc.materialtest.R;

public class Add_Contacts_Activity extends AppCompatActivity implements View.OnClickListener{

    private Button contactButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__contacts_);
        contactButton = (Button) findViewById(R.id.contacts);
        contactButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contacts:
                Intent contacts_intent = new Intent(Intent.ACTION_PICK);
                contacts_intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                startActivityForResult(contacts_intent, 1);
                break;
            default:
        }
    }
}
