package com.example.flo.chicoutlife;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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
    private static final int REQUEST_IMAGE = 100;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_annonce);

        // Récupère la date d'aujourd'hui
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        final String dateToday = formatter.format(date);

        // Récupère la Description de l'annonce
        final EditText textDescription = (EditText) findViewById(R.id.RecupDescrAnnonce);

        // Récupère l'id  du Vendeur
        mAuth = FirebaseAuth.getInstance();
        final String idVend = mAuth.getUid();

        // Récupère l'image
        final Button photoAnnonce = (Button) findViewById(R.id.photoAnnonce);
        imageView = (ImageView)findViewById(R.id.article);
        photoAnnonce.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // Renvoi vers une page de confirmation
                startActivityForResult(intentPhoto, REQUEST_IMAGE);
            }
        });
        final Bitmap imageArticle = null;

        // Récupère le Prix
        final EditText textPrix = (EditText) findViewById(R.id.RecupPrixArticle);

        // Récupère les tags
        final CheckBox appart = (CheckBox)findViewById(R.id.tag_appart);
        final CheckBox electro = (CheckBox)findViewById(R.id.tag_electro);
        final CheckBox nourriture = (CheckBox)findViewById(R.id.tag_nourriture);
        final CheckBox vetement = (CheckBox)findViewById(R.id.tag_vetement);

        // Récupère le Titre de l'annonce
        final EditText textTitre = (EditText) findViewById(R.id.RecupTitre);


        // Bouton de validation pour la création d'une annonce
        final Button buttonValider = findViewById(R.id.publierAnnonce);

        buttonValider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Insert l'objet annonce dans fire base
                writeNewRAnnonce(dateToday, textDescription.getText().toString(), idVend, imageArticle, textPrix.getText().toString(), appart.isChecked(), electro.isChecked(), nourriture.isChecked(), vetement.isChecked(), textTitre.getText().toString());
                Intent intentCreateAnnonce = new Intent(CreateAnnonce.this, CreateAnnonce.class); // Renvoi vers une page de confirmation
                startActivity(intentCreateAnnonce);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK) {
            //Process and display the image
            Bitmap userImage = (Bitmap)data.getExtras().get("data");
            imageView.setImageBitmap(userImage);
        }
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
