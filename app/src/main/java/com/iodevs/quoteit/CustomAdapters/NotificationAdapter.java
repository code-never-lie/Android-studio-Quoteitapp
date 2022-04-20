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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iodevs.quoteit.Activities.SignUpActivity;
import com.iodevs.quoteit.Model.Category;
import com.iodevs.quoteit.R;

import java.util.ArrayList;

/**
 * Created by Touseef Rao on 9/25/2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Category> categoryList=new ArrayList <Category>();
    private ArrayList<String> categoryName=new ArrayList <>();


    private DatabaseReference myRootRef;
    private String currentUserid;
    private String tempName;
    private boolean check;
    private FirebaseUser user;


    public NotificationAdapter(Context mContext, ArrayList <Category> categoryList,ArrayList<String> categoryName) {
        this.mContext = mContext;
        this.categoryList = categoryList;
        this.categoryName=categoryName;
        check=false;
        user=FirebaseAuth.getInstance().getCurrentUser();
        currentUserid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        myRootRef= FirebaseDatabase.getInstance().getReference().child("Favourite Quote").child(currentUserid).child("Category");
    }


    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_single_layout,parent,false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationAdapter.ViewHolder holder, final int position) {

        if(!categoryList.isEmpty())
        {
        final Category temp=categoryList.get(position);
//            tempName=categoryName.get(position);
       /* if(!(categoryName.size()<position+1))
        {

        }
*/

        holder.categoryName.setText(temp.getName());
        holder.notify_btn.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                if(user.isAnonymous()) {
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
                            ((Activity)mContext).finish();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();


                }
                else
                {
                    if (!check) {
                        holder.notify_btn.setImageResource(R.drawable.notification_bell_filled);
                        check = true;
                        myRootRef.child(temp.getName()).setValue(temp);


                    } else {
                        holder.notify_btn.setImageResource(R.drawable.notification_icon);
                        check = false;
                        myRootRef.child(temp.getName()).removeValue();


                    }

                }


            }
        });
    }}

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
    public  class ViewHolder extends RecyclerView.ViewHolder{
       TextView categoryName;
       ImageButton notify_btn;

        public ViewHolder(View itemView) {
            super(itemView);

            notify_btn=(ImageButton)itemView.findViewById(R.id.notifiocation_icon_btn);
            categoryName=(TextView)itemView.findViewById(R.id.notification_Category);
        }
    }
}
