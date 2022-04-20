package com.iodevs.quoteit.Activities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iodevs.quoteit.CustomAdapters.CategoryAdapter;
import com.iodevs.quoteit.CustomAdapters.NotificationAdapter;
import com.iodevs.quoteit.Model.Category;
import com.iodevs.quoteit.R;

import java.util.ArrayList;

public class Notification extends AppCompatActivity {
    private RecyclerView allNotification;
    private ArrayList<Category> categoryList;
    private NotificationAdapter notificationAdapter;
    private ArrayList<String> categoryName;

    private DatabaseReference rootRef;
    private String currentUserid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

         //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.notification_toolbar);
        toolbar.setTitle("Notification");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //ALl UI intiliaze

        allNotification=(RecyclerView)findViewById(R.id.notification_recyclervirw);
        categoryList=new ArrayList <Category>();
        categoryName=new ArrayList <String>();
        notificationAdapter=new NotificationAdapter(this,categoryList,categoryName);
        allNotification.setLayoutManager(new LinearLayoutManager(this));
        allNotification.setAdapter(notificationAdapter);





        //Firebase
        rootRef= FirebaseDatabase.getInstance().getReference();
        currentUserid= FirebaseAuth.getInstance().getCurrentUser().getUid();

        retrivingAllCategory();
        retrivingnotifyCategoey();

    }

    private void retrivingnotifyCategoey() {
        rootRef.child("Favourite Quote").child(currentUserid).child("Category").addChildEventListener(new ChildEventListener() {
            String temp;
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                temp=dataSnapshot.getKey();
                categoryName.add(temp);
                notificationAdapter.notifyDataSetChanged();
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

    private void retrivingAllCategory() {
        rootRef.child("Category").addChildEventListener(new ChildEventListener() {
            Category temp=new Category();
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                temp=dataSnapshot.getValue(Category.class);
                categoryList.add(temp);
                notificationAdapter.notifyDataSetChanged();;
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
