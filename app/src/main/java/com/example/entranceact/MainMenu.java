package com.example.entranceact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
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
    private TextView textViewWelcome, textViewEmptyProjectName;
    private EditText edTextProjectKeyCheck, edTextProjectName, edTextProjectKey;
    private String userName, userLogin, userPassword, userEmail, projectKey, projectName, userColor;
    private DatabaseReference dbProject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#333333"));
        Init();
    }

    public void Init(){
        textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewEmptyProjectName = findViewById(R.id.textViewEmptyProjectName);
        edTextProjectKey = findViewById(R.id.edTextProjectKey);
        edTextProjectKeyCheck = findViewById(R.id.edTextProjectKeyCheck);
        //edTextProjectKeyCheck.setRawInputType(0x00000000);
        edTextProjectName = findViewById(R.id.edTextProjectName);
        Intent intent = getIntent();
        userName = intent.getStringExtra(Const.USER_NAME);
        userLogin = intent.getStringExtra(Const.USER_LOGIN);
        userPassword = intent.getStringExtra(Const.USER_PASSWORD);
        userEmail = intent.getStringExtra(Const.USER_EMAIL);
        textViewWelcome.setText("Добро пожаловать, " + userName);
        dbProject = FirebaseDatabase.getInstance().getReference(Const.DB_PROJECT_REF);
    }


    public void onClickStartNewProject(View view){
        projectName = edTextProjectName.getText().toString();
        if (!TextUtils.isEmpty(projectName)){
            ValueEventListener vList = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Boolean chkProjectName = false;
                    for(DataSnapshot ds : snapshot.getChildren()){
                        ProjectInfo chekLogAndProjName = ds.getValue(ProjectInfo.class);
                       // String logChk = chekLogAndProjName.projectCreator;
                        String projNameChk = chekLogAndProjName.projectName;
                        if (projectName.equals(projNameChk)){
                            chkProjectName = true;
                            break;
                        }
                        else{
                            chkProjectName = false;
                        }

                    }

                    if(chkProjectName == false){
                        userColor = genColor();
                        projectKey = makeProjectKey(projectName,userLogin);
                        ProjectUsers NewProjectUser = new ProjectUsers(userLogin, userName, userPassword,
                                userEmail, userColor);
                        dbProject.child(projectKey).child("UserInDesk").child(userLogin).setValue(NewProjectUser);
                        ProjectInfo NewProjectInfo = new ProjectInfo(projectName, projectKey, userLogin);
                        dbProject.child(projectKey).child("ProjectInfo").setValue(NewProjectInfo);
                        edTextProjectKeyCheck.setText(projectKey);
                        textViewEmptyProjectName.setText("");
                    }
                    else{
                        textViewEmptyProjectName.setTextColor(Color.parseColor("#FF0000"));
                        textViewEmptyProjectName.setText("У Вас уже имеется такой проект!");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            dbProject.child(makeProjectKey(projectName,userLogin)).addListenerForSingleValueEvent(vList);
        }

        else{
            textViewEmptyProjectName.setTextColor(Color.parseColor("#FF0000"));
            textViewEmptyProjectName.setText("Введите название проекта!");
        }

    }

    public void onClickConnectToProject(View view){
        Intent intent = new Intent(MainMenu.this, CheckAct.class);
        startActivity(intent);
    }

    public String makeProjectKey(String projectName, String urLogin){
    String key = Encrypting.sha256(projectName+urLogin);
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
        dbProject.addListenerForSingleValueEvent(vlList);
    }



}