package com.example.flo.chicoutlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.auth.FirebaseAuth;

public class CreateAnnonce  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renseignement_arrive_activity);

        // Faire un object Annonce.java avec les truc suivant :
        
        // Remplir firebase avec l'objet annonce bien remplit

        // Récupère le Titre de l'annonce

        // Récupère la Description de l'annonce

        // Récupère le Prix
        EditText textPrix = (EditText) findViewById(R.id.numberText);
        String prix = textPrix.getText().toString();

        // Récupère l'image

        // Récupère les tags

        // Récupère la date d'aujourd'hui

        // Récupère l'id  du Vendeur

        // Bouton de validation pour la création d'une annonce
        final Button buttonValider = findViewById(R.id.valider);
        buttonValider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Insert l'objet annonce dans fire base
                Intent intentCreateAnnonce = new Intent(CreateAnnonce.this, CreateAnnonce.class); // Renoi vers une page de confirmation
                startActivity(intentCreateAnnonce);
                finish();
            }
        });
    }
}
