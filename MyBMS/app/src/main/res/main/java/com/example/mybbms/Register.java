package com.example.mybbms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText emailTxt,pass,repass,nameTxt;
    Button signup,existedUser;
    FirebaseAuth auth;

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth =FirebaseAuth.getInstance();
        existedUser=(Button)findViewById(R.id.existingUser);
        signup= (Button)findViewById(R.id.signUp);
        nameTxt=(EditText)findViewById(R.id.Name);
        emailTxt =(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.Password);
        repass=(EditText)findViewById(R.id.retypePassword);
        existedUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerUser();
            }
        });
    }
    public void registerUser(){
        String email,password,repas,name;
        name=nameTxt.getText().toString().trim();
        email=emailTxt.getText().toString().trim();
        password=pass.getText().toString().trim();
        repas=repass.getText().toString().trim();

        if(name.isEmpty()){
            nameTxt.requestFocus();
            nameTxt.setError("enter user name");
            return;
        }

        if(email.isEmpty())
        { emailTxt.requestFocus();
            Toast.makeText(getApplicationContext(),"please provide your email",Toast.LENGTH_SHORT).show();
            return;}

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailTxt.requestFocus();
            Toast.makeText(getApplicationContext(),"enter a valid email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.isEmpty()){
            pass.requestFocus();
            pass.setError("fill in your password");
            Toast.makeText(getApplicationContext(),"please fill in your password",Toast.LENGTH_SHORT).show();

            return;}
        if(pass.length()<6){ pass.requestFocus();
            pass.setError("minimum password length is  6 characters");
            return;
        }
        if(!password.matches(repas))
        {repass.requestFocus();
            repass.setError("retyped password not match password");
            return;}
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(name,email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getApplicationContext(),"user registered Successfully",Toast.LENGTH_SHORT).show();
                                            }
                                            else{ Toast.makeText(getApplicationContext(),"task unsuccessful",Toast.LENGTH_SHORT).show();}
                                        }

                                    });

                        }
                        else{Toast.makeText(getApplicationContext(),"task Unsuccessful",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}