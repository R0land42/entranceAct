package com.example.entranceact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class DeskAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private String curentProjetKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desk);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#333333"));

        Intent intent = getIntent();
        curentProjetKey = intent.getStringExtra(Const.CURENT_PROJECT_KEY);

        drawerLayout = findViewById(R.id.nav_projDesk);
        Toolbar toolbar = findViewById(R.id.projDeskToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_draw_open, R.string.navigation_draw_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigationDrawer_viewProjDesk);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.currentChat:
                Intent intentChat = new Intent(this, ChatAct.class);
                intentChat.putExtra(Const.CURENT_PROJECT_KEY, curentProjetKey);
                startActivity(intentChat);
                break;

            case R.id.goToMainMenu:
                Intent intentMainMenu = new Intent(this, MainMenuAct.class);
                startActivity(intentMainMenu);
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
}