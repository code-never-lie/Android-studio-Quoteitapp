package com.iodevs.quoteit.CustomAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iodevs.quoteit.Activities.Home;
import com.iodevs.quoteit.Activities.SignUpActivity;
import com.iodevs.quoteit.Model.TextQuote;
import com.iodevs.quoteit.R;

import java.util.ArrayList;

/**
 * Created by Touseef Rao on 9/14/2018.
 */

public class TextQuoteHomeAdapter extends RecyclerView.Adapter<TextQuoteHomeAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<TextQuote> textQuoteList=new ArrayList <TextQuote>();
    private ArrayList<TextQuote> favrtQuoteList=new ArrayList <TextQuote>();
    private DatabaseReference myRootRef;
    private String currentUserid;
    private FirebaseUser user;

    private boolean flag;




    TextQuoteHomeAdapter(){



    };

    public interface OnItemClickListener{
        void OnCLick(ImageButton favourite);
    }

    public TextQuoteHomeAdapter(Context mContext, ArrayList <TextQuote> textQuoteList,boolean flag,ArrayList<TextQuote> favrtQuoteList) {
        this.mContext = mContext;
        this.textQuoteList = textQuoteList;
        this.flag=flag;
        user=FirebaseAuth.getInstance().getCurrentUser();
        this.favrtQuoteList=favrtQuoteList;


        currentUserid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        myRootRef= FirebaseDatabase.getInstance().getReference().child("Favourite Quote").child(currentUserid).child("Text Quote");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_quotation_cardview_single_layout,parent,false);
        return new TextQuoteHomeAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final TextQuote textQuote=textQuoteList.get(position);
        int temp=0;
        holder.quoteText.setText(textQuote.getQuoteText());
        holder.authorName.setText(textQuote.getAuthorName());




        if(!flag)
        {

            holder.favrt_btn.setImageResource(R.drawable.star_icon_fillled);

        }






        holder.favrt_btn.setOnClickListener(new View.OnClickListener() {
            boolean check=false;

            @Override
            public void onClick(View v) {

                if(user.isAnonymous())
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

                    alertDialogBuilder.setMessage("Create Your Account First !!!");
                    alertDialogBuilder.setPositiveButton("Sign Up",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    mContext.startActivity(new Intent(mContext, SignUpActivity.class));

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

                    if (!flag) {
                        textQuoteList.remove(position);
                        notifyDataSetChanged();
                        myRootRef.child(textQuote.getQuoteId()).removeValue();

                    } else {
                        if (!check) {

                            holder.favrt_btn.setImageResource(R.drawable.star_icon_fillled);
                            check = true;
                            myRootRef.child(textQuote.getQuoteId()).setValue(textQuote);

                        } else {
                            check = false;
                            holder.favrt_btn.setImageResource(R.drawable.star_icon);
                            myRootRef.child(textQuote.getQuoteId()).removeValue();
                        }


                    }


                }





            }
        });
        holder.share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"Share",Toast.LENGTH_SHORT).show();
            }
        });
    }

   /* private void checkFavourite(final TextQuote temp) {

        myRootRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    if (dataSnapshot.child(temp.getQuoteId()).exists())
                    {
                        check=true;
                    }
                    else {

                        check=false;
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



    }*/

    @Override
    public int getItemCount() {
        return textQuoteList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        TextView quoteText,authorName;
        ImageButton favrt_btn,share_btn;

        public ViewHolder(View itemView) {
            super(itemView);
            quoteText=(TextView)itemView.findViewById(R.id.text_quotation);
            authorName=(TextView)itemView.findViewById(R.id.author_name);
            favrt_btn=(ImageButton) itemView.findViewById(R.id.favt_btn);
            share_btn=(ImageButton) itemView.findViewById(R.id.share_btn);

        }
        public  void bind(final OnItemClickListener listener, int position)
        {
            favrt_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //listener.OnCLick();
                }
            });
        }

    }



}
