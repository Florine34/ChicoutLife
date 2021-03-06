package com.example.flo.chicoutlife;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreateAnnonce  extends AppCompatActivity {
    private DatabaseReference rAnnonceDatabase = FirebaseDatabase.getInstance().getReference("Annonces");
    private FirebaseAuth mAuth;
    private static final int REQUEST_IMAGE = 100;
    private ImageView imageView;
    boolean photoCapture = false;
    String imageArticle;
    String key;

    private String[] titleTags = {
            "Appartement",
            "Electronique",
            "Nourriture",
            "Vetements"
    };
    private boolean[] etatTags = {
            false,
            false,
            false,
            false
    };


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
        final ImageButton photoAnnonce = (ImageButton) findViewById(R.id.photoAnnonce);
        imageView = (ImageView)findViewById(R.id.article);
        photoAnnonce.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                photoCapture = true;
                Intent intentPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // Renvoi vers une page de confirmation
                startActivityForResult(intentPhoto, REQUEST_IMAGE);
            }
        });

        //Image view de base afficher
        imageView.setImageResource(R.drawable.image_vide);

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
                Log.d("passage","Dans CreateAnnonce.OnCreate.buttonValidersetOnclick  photoCapture = " + photoCapture + "imageArticle chemin " + imageArticle);
                etatTags  = new boolean[]{appart.isChecked(), electro.isChecked(), nourriture.isChecked(), vetement.isChecked()};
                //initialisation chemin image dans bdd
                if(photoCapture){
                    imageArticle = UUID.randomUUID().toString()+".jpg";
                }
                else {
                    imageArticle = getString(R.string.photo_vide);

                }
                FirebaseStorage storage = FirebaseStorage.getInstance();

                // Create a storage reference from our app
                StorageReference storageRef = storage.getReferenceFromUrl("gs://chicoutlife-37a65.appspot.com");

                // Create a reference to "mountains.jpg"
                StorageReference mountainsRef = storageRef.child(imageArticle);

                // Create a reference to 'images/mountains.jpg'
                StorageReference mountainImagesRef = storageRef.child("images/"+imageArticle);

                // While the file names are the same, the references point to different files

                mountainsRef.getName().equals(mountainImagesRef.getName());    // true
                mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false



                // Get the data from an ImageView as bytes
                imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();
                Bitmap bitmap = imageView.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                if(bitmap!=null){
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                }



                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    }
                });

                if(!textDescription.getText().toString().equals("") && !textPrix.getText().toString().equals("") && !textTitre.getText().toString().equals("")) {
                    writeNewRAnnonce(dateToday, textDescription.getText().toString(), idVend, imageArticle, textPrix.getText().toString(), textTitre.getText().toString());
                    Intent intentCreateAnnonce = new Intent(CreateAnnonce.this, Confirm_Create_Annoce.class);
                    startActivity(intentCreateAnnonce);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Vous devez remplir tous les renseignements", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK) {
            //Process and display the image
            Bitmap imageBitmap = (Bitmap)data.getExtras().get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }

    private void writeNewRAnnonce(String dateAjout, String description, String idVendeur, String image, String prix, String titre) {
        if(photoCapture){
            key = image.substring(0,image.length()-4); // Tronquer le .jpg
        }
        else{
            key = image.substring(0,image.length()-4)+titre;
        }

        RAnnonce annonce = new RAnnonce(dateAjout, description, idVendeur, image, prix,titre);

        Map<String, Object> annonceToAdd = annonce.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key,annonceToAdd);
        rAnnonceDatabase.updateChildren(childUpdates);

        // tags
        for(int i= 0; i< titleTags.length; i++){
            FirebaseDatabase.getInstance().getReference("Annonces").child(key).child("Tags").child(titleTags[i]).setValue(etatTags[i]);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.action_quit:
                finish();
                System.exit(0);
                return true;
            case R.id.action_goHome:
                Intent intentAccueil = new Intent(CreateAnnonce.this, Home_screen.class);
                startActivity(intentAccueil);
                finish();
                return true;
            case R.id.action_goBack:

                Intent intentRetour = new Intent(CreateAnnonce.this, Confirm_Create_Annoce.class); // TODO
                startActivity(intentRetour);
                finish();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

}
