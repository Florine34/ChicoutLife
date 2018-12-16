package com.example.flo.chicoutlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Home_screen extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;

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

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null)
                {
                    startActivity(new Intent(Home_screen.this, singin_activity.class));
                }
            }
        };
        // Bouton Acces annonces

        final Button buttonAnnonces = findViewById(R.id.acces_libre_annonces);
        buttonAnnonces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAnnonces = new Intent(Home_screen.this,AnnoncesAccesLibre.class);
                startActivity(intentAnnonces);
                finish();
            }
        });

        // Bouton renseignement
        final Button buttonRenseignement = findViewById(R.id.renseignement);
        buttonRenseignement.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentRenseignement = new Intent(Home_screen.this, RenseignementActivity.class);
                startActivity(intentRenseignement);
                finish();
            }
        });

        // Bouton renseignement remplis
        final Button buttonRenseignementRemplis = findViewById(R.id.renseignement_remplis);
        buttonRenseignementRemplis.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle bundleToDoList = new Bundle();
                bundleToDoList.putString("NOMBRE_PAGE", "1");
                bundleToDoList.putString("NOM_PAGE","Tache001");
                bundleToDoList.putString("TYPE_INTENT","accesbyintent");

                Intent intentToDoList = new Intent(Home_screen.this, ToDoListInfosAnnonces.class);
                intentToDoList.putExtras(bundleToDoList);


                startActivity(intentToDoList);
                finish();
            }
        });




        // Button logout
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();

            }
        });


    }

    @Override
    //create the menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_quit:
                finish();
                System.exit(0);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
