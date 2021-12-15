package com.example.entranceact;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInAct extends AppCompatActivity {
    private EditText editTextLogin, editTextTextPassword;
    private DatabaseReference dbUsers;
    private TextView textView4;
    public static CurentUser curentUser = new CurentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#333333"));
        Init();
    }

    public void Init(){
        editTextLogin = findViewById(R.id.editTextLogin);
        editTextTextPassword = findViewById(R.id.editTextPassword);
        dbUsers = FirebaseDatabase.getInstance().getReference(Const.DB_USERS_REF);
        textView4 = findViewById(R.id.textView4);
    }

    public void onClickEnter(View view){
        String login = editTextLogin.getText().toString();
        String password = Encrypting.sha256(editTextTextPassword.getText().toString());
        searchLoginAndPassword(login, password);
    }

    public void onClickSignUpFormOp(View view){
        Intent intent = new Intent(SignInAct.this, SignUpAct.class);
        startActivity(intent);
    }

    public void searchLoginAndPassword(String log, String passwr){
        ValueEventListener vList = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    users users = ds.getValue(users.class);
                    String login = users.login;
                    String password = users.password;
                    String name = users.name;
                    String email = users.email;
                    if (login.equals(log)){
                        if(password.equals(passwr)){
                            Intent intent = new Intent(SignInAct.this, MainMenuAct.class);
                            curentUser = new CurentUser(login, name);
                            textView4.setText("");
                            startActivity(intent);
                            break;
                        }
                        else{
                            textView4.setTextColor(Color.parseColor("#FF0000"));
                            textView4.setText("Неверный логин или пароль!");
                        }

                    }
                    else{
                        textView4.setTextColor(Color.parseColor("#FF0000"));
                        textView4.setText("Неверный логин или пароль!");
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        dbUsers.addListenerForSingleValueEvent(vList);
    }

    public void onClickChek(View view){
        Intent intent = new Intent(SignInAct.this, ConnectToProjectAct.class);
        startActivity(intent);
    }


}
