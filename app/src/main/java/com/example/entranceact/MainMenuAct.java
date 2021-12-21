package com.example.entranceact;

import static com.example.entranceact.SignInAct.curentUser;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class MainMenuAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private TextView textViewWelcome, textViewEmptyProjectName, textViewMenuName;
    private EditText edTextProjectKeyCheck, edTextProjectName;
    private String projectKey, projectName, userColor;
    private DatabaseReference dbProject, dbUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#333333"));
        drawerLayout = findViewById(R.id.nav_mainMenu);
        Toolbar toolbar = findViewById(R.id.mainMenuToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_draw_open, R.string.navigation_draw_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigationDrawer_viewMainMenu);
        navigationView.setNavigationItemSelectedListener(this);

        Init();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.connectToProject:
                Intent intentConnectToProj = new Intent(this,ConnectToProjectAct.class);
                startActivity(intentConnectToProj);
                break;

            case R.id.recetnProjects:
                Intent intentRecentProj = new Intent(this,RecentProjectsAct.class);
                startActivity(intentRecentProj);
                break;

            case R.id.logOut:
                Intent intentLogOut = new Intent(this,SignInAct.class);
                startActivity(intentLogOut);
                break;

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }


    public void Init(){
        textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewEmptyProjectName = findViewById(R.id.textViewEmptyProjectName);
        edTextProjectKeyCheck = findViewById(R.id.edTextProjectKeyCheck);
        edTextProjectName = findViewById(R.id.edTextProjectName);
        textViewWelcome.setText("Добро пожаловать, " + curentUser.curentName + ", снова");
        dbProject = FirebaseDatabase.getInstance().getReference(Const.DB_PROJECT_REF);
        dbUsers = FirebaseDatabase.getInstance().getReference(Const.DB_USERS_REF);
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
                        projectKey = makeProjectKey(projectName,curentUser.curentLog);
                        ProjectUsers NewProjectUser = new ProjectUsers(curentUser.curentLog, curentUser.curentName, userColor);
                        ProjectInfo NewProjectInfo = new ProjectInfo(projectName, projectKey, curentUser.curentLog);
                        UserProject NewUserProject = new UserProject(projectKey, projectName);
                        dbProject.child(projectKey).child("UserInDesk").child(curentUser.curentLog).setValue(NewProjectUser);
                        dbProject.child(projectKey).child("ProjectInfo").setValue(NewProjectInfo);
                        dbUsers.child(curentUser.curentLog).child("userProjects").child(projectKey).setValue(NewUserProject);
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
            dbProject.child(makeProjectKey(projectName,curentUser.curentLog)).addListenerForSingleValueEvent(vList);
        }

        else{
            textViewEmptyProjectName.setTextColor(Color.parseColor("#FF0000"));
            textViewEmptyProjectName.setText("Введите название проекта!");
        }
    }

    public String makeProjectKey(String projName, String urLogin){
        String key = Encrypting.sha256(projName+urLogin);
        return key;
    }

    public String genColor(){
        Random random = new Random();
        int color = Color.argb(255, random.nextInt(256),random.nextInt(256),random.nextInt(256));
        String ress = String.valueOf(color);
        return ress;


    }

}