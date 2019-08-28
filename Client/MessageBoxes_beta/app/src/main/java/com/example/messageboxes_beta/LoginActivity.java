package com.example.messageboxes_beta;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import org.json.*;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.Util.HttpUtil;
import com.Util.OnHttpRepsonLinstener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //从 xml获取输入框和Button句柄

        final EditText etUsername = (EditText) findViewById(R.id.et_username);
        final EditText etPassword = findViewById(R.id.et_password);
        TextView tv_register = (TextView) findViewById(R.id.tv_register);
       // Button btnRegist = findViewById(R.id.btn_regist);

        Button btnLogin = findViewById(R.id.btn_login);
        //立即注册控件的点击事件
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //为了跳转到注册界面，并实现注册功能
                Intent intent=new Intent(LoginActivity.this,RegistActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        //Click触发btnRegist
      //  btnRegist.setOnClickListener(new View.OnClickListener() {
       //     @Override
      //      public void onClick(View v) {
        //        String username = etUsername.getText().toString();
         //       String password = etPassword.getText().toString();
          //      registTX(username, password);
         //   }
       // });
        //Click触发btnLogin
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginTX(username, password);
            }
        });
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.READ_CONTACTS);
//            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED){
//                requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS},REQUEST_CODE_ASK_PERMISSIONS);
//                return;
//            }
//        }
//        verifyStoragePermissions();
        //获取短信提取权限
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, REQUEST_CODE_ASK_PERMISSIONS);
        getSmsFromPhone();
//        Log.e("TAG",checkSelfPermission(Manifest.permission.READ_SMS)+"");


    }


    private void verifyStoragePermissions() {
        if (
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) { // 申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    /**
     * 用户登录方法
     *
     * @param username
     * @param password
     */
    private void loginTX(final String username, String password) {
        final String url = getString(R.string.ip_config) + "/MessageBox_Server_beta/userLogin?username=" + username + "&password=" + password;
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpUtil.getUrlConnectJson(LoginActivity.this, url, new OnHttpRepsonLinstener() {
                            @Override
                            public void onGetString(String json) {
                                try {
                                    //Toast.makeText(LoginActivity.this, "URL=" + url, Toast.LENGTH_LONG).show();
                                    JSONObject jsonObject = new JSONObject(json);
                                    int code = jsonObject.getInt("code");
                                    //String id=jsonObject.getString("id");
                                    if (code == 200) {
                                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                                        //跳转到主界面
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.putExtra("username", username);
                                        intent.putExtra("msgArray",MsgArray);
                                        startActivityForResult(intent, 1000);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "用户名或者密码错误", Toast.LENGTH_LONG).show();
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

    /**
     * 用户注册方法
     *
     * @param username
     * @param password
     */

    private void registTX(String username, String password) {
        //final   String url = getString(R.string.ip_config)+"/message/uRegistServlet?username="+username+"&password="+password;
        final String url = getString(R.string.ip_config) + "/MessageBox_Server_beta/userRegist?username=" + username + "&password=" + password;
        //Toast.makeText(LoginActivity.this,"URL="+url,Toast.LENGTH_LONG).show();
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpUtil.getUrlConnectJson(LoginActivity.this, url, new OnHttpRepsonLinstener() {
                            @Override
                            public void onGetString(String json) {
                                try {
                                    //Toast.makeText(LoginActivity.this, "URL=" + url, Toast.LENGTH_LONG).show();
                                    JSONObject jsonObject = new JSONObject(json);
                                    int code = jsonObject.getInt("code");
                                    if (code == 200) {
                                        Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "注册失败，用户名已经被注册过了", Toast.LENGTH_LONG).show();
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