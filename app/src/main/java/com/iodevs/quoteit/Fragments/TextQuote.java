package com.iodevs.quoteit.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iodevs.quoteit.CustomAdapters.TextQuoteAdapter;
import com.iodevs.quoteit.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TextQuote.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TextQuote#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TextQuote extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private String category,search;
    private RecyclerView textQuoteRecyclerView;
    private ArrayList<com.iodevs.quoteit.Model.TextQuote> textQuoteList;
    private TextQuoteAdapter textQuoteAdapter;
    //firebase
    private DatabaseReference textQuoteRef;




    public TextQuote() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TextQuote.
     */
    // TODO: Rename and change types and number of parameters
    public static TextQuote newInstance(String param1, String param2) {
        TextQuote fragment = new TextQuote();
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
        View RootView=inflater.inflate(R.layout.fragment_text_quote, container, false);

            Bundle b=getArguments();
         category  = b.getString("category");
         search=getArguments().getString("searchquote");
  //      Log.d("categoryasd",category);

//        Log.d("searchasd",search);

        textQuoteRef=FirebaseDatabase.getInstance().getReference().child("Text Quote").child(category);
        textQuoteRecyclerView=(RecyclerView)RootView.findViewById(R.id.text_quote_recyclerview);
        textQuoteList=new ArrayList <com.iodevs.quoteit.Model.TextQuote>();
        textQuoteAdapter=new TextQuoteAdapter(getContext(),textQuoteList);
        textQuoteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        textQuoteRecyclerView.setAdapter(textQuoteAdapter);





        retriveTextQuote();




        return RootView;
    }

    private void retriveTextQuote() {


          textQuoteRef.addChildEventListener(new ChildEventListener() {
              com.iodevs.quoteit.Model.TextQuote temp=new com.iodevs.quoteit.Model.TextQuote();
              @Override
              public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                      Log.d("check_retribe_key", dataSnapshot.getKey());
                      temp = dataSnapshot.getValue(com.iodevs.quoteit.Model.TextQuote.class);
                          textQuoteList.add(temp);
                          textQuoteAdapter.notifyDataSetChanged();

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
