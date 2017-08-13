package com.xiaowangzhixiao.firstcar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActionActivity extends AppCompatActivity implements View.OnClickListener {
    private BluetoothWithLock bt_son;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        bt_son = MainActivity.bt;
        bt_son.setOnDataReceivedListener(new BluetoothWithLock.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                String str = message.replace("\\n","\n");
                System.out.println(str);
                String[] words = str.split(" ");
                String[] word;
                TextView tx_message;
                word = words[0].split(":");
                if (word[0].equals("msg"))
                {
                    Toast.makeText(ActionActivity.this,str,Toast.LENGTH_SHORT).show();
                }else if (word[0].equals("x") || word[0].equals("pitch") || word[0].equals("speedmax"))
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
                            case "speedmax":
                                tx_message = (EditText)findViewById(R.id.act_et_speedmax);
                                tx_message.setText(String.format("%s", word[1]));
                                break;
                            case "movespeed":
                                tx_message = (EditText)findViewById(R.id.act_et_movespeed);
                                tx_message.setText(String.format("%s", word[1]));
                                break;
                            case "moveradium":
                                tx_message = (EditText)findViewById(R.id.act_et_moveradium);
                                tx_message.setText(String.format("%s", word[1]));
                                break;
                            case "angleradium":
                                tx_message = (EditText)findViewById(R.id.act_et_angleradium);
                                tx_message.setText(String.format("%s", word[1]));
                                break;
                            case "anglespeed":
                                tx_message = (EditText)findViewById(R.id.act_et_anglespeed);
                                tx_message.setText(String.format("%s", word[1]));
                                break;
                            case "start":
                                tx_message = (EditText)findViewById(R.id.act_et_start);
                                tx_message.setText(String.format("%s", word[1]));
                                break;
                            case "factor":
                                tx_message = (EditText)findViewById(R.id.act_et_factor);
                                tx_message.setText(String.format("%s", word[1]));
                                break;
                            case "speedmin":
                                tx_message = (EditText)findViewById(R.id.act_et_speedmin);
                                tx_message.setText(String.format("%s", word[1]));
                                break;
                        }
                    }
                }
            }
        });

        findViewById(R.id.act_bt_go).setOnClickListener(this);
        findViewById(R.id.act_bt_pos_add).setOnClickListener(this);
        findViewById(R.id.act_bt_stop).setOnClickListener(this);
        findViewById(R.id.act_bt_movespeed).setOnClickListener(this);
        findViewById(R.id.act_bt_speedmax).setOnClickListener(this);
        findViewById(R.id.act_bt_moveradium).setOnClickListener(this);
        findViewById(R.id.act_bt_angleradium).setOnClickListener(this);
        findViewById(R.id.act_bt_anglespeed).setOnClickListener(this);
        findViewById(R.id.act_bt_start).setOnClickListener(this);
        findViewById(R.id.act_bt_factor).setOnClickListener(this);
        findViewById(R.id.act_bt_speedmin).setOnClickListener(this);
        findViewById(R.id.act_bt_param).setOnClickListener(this);
        findViewById(R.id.act_bt_save).setOnClickListener(this);
        findViewById(R.id.act_bt_start).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String string;
        EditText editText;
        EditText editText1;
        EditText editText2;
        switch (v.getId())
        {
            case R.id.act_bt_go:
                editText = (EditText)findViewById(R.id.act_et_x);
                editText1 = (EditText)findViewById(R.id.act_et_y);
                editText2 = (EditText)findViewById(R.id.act_et_yaw);
                bt_son.send(String.format("action %s %s %s", editText.getText().toString(), editText1.getText().toString(), editText2.getText().toString()),true);
                break;
            case R.id.act_bt_pos_add:
                //暂定
                break;
            case R.id.act_bt_movespeed:
                editText = (EditText)findViewById(R.id.act_et_movespeed);
                bt_son.send(String.format("param movespeed %s",editText.getText().toString()),true);
                break;
            case R.id.act_bt_speedmax:
                editText = (EditText)findViewById(R.id.act_et_speedmax);
                bt_son.send(String.format("param speedmax %s",editText.getText().toString()),true);
                break;
            case R.id.act_bt_moveradium:
                editText = (EditText)findViewById(R.id.act_et_moveradium);
                bt_son.send(String.format("param moveradium %s",editText.getText().toString()),true);
                break;
            case R.id.act_bt_angleradium:
                editText = (EditText)findViewById(R.id.act_et_angleradium);
                bt_son.send(String.format("param angleradium %s",editText.getText().toString()),true);
                break;
            case R.id.act_bt_anglespeed:
                editText = (EditText)findViewById(R.id.act_et_anglespeed);
                bt_son.send(String.format("param anglespeed %s",editText.getText().toString()),true);
                break;
            case R.id.act_bt_start:
                editText = (EditText)findViewById(R.id.act_et_start);
                bt_son.send(String.format("param start %s",editText.getText().toString()),true);
                break;
            case R.id.act_bt_factor:
                editText = (EditText)findViewById(R.id.act_et_factor);
                bt_son.send(String.format("param factor %s",editText.getText().toString()),true);
                break;
            case R.id.act_bt_speedmin:
                editText = (EditText)findViewById(R.id.act_et_speedmin);
                bt_son.send(String.format("param speedmin %s",editText.getText().toString()),true);
                break;
            case R.id.act_bt_param:
                bt_son.send(String.format("param print"),true);
                break;
            case R.id.act_bt_save:
                bt_son.send(String.format("param save"),true);
                break;
            case R.id.act_bt_stop:
                bt_son.send("stop",true);
                break;
        }
    }
}
