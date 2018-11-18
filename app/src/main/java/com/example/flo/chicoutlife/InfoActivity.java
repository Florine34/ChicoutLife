package com.example.flo.chicoutlife;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class InfoActivity extends Activity {

    Context context = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);
        
        FirebaseDatabase database;
        final DatabaseReference infoPage;

        Intent intent= getIntent();
        Bundle b = intent.getExtras();

        if(b!=null)
        {
            String page =(String) b.get("NOM_PAGE");
            database = FirebaseDatabase.getInstance();
            infoPage = database.getReference("InfosPages").child(page);
            Log.d("passage","page : " + page);
            infoPage.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    String titre = (String) dataSnapshot.child("Titre").getValue();
                    String texte = (String) dataSnapshot.child("Texte").getValue();

                    TextView viewTitre = (TextView)findViewById(R.id.titreInfoPage);
                    viewTitre.setText(titre);

                    TextView viewTexte = (TextView)findViewById(R.id.contenuInfopage);
                    viewTexte.setText(texte);

                    LinearLayout linearLienWeb = (LinearLayout) findViewById(R.id.linearLienWeb);
                    DataSnapshot liens = dataSnapshot.child("LiensWeb");
                    for(DataSnapshot lien : liens.getChildren()){
                        TextView lienX  = new TextView(context);
                        lienX.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));

                        lienX.setAutoLinkMask(Linkify.ALL);
                        lienX.setText((String)lien.getValue());


                        linearLienWeb.addView(lienX);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value

                }
            });
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
                Intent intentAccueil = new Intent(InfoActivity.this, Home_screen.class);
                startActivity(intentAccueil);
                finish();
                return true;

            case R.id.action_goBack:
                Intent intentRetour = new Intent(InfoActivity.this, ToDoListActivity.class);
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
