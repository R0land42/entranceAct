package com.example.entranceact;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    private EditText edTextlogin, edTextName, edTextPassword, edTextEmail;
    private DatabaseReference dbUsers;
    private TextView textView3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        edTextlogin = findViewById(R.id.edTextlogin);
        edTextName = findViewById(R.id.edTextName);
        edTextPassword = findViewById(R.id.edTextPassword);
        edTextEmail = findViewById(R.id.edTextEmail);
        textView3 = findViewById(R.id.textView3);
        dbUsers = FirebaseDatabase.getInstance().getReference(Const.DB_USERS_REF);

        setContentView(R.layout.activity_sign_up);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setStatusBarColor(Color.parseColor("#333333"));
    }


    public void onClickSignUp(View view){
        String login = edTextlogin.getText().toString();
        String name = edTextName.getText().toString();
        String password = Encrypting.sha256(edTextPassword.getText().toString());;
        String email = edTextEmail.getText().toString();
        users NewUsers = new users(login, name, password, email);
        if (!TextUtils.isEmpty(login) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(email)){
            dbUsers.child(login).setValue(NewUsers);
            textView3.setTextColor(Color.parseColor("#00FF00"));
            textView3.setText("Регистрация прошла успешно!");;
        }
        else{
            checkEmptyField(login,name,password,email);
        }
    }

    public void checkEmptyField(String login, String name, String password, String email){
        String message = "Ошибка регистрации! Заполните поле: ";
        if(TextUtils.isEmpty(login)) {
            message = message + "<Логин> ";
        }
        if(TextUtils.isEmpty(name)) {
            message = message + "<Имя> ";
        }
        if(TextUtils.isEmpty(password)) {
            message = message + "<Пароль> ";
        }
        if(TextUtils.isEmpty(email)) {
            message = message + "<Почта> ";
        }
        textView3.setTextColor(Color.parseColor("#FF0000"));
        textView3.setText(message);;
    }


}