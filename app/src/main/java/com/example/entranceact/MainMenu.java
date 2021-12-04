package com.example.entranceact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class MainMenu extends AppCompatActivity {
    private TextView textViewWelcome;
    private EditText edTextProjectKeyCheck, edTextProjectName, edTextProjectKey;
    private String userName, userLogin, userPassword, userEmail, projectKey, projectName, userColor;
    private DatabaseReference dbProjectUsersIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textViewWelcome = findViewById(R.id.textViewWelcome);
        edTextProjectKey = findViewById(R.id.edTextProjectKey);
        edTextProjectKeyCheck = findViewById(R.id.edTextProjectKeyCheck);
        edTextProjectName = findViewById(R.id.edTextProjectName);
        Intent intent = getIntent();
        userName = intent.getStringExtra(Const.USER_NAME);
        userLogin = intent.getStringExtra(Const.USER_LOGIN);
        userPassword = intent.getStringExtra(Const.USER_PASSWORD);
        userEmail = intent.getStringExtra(Const.USER_EMAIL);
        textViewWelcome.setText("Добро пожаловать, " + userName);
        dbProjectUsersIn = FirebaseDatabase.getInstance().getReference(Const.DB_PROJECT_REF);
        setContentView(R.layout.activity_main_menu);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setStatusBarColor(Color.parseColor("#333333"));
    }

    public void onClickStartNewProject(View view){
        projectName = edTextProjectName.getText().toString();
        userColor = genColor();
        projectKey = makeProjectKey(projectName,userName);
        ProjectUsers NewProjectUser = new ProjectUsers(userLogin, userName, userPassword,
                userEmail, projectKey, userColor);
        dbProjectUsersIn.child(projectKey).child("UserInDesk").child(userLogin).setValue(NewProjectUser);

        edTextProjectKeyCheck.setText(projectKey);
        int color= Integer.parseInt(userColor);
        textViewWelcome.setTextColor(color);

    }

    public void onClickConnectToProject(View view){

    }

    public String makeProjectKey(String projectName, String userName){
    String key = projectName+userName;
        return key;
    }


    public String genColor(){
        Random random = new Random();
        int color = Color.argb(255, random.nextInt(256),random.nextInt(256),random.nextInt(256));
        String ress;
        return ress = String.valueOf(color);


    }

    public void checkColor(String userColor){
        ValueEventListener vlList = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }



}