package com.example.epon_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MemberLogin extends AppCompatActivity implements View.OnClickListener {
   /* changes
    EditText UsernameEt, PasswordEt;
    /* changes */

    private EditText editTextEmail, editTextPassword;
    private Button signIN;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_login);
        /* changes
        UsernameEt = (EditText)findViewById(R.id.etUserName);
        PasswordEt = (EditText)findViewById(R.id.etPassword);
         changes */

        TextView textView7 = findViewById(R.id.textView7);
        ImageView imageView = findViewById(R.id.imageView);
        Button btnLogin =findViewById(R.id.btnLogin);

        textView7.setOnClickListener(this);
        imageView.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        signIN = (Button)  findViewById(R.id.btnLogin);
        signIN.setOnClickListener(this);

        editTextEmail = (EditText)findViewById(R.id.etUserName);
        editTextPassword = (EditText) findViewById(R.id.etPassword);

        mAuth = FirebaseAuth.getInstance();


    }
    /* changes
    public void onLogin(View view){
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        String type = "login";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, username, password);
    }
     changes */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textView7:
                Toast.makeText(this, "Registration Page", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MemberLogin.this,Registration.class);
                startActivity(intent);
        }
        switch (v.getId()){
            case R.id.imageView:
                Toast.makeText(this, "Landing Page", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MemberLogin.this,MainActivity.class);
                startActivity(intent);

        }

        switch (v.getId()){
            case R.id.btnLogin:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String etUserName = editTextEmail.getText().toString().trim();
                String etPassword = editTextPassword.getText().toString().trim();

                if(etUserName.isEmpty()){
                    editTextEmail.setError("Email is required!");
                    editTextEmail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(etUserName).matches()){
                editTextEmail.setError("Please enter a valid email!");
                editTextEmail.requestFocus();
                return;
                }
                if(etPassword.isEmpty()){
                    editTextPassword.setError("Password is required!");
                    editTextPassword.requestFocus();
                    return;
                }
                if(etPassword.length() < 6){
                    editTextPassword.setError("Min password length is 6 characters!");
                    editTextPassword.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(etUserName, etPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            //redirect to user profile
                            startActivity(new Intent(MemberLogin.this,Dashboard.class));
                            
                        }else{
                            Toast.makeText(MemberLogin.this, "Failed to Login! Please Check your credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}