package com.iodevs.quoteit.CustomAdapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iodevs.quoteit.Activities.Quote;
import com.iodevs.quoteit.Model.Category;
import com.iodevs.quoteit.Model.TextQuote;
import com.iodevs.quoteit.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

/**
 * Created by Touseef Rao on 9/14/2018.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private ArrayList<Category> categoryList=new ArrayList <Category>();
    private Context mContext;



    public CategoryAdapter(Context mContext, ArrayList <Category> categoryList) {
        this.categoryList = categoryList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_single_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Category category=categoryList.get(position);
        if(!categoryList.isEmpty()) {

            holder.categorName.setText(category.getName());

            Picasso.get().load(categoryList.get(position).getImage()).into(new Target() {
                @Override

                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                    Drawable drawImage = new BitmapDrawable(mContext.getResources(), bitmap);


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.categoryImage.setImageDrawable(drawImage);
                    }
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
            holder.categoryImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, Quote.class);


                    intent.putExtra("category_Name",category.getName());
                    mContext.startActivity(intent);
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
       ImageView categoryImage;
       TextView categorName;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryImage=itemView.findViewById(R.id.category_image);
            categorName=itemView.findViewById(R.id.category_name);

        }
    }
}
