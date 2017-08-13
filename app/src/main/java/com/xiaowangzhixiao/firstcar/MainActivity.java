package com.xiaowangzhixiao.firstcar;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static BluetoothWithLock bt;
    public static int target = 1;
    public static int launch_now = 0;
    public static int pos_now = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        findViewById(R.id.main_bt_action).setOnClickListener(this);
        findViewById(R.id.main_bt_clear).setOnClickListener(this);
        findViewById(R.id.main_bt_connect).setOnClickListener(this);
        findViewById(R.id.main_bt_reboot).setOnClickListener(this);
        findViewById(R.id.main_bt_send).setOnClickListener(this);
        findViewById(R.id.main_bt_stop).setOnClickListener(this);

        TextView textView = (TextView)findViewById(R.id.main_msg);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        bt = new BluetoothWithLock(this);
        if(!bt.isBluetoothAvailable()) {
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }

        final Thread thread = new Thread(){
            public void run(){
                while(true){
                    if(bt.getServiceState() == BluetoothState.STATE_CONNECTED)
                    {
                        bt.send("pos now",true);
                        System.out.println("send1!");
                        try {
                            sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        thread.start();

        bt.setBluetoothConnectionListener(new BluetoothWithLock.BluetoothConnectionListener() {
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() {
                Toast.makeText(getApplicationContext()
                        , "Connection lost", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() {
                Toast.makeText(getApplicationContext()
                        , "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClick(View v) {
        EditText editText;
        TextView tx_message;
        switch (v.getId()){
            case R.id.main_bt_action:
                startActivity(new Intent(this, ActionActivity.class));
                break;
            case R.id.main_bt_connect:
                if(bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }
                break;
            case R.id.main_bt_send:
                editText = (EditText)findViewById(R.id.main_input);
                bt.send(editText.getText().toString(),true);
                break;
            case R.id.main_bt_home:
                bt.send("launch set pitch 0",true);
                bt.send("launch set roll 0",true);
                bt.send("launch set speed 7.7",true);
                break;
            case R.id.main_bt_stop:
                bt.send("stop",true);
                break;
            case R.id.main_bt_clear:
                tx_message = (TextView)findViewById(R.id.main_msg);
                tx_message.setText("");
                break;
            case R.id.main_bt_reboot:
                bt.send("reboot",true);
                break;
            default:
                break;
        }
    }



    @Override
    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if(!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        bt.setOnDataReceivedListener(new BluetoothWithLock.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                String str = message.replace("\\n","\n");

                System.out.println(str);
                String[] words = str.split(" ");
                String[] word;
                TextView tx_message;
                word = words[0].split(":");
                if (word[0].equals("msg"))
                {
                    tx_message= (TextView)findViewById(R.id.main_msg);
                    tx_message.append(str+'\n');
                    final int scrollAmount = tx_message.getLayout().getLineTop(tx_message.getLineCount()) - tx_message.getHeight();
                    if (scrollAmount > 0)
                        tx_message.scrollTo(0, scrollAmount);
                    else
                        tx_message.scrollTo(0, 0);
                }else if (word[0].equals("x") || word[0].equals("pitch"))
                {
                    for (String word1 : words) {
                        word = word1.split(":");
                        switch (word[0]) {
                            case "x":
                                tx_message = (TextView)findViewById(R.id.main_tv_x);
                                tx_message.setText(String.format("x:%s", word[1]));
                                break;
                            case "y":
                                tx_message = (TextView)findViewById(R.id.main_tv_y);
                                tx_message.setText(String.format("y:%s", word[1]));
                                break;
                            case "pitch":
                                tx_message = (TextView)findViewById(R.id.main_tv_pitch);
                                tx_message.setText(String.format("pitch:%s", word[1]));
                                break;
                            case "roll":
                                tx_message = (TextView)findViewById(R.id.main_tv_roll);
                                tx_message.setText(String.format("roll:%s", word[1]));
                                break;
                            case "speed":
                                tx_message = (TextView)findViewById(R.id.main_tv_speed);
                                tx_message.setText(String.format("speed:%s", word[1]));
                                break;
                            case "yaw":
                                tx_message = (TextView)findViewById(R.id.main_tv_yaw);
                                tx_message.setText(String.format("yaw:%s", word[1]));
                                break;
                        }
                    }
                }else {
                    tx_message = (TextView) findViewById(R.id.main_msg);
                    tx_message.append(str + '\n');
                    final int scrollAmount = tx_message.getLayout().getLineTop(tx_message.getLineCount()) - tx_message.getHeight();
                    if (scrollAmount > 0)
                        tx_message.scrollTo(0, scrollAmount);
                    else
                        tx_message.scrollTo(0, 0);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bt.stopService();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if(resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if(requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if(resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}

