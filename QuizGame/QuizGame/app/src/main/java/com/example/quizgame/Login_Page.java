package com.example.quizgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Page extends AppCompatActivity {

    EditText mail;
    EditText password;
    Button signIn;
    SignInButton signInGoogle;

    TextView signUp;

    TextView forgotPassword;
    ProgressBar progressBarSignIn;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);

        mail = findViewById(R.id.editTextEmailAddress);
        password = findViewById(R.id.editTextLoginPassword);
        signIn = findViewById(R.id.buttonLoginSignin);
        signInGoogle = findViewById(R.id.buttonLoginGoogleSingin);
        signUp = findViewById(R.id.textViewLoginSignup);
        forgotPassword = findViewById(R.id.textViewLoginForgetPassword);
        progressBarSignIn = findViewById(R.id.progressBarSignin);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = mail.getText().toString();
                String userPassword = password.getText().toString();
                signInWithFirebase(userEmail, userPassword);
            }
        });

        signInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Google sign-in logic placeholder
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login_Page.this, Sign_Up_Page.class);
                startActivity(i);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Login_Page.this, Forgot_Password.class);
                startActivity(i);

            }
        });
    }

    public void signInWithFirebase(String userEmail, String userPassword) {
        progressBarSignIn.setVisibility(View.VISIBLE);
        signIn.setClickable(false);

        auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBarSignIn.setVisibility(View.INVISIBLE);
                        if (task.isSuccessful()) {
                            Intent i = new Intent(Login_Page.this, MainActivity222.class);
                            startActivity(i);
                            finish();
                            Toast.makeText(Login_Page.this, "Logowanie powiodło się", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Login_Page.this, "Logowanie nie powiodło się", Toast.LENGTH_SHORT).show();
                            signIn.setClickable(true);
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user =auth.getCurrentUser();
        if (user!=null){
                Intent i = new Intent(Login_Page.this, MainActivity222.class);
                startActivity(i);
                finish();
        }
    }
}