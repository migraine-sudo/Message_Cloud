package com.example.messageboxes_beta;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       /* Bundle bundle = intent.getExtras();
        SmsMessage msg = null;
        System.out.println("Receive Something");
        if (null != bundle) {
            Object[] smsObj = (Object[]) bundle.get("pdus");
            for (Object object : smsObj) {
                msg = SmsMessage.createFromPdu((byte[]) object);
                Date date = new Date(msg.getTimestampMillis());//时间
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String receiveTime = format.format(date);
                System.out.println("number:" + msg.getOriginatingAddress() + "   body:" + msg.getDisplayMessageBody() + "  time:" + msg.getTimestampMillis());
                //在这里写自己的逻辑
                if (msg.getOriginatingAddress().equals("10086")) {
                    System.out.println("receive message="+msg.getDisplayMessageBody());
                    //TODO
                }
            }
        }*/
    }

}
