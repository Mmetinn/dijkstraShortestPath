package com.example.mmetin.shortestpath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
    public void clicked(View v){
        Intent i = new Intent(Main2Activity.this,MainActivity.class);
        startActivity(i);
    }
}
