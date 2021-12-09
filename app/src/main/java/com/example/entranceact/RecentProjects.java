package com.example.entranceact;

import static com.example.entranceact.MainActivity.curentUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecentProjects extends AppCompatActivity {
    private ArrayAdapter<String> arrayAdapter;
    private ListView listViewRecentProjects;
    private List<String> listRecetnProjectName, listRecetnProjectKey;
    private DatabaseReference dbUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_projects);
        init();
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
                Intent intent = new Intent(RecentProjects.this, ChatAct.class);
                intent.putExtra(Const.CURENT_PROJECT_KEY, itemProject);
                startActivity(intent);
            }
        });





    }

}
