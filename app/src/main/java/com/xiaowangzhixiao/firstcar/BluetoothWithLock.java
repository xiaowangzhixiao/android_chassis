package com.xiaowangzhixiao.firstcar;

import android.content.Context;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

/**
 * Created by dell on 2016/12/5.
 */

public class BluetoothWithLock extends BluetoothSPP {
    public BluetoothWithLock(Context context) {
        super(context);
    }

    @Override
    public synchronized void send(String data, boolean CRLF){
        super.send(data,CRLF);
    }

    @Override
    public synchronized void send(byte[] data, boolean CRLF){
        super.send(data,CRLF);
    }
}
