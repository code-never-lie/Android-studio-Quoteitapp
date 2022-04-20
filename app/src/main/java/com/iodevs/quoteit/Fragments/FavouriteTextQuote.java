package com.iodevs.quoteit.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iodevs.quoteit.CustomAdapters.TextQuoteAdapter;
import com.iodevs.quoteit.CustomAdapters.TextQuoteHomeAdapter;
import com.iodevs.quoteit.Model.*;
import com.iodevs.quoteit.Model.TextQuote;
import com.iodevs.quoteit.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavouriteTextQuote.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavouriteTextQuote#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteTextQuote extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView favouriteTextQuoteRecyclerView;
    private ArrayList<com.iodevs.quoteit.Model.TextQuote> textQuoteList;
    private ArrayList<com.iodevs.quoteit.Model.TextQuote> favrtQuoteList;

    private TextQuoteHomeAdapter textQuoteHomeAdapter;
    private DatabaseReference textQuoteRef;
    private String currentUserid;

    public FavouriteTextQuote() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavouriteTextQuote.
     */
    // TODO: Rename and change types and number of parameters
    public static FavouriteTextQuote newInstance(String param1, String param2) {
        FavouriteTextQuote fragment = new FavouriteTextQuote();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_favourite_text_quote, container, false);
        currentUserid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        textQuoteRef= FirebaseDatabase.getInstance().getReference().child("Favourite Quote").child(currentUserid).child("Text Quote");


        favouriteTextQuoteRecyclerView=(RecyclerView)view.findViewById(R.id.favourite_text_quote);
        textQuoteList=new ArrayList <com.iodevs.quoteit.Model.TextQuote>();
        favrtQuoteList=new ArrayList <TextQuote>();
        textQuoteHomeAdapter=new TextQuoteHomeAdapter(getContext(),textQuoteList,false,favrtQuoteList);
        favouriteTextQuoteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favouriteTextQuoteRecyclerView.setAdapter(textQuoteHomeAdapter);



        retrivingFavourireTextQuote();


         return view;
    }

    private void retrivingFavourireTextQuote() {

        textQuoteRef.addChildEventListener(new ChildEventListener() {
            TextQuote temp=new TextQuote();
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                temp=dataSnapshot.getValue(TextQuote.class);
                textQuoteList.add(temp);
                textQuoteHomeAdapter.notifyDataSetChanged();
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
