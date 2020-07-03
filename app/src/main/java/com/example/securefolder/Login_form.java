package com.example.securefolder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_form extends AppCompatActivity {

    private EditText txtEmail,txtPassword;
    private Button loginButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        getSupportActionBar().setTitle("Login Form");

        txtEmail = findViewById(R.id.emailEditTextId);
        txtPassword = findViewById(R.id.passwordEditTextId);
        loginButton = findViewById(R.id.loginButtonId);

        firebaseAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Login_form.this,"Please Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }if(TextUtils.isEmpty(password)){
                    Toast.makeText(Login_form.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.length() < 6){
                    Toast.makeText(Login_form.this,"Password Too Short, Please Enter At least 6 digit",Toast.LENGTH_SHORT).show();
                }


                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login_form.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                        //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        finish();
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(Login_form.this,"Please Verify Your Email Id",Toast.LENGTH_SHORT).show();
                                    }


                                } else {

                                    Toast.makeText(Login_form.this,"Login Failed or User Not Available",Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });

            }
        });
    }

    public void signUpForm(View view) {

        startActivity(new Intent(getApplicationContext(),Signup_Form.class));
    }
}
