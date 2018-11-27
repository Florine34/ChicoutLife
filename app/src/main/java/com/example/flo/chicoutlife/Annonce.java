package com.example.flo.chicoutlife;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class Annonce  extends Activity {

    FirebaseDatabase database;
    DatabaseReference annonce;
    FirebaseStorage storage ;
    private StorageReference mStorageRef;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.annonces_pages);
        Intent intent = getIntent();

        if (intent != null){
            Log.d("passage","Dans annonce intent non null");
            if (intent.hasExtra("CHEMIN_ANNONCE")){ // vérifie qu'une valeur est associée à la clé “edittext”
                Log.d("passage","Dans annonce intent.hasextra CHEMIN ANNONCE");
                ImageView imageAnnonce;

                database = FirebaseDatabase.getInstance();
                storage =  FirebaseStorage.getInstance();
                annonce = database.getReference("Annonces").child(intent.getStringExtra("CHEMIN_ANNONCE"));
                Log.d("passage","Dans annonce nom chemin : " + intent.getStringExtra("CHEMIN_ANNONCE"));
                annonce.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String titre= (String)dataSnapshot.child("Titre").getValue();
                        String description = (String)dataSnapshot.child("Description").getValue();
                        String prix = (String) dataSnapshot.child("Prix").getValue();

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
                                imageAnnonce = Bitmap.createScaledBitmap(imageAnnonce,640,480,false);
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
        }


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
                Intent intentRetour = new Intent(Annonce.this, ChoiceAnnonce.class); // TODO
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
