package com.Util;

import android.app.Activity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    public static void getUrlConnectJson(Activity aactivity, final String urlString, final OnHttpRepsonLinstener onHttpRepsonLinstener){
        try {
            final URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("accept", "*/*");
            urlConnection.setRequestProperty("connection", "Keep-Alive");
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setUseCaches(false);
            // 发送POST请求必须设置如下两行
//            urlConnection.setDoOutput(true);
//            urlConnection.setDoInput(true);

//            OutputStream outputStream = urlConnection.getOutputStream();
//            String data = action;
//            outputStream.write(data.getBytes());
//            outputStream.flush();
//
//            urlConnection.setReadTimeout(1000);
//            urlConnection.setConnectTimeout(1000);
//            urlConnection.setRequestMethod("GET");
//            urlConnection.setRequestProperty("accept", "*/*");
//            urlConnection.setRequestProperty("connection", "Keep-Alive");
            int code = urlConnection.getResponseCode();
            if (code == 200) {
                InputStream inputStream  = urlConnection.getInputStream();

                final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[128];
                while ((len = inputStream.read(buf)) != -1)	{
                    byteArrayOutputStream.write(buf, 0, len);
                }
                byteArrayOutputStream.flush();
                aactivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onHttpRepsonLinstener.onGetString(byteArrayOutputStream.toString());
                    }
                });

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
