package com.example.flo.chicoutlife;

import android.app.Activity;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Objects;

public class InfoActivity extends Fragment {
    ViewGroup racine;
    Context context ;
    static Intent intentInfoActivity;
    Bundle saved;
    static Bundle bundleInfo;


    public static Fragment newInstance(Intent intent){
        intentInfoActivity = intent ;
        return new InfoActivity();
    }

    public static Fragment newInstance(Bundle bundle){
        bundleInfo = bundle ;
        Log.d("passage","Dans InfoActivity.newInstance(bundle) " + bundle.get("NOM_PAGE"));
        return new InfoActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saved = savedInstanceState;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup racineView =(ViewGroup) inflater.inflate(R.layout.info_activity,container,false);
        racine = racineView;
        return racine;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseDatabase database;
        final DatabaseReference infoPage;

        context = getContext();
        String test = "truc.jpg";
        String test2 = test.substring(0,test.length()-4);
        if(bundleInfo!=null)//On regarde si le bundle est null sinon InfoActivity Crah a la rotation en mode tablette a gauche du viewPager (mal fermer ?)
        {
            String page =(String) bundleInfo.get("NOM_PAGE");
            if(page != null) {
                database = FirebaseDatabase.getInstance();
                infoPage = database.getReference("InfosPages").child(page);
                Log.d("passage", " dans infoActivity page : " + page);
                infoPage.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        String titre = (String) dataSnapshot.child("Titre").getValue();
                        String texte = (String) dataSnapshot.child("Texte").getValue();


                        TextView viewTitre = (TextView) ((getActivity())).findViewById(R.id.titreInfoPage);
                        Log.d("passage", "titre: " + titre);
                        viewTitre.setText(titre);

                        TextView viewTexte = (TextView) getActivity().findViewById(R.id.contenuInfopage);

                        texte = texte.replace(".", "\n");
                        viewTexte.setText(texte);

                        LinearLayout linearLienWeb = (LinearLayout) getActivity().findViewById(R.id.linearLienWeb);
                        DataSnapshot liens = dataSnapshot.child("LiensWeb");
                        for (DataSnapshot lien : liens.getChildren()) {
                            TextView lienX = new TextView(context);
                            lienX.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));

                            lienX.setAutoLinkMask(Linkify.ALL);
                            lienX.setText((String) lien.getValue());


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

}
