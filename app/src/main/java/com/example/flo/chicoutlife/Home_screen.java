package com.example.flo.chicoutlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Home_screen extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    private TextView mIdTextView;
    private TextView mMailTextView;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_activity);

        Button button = (Button) findViewById(R.id.signout);


        mAuth = FirebaseAuth.getInstance();

        mMailTextView = (TextView) findViewById(R.id.infoMail);
        mMailTextView.setText("Email User: "+mAuth.getCurrentUser().getEmail());

        mIdTextView = (TextView) findViewById(R.id.textView);
        mIdTextView.setText("ID User: "+mAuth.getUid());

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null)
                {
                    startActivity(new Intent(Home_screen.this, singin_activity.class));
                }
            }
        };

        final Button buttonInscription = findViewById(R.id.renseignement);
        buttonInscription.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentAccueil = new Intent(Home_screen.this, RenseignementActivity.class);
                startActivity(intentAccueil);
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();


            }
        });


    }
}
