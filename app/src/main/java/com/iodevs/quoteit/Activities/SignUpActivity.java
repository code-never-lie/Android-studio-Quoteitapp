package com.iodevs.quoteit.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iodevs.quoteit.Model.User;
import com.iodevs.quoteit.R;

public class SignUpActivity extends AppCompatActivity {
    private EditText name,email,pass,confirm_pass;
    private Button signup_up,login_btn;

    private String userName,userEmail,userPass,userConfirmPas;

    FirebaseAuth mAuth;
    DatabaseReference myRootRef;

   // Dialog myProgressBar;
   private ProgressBar progressBar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_sign_up);

        //Intilaze All UI
        name=(EditText)findViewById(R.id.user_name);
        email=(EditText)findViewById(R.id.email_signup);
        pass=(EditText)findViewById(R.id.signup_pass);
        confirm_pass=(EditText)findViewById(R.id.signup_pass_confirm);
        signup_up=(Button) findViewById(R.id.sign_btn);
        login_btn=(Button)findViewById(R.id.sign_login_btn);


        Toolbar toolbar = (Toolbar) findViewById(R.id.signUp_toolbar);
        progressBar=(ProgressBar)findViewById(R.id.signup_progressbar);
        progressBar.setVisibility(View.GONE);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        final User user=new User();
      //  myProgressBar=new  Dialog(this);
        //Firebase Auth Intilaze
        mAuth=FirebaseAuth.getInstance();
        myRootRef= FirebaseDatabase.getInstance().getReference();

        //Click listener
        signup_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userName=name.getText().toString().trim();
                userEmail=email.getText().toString().trim();
                userPass=pass.getText().toString().trim();
                userConfirmPas=confirm_pass.getText().toString().trim();




                if(TextUtils.isEmpty(userName))
                {
                   // Toast.makeText(SignUpActivity.this,"Entered Your Name",Toast.LENGTH_SHORT).show();
                    name.setError("Entered Your Name");
                }
                else if(TextUtils.isEmpty(userEmail))
                {
                    //Toast.makeText(SignUpActivity.this,"Entered Your Email",Toast.LENGTH_SHORT).show();
                    email.setError("Entered Your Email");
                }else if(TextUtils.isEmpty(userPass))
                {
                   // Toast.makeText(SignUpActivity.this,"Entered Your Password",Toast.LENGTH_SHORT).show();
                    pass.setError("Entered Your Password");
                }
                else if(TextUtils.isEmpty(userConfirmPas))
                {
                   // Toast.makeText(SignUpActivity.this,"Entered Your Confirm Password",Toast.LENGTH_SHORT).show();
                    confirm_pass.setError("Entered Your Confirm Password");
                }
                else
                {

                    if(!userPass.equals(userConfirmPas))
                    {
                        //Toast.makeText(SignUpActivity.this,"Password Not Match",Toast.LENGTH_SHORT).show();
                        confirm_pass.setError("Password Not Match");
                    }
                    else
                    {
//                        myProgressBar.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                        myProgressBar.setContentView(R.layout.custom_dialog_progress);
//                        myProgressBar.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                        myProgressBar.setCancelable(false);
//
//                        myProgressBar.show();
                        progressBar.setVisibility(View.VISIBLE);
                        signup_up.setVisibility(View.INVISIBLE);
                        user.setEmail(userEmail);
                        user.setName(userName);

                        AuthCredential credential = EmailAuthProvider.getCredential(userEmail, userPass);



                        mAuth.getCurrentUser().linkWithCredential(credential)
                                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {

                                            String currentUser=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                            user.setUserId(currentUser);
                                            myRootRef.child("User").child(currentUser).setValue(user).addOnSuccessListener(new OnSuccessListener <Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
//                                                    if(myProgressBar!= null)
//                                                    {
//                                                        myProgressBar.cancel();
//                                                        myProgressBar.hide();
//                                                    }

                                                    signup_up.setVisibility(View.VISIBLE);
                                                    progressBar.setVisibility(View.INVISIBLE);

                                                    Intent intent=new Intent(SignUpActivity.this,Home.class);
                                                    startActivity(intent);
                                                    finish();

                                                }
                                            });


                                        } else {

                                            signup_up.setVisibility(View.VISIBLE);
                                            progressBar.setVisibility(View.INVISIBLE);

                                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();

                                        }


                                    }
                                });



                    }
                }

            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
