package com.example.flo.chicoutlife;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.util.Log;
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
}
