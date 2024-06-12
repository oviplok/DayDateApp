package com.example.datedictator.view.activities.matches;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.datedictator.R;
import com.example.datedictator.view.activities.MainActivity;
import com.example.datedictator.view.activities.settings.SettingsActivity;
import com.example.datedictator.view.adapters.matches.MatchesAdapter;
import com.example.datedictator.repository.model.Match;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MatchesActivity extends AppCompatActivity {
    private RecyclerView.Adapter mMatchesAdapter;
    private FirebaseAuth mAuth;
    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            userSex = extras.getString("sex");
//            Toast.makeText(this, userSex, Toast.LENGTH_SHORT).show();
//        }

//        if(Objects.equals(userSex, "Female")){
//            prefer="Male";
//            Toast.makeText(this, "prefer "+prefer, Toast.LENGTH_SHORT).show();
//        }
//        else {
//            prefer="Female";
//            Toast.makeText(this, "prefer "+prefer, Toast.LENGTH_SHORT).show();
//        }

        mAuth = FirebaseAuth.getInstance();
//        userId = mAuth.getCurrentUser().getUid();
//        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        currentUserID = mAuth.getCurrentUser().getUid();

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mMatchesLayoutManager = new LinearLayoutManager(MatchesActivity.this);
        mRecyclerView.setLayoutManager(mMatchesLayoutManager);
        mMatchesAdapter = new MatchesAdapter(getDataSetMatches(), MatchesActivity.this);
        mRecyclerView.setAdapter(mMatchesAdapter);

        getUserMatchId();
    }

    private void getUserMatchId() {

        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(currentUserID)
                .child("Connections").child("Matches");

        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for(DataSnapshot match : dataSnapshot.getChildren()){
                        FetchMatchInformation(match.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void FetchMatchInformation(String key) {
        DatabaseReference userDb = FirebaseDatabase.getInstance()
                .getReference().child("Users").child(key);

        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String userId = dataSnapshot.getKey();
                    String name = "";
                    String profileImageUrl = "";
                    if(dataSnapshot.child("name").getValue()!=null){
                        name = dataSnapshot.child("name").getValue().toString();
                    }
                    if(dataSnapshot.child("profileImageUrl").getValue()!=null){
                        profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
                    }


                    Match obj = new Match(userId, name, profileImageUrl);
                    resultsMatches.add(obj);
                    mMatchesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private ArrayList<Match> resultsMatches = new ArrayList<Match>();
    private List<Match> getDataSetMatches() {
        return resultsMatches;
    }

    public void goToMatches(View view) {
        Intent intent = new Intent(MatchesActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}