package com.example.mybbms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity3 extends AppCompatActivity {
    Switch auto,fan,swing,toy;
    FirebaseAuth auth;
    TextView temperature,humidity,sound;
    DatabaseReference refer,reference,auto_reference,fan_reference,swing_reference,toy_reference,humid,temp,cry;
    FirebaseUser user;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        //user= FirebaseAuth.getInstance().getCurrentUser();
       // userId=user.getUid();

        refer= FirebaseDatabase.getInstance().getReference("Status");
        reference= FirebaseDatabase.getInstance().getReference("Controller");
        //reference=refer.child(userId);
        temperature=(TextView)findViewById(R.id.temperature);
        humidity=(TextView)findViewById(R.id.humidity);
        sound=(TextView)findViewById(R.id.sound);
        humid= refer.child("Humidity");

        temp= refer.child("Temperature");
        cry=refer.child("Sound Detection");

        auto=(Switch)findViewById(R.id.auto_switch);
        fan=(Switch)findViewById(R.id.fan_switch);
        swing=(Switch)findViewById(R.id.Swing_switch);
        toy=(Switch)findViewById(R.id.toy_switch);
        auto_reference=reference.child("AUTO");
        swing_reference=reference.child("Motor");
        toy_reference=reference.child("Music");
        fan_reference=reference.child("Fan");



        auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    auto_reference.setValue("ON");
                }
                else{auto_reference.setValue("OFF");}
            }
        });

        toy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    toy_reference.setValue("ON");
                }
                else{  toy_reference.setValue("OFF");}
            }
        });
        swing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    swing_reference.setValue("ON");

                }
                else{ swing_reference.setValue("OFF");}
            }
        });

        fan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    fan_reference.setValue("ON");
                }
                else{
                    fan_reference.setValue("OFF");
                }
            }
        });
        humid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value;
                value =    snapshot.getValue(String.class);
                humidity.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"failed to read database",Toast.LENGTH_SHORT).show();

            }
        });
        cry.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value;
                value =    snapshot.getValue(String.class);
                sound.setText(value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"failed to read database",Toast.LENGTH_SHORT).show();

            }
        });

        temp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String value;
                value =    snapshot.getValue(String.class);
                temperature.setText(value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"failed to read database",Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.signout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.signout:
                auth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}