package com.example.wxx.apostil;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.TimeZone;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTitle;
    private EditText editContent;
    private Button saveButton;
    private Button cancleButton;
    private String title;
    private String content;
    private String id;
    private int from;
    private MyPostMethod postMethod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editContent= (EditText) findViewById(R.id.edit_content);
        editTitle= (EditText) findViewById(R.id.edit_title);
        saveButton= (Button) findViewById(R.id.saveBtn);
        cancleButton= (Button) findViewById(R.id.cancleBtn);

        Intent intent =getIntent();
        from = intent.getExtras().getInt(MyConstants.REQUSTFROM);
        title=intent.getStringExtra(MyConstants.TITLE);
        content=intent.getStringExtra(MyConstants.CONTENT);
        if (from==MyConstants.DETAIL){
            editContent.setText(content);
            editTitle.setText(title);
            id=intent.getExtras().getString(MyConstants.ID);
        }
        saveButton.setOnClickListener(this);
        cancleButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancleBtn:
                break;
            case R.id.saveBtn:
                title=editTitle.getText().toString();
                content=editContent.getText().toString();
                postMethod=new MyPostMethod(title,content);
                if(from==MyConstants.MAIN){
                    postMethod.execute(MyConstants.BASE_URL+"add/");
                }else if(from == MyConstants.DETAIL){
                    postMethod.execute(MyConstants.BASE_URL+id+"/edit/");
                }
                break;
        }
        finish();
    }



}
