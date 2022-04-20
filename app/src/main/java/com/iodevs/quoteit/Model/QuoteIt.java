package com.iodevs.quoteit.Model;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iodevs.quoteit.Activities.Home;
import com.iodevs.quoteit.Activities.Quote;
import com.iodevs.quoteit.Activities.SplashScreen;
import com.iodevs.quoteit.CustomAdapters.TextQuoteHomeAdapter;

import java.util.concurrent.Executor;

/**
 * Created by Touseef Rao on 9/27/2018.
 */

public class QuoteIt extends Application {


    private static final String TAG = "Activity";
    private static Context mContext;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    public void onCreate() {
        super.onCreate();
//        Activity activity=(Activity) getApplicationContext();
        mContext=getApplicationContext();
       mAuth=FirebaseAuth.getInstance();
       user=mAuth.getCurrentUser();
       if(user!=null)
       {
          /* Toast.makeText(getApplicationContext(), "Alreadyuser",
                   Toast.LENGTH_SHORT).show();*/
           Intent intent =new Intent(QuoteIt.this,SplashScreen.class);

           startActivity(intent);
       }
       else
       {
           mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener <AuthResult>() {
               @Override
               public void onComplete(@NonNull Task <AuthResult> task) {
                   if (task.isSuccessful()) {
                       // Sign in success, update UI with the signed-in user's information
                       Log.d("qazxsw", "signInAnonymously:success");
                       //FirebaseUser user = mAuth.getCurrentUser();
                       // updateUI(user);
                   } else {
                       // If sign in fails, display a message to the user.
                       Log.w("qazxsw", "signInAnonymously:failure", task.getException());
                      // Toast.makeText(getApplicationContext(), "Authentication failed.",
                           //    Toast.LENGTH_SHORT).show();
                       //updateUI(null);
                   }
               }
           });
       }

       }







        // mAuth = FirebaseAuth.getInstance();

    }


