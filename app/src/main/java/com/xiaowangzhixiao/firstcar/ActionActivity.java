package com.xiaowangzhixiao.firstcar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

public class ActionActivity extends AppCompatActivity implements View.OnClickListener {
    private BluetoothSPP bt_son;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        bt_son = MainActivity.bt;
        bt_son.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                String str = message.replace("\\n","\n");
                String[] words = str.split(" ");
                String[] word;
                TextView tx_message;
                word = words[0].split(":");
                if (word[0].equals("msg"))
                {
                    Toast.makeText(ActionActivity.this,str,Toast.LENGTH_SHORT).show();
                }else if (word[0].equals("x") || word[0].equals("pitch"))
                {
                    for (String word1 : words) {
                        word = word1.split(":");
                        switch (word[0]) {
                            case "x":
                                tx_message = (TextView)findViewById(R.id.act_tv_x);
                                tx_message.setText(String.format("x:%s", word[1]));
                                break;
                            case "y":
                                tx_message = (TextView)findViewById(R.id.act_tv_y);
                                tx_message.setText(String.format("y:%s", word[1]));
                                break;
                            case "pitch":
                                tx_message = (TextView)findViewById(R.id.act_tv_pitch);
                                tx_message.setText(String.format("pitch:%s", word[1]));
                                break;
                            case "roll":
                                tx_message = (TextView)findViewById(R.id.act_tv_roll);
                                tx_message.setText(String.format("roll:%s", word[1]));
                                break;
                            case "speed":
                                tx_message = (TextView)findViewById(R.id.act_tv_speed);
                                tx_message.setText(String.format("speed:%s", word[1]));
                                break;
                            case "yaw":
                                tx_message = (TextView)findViewById(R.id.act_tv_yaw);
                                tx_message.setText(String.format("yaw:%s", word[1]));
                                break;
                        }
                    }
                }
            }
        });

        findViewById(R.id.act_bt_go).setOnClickListener(this);
        findViewById(R.id.act_bt_launch_add).setOnClickListener(this);
        findViewById(R.id.act_bt_left).setOnClickListener(this);
        findViewById(R.id.act_bt_pos_add).setOnClickListener(this);
        findViewById(R.id.act_bt_right).setOnClickListener(this);
        findViewById(R.id.act_bt_set).setOnClickListener(this);
        findViewById(R.id.act_bt_stop).setOnClickListener(this);
        findViewById(R.id.act_bt_target).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String string;
        EditText editText;
        EditText editText1;
        switch (v.getId())
        {
            case R.id.act_bt_go:
                editText = (EditText)findViewById(R.id.act_et_x);
                editText1 = (EditText)findViewById(R.id.act_et_y);
                bt_son.send(String.format("action %s %s 300", editText.getText().toString(), editText1.getText().toString()),true);
                break;
            case R.id.act_bt_launch_add:
                break;
            case R.id.act_bt_left:
                final Thread thread = new Thread(){
                    @Override
                    public void run() {
                        for (int i=0;i<5;i++){
                            bt_son.send("action left",true);
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                thread.start();
                break;
            case R.id.act_bt_pos_add:
                break;
            case R.id.act_bt_set:
                break;
            case R.id.act_bt_right:
                final Thread thread1 = new Thread(){
                    @Override
                    public void run() {
                        for (int i=0;i<5;i++){
                            bt_son.send("action right",true);
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                thread1.start();
                break;
            case R.id.act_bt_stop:
                bt_son.send("stop",true);
                break;
            case R.id.act_bt_target:
                break;
        }
    }
}
