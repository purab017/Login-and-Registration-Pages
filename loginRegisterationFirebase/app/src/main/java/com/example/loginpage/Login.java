package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    TextInputEditText EditTextEmail, EditTextPassword;
    Button btnLogin;
    TextView textView;

    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is already log in! if so it will go to main activity
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(Login.this , MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditTextEmail= findViewById(R.id.email);
        EditTextPassword= findViewById(R.id.password);
        btnLogin = findViewById(R.id.btn_login);


        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        textView = findViewById(R.id.registerNow);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create intent to go to register activity
                Intent intent = new Intent(Login.this , Register.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                //read text from edit text!
                String email,password;
                email =  String.valueOf(EditTextEmail.getText());
                password = String.valueOf(EditTextPassword.getText());

                //both fields can't be empty!

                if(TextUtils.isEmpty(email) ){
                    Toast.makeText(Login.this , "Please fill email field" , Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Login.this , "Please fill password field" , Toast.LENGTH_SHORT).show();
                    return;

                }

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){

                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            //if login work open main Activity!
                            Intent intent = new Intent(Login.this , MainActivity.class);
                            startActivity(intent);
                            finish();

                        }else{
                            Toast.makeText(Login.this, "Login failure", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

    }
}