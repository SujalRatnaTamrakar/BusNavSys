package com.example.chirag.navigationdrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpBus extends AppCompatActivity {

    private EditText FirstName;
    private EditText LastName;
    private EditText PhoneNo;
    private EditText Email;
    private EditText BusNo;
    private EditText BusRoute;
    private EditText password1;
    private EditText password2;
    FirebaseAuth myFirebaseAuth;
    DatabaseReference refe;
    Driver driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_bus);
        myFirebaseAuth = FirebaseAuth.getInstance();
        FirstName = (EditText) findViewById(R.id.editText3);
        LastName = (EditText) findViewById(R.id.editText2);
        PhoneNo = (EditText) findViewById(R.id.editText9);
        Email = (EditText) findViewById(R.id.editText10);
        BusNo = (EditText) findViewById(R.id.editText11);
        BusRoute = (EditText) findViewById(R.id.editText12);
        password1 = (EditText) findViewById(R.id.editText14);
        password2 = (EditText) findViewById(R.id.editText13);
        Button signUp = (Button) findViewById(R.id.button2);
        TextView login = (TextView) findViewById(R.id.textView6);
        driver = new Driver();
        refe= FirebaseDatabase.getInstance().getReference().child("Driver");

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName= FirstName.getText().toString();
                String lName= LastName.getText().toString();
                String email= Email.getText().toString();
                String busNo = BusNo.getText().toString();
                String busRoute = BusRoute.getText().toString();
                String pwd1= password1.getText().toString();
                String pwd2= password2.getText().toString();
                if (fName.isEmpty()){
                    FirstName.setError("Provide First Name");
                    FirstName.requestFocus();
                } else if (lName.isEmpty()){
                    LastName.setError("Provide Last Name");
                    LastName.requestFocus();
                } else if (email.isEmpty()){
                    Email.setError("Provide Email");
                    Email.requestFocus();
                } else if (busNo.isEmpty()) {
                    LastName.setError("Provide BusNo");
                    LastName.requestFocus();
                } else if (busRoute.isEmpty()) {
                    LastName.setError("Provide Bus Route");
                    LastName.requestFocus();
                }  else if (pwd1.isEmpty()){
                    password1.setError("Provide Password");
                    password1.requestFocus();
                } else if (pwd2.isEmpty()){
                    password2.setError("Re enter the password");
                    password2.requestFocus();
                } else if (pwd1.equals(pwd2)){
                    Long phone = Long.parseLong(PhoneNo.getText().toString().trim());
                    driver.setFirstName(FirstName.getText().toString().trim());
                    driver.setLastName(LastName.getText().toString().trim());
                    driver.setEmail(email);
                    driver.setPhoneNumber(phone);
                    driver.setPassword1(pwd1);
                    driver.setPassword2(pwd2);
                    refe.push().setValue(driver);
                    Toast.makeText(SignUpBus.this, "Database Appended for Bus Driver", Toast.LENGTH_SHORT).show();
                    myFirebaseAuth.createUserWithEmailAndPassword(email , pwd2).addOnCompleteListener(SignUpBus.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(SignUpBus.this, "SignUp error occurred",Toast.LENGTH_LONG).show();
                            } else {
                                FirebaseAuth.getInstance().signOut();
                                Intent intToLogIn= new Intent(SignUpBus.this , LoginActivity.class);
                                startActivity(intToLogIn);
                                Toast.makeText(SignUpBus.this, "Account has been made",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(SignUpBus.this, "Error Occurred",Toast.LENGTH_LONG).show();
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent (SignUpBus.this , LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
