package com.example.entranceact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {
    private TextView textViewWelcome;
    private String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#333333"));
        init();
    }

    public void init(){
        textViewWelcome = findViewById(R.id.textViewWelcome);
        Intent intent = getIntent();
        userName = intent.getStringExtra(Const.USER_NAME);
        textViewWelcome.setText("Добро пожаловать, " + userName);
    }




}