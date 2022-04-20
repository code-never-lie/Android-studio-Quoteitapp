package com.iodevs.quoteit.Activities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iodevs.quoteit.CustomAdapters.TextQuoteHomeAdapter;
import com.iodevs.quoteit.Model.TextQuote;
import com.iodevs.quoteit.R;

import java.util.ArrayList;

public class LatestTextQuote extends AppCompatActivity {



    private RecyclerView latestTextQuoteRecyclerView;
    private ArrayList<TextQuote> textQuoteList;
    private ArrayList<TextQuote> favrtQuoteList;
    private TextQuoteHomeAdapter textQuoteHomeAdapter;
    private DatabaseReference textQuoteRef;
    private String currentUserid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_text_quote);

        //Toolbbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.latest_text_quote_toolbar);
        toolbar.setTitle("Latext Text Quote");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentUserid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        textQuoteRef= FirebaseDatabase.getInstance().getReference().child("Text Quote");


        latestTextQuoteRecyclerView=(RecyclerView)findViewById(R.id.latest_text_quote_recyclerview);
        textQuoteList=new ArrayList <com.iodevs.quoteit.Model.TextQuote>();
        favrtQuoteList=new ArrayList <TextQuote>();
        textQuoteHomeAdapter=new TextQuoteHomeAdapter(LatestTextQuote.this,textQuoteList,true,favrtQuoteList);
        latestTextQuoteRecyclerView.setLayoutManager(new LinearLayoutManager(LatestTextQuote.this));

        latestTextQuoteRecyclerView.setAdapter(textQuoteHomeAdapter);
        retrivingLatestTextQuote();
    }

    private void retrivingLatestTextQuote() {

        textQuoteRef.addChildEventListener(new ChildEventListener() {
            TextQuote textQuote=new TextQuote();
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    textQuote=snapshot.getValue(TextQuote.class);
                    textQuoteList.add(textQuote);
                    textQuoteHomeAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
