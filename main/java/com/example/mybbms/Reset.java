package com.example.mybbms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Reset extends AppCompatActivity {
    Button resetBtn;
    EditText resetTxt;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        resetBtn=(Button)findViewById(R.id.resetPassword);
        resetTxt=(EditText) findViewById(R.id.reset);
        auth = FirebaseAuth.getInstance();

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });}
    public void resetPassword () {
        String email = resetTxt.getText().toString().trim();

        if(email.isEmpty()){
            resetTxt.requestFocus();
            resetTxt.setError("enter your email");
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        { resetTxt.requestFocus();
            resetTxt.setError("enter valid email");
            return;}
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"check your email for reset code",Toast.LENGTH_SHORT).show();}

                else
                {Toast.makeText(getApplicationContext(),"something went wrong",Toast.LENGTH_SHORT).show();}
            }
        });
    }
}