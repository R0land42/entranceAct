package com.example.entranceact;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
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

public class SignUpAct extends AppCompatActivity {
    private EditText edTextlogin, edTextName, edTextPassword, edTextEmail;
    private DatabaseReference dbUsers;
    private TextView textView3;
    public static Activity SignUpActivityContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        SignUpActivityContext = this;
        Init();
        getWindow().setStatusBarColor(Color.parseColor("#333333"));
    }

    public void Init(){
        edTextlogin = findViewById(R.id.edTextlogin);
        edTextName = findViewById(R.id.edTextName);
        edTextPassword = findViewById(R.id.edTextPassword);
        edTextEmail = findViewById(R.id.edTextEmail);
        textView3 = findViewById(R.id.textView3);
        dbUsers = FirebaseDatabase.getInstance().getReference(Const.DB_USERS_REF);
    }

    public void onClickSignUp(View view) {
        String login = edTextlogin.getText().toString();
        String name = edTextName.getText().toString();
        String password = Encrypting.sha256(edTextPassword.getText().toString());
        String email = edTextEmail.getText().toString();
        if (!TextUtils.isEmpty(login) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(email)) {
            ValueEventListener vList = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Boolean chkLog = true;
                    Boolean chkEmail = true;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        users users = ds.getValue(users.class);
                        String loginChk = users.login;
                        String emailChk = users.email;
                        if (loginChk.equals(login) || emailChk.equals(email)) {
                            if (emailChk.equals(email)){
                                chkEmail = false;
                            }
                            if (loginChk.equals(login)){
                                chkLog = false;
                            }
                            break;
                        } else {
                            chkEmail = true;
                            chkLog = true;
                        }
                    }
                    users NewUsers = new users(login, name, password, email);
                    if (chkLog && chkEmail) {
                        dbUsers.child(login).setValue(NewUsers);
                        textView3.setTextColor(Color.parseColor("#00FF00"));
                        textView3.setText("?????????????????????? ???????????? ??????????????!");
                    } else {
                        if (!chkLog){
                            textView3.setTextColor(Color.parseColor("#FF0000"));
                            textView3.setText("???????????????????????? ?? ?????????? ?????????????? ?????? ????????????????????????????");
                        }
                        if (!chkEmail){
                            textView3.setTextColor(Color.parseColor("#FF0000"));
                            textView3.setText("???????????????????????? ?? ?????????? ???????????? ?????? ????????????????????????????");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            };
            dbUsers.addListenerForSingleValueEvent(vList);
        }
        else {
            checkEmptyField(login, name, password, email);
        }
    }


    public void checkEmptyField(String login, String name, String password, String email){
        String message = "???????????? ??????????????????????! ?????????????????? ????????: ";
        if(TextUtils.isEmpty(login)) {
            message = message + "<??????????> ";
        }
        if(TextUtils.isEmpty(name)) {
            message = message + "<??????> ";
        }
        if(TextUtils.isEmpty(password)) {
            message = message + "<????????????> ";
        }
        if(TextUtils.isEmpty(email)) {
            message = message + "<??????????> ";
        }
        textView3.setTextColor(Color.parseColor("#FF0000"));
        textView3.setText(message);;
    }






}