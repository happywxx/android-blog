package com.example.wxx.apostil;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wxx on 2017/3/5.
 */

public class MyPostMethod extends AsyncTask<String,Void,String> {

    private String title;
    private String content;

    public MyPostMethod(String title,String content){
        this.title=title;
        this.content=content;
    }
    @Override
    protected String doInBackground(String... strings) {
        String urlString=strings[0];
        HttpURLConnection urlConnection=null;
        URL url;
        try {
            url=new URL(urlString);


            /**
             * 建立URLConnection,并设置相关属性
             */
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("User-Agent",System.getProperty("http.agent"));
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(false);

            /**
             * 准备相关数据，此处为JSON数据，其他类型的数据与之类似
             */
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(MyConstants.TITLE, title);
            jsonObject.put(MyConstants.CONTENT, content);

            /**
             * 发送数据
             */
            OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
            writer.write(jsonObject.toString());
            writer.flush();
            writer.close();

            Log.i("JSONString",title+content);
            /**
             * 建立连接
             */
            urlConnection.connect();

            /**
             * 处理响应
             */
            InputStream inputStream;

            if (urlConnection.getResponseCode()<HttpURLConnection.HTTP_BAD_REQUEST){
                inputStream=urlConnection.getInputStream();
            }else {
                inputStream=urlConnection.getErrorStream();
            }

            String tmp,response="";
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));

            while ((tmp=bufferedReader.readLine())!=null){
                response+=tmp;
            }

            inputStream.close();
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }finally {
            if (urlConnection!=null){
                urlConnection.disconnect();
            }

        }

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Log.e("Server_Response",s);

    }
}
