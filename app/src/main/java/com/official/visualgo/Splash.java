package com.official.visualgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash extends AppCompatActivity {

    Handler h;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        auth=FirebaseAuth.getInstance();


                h=new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        if(FirebaseAuth.getInstance().getCurrentUser()!=null ){
                            startActivity(new Intent(Splash.this,MainActivity.class));
                            finish();
                        }else{
                            startActivity(new Intent(Splash.this,Introslides.class));
                            finish();
                        }



                    }
                },2000);




}
}
