package com.example.messageboxes_beta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.Util.HttpUtil;
import com.Util.OnHttpRepsonLinstener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private static final CountDownLatch ctl = new CountDownLatch(10);
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        String usernmae = bundle.getString("username");
        ArrayList<String> msg = new ArrayList<String>();
        msg = bundle.getStringArrayList("msgArray");

        System.out.println("username=" + usernmae);
        listdata.clear();

        for (int i = 0; i < msg.size(); i++) {
            String url = getString(R.string.ip_config) + "/MessageBox_Server_beta//insertMessage?username=" + usernmae + "&message=" + msg.get(i);
            //UploadMeessageTX(url,usernmae);
        }
        Button btnfresh = findViewById(R.id.button_fresh);
        btnfresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> msg = new ArrayList<String>();
                Bundle bundle = getIntent().getExtras();
                getSmsFromPhone();
                msg = MsgArray;
                String usernmae = bundle.getString("username");

                listdata.clear();
                for (int i = 0; i < msg.size(); i++) {
                    String url = getString(R.string.ip_config) + "/MessageBox_Server_beta//insertMessage?username=" + usernmae + "&message=" + msg.get(i);
                    UploadMeessageTX(url,usernmae);
                }
                    readMessageTX(usernmae);


                System.out.println("123");
            }
        });
    }


        //返回登录界面
        @Override
        public boolean onKeyDown ( int keyCode, KeyEvent event){
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent, 1000);
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }


//上传Message
    private void UploadMeessageTX(String URL,String username)
    {

        // listdata.add("Your Message:");
        final String url=URL;
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtil.getUrlConnectJson(MainActivity.this, url, new OnHttpRepsonLinstener() {
                            @Override
                            public void onGetString(String json) {

                                try {
                                    //Toast.makeText(LoginActivity.this, "URL=" + url, Toast.LENGTH_LONG).show();
                                    JSONObject jsonObject = new JSONObject(json);
                                    int code=jsonObject.getInt("code");

                                    if (code == 200) {
                                        Toast.makeText(MainActivity.this,"上传成功",Toast.LENGTH_LONG);

                                    } else {
                                        //Toast.makeText(MainActivity.this, "上传失败", Toast.LENGTH_LONG).show();
                                        System.out.println("上传失败");
                                    }
                                } catch (JSONException e) {

                                    e.printStackTrace();
                                }

                            }
                        }
                );
            }
        }

        ).start();
    }
//读取Message
    List<String> listdata = new ArrayList<String>();
    private void readMessageTX(String username)
    {
        //创建ListView
        listview = (ListView)findViewById(R.id.message_list);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, listdata);//listdata和str均可

        final   String url = getString(R.string.ip_config)+"/MessageBox_Server_beta/readMessage?username="+username;

       // listdata.add("Your Message:");

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtil.getUrlConnectJson(MainActivity.this, url, new OnHttpRepsonLinstener() {
                            @Override
                            public void onGetString(String json) {

                                try {
                                    //Toast.makeText(LoginActivity.this, "URL=" + url, Toast.LENGTH_LONG).show();
                                    JSONObject jsonObject = new JSONObject(json);
                                    //int code=jsonObject.getInt("code");
                                    JSONArray message = jsonObject.getJSONArray("package");
                                    JSONObject packageObject=message.getJSONObject(0);
                                    int code=packageObject.getInt("code");

                                    if (code == 200) {
                                       // Toast.makeText(MainActivity.this, "读取数据库中", Toast.LENGTH_LONG).show();
                                        for (int i=0;i<100;i++)
                                        {
                                            try {
                                                JSONObject packageObjectx=message.getJSONObject(i);
                                                if(packageObject!=null)
                                                    listdata.add(packageObjectx.getString("message"));}
                                            catch (JSONException e){e.printStackTrace();}

                                            //System.out.println("message="+packageObjectx.getString("message"));
                                            //Toast.makeText(MainActivity.this, "message="+packageObjectx.getString("message"), Toast.LENGTH_LONG).show();
                                        }
                                        //显示ListView
                                        arrayAdapter.notifyDataSetChanged();
                                        listview.setAdapter(arrayAdapter);

                                    } else {
                                        Toast.makeText(MainActivity.this, "用户还没有上传数据", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {

                                    e.printStackTrace();
                                }

                            }
                        }
                );
            }
        }

        ).start();

        //listview.deferNotifyDataSetChanged();
    }
    private Uri SMS_INBOX = Uri.parse("content://sms/");

    ArrayList<String> MsgArray=new ArrayList<String>();
    public void getSmsFromPhone() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ContentResolver cr = getContentResolver();
                String[] projection = new String[]{"body"};//"_id", "address", "person",, "date", "type
                //String where = " address = '10690630746185017590' AND date >  " + (System.currentTimeMillis() - 10 * 60 * 1000);
                //String where = " address = '10690630746185017590'";
                //Cursor cur = cr.query(SMS_INBOX, projection, where, null, "date desc");
                Cursor cur = cr.query(SMS_INBOX, projection, null, null, "date desc");
                Log.e("getSmsFromPhone!","sdfasdfas");
                if (null == cur)
                    return;
                while(cur.moveToNext()) {
                    //String number = cur.getString(cur.getColumnIndex("address"));//手机号
                    //String name = cur.getString(cur.getColumnIndex("person"));//联系人姓名列表
                    String body = cur.getString(cur.getColumnIndex("body"));

                    //System.out.println("Body="+body);
                    //这里我是要获取自己短信服务号码中的验证码~~
                    // Pattern pattern = Pattern.compile(" [a-zA-Z0-9]{10}");
                    Pattern pattern=Pattern.compile("[验证]");
                    Matcher matcher = pattern.matcher(body);
                    System.out.println("find match="+body);
                    if (matcher.find()) {
                        System.out.println("find match="+body);
                        MsgArray.add(body);
                        //String res = matcher.group(1);
                        //mobileText.setText(res);
                        //Log.e(
                        //        "GetMsg=" , res);
                    }

                }
                System.out.print("123");

            }
        }

        ).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int i = 0; i < permissions.length; i++) {
                    Log.e("TAG",permissions[i]);
                }
//                    Toast.makeText(this, "授权失败，请在设置中打开文件读写权限", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}

