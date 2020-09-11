package com.stark.quizzer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Quizzer_signup extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText pass,email;
    private TextView login;
    private Button register;
    private LazyLoader loader;
    private  Intent intent;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizzer_signup);
        mAuth=FirebaseAuth.getInstance();
        loader=findViewById(R.id.lazyLoader);
        pass=findViewById(R.id.password);
        login=findViewById(R.id.login);
        email=findViewById(R.id.email);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SignUp");
        register=findViewById(R.id.register);
         intent=new Intent(Quizzer_signup.this,MainActivity.class);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.setVisibility(View.VISIBLE);
                if(email.getText().toString().isEmpty())
                {
                    email.setError("Required");
                }
                else
                {
                    email.setError(null);
                }
                if(pass.getText().toString().isEmpty())
                {
                    pass.setError("Required");
                }
                else
                {
                    pass.setError(null);
                }
              if(!email.getText().toString().equals("") && !pass.getText().toString().equals("")) {
                mAuth.createUserWithEmailAndPassword(email.getText().toString(),pass.getText().toString()).addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    startActivity(intent);
                                    finish();

                                }
                                else
                                {
                                    Toast.makeText(Quizzer_signup.this, "SignUp failed", Toast.LENGTH_SHORT).show();
                                }
                                loader.setVisibility(View.INVISIBLE);

                            }
                        }
                );}

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.setVisibility(View.VISIBLE);
                if(email.getText().toString().isEmpty())
                {
                    email.setError("Required");
                }
                else
                {
                    email.setError(null);
                }
                if(pass.getText().toString().isEmpty())
                {
                    pass.setError("Required");
                }
                else
                {
                    pass.setError(null);
                }

                mAuth.signInWithEmailAndPassword(email.getText().toString(),pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(Quizzer_signup.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                        loader.setVisibility(View.INVISIBLE);

                    }
                });

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null)
        {
            Intent cIntetnt=new Intent(Quizzer_signup.this,MainActivity.class);
            startActivity(cIntetnt);
             finish();
        }

    }
}
