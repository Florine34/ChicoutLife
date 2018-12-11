package com.example.flo.chicoutlife;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class ConteneurInfosAnnonces  extends AppCompatActivity {

    private ViewPager pages;
    private PagerAdapter adapterPages;
    private  Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infos_annonces);
        intent = getIntent();

        pages = findViewById(R.id.viewPages);
        adapterPages = new FragmentsSwipeAdapter(getSupportFragmentManager(),intent);
        pages.setAdapter(adapterPages);

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
                Intent intentAccueil = new Intent(this, Home_screen.class);
                startActivity(intentAccueil);
                //finish(); TODO a voir finish
                return true;
            case R.id.action_goBack:
                Intent intentRetour = new Intent(this, ToDoListInfosAnnonces.class); // TODO
                intentRetour.putExtra("NOM_PAGE", intent.getStringExtra("NOM_PAGE"));
                intentRetour.putExtra("NOMBRE_PAGE", "2");
                intentRetour.putExtra("TYPE_INTENT", "accesbyintent");
                startActivity(intentRetour);
                // finish();
                return true;

            case R.id.action_quit:
                // finish();
                System.exit(0);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
