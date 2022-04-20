package com.iodevs.quoteit.CustomAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.common.oob.SignUp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iodevs.quoteit.Activities.Home;
import com.iodevs.quoteit.Activities.MainActivity;
import com.iodevs.quoteit.Activities.SignUpActivity;
import com.iodevs.quoteit.Model.TextQuote;
import com.iodevs.quoteit.R;

import java.util.ArrayList;

/**
 * Created by Touseef Rao on 9/20/2018.
 */

public class TextQuoteAdapter extends RecyclerView.Adapter<TextQuoteAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<TextQuote> textQuoteList=new ArrayList <TextQuote>();
    private DatabaseReference myRootRef;
    private String currentUserid;
    private boolean check;
    private FirebaseUser user;


    public TextQuoteAdapter(Context mContext, ArrayList <TextQuote> textQuoteList) {
        this.mContext = mContext;
        this.textQuoteList = textQuoteList;
        currentUserid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        check=false;
        user=FirebaseAuth.getInstance().getCurrentUser();

        myRootRef= FirebaseDatabase.getInstance().getReference().child("Favourite Quote").child(currentUserid).child("Text Quote");
    }

    @NonNull
    @Override
    public TextQuoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_quote_single_layout,parent,false);
        return new TextQuoteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TextQuoteAdapter.ViewHolder holder, int position) {

        final TextQuote temp=textQuoteList.get(position);
        holder.textQuote.setText(temp.getQuoteText());

        holder.favBtn.setOnClickListener(new View.OnClickListener() {
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
                    if (check) {

                        myRootRef.child(temp.getQuoteId()).removeValue();
                        check = false;

                    } else {
                        holder.favBtn.setImageResource(R.drawable.star_icon_fillled);
                        check = true;
                        myRootRef.child(temp.getQuoteId()).setValue(temp);
                    }

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return textQuoteList.size();
    }
    public  class ViewHolder extends RecyclerView.ViewHolder{

        TextView textQuote;
        ImageButton favBtn,shareBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            textQuote=itemView.findViewById(R.id.text_quote);
            favBtn=itemView.findViewById(R.id.favrt_text_quote_btn);
            shareBtn=itemView.findViewById(R.id.share_text_quote_btn);
        }
    }

}
