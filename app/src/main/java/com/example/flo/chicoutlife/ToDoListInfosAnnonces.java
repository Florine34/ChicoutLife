package com.example.flo.chicoutlife;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

public class ToDoListInfosAnnonces extends AppCompatActivity {
    private ViewPager pages;
    private PagerAdapter adapterPages;
    private boolean tablette;
    private  boolean land;
    private boolean infosAnnonces;// Pour savoir si le viewPager infoAnnonces est afficher
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity_contenu_arrivee);
        tablette = getResources().getBoolean(R.bool.tablette);
        land= getResources().getBoolean(R.bool.land);
        Log.d("passage","ToDoListInfosAnnonces dans on Create bool tablette " + tablette);
        if(tablette){

            Bundle bundle = getIntent().getExtras();
            bundle.putString("NOM_ACTIVITE","ToDoListInfosAnnonces");

            pages = findViewById(R.id.viewPagerOrientation);
            adapterPages = new FragmentsSwipeAdapter(getSupportFragmentManager(), bundle);
            ((FragmentsSwipeAdapter) adapterPages).addFragmentList(InfoActivity.newInstance(bundle));
            if(bundle.get("NOMBRE_PAGES") == "2") {
                ((FragmentsSwipeAdapter) adapterPages).addFragmentList(ChoiceAnnonce.newInstance(bundle));
            }
            pages.setAdapter(adapterPages);
            infosAnnonces = true;
        }
        else{
            infosAnnonces = false;
            Log.d("passage" , "Dans ToDoListInfosAnnonces.OnCreate fragments support manager?" + getSupportFragmentManager().getFragments().size());

        }


    }
    public void reinstanciatePageAdapter(Bundle bundle){

        Log.d("passage","est dans ToDoListInfosAnnocnes dans reinstanciatePageAdapter");
        boolean tablette = getResources().getBoolean(R.bool.tablette);
        pages=findViewById(R.id.viewPagerOrientation);
        if(!tablette) {

            findViewById(R.id.viewPagerOrientation).setVisibility(View.VISIBLE);
            findViewById(R.id.nomsPages).setVisibility(View.VISIBLE);
            findViewById(R.id.titletodo).setVisibility(View.GONE);
            findViewById(R.id.fragments_todolist).setVisibility(View.GONE);
            infosAnnonces = true;

        }

        adapterPages = new FragmentsSwipeAdapter(getSupportFragmentManager(), bundle);
        ((FragmentsSwipeAdapter) adapterPages).removeFragmentAllList();
        ((FragmentsSwipeAdapter) adapterPages).addFragmentList(InfoActivity.newInstance(bundle));
        Log.d("passage", "est dans ToDoListInfosAnnocnes.reinstanciatePageAdapter " + bundle.getString("NOMBRE_PAGE"));
        if (bundle.getString("NOMBRE_PAGE") == "2") {
            bundle.putString("NOM_ACTIVITE","ToDoListInfosAnnonces");
            ((FragmentsSwipeAdapter) adapterPages).addFragmentList(ChoiceAnnonce.newInstance(bundle));
        }

        pages.setAdapter(adapterPages);
        adapterPages.startUpdate(pages);

        Log.d("passage","est dans ToDoListInfosAnnocnes dans reinstanciatePageAdapter changement effectuer");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        
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
                case R.id.action_quit:
                    finish();
                    System.exit(0);
                    return true;
                case R.id.action_goHome:
                    Intent intentAccueil = new Intent(ToDoListInfosAnnonces.this, Home_screen.class);
                    startActivity(intentAccueil);
                    finish();
                    return true;
                case R.id.action_goBack:
                    if((!land && !infosAnnonces )) {//cas portrait todolistjust

                        Intent intentRetour = new Intent(ToDoListInfosAnnonces.this, Home_screen.class); // TODO
                        startActivity(intentRetour);
                        finish();
                        return true;

                    }else if(land && infosAnnonces) {

                        Intent intentRetour;
                        if(tablette){//Cas telephone, paysage
                            intentRetour = new Intent(ToDoListInfosAnnonces.this, Home_screen.class);
                        }else {//Cas paysage

                            intentRetour = new Intent(ToDoListInfosAnnonces.this, ToDoListInfosAnnonces.class);
                        }
                        startActivity(intentRetour);
                        finish();
                        return true;
                    }

                    else if(!infosAnnonces){//Cas paysage telephone

                        Intent intentAccueil2 = new Intent(ToDoListInfosAnnonces.this, Home_screen.class);
                        startActivity(intentAccueil2);
                        finish();
                        return true;

                    }else {//Cas portrait telephone sur frag infosannonce

                        Intent intentRetour = new Intent(ToDoListInfosAnnonces.this, ToDoListInfosAnnonces.class); // TODO
                        startActivity(intentRetour);
                        finish();
                        return true;
                    }

                default:
                    // If we got here, the user's action was not recognized.
                    // Invoke the superclass to handle it.
                    return super.onOptionsItemSelected(item);
            }

    }
}
