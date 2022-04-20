package com.iodevs.quoteit.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.iodevs.quoteit.R;

public class MainActivity extends AppCompatActivity {

    private EditText email,password;
    private Button login_btn,alreadyAccount,forgetPassword;
    private SignInButton gmailSignIn;

    private FirebaseAuth mAuth;

    private GoogleSignInOptions gso;
    private String mail,pass;

    private static final int RC_SIGN_IN=1;
   //private GoogleApiClient mGoogleSignInClient;
    GoogleSignInClient mGoogleSignInClient;


   // Dialog myProgressBar;

   private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

                gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);





        //Intilaize UI
        email=(EditText)findViewById(R.id.email_login);
        password=(EditText)findViewById(R.id.login_pass);
        login_btn=(Button)findViewById(R.id.login_btn);



        alreadyAccount=(Button)findViewById(R.id.new_Accnt_btn);
        gmailSignIn=(SignInButton) findViewById(R.id.gmailSigIn);
        forgetPassword=(Button)findViewById(R.id.forget_pass);

        progressBar=(ProgressBar)findViewById(R.id.login_progressbar);
        progressBar.setVisibility(View.GONE);





//        myProgressBar=new  Dialog(this);
//        myProgressBar.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        myProgressBar.setContentView(R.layout.custom_dialog_progress);
//        myProgressBar.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        myProgressBar.setCancelable(false);

        //Firebase Auth Variable
        mAuth=FirebaseAuth.getInstance();









       /* mGoogleSignInClient=new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(MainActivity.this,"Error !",Toast.LENGTH_SHORT).show();
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();*/

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mail=email.getText().toString().trim();
                pass=password.getText().toString().trim();
                if(TextUtils.isEmpty(mail))
                {
                   // Toast.makeText(MainActivity.this,"Enter Your Email",Toast.LENGTH_SHORT).show();
                    email.setError("Enter Your Email");
                }
                else if(TextUtils.isEmpty(pass))
                {
                   // Toast.makeText(MainActivity.this,"Enter You Password",Toast.LENGTH_SHORT).show();
                    password.setError("Enter Your Password");
                }
                else{
                    /*progressBar.setVisibility(View.VISIBLE);
                    login_btn.setVisibility(View.GONE);*/
                  //  myProgressBar.show();
                    mAuth.signInWithEmailAndPassword(mail, pass)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        progressBar.setVisibility(View.GONE);
                                        login_btn.setVisibility(View.VISIBLE);
//                                        if(myProgressBar!= null)
//                                        {
//                                            myProgressBar.cancel();
//                                            myProgressBar.hide();
//                                        }
                                        Intent intent=new Intent(getBaseContext(),Home.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        //progress bar
//                                        if(myProgressBar!= null)
//                                        {
//                                            myProgressBar.cancel();
//                                            myProgressBar.hide();
//                                        }
                                        /*progressBar.setVisibility(View.GONE);
                                        login_btn.setVisibility(View.VISIBLE);*/
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();


                                    }

                                    // ...
                                }
                            });


                }
            };

                });





       alreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getBaseContext(),SignUpActivity.class);
                startActivity(intent);
                finish();

            }
        });
       gmailSignIn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              // myProgressBar.show();
               progressBar.setVisibility(View.VISIBLE);
               login_btn.setVisibility(View.GONE);
               signIn();

    }


    });
       forgetPassword.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(getBaseContext(),ForgetPassword.class);
               startActivity(intent);
               finish();

           }
       });



}

    @Override
    protected void onStart() {
        super.onStart();

       // GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    }

   private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(myProgressBar!= null)
//        {
//            myProgressBar.cancel();
//            myProgressBar.hide();
//        }
        progressBar.setVisibility(View.GONE);
        login_btn.setVisibility(View.VISIBLE);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.


            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Log.d("accountname",account.getDisplayName());
//            Log.d("user_name",mAuth.getCurrentUser().getDisplayName());
            firebaseAuthWithGoogle(account);


        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("signinfailed",e.getMessage());

        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {


        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                           // Log.d(TAG, "signInWithCredential:success");
                           // FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this,"Google Sign in",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getBaseContext(),Home.class);

                            startActivity(intent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                });
    }


}
