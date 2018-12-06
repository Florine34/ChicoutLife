package com.example.flo.chicoutlife;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateAnnonce  extends AppCompatActivity {
    private DatabaseReference rAnnonceDatabase = FirebaseDatabase.getInstance().getReference("Annonces");
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_annonce);

        // Récupère la date d'aujourd'hui
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        final String dateToday = formatter.format(date);

        // Récupère la Description de l'annonce
        EditText textDescription = (EditText) findViewById(R.id.RecupDescrAnnonce);
        final String description = textDescription.getText().toString();

        // Récupère l'id  du Vendeur
        mAuth = FirebaseAuth.getInstance();
        final String idVend = mAuth.getUid();


        // Récupère l'image
        final Bitmap imageArticle = null;

        // Récupère le Prix
        EditText textPrix = (EditText) findViewById(R.id.RecupPrixArticle);
        final String prix = textPrix.getText().toString();


        // Récupère les tags
        final boolean tag_appart = false;
        final boolean tag_elect = false;
        final boolean tag_nourriture = false;
        final boolean tag_vetement = false;

        // Récupère le Titre de l'annonce
        EditText textTitre = (EditText) findViewById(R.id.RecupTitre);
        final String titre = textTitre.getText().toString();


        // Bouton de validation pour la création d'une annonce
        final Button buttonValider = findViewById(R.id.valider);
        buttonValider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Insert l'objet annonce dans fire base
                writeNewRAnnonce(dateToday, description, idVend, imageArticle, prix, tag_appart, tag_elect, tag_nourriture, tag_vetement, titre);
                Intent intentCreateAnnonce = new Intent(CreateAnnonce.this, CreateAnnonce.class); // Renvoi vers une page de confirmation
                startActivity(intentCreateAnnonce);
                finish();
            }
        });




    }

    private void writeNewRAnnonce(String dateAjout, String description, String idVendeur, Bitmap image, String prix, boolean tag_appartement, boolean tag_electonique, boolean tag_tag_nourriture, boolean tag_vetement, String titre) {

        String key = "Annonce";
        RAnnonce annonce = new RAnnonce(dateAjout, description, idVendeur, image, prix, tag_appartement, tag_electonique, tag_tag_nourriture, tag_vetement, titre);

        Map<String, Object> annonceToAdd = annonce.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key,annonceToAdd);
        rAnnonceDatabase.updateChildren(childUpdates);
    }

}
