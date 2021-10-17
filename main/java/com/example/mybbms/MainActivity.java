package com.example.mybbms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    TextView register,forgetPassword;
    Button signIn;
    FirebaseAuth mauth;
    EditText editTextUserName, editTextpassWord;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register=(TextView)findViewById(R.id.register);
        forgetPassword=(TextView)findViewById(R.id.forgotPassword);
        register.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        signIn=(Button)findViewById(R.id.signIn);
        signIn.setOnClickListener(this);
        editTextUserName=(EditText)findViewById(R.id.userName);
        editTextpassWord=(EditText)findViewById(R.id.password);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        mauth=FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.register:
                startActivity(new Intent(this,Register.class));
                break;

            case R.id.signIn:

                userLogin();
                break;

            case R.id.forgotPassword:
                startActivity(new Intent(this,Reset.class));
        }

    }
    public void userLogin(){
        String userName = editTextUserName.getText().toString().trim();
        String passWord= editTextpassWord.getText().toString().trim();
        if(userName.isEmpty())
        { editTextUserName.requestFocus();
            Toast.makeText(getApplicationContext(),"please fill in your email",Toast.LENGTH_SHORT).show();


            return;}
        if(!Patterns.EMAIL_ADDRESS.matcher(userName).matches())
        {
            editTextUserName.requestFocus();
            Toast.makeText(getApplicationContext(),"enter a valid email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(passWord.isEmpty()){
            editTextpassWord.requestFocus();
            Toast.makeText(getApplicationContext(),"please fill in your password",Toast.LENGTH_SHORT).show();

            return;}
        if(passWord.length()<6){
            editTextpassWord.requestFocus();
            editTextpassWord.setError("minimum password length is  6 characters");
            return;
        }

        mauth.signInWithEmailAndPassword(userName,passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()) {
                        progressBar.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                        startActivity(intent);

                    }
                    else{
                        user.sendEmailVerification();
                        Toast.makeText(getApplicationContext(),"check your email to verify",Toast.LENGTH_SHORT).show();
                    }
                }
                else{Toast.makeText(getApplicationContext(),"task unsuccessful",Toast.LENGTH_SHORT).show();}
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}