package com.example.entranceact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private EditText editTextLogin, editTextTextPassword;
    private DatabaseReference dUsers;
    private String usersRef = "Users";
    private TextView textView4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#333333"));
        Init();
    }

    public void Init(){
        editTextLogin = findViewById(R.id.editTextLogin1);
        editTextTextPassword = findViewById(R.id.editTextTextPassword1);
        dUsers = FirebaseDatabase.getInstance().getReference(usersRef);
        textView4 = findViewById(R.id.textView4);
    }

    public void onClickEnter(View view){
        String login = editTextLogin.getText().toString();
        String password = editTextTextPassword.getText().toString();
        searchLogin(login, password);

    }

    public void onClickSignUpFormOp(View view){
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);

    }

    public void searchLogin(String login, String password){
        dUsers.orderByChild("users").equalTo(login).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("login").getValue(String.class);
                textView4.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }


}