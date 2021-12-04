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

public class MainActivity extends AppCompatActivity {
    private EditText editTextLogin, editTextTextPassword;
    private DatabaseReference dUsers;
    private String usersRef = "Users";
    private TextView textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editTextLogin = findViewById(R.id.editTextLogin);
        editTextTextPassword = findViewById(R.id.editTextPassword);
        dUsers = FirebaseDatabase.getInstance().getReference(usersRef);
        textView4 = findViewById(R.id.textView4);

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#333333"));

    }

    public void onClickEnter(View view){
        String login = editTextLogin.getText().toString();
        String password = Encrypting.sha256(editTextTextPassword.getText().toString());
        searchLogin(login, password);

    }

    public void onClickSignUpFormOp(View view){
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);

    }

    public void searchLogin(String log, String pass){
        ValueEventListener vList = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    users users = ds.getValue(users.class);
                    String login = users.login;
                    String password = users.password;
                    if (login.equals(log)){
                        if(password.equals(pass)){
                            textView4.setText(new StringBuilder().append("Добро пожаловать, ").append(login).append(". Снова.").toString());
                        }
                        else{
                            textView4.setText("Неправильный пароль!");
                        }
                    }
                    else{
                        textView4.setText("Неправильный логин!");
                    }
                    break;
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        dUsers.addValueEventListener(vList);
    }




}
