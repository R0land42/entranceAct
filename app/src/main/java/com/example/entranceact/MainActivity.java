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

import java.util.ArrayList;
import java.util.List;

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


    public void searchLogin(String log, String passwr){
        ValueEventListener vList = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    users users = ds.getValue(users.class);
                    String zalupa = users.login;
                    String penis = users.password;
                    if (zalupa.equals(log)){
                        if(penis.equals(passwr)){
                            textView4.setText("Ну круто, здорово! " + zalupa + " " + penis + " ");
                            break;
                        }
                        else{
                            textView4.setText("Говно, неверный пароль! " + zalupa + " " + penis);
                            break;
                        }

                    }
                    else{
                        textView4.setText("Говно, неверный логин " + zalupa + " " + penis);
                        break;
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        dUsers.addValueEventListener(vList);
    }




}
