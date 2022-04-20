package com.iodevs.quoteit.CustomAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iodevs.quoteit.Activities.Quote;
import com.iodevs.quoteit.R;

import java.util.ArrayList;

/**
 * Created by Touseef Rao on 9/29/2018.
 */

public class SearchAdapter  extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> searchTagList=new ArrayList <String>();

    public SearchAdapter(Context mContext, ArrayList <String> searchTagList) {
        this.mContext = mContext;
        this.searchTagList = searchTagList;
    }

    public ArrayList <String> getSearchTagList() {
        return searchTagList;
    }

    public void setSearchTagList(ArrayList <String> searchTagList) {
        this.searchTagList = searchTagList;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_single_layout,parent,false);
        return new SearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, final int position) {
      holder.notify_btn.setVisibility(View.INVISIBLE);
      holder.searchTagName.setText(searchTagList.get(position));
      holder.mlinearLayout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent(mContext,Quote.class);
              intent.putExtra("category_Name",searchTagList.get(position));
              mContext.startActivity(intent);

          }
      });
//      notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return searchTagList.size();
    }
    public  class ViewHolder extends RecyclerView.ViewHolder{
        TextView searchTagName;
        ImageButton notify_btn;
        LinearLayout mlinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            notify_btn=(ImageButton)itemView.findViewById(R.id.notifiocation_icon_btn);
            searchTagName=(TextView)itemView.findViewById(R.id.notification_Category);
            mlinearLayout=(LinearLayout)itemView.findViewById(R.id.notification_single_layout);
        }
    }
}
