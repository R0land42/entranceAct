package com.example.entranceact;

import static com.example.entranceact.SignInAct.curentUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private EditText edTextMessage;
    private DatabaseReference dbChat;
    private String curentProjetKey;
    private ArrayList<String> messages  = new ArrayList<>();
    private ArrayList<String> userNames  = new ArrayList<>();
    private ArrayList<String> timeMessages  = new ArrayList<>();
    private RecyclerView reycleViewMessages;
    private DrawerLayout drawerLayout;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#333333"));



        Init();
        drawerLayout = findViewById(R.id.nav_Chat);
        Toolbar toolbar = findViewById(R.id.ChatToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_draw_open, R.string.navigation_draw_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigationDrawer_viewChat);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.currentDesk:
                Intent intentChat = new Intent(this, DeskAct.class);
                intentChat.putExtra(Const.CURENT_PROJECT_KEY, curentProjetKey);
                startActivity(intentChat);
                break;

            case R.id.goToMainMenu:
                Intent intentMainMenu = new Intent(this, MainMenuAct.class);
                startActivity(intentMainMenu);
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
        Intent intent = getIntent();
        curentProjetKey = intent.getStringExtra(Const.CURENT_PROJECT_KEY);
        edTextMessage = findViewById(R.id.edTextMessage);
        dbChat = FirebaseDatabase.getInstance().getReference(Const.DB_PROJECT_REF);




        reycleViewMessages = findViewById(R.id.reycleViewMessages);
        reycleViewMessages.setLayoutManager(new LinearLayoutManager(this));
        DataAdapterForMessages dataAdapterForMessages = new DataAdapterForMessages(this, messages, userNames, timeMessages);
        reycleViewMessages.setAdapter(dataAdapterForMessages);
        dbChat.child(curentProjetKey).child("ProjectZChat").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                MessageInfo msgInfo = snapshot.getValue(MessageInfo.class);
                messages.add(msgInfo.msg);
                userNames.add(msgInfo.name);
                timeMessages.add(msgInfo.time);
                dataAdapterForMessages.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void onClickSendMessage(View view){
        String msg = edTextMessage.getText().toString();
        Date currentDate = new Date();
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String time = timeFormat.format(currentDate);
        String name = curentUser.curentName;
        MessageInfo messageInfo = new MessageInfo(msg, name, time);

        if (TextUtils.isEmpty(msg)){
            Toast.makeText(this, "Введите сообщение", Toast.LENGTH_SHORT).show();
            return;
        }
        if (msg.length() == Const.MAX_MSG_LENGTH){
            Toast.makeText(this, "Слишком больше сообщение", Toast.LENGTH_SHORT).show();
            return;
        }
        dbChat.child(curentProjetKey).child("ProjectZChat").push().setValue(messageInfo);
        edTextMessage.setText("");
        reycleViewMessages.smoothScrollToPosition(messages.size());
    }



}