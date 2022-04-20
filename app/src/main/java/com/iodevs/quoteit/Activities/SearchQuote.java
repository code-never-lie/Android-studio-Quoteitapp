package com.iodevs.quoteit.Activities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iodevs.quoteit.CustomAdapters.SearchAdapter;
import com.iodevs.quoteit.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchQuote extends AppCompatActivity {
    private EditText searchTextEditText;
    private ImageButton searchBtn;
    private String searchText;

    private RecyclerView searchSuggestionrecyclerView;


    private ArrayList <String> searchTag;
    private ArrayList <String> tempSearchTag;
    private ArrayList <String> matchingSearchTag;
    private SearchAdapter searchAdapter;
    private SearchAdapter allSearchTag;
    //Firebase
    private DatabaseReference searchTagRef;

    private String temp1="Hard Working";
    private String temp2="Health";
    private String temp3="Friends";
    private String temp4="Joy" ;
    private String temp5="Love" ;
    private String temp6="Motivational";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_quote);

       
        Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        toolbar.setTitle("Latext Text Quote");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        searchTextEditText = (EditText) findViewById(R.id.search_edit_Text);
        searchBtn = (ImageButton) findViewById(R.id.searchBtn);
        searchTag = new ArrayList <String>();
        tempSearchTag = new ArrayList <String>();
        matchingSearchTag = new ArrayList <String>();


        searchSuggestionrecyclerView = (RecyclerView) findViewById(R.id.search_tags);
        // searchAdapter=new SearchAdapter(SearchQuote.this,matchingSearchTag);
        allSearchTag = new SearchAdapter(SearchQuote.this, searchTag);
        searchSuggestionrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchSuggestionrecyclerView.setAdapter(allSearchTag);


        //Firebase
        searchTagRef = FirebaseDatabase.getInstance().getReference().child("Search Tags");

       /* searchTagRef.child(temp1).setValue("");*/
       /* searchTagRef.child(temp2).setValue("");*/
       /* searchTagRef.child(temp3).setValue("");*/
       /* searchTagRef.child(temp4).setValue("");*/
       /* searchTagRef.child(temp5).setValue("");*/
       /* searchTagRef.child(temp6).setValue("");*/

        retrivingSearchTag();


        searchTextEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList <String> result = new ArrayList <>();
                String matchWord = s.toString().toLowerCase();
                if (matchWord.equals("")) {
                    result = tempSearchTag;
                } else {
                    result = matchingSearchTag(matchWord);
                }

                searchTag.removeAll(searchTag);
                for (int i = 0; i < result.size(); i++) {
                    searchTag.add(result.get(i));
                }

                allSearchTag.notifyDataSetChanged();

               // Toast.makeText(SearchQuote.this, searchText, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private ArrayList <String> matchingSearchTag(String searchText) {

        ArrayList <String> temp = new ArrayList <>();

        for (int i = 0; i < searchTag.size(); i++) {

            if (searchTag.get(i).toLowerCase().contains(searchText)) {
                temp.add(searchTag.get(i));
            }
        }

        return temp;
    }

    private void retrivingSearchTag() {

        searchTagRef.addChildEventListener(new ChildEventListener() {
            String temp;

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("checkingtagsearch", dataSnapshot.getKey());
                temp = dataSnapshot.getKey();
                searchTag.add(temp);
                tempSearchTag.add(temp);
                allSearchTag.notifyDataSetChanged();
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
