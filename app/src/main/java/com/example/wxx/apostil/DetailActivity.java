package com.example.wxx.apostil;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView datTextView;
    private TextView contentTextView;
    private Button editButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        titleTextView = (TextView) findViewById(R.id.title_detail);
        datTextView = (TextView) findViewById(R.id.date_detail);
        contentTextView = (TextView) findViewById(R.id.content_detail);
        editButton = (Button) findViewById(R.id.button_edit);

        final Intent intent = getIntent();
        titleTextView.setText(intent.getStringExtra(MyConstants.TITLE));
        datTextView.setText(intent.getStringExtra(MyConstants.DATE));
        contentTextView.setText(intent.getStringExtra(MyConstants.CONTENT));

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(view.getContext(),EditActivity.class);
                i.putExtra(MyConstants.REQUSTFROM,MyConstants.DETAIL);
                i.putExtra(MyConstants.TITLE,intent.getStringExtra(MyConstants.TITLE));
                i.putExtra(MyConstants.DATE,intent.getStringExtra(MyConstants.DATE));
                i.putExtra(MyConstants.CONTENT,intent.getStringExtra(MyConstants.CONTENT));
                i.putExtra(MyConstants.ID,intent.getStringExtra(MyConstants.ID));
                startActivity(i);
            }
        });
    }
}
