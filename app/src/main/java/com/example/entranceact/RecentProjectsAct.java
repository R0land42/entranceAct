package com.example.entranceact;

import static com.example.entranceact.SignInAct.curentUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecentProjectsAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ArrayAdapter<String> arrayAdapter;
    private ListView listViewRecentProjects;
    private List<String> listRecetnProjectName, listRecetnProjectKey;
    private DatabaseReference dbUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_projects);

        drawerLayout = findViewById(R.id.nav_recentProj);
        Toolbar toolbar = findViewById(R.id.recentProjToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_draw_open, R.string.navigation_draw_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigationDrawer_viewRecentProj);
        navigationView.setNavigationItemSelectedListener(this);

        init();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.createNewProject:
                Intent intentMainMenu = new Intent(this,MainMenuAct.class);
                startActivity(intentMainMenu);
                break;

            case R.id.connectToProject:
                Intent intentConnectToProj = new Intent(this,ConnectToProjectAct.class);
                startActivity(intentConnectToProj);
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

    public void init(){
        listViewRecentProjects = findViewById(R.id.listViewRecentProjects);
        listRecetnProjectName = new ArrayList<>();
        listRecetnProjectKey = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listRecetnProjectName);
        listViewRecentProjects.setAdapter(arrayAdapter);
        dbUsers = FirebaseDatabase.getInstance().getReference(Const.DB_USERS_REF);
        getRecetnProjects();
        openRecentProject();

    }

    public void getRecetnProjects (){
        ValueEventListener vList = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (listRecetnProjectName.size() > 0){
                    listRecetnProjectName.clear();
                }if (listRecetnProjectKey.size() > 0){
                    listRecetnProjectKey.clear();
                }
                for (DataSnapshot ds : snapshot.getChildren()){
                    UserProject userProject = ds.getValue(UserProject.class);
                    String projKey = userProject.projectKey;
                    String projName = userProject.projectName;
                    assert projName != null;
                    listRecetnProjectName.add(projName);
                    listRecetnProjectKey.add(projKey);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        dbUsers.child(curentUser.curentLog).child("userProjects").addListenerForSingleValueEvent(vList);
    }

    public void openRecentProject(){
        listViewRecentProjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemProject = listRecetnProjectKey.get(position);
                Intent intent = new Intent(RecentProjectsAct.this, ChatAct.class);
                intent.putExtra(Const.CURENT_PROJECT_KEY, itemProject);
                startActivity(intent);
            }
        });





    }

}
