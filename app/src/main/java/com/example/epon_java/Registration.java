package com.example.epon_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Registration extends AppCompatActivity implements View.OnClickListener {
    // Firebase //
    private TextView registerUser;
    private EditText editTextFullName, editTextEmaill, editTextPassword;
    private FirebaseAuth mAuth;
    // Firebase //
    TextInputLayout gender;
    AutoCompleteTextView act_gender;
    EditText birthdate;

    DatePickerDialog.OnDateSetListener onDateSetListener;

    ArrayList<String> arrayList_gender;
    ArrayAdapter<String> arrayAdapter_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

       // Firebase //
       mAuth = FirebaseAuth.getInstance();

       registerUser =(Button) findViewById(R.id.register);
       registerUser.setOnClickListener(this);

       editTextFullName = (EditText) findViewById(R.id.fullname);
       editTextEmaill =(EditText) findViewById(R.id.emailadd);
       editTextPassword = (EditText) findViewById(R.id.confirmpass);

        // Firebase //

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        birthdate = findViewById(R.id.date);

        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Registration.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener, year, month, day);
              datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();



            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth+" / "+month+" / "+year;
                birthdate.setText(date);

            }

        };


        gender = (TextInputLayout) findViewById(R.id.gender);
        act_gender = (AutoCompleteTextView) findViewById(R.id.act_gender);

        arrayList_gender = new ArrayList<>();
        arrayList_gender.add("Male");
        arrayList_gender.add("Female");

        arrayAdapter_gender = new ArrayAdapter<>(getApplicationContext(),R.layout.tv_entity,arrayList_gender);
        act_gender.setAdapter((arrayAdapter_gender));


        Button register = findViewById(R.id.register);
        TextView textView7 = findViewById(R.id.textView7);
        ImageView imageView = findViewById(R.id.imageView);

        register.setOnClickListener(this);
        textView7.setOnClickListener(this);
        imageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textView7:
                Toast.makeText(this, "Login Page", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Registration.this,MemberLogin.class);
                startActivity(intent);
        }
        switch (v.getId()){
            case R.id.imageView:
                Toast.makeText(this, "Landing Page", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Registration.this,MainActivity.class);
                startActivity(intent);
        }

        switch (v.getId()){
            case R.id.register:
               registerUser();
        }



    }

    public void onRadioButtonClick(View view) {
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        Toast.makeText(this, "Member Type selected", Toast.LENGTH_SHORT).show();

    }

    private void registerUser()
    {
        String emailadd = editTextEmaill.getText().toString().trim();
        String confirmpass = editTextPassword.getText().toString().trim();
        String fullname = editTextFullName.getText().toString().trim();

        if(fullname.isEmpty()){
            editTextFullName.setError("Full Name is required!");
            editTextFullName.requestFocus();
            return;
        }
        if(emailadd.isEmpty()){
            editTextEmaill.setError("Email is required!");
            editTextEmaill.requestFocus();
            return;
        }
        if(confirmpass.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailadd).matches()){
            editTextEmaill.setError("Please provide a valid email");
            editTextEmaill.requestFocus();
            return;
    }
        if(confirmpass.length()<6){
            editTextPassword.setError("Min password length should be 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(emailadd,confirmpass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user = new User(fullname,emailadd);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    
                                    if(task.isSuccessful()){
                                        Toast.makeText(Registration.this, "Registered successfully!", Toast.LENGTH_LONG ).show();


                                    }else{
                                        Toast.makeText(Registration.this, "Failed to register. Try again!", Toast.LENGTH_LONG ).show();
                                    }
                                    
                                }
                            });
                        }else{
                            Toast.makeText(Registration.this, "Failed to register.", Toast.LENGTH_LONG ).show();
                        }
                    }
                });
    }

    }
