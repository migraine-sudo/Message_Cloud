package com.example.messageboxes_beta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Util.HttpUtil;
import com.Util.OnHttpRepsonLinstener;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
       // Click触发btnRegist
        Button btnRegist = findViewById(R.id.btn_register);
        final EditText etUsername = (EditText) findViewById(R.id.et_user_name);
        final EditText etPassword = findViewById(R.id.et_psw);
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("注册中");
                String username = etUsername.getText().toString();
               String password = etPassword.getText().toString();
              registTX(username, password);
           }
         });
    }

    private void registTX(String username, String password) {
        //final   String url = getString(R.string.ip_config)+"/message/uRegistServlet?username="+username+"&password="+password;
        final String url = getString(R.string.ip_config) + "/MessageBox_Server_beta/userRegist?username=" + username + "&password=" + password;
        //Toast.makeText(LoginActivity.this,"URL="+url,Toast.LENGTH_LONG).show();
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpUtil.getUrlConnectJson(RegistActivity.this, url, new OnHttpRepsonLinstener() {
                            @Override
                            public void onGetString(String json) {
                                try {
                                    //Toast.makeText(LoginActivity.this, "URL=" + url, Toast.LENGTH_LONG).show();
                                    JSONObject jsonObject = new JSONObject(json);
                                    int code = jsonObject.getInt("code");
                                    if (code == 200) {
                                        Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(RegistActivity.this, "注册失败，用户名已经被注册过了", Toast.LENGTH_LONG).show();
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
}
