package com.iodevs.quoteit.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.iodevs.quoteit.R;

public class ForgetPassword extends AppCompatActivity {
    Button send_btn,login_Btn;
    EditText email;
    TextView emailtext;

    FirebaseAuth mAuth;

   // Dialog myProgressBar;
    private ProgressBar myprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_forget_password);

       send_btn=(Button)findViewById(R.id.send_email);
       email=(EditText) findViewById(R.id.email_forget_pass);
       emailtext=(TextView)findViewById(R.id.Email_Send_confirm);
       login_Btn=(Button)findViewById(R.id.forget_pass_login_btn);
       myprogress=(ProgressBar) findViewById(R.id.forget_progressbar);
       myprogress.setVisibility(View.INVISIBLE);


       mAuth=FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.forget_Password_toolbar);
        toolbar.setTitle("Forget Password");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(ForgetPassword.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });





  //     myProgressBar=new  Dialog(this);

       send_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
//               myProgressBar.requestWindowFeature(Window.FEATURE_NO_TITLE);
//               myProgressBar.setContentView(R.layout.custom_dialog_progress);
//               myProgressBar.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//               myProgressBar.setCancelable(false);
//
//               myProgressBar.show();
             myprogress.setVisibility(View.VISIBLE);
             send_btn.setVisibility(View.GONE);
               sendEmail();
           }
       });
       login_Btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(ForgetPassword.this,MainActivity.class);
               startActivity(intent);
               finish();
           }
       });

    }

    private void sendEmail() {
        String mail = email.getText().toString().trim();

        if (TextUtils.isEmpty(mail)) {
            email.setError("Enter Your Email");
        } else {
            mAuth.sendPasswordResetEmail(mail)
                    .addOnCompleteListener(new OnCompleteListener <Void>() {
                        @Override
                        public void onComplete(@NonNull Task <Void> task) {
                            if (task.isSuccessful()) {
//                            if(myProgressBar!= null)
//                            {
//                                myProgressBar.cancel();
//                                myProgressBar.hide();
//                            }
                                send_btn.setVisibility(View.VISIBLE);
                                myprogress.setVisibility(View.GONE);
                                send_btn.setEnabled(false);
                                email.setVisibility(View.INVISIBLE);
                                emailtext.setVisibility(View.VISIBLE);

                                Log.d("Verification", "Email sent.");

                            } else {
//                            if(myProgressBar!= null)
//                            {
//                                myProgressBar.cancel();
//                                myProgressBar.hide();
//                            }
                                send_btn.setVisibility(View.VISIBLE);
                                myprogress.setVisibility(View.GONE);
                                Toast.makeText(ForgetPassword.this, "Error While Sending Email", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }
}
