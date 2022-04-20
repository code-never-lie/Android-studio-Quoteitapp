package com.iodevs.quoteit.CustomAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.oob.SignUp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iodevs.quoteit.Activities.FullScreenImageQuote;
import com.iodevs.quoteit.Activities.MainActivity;
import com.iodevs.quoteit.Activities.SignUpActivity;
import com.iodevs.quoteit.Model.ImageQuote;
import com.iodevs.quoteit.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Touseef Rao on 9/15/2018.
 */

public class ImageQuoteAdapter extends RecyclerView.Adapter<ImageQuoteAdapter.ViewHolder> implements Serializable {

    private Context mContext;
    private ArrayList<ImageQuote> imageQuoteList;
    private DatabaseReference myRootRef;
    private String currentUserid;
    private boolean check;
    private boolean flag;
    private FirebaseUser user;
    public ImageQuoteAdapter(Context mContext, ArrayList <ImageQuote> imageQuoteList,boolean flag) {
        this.mContext = mContext;
        this.flag=flag;
        this.imageQuoteList = imageQuoteList;
        check=false;
        user=FirebaseAuth.getInstance().getCurrentUser();

        currentUserid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        myRootRef= FirebaseDatabase.getInstance().getReference().child("Favourite Quote").child(currentUserid).child("Image Quote");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_quote_cardview_single_layout,parent,false);
        return new ImageQuoteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        Log.d("Imageurl",imageQuoteList.get(position).getImageUrl());
        final ImageQuote imageQuote=imageQuoteList.get(position);

        Picasso.get().load(imageQuoteList.get(position).getImageUrl()).into(new Target() {
            @Override

            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                Drawable drawImage = new BitmapDrawable(mContext.getResources(),bitmap);



                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.imageQuote.setImageDrawable(drawImage);
                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        if(!flag)
        {
            holder.favouriteBtn.setImageResource(R.drawable.favourite_filled);
            check=true;
        }

        holder.favouriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(user.isAnonymous())
                {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

                    alertDialogBuilder.setMessage("Create Your Account First !!!");
                            alertDialogBuilder.setPositiveButton("Sign Up",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            Intent intent=new Intent(mContext, MainActivity.class);
                                            mContext.startActivity(intent);
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
                else {


                    if (check) {
                        if (flag) {
                            holder.favouriteBtn.setImageResource(R.drawable.favourite);
                            check = false;
                        } else {

                            myRootRef.child(imageQuoteList.get(position).getQuoteId()).removeValue();
                            imageQuoteList.remove(position);
                            notifyDataSetChanged();
                            check = false;
                        }
                    } else {
                        holder.favouriteBtn.setImageResource(R.drawable.favourite);
                        check = true;
                        holder.favouriteBtn.setImageResource(R.drawable.favourite_filled);
                        myRootRef.child(imageQuoteList.get(position).getQuoteId()).setValue(imageQuoteList.get(position));


                    }


                }

            }
        });
        holder.imageQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, FullScreenImageQuote.class);
                intent.putExtra("image",imageQuote);
                mContext.startActivity(intent);
            }
        });









    }

    @Override
    public int getItemCount() {
        return imageQuoteList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

       ImageView imageQuote;
       ImageButton favouriteBtn;
        public ViewHolder(View itemView) {
            super(itemView);
            imageQuote=(ImageView) itemView.findViewById(R.id.image_view_quote);
            favouriteBtn=(ImageButton)itemView.findViewById(R.id.favourite_btn);


        }
    }



}
