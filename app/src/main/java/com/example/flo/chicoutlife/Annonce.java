package com.example.flo.chicoutlife;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Annonce  extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference annonce;
    FirebaseStorage storage ;
    private StorageReference mStorageRef;
    String idUser ;
    String titre;

    Intent intent;
    Bundle bundleAnnonce;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.annonces_pages);
        intent = getIntent();
        bundleAnnonce = intent.getExtras();

        if (bundleAnnonce != null){
            Log.d("passage","Dans annonce intent non null");

                Log.d("passage","Dans annonce intent.hasextra CHEMIN ANNONCE");
                ImageView imageAnnonce;

                database = FirebaseDatabase.getInstance();
                storage =  FirebaseStorage.getInstance();
                annonce = database.getReference("Annonces").child(bundleAnnonce.getString("CHEMIN_ANNONCE"));
                Log.d("passage","Dans annonce nom chemin : " + bundleAnnonce.getString("CHEMIN_ANNONCE"));

                annonce.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        titre= (String)dataSnapshot.child("Titre").getValue();
                        String description = (String)dataSnapshot.child("Description").getValue();
                        String prix = (String) dataSnapshot.child("Prix").getValue();
                        idUser = (String) dataSnapshot.child("IdVendeur").getValue();

                        Log.d("passage","Dans annonce titre " + titre +" description" + description);
                        TextView viewTitre = (TextView)findViewById(R.id.titreAnnonce);
                        viewTitre.setText(titre);

                        TextView viewDescription = (TextView)findViewById(R.id.descriptionAnnonce);
                        viewDescription.setText(description);

                        TextView viewPrix = (TextView) findViewById(R.id.prixAnnonce);
                        viewPrix.setText(prix);

                        final ImageView viewImage = (ImageView) findViewById(R.id.imageAnnonce);

                        String cheminImg = (String)dataSnapshot.child("Image").getValue();
                        mStorageRef =  storage.getReference();
                        StorageReference imageRef = mStorageRef.child(cheminImg);

                        final long ONE_MEGABYTE = 4096 * 4096;
                        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {


                                /*Recuperation de l'image dans la base de donnee*/
                                Bitmap imageAnnonce =null;
                                imageAnnonce = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                imageAnnonce = Bitmap.createScaledBitmap(imageAnnonce,480,360,false);//640 480
                                viewImage.setImageBitmap(imageAnnonce);




                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                                Log.e("passage","failure dans choiceAnnonce " + exception.getMessage());
                            }
                        });


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                    });

        }

        // Bouton choose menu
        final Button btnContacterVendeur= findViewById(R.id.contactVendeurBtn);
        btnContacterVendeur.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatabaseReference userVendeur = database.getReference("RUser").child(idUser);
                userVendeur.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String email = (String) dataSnapshot.child("email").getValue();
                        Intent send = new Intent(Intent.ACTION_SENDTO);
                        String uriText = "mailto:" + Uri.encode(email) +
                                "?subject=" + Uri.encode("Annonce : " + titre) +
                                "&body=" + Uri.encode("Bonjour, Je suis intéressé par votre annonce "+titre);
                        Uri uri = Uri.parse(uriText);

                        send.setData(uri);
                        startActivity(Intent.createChooser(send, "Send mail..."));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("OnCancelledEmail","Echec récupération email");
                    }
                });



            }


        });


    }

    @Override
    //create the menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_goHome:
                Intent intentAccueil = new Intent(Annonce.this, Home_screen.class);
                startActivity(intentAccueil);
                finish();
                return true;
            case R.id.action_goBack:
                Intent intentRetour;
                String nomActivite = bundleAnnonce.getString("NOM_ACTIVITE");
                if( nomActivite!=null && nomActivite.equals("AnnoncesAccesLibre")){
                    intentRetour = new Intent(Annonce.this,AnnoncesAccesLibre.class);
                }
                else {
                    intentRetour = new Intent(Annonce.this, ToDoListInfosAnnonces.class);
                    Bundle bundleAnnonceExtra = new Bundle();

                    bundleAnnonceExtra.putString("NOM_PAGE", bundleAnnonce.getString("NOM_PAGE"));
                    bundleAnnonceExtra.putString("NOMBRE_PAGE", "2");
                    bundleAnnonceExtra.putString("TYPE_INTENT", "accesbyintent");
                }
                startActivity(intentRetour);
                finish();
                return true;

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
