package com.example.entranceact;

import static com.example.entranceact.SignInAct.curentUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatAct extends AppCompatActivity {
    private EditText edTextMessage;
    private DatabaseReference dbChat;
    private String userName, userLogin, userPassword, userEmail, curentProjetKey;
    private ArrayList<String> messages  = new ArrayList<>();
    private RecyclerView reycleViewMessages;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Init();

    }

    public void Init(){
        Intent intent = getIntent();
        //userName = intent.getStringExtra(Const.USER_NAME);
        //userLogin = intent.getStringExtra(Const.USER_LOGIN);
        //userPassword = intent.getStringExtra(Const.USER_PASSWORD);
        //userEmail = intent.getStringExtra(Const.USER_EMAIL);
        curentProjetKey = intent.getStringExtra(Const.CURENT_PROJECT_KEY);

        edTextMessage = findViewById(R.id.edTextMessage);
        dbChat = FirebaseDatabase.getInstance().getReference(Const.DB_PROJECT_REF);

        reycleViewMessages = findViewById(R.id.reycleViewMessages);
        reycleViewMessages.setLayoutManager(new LinearLayoutManager(this));

        DataAdapterForMessages dataAdapterForMessages = new DataAdapterForMessages(this, messages);

        reycleViewMessages.setAdapter(dataAdapterForMessages);

        dbChat.child(curentProjetKey).child("ProjectZChat").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String msg = snapshot.getValue(String.class);
                messages.add(msg);
                dataAdapterForMessages.notifyDataSetChanged();
                reycleViewMessages.scrollToPosition(messages.size());
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


        /*dbChat.child(curentProjetKey).child("ProjectZChat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    String msg = ds.getValue(String.class);
                    messages.add(msg);
                }
                dataAdapterForMessages.notifyDataSetChanged();
                reycleViewMessages.scrollToPosition(messages.size());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        */
    }

    public void onClickSendMessage(View view){
        String msg = edTextMessage.getText().toString();
        String msgTosend = curentUser.curentName + "(" + curentUser.curentLog + ")" + System.getProperty ("line.separator") + msg;
        if (TextUtils.isEmpty(msg)){
            Toast.makeText(this, "Введите сообщение", Toast.LENGTH_SHORT).show();
            return;
        }
        if (msg.length() == Const.MAX_MSG_LENGTH){
            Toast.makeText(this, "Слишком больше сообщение", Toast.LENGTH_SHORT).show();
            return;
        }
        dbChat.child(curentProjetKey).child("ProjectZChat").push().setValue(msgTosend);
        edTextMessage.setText("");
    }



}