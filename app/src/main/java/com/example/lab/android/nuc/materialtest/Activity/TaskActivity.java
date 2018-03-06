package com.example.lab.android.nuc.materialtest.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lab.android.nuc.materialtest.R;
import com.example.lab.android.nuc.materialtest.Task.Msg;
import com.example.lab.android.nuc.materialtest.Task.MsgAdapter;

import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity {

    private List<Msg> msgList = new ArrayList<>();

    //建立发送的所有实例
    private Button send;
    private EditText inputText;

    //建立接受的实例
    private Button received;
    private EditText outputText;

    private RecyclerView msgrecyclerView;

    private MsgAdapter adapter;

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        //初始化消息数据
        initMags();
        received = (Button) findViewById(R.id.received);
        outputText = (EditText) findViewById(R.id.output_text);

        send = (Button) findViewById(R.id.send);
        inputText = (EditText)findViewById(R.id.input_text);

        backButton = (Button) findViewById(R.id.title_back);

        msgrecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        msgrecyclerView.setLayoutManager(layoutManager);

        adapter = new MsgAdapter(msgList);

        msgrecyclerView.setAdapter(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content_1 = inputText.getText().toString();
                //判断一下,输入是否为空
                if (!"".equals(content_1)){
                    Msg msg_1 = new Msg(content_1,Msg.TYRE_SENT);

                    msgList.add(msg_1);

                    //当有新消息时，通知列表有新消息产生,并刷新列表中的数据
                    adapter.notifyItemInserted(msgList.size() - 1);

                    //将ListView定位到最后一行
                    msgrecyclerView.scrollToPosition(msgList.size() - 1);

                    //发完消息后，清空输入栏
                    inputText.setText("");
                }
            }
        });

        received.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content_2 = outputText.getText().toString();
                if (!"".equals(content_2)){
                    Msg msg_2 = new Msg(content_2,Msg.TYPE_RECEIVED);

                    msgList.add(msg_2);

                    adapter.notifyItemInserted(msgList.size() - 1);

                    msgrecyclerView.scrollToPosition(msgList.size() - 1);

                    outputText.setText("");
                }
            }
        });


    }


    //在没有开始发送消息前定义三个初始消息
    private void initMags(){
        Msg msg1 = new Msg("你好！",Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("你好！请问你是。。。？",Msg.TYRE_SENT);
        msgList.add(msg2);
        Msg msg3 = new Msg("我是小明,很高兴见到你.",Msg.TYPE_RECEIVED);
        msgList.add(msg3);

    }
}
