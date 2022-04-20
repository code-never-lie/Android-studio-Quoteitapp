package com.iodevs.quoteit.Activities;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iodevs.quoteit.CustomAdapters.ImageQuoteAdapter;
import com.iodevs.quoteit.Model.ImageQuote;
import com.iodevs.quoteit.R;

import java.util.ArrayList;

public class LatestImageQuote extends AppCompatActivity {
    private DatabaseReference imageQuoteRef;
    private ArrayList<ImageQuote> imageQuoteList;
    private ImageQuoteAdapter imageQuoteAdapter;
    private RecyclerView latestImageQuoteRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_image_quote);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.latest_image_quote_toolbar);
        toolbar.setTitle("Latext Text Quote");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Recyclerview
        imageQuoteRef= FirebaseDatabase.getInstance().getReference().child("Image Quote");


        latestImageQuoteRecyclerView=(RecyclerView)findViewById(R.id.latest_image_quote_recyclerview);
        imageQuoteList=new ArrayList <com.iodevs.quoteit.Model.ImageQuote>();
        imageQuoteAdapter=new ImageQuoteAdapter(LatestImageQuote.this,imageQuoteList,true);
        latestImageQuoteRecyclerView.setLayoutManager(new GridLayoutManager(LatestImageQuote.this,2));
        latestImageQuoteRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        latestImageQuoteRecyclerView.setItemAnimator(new DefaultItemAnimator());
        latestImageQuoteRecyclerView.setAdapter(imageQuoteAdapter);

        retrivingLatestImageQuote();

    }

    private void retrivingLatestImageQuote() {
        imageQuoteRef.addChildEventListener(
                new ChildEventListener() {

                    ImageQuote imageQuote=new ImageQuote();
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        for(DataSnapshot snapshotL:dataSnapshot.getChildren())
                        {
                            imageQuote=snapshotL.getValue(ImageQuote.class);
                            imageQuoteList.add(imageQuote);
                            imageQuoteAdapter.notifyDataSetChanged();


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
                }
        );

    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
