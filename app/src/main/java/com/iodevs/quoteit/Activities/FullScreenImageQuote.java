package com.iodevs.quoteit.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iodevs.quoteit.Model.ImageQuote;
import com.iodevs.quoteit.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.Serializable;
import java.util.ArrayList;

public class FullScreenImageQuote extends AppCompatActivity implements Serializable {
    ImageView imageView;
    ImageButton favrt_btn,share_Btn;

    ImageQuote imageQuote;

    private DatabaseReference favrtImageRef;
    private String currentUser;
    private boolean check;
    private  boolean already;
    private ArrayList<ImageQuote> imageQuoteArrayList;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image_quote);
        imageQuote=(ImageQuote) getIntent().getSerializableExtra("image");
        Toolbar toolbar = (Toolbar) findViewById(R.id.fullsize_imageQuote_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        currentUser= FirebaseAuth.getInstance().getUid();
        user=FirebaseAuth.getInstance().getCurrentUser();
        check=true;
        imageQuoteArrayList=new ArrayList <ImageQuote>();

         imageView=(ImageView)findViewById(R.id.fullsize_image_quote);
         favrt_btn=(ImageButton)findViewById(R.id.fullscreen_favt_btn);
         share_Btn=(ImageButton)findViewById(R.id.fullscreenimage_share_btn);
         favrtImageRef= FirebaseDatabase.getInstance().getReference().child("Favourite Quote").child(currentUser).child("Image Quote");
         setImage();
         checkingAlreadyFavourite();

        Log.d("imagefromintent",imageQuote.getQuoteId());

         share_Btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(FullScreenImageQuote.this,"Share",Toast.LENGTH_SHORT).show();
             }
         });

         favrt_btn.setOnClickListener(new View.OnClickListener() {


             @Override
             public void onClick(View v) {


                 if(user.isAnonymous())
                 {
                     AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FullScreenImageQuote.this);

                     alertDialogBuilder.setMessage("Create Your Account First !!!");
                     alertDialogBuilder.setPositiveButton("SignUp",
                             new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface arg0, int arg1) {
                                     Intent intent=new Intent(FullScreenImageQuote.this, SignUpActivity.class);
                                     startActivity(intent);
                                     //  mContext.startActivity(new Intent(mContext, MainActivity.class));
                                 }
                             });

                     alertDialogBuilder.setNegativeButton("Not Now",new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             dialog.cancel();

                         }
                     });

                     AlertDialog alertDialog = alertDialogBuilder.create();
                     alertDialog.show();

                 }
                 else{
                 if(check)
                 {
                     favrt_btn.setImageResource(R.drawable.star_icon_fillled);
                     setFavrtQuote();
                     check=false;

                 }
                 else
                 {
                     favrt_btn.setImageResource(R.drawable.star_icon);
                     removeFavouriteQuote();
                     check=true;

                 }
             }
             }
         });




    }

    private void removeFavouriteQuote() {
        favrtImageRef.child(imageQuote.getQuoteId()).removeValue();



    }

    private void checkingAlreadyFavourite() {
        favrtImageRef.addChildEventListener(new ChildEventListener() {
            ImageQuote temp=new ImageQuote();
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                temp=dataSnapshot.getValue(ImageQuote.class);
                already=checkMatching(temp.getQuoteId());

                if(already)
                {
                    favrt_btn.setImageResource(R.drawable.star_icon_fillled);
                    check=false;
                }
                else {
                    check=true;
                }
               // imageQuoteArrayList.add(temp);
                Log.d("imagefromfirebase",temp.getQuoteId());


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

    private boolean checkMatching(String temp) {


            if(imageQuote.getQuoteId().equals(temp)) {
                return true;

            }

        return false;

    }

    private void setFavrtQuote() {

        favrtImageRef.child(imageQuote.getQuoteId()).setValue(imageQuote);





    }

    private void setImage() {

        Picasso.get().load(imageQuote.getImageUrl()).into(new Target() {
            @Override

            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                Drawable drawImage = new BitmapDrawable(getBaseContext().getResources(),bitmap);



                imageView.setImageDrawable(drawImage);

            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return  true;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
