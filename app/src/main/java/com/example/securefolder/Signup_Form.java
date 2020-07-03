package com.example.securefolder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Signup_Form extends AppCompatActivity {
    EditText txtEmail,txtPassword,txtConfirmPassword;
    Button registerButton;
    ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__form);

        getSupportActionBar().setTitle("SignUp Form");

        txtEmail = findViewById(R.id.emailEditTextId);
        txtPassword = findViewById(R.id.passwordEditTextId);
        txtConfirmPassword = findViewById(R.id.confirmPasswordEditTexId);
        registerButton = findViewById(R.id.registerButtonId);
        progressBar = findViewById(R.id.progressBarId);

        firebaseAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                String confirmPassword = txtConfirmPassword.getText().toString().trim();


                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Signup_Form.this,"Please Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }if(TextUtils.isEmpty(password)){
                    Toast.makeText(Signup_Form.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }if(TextUtils.isEmpty(confirmPassword)){
                    Toast.makeText(Signup_Form.this,"Please Enter Confirm Password",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.length() < 6){
                    Toast.makeText(Signup_Form.this,"Password Too Short, Please Enter At least 6 digit",Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.VISIBLE);

                if(password.equals(confirmPassword)){

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Signup_Form.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                       // email verification link sent to user email
                                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                 if(task.isSuccessful()){
                                                     startActivity(new Intent(Signup_Form.this,Login_form.class));
                                                     Toast.makeText(Signup_Form.this,"Registration Successful, Please Verify Your Email",Toast.LENGTH_SHORT).show();
                                                 }else{
                                                     Toast.makeText(Signup_Form.this,"Error:"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                                 }
                                            }
                                        });

                                    } else {

                                        if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                            Toast.makeText(Signup_Form.this,"User Is Already Registered",Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(Signup_Form.this,"Error:"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    // ...
                                }
                            });
                }
            }
        });
    }
}
