package com.example.flo.chicoutlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class AnnoncesAccesLibre extends AppCompatActivity {
    private Bundle bundle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = new Bundle();
        bundle.putString("NOM_ACTIVITE","AnnoncesAccesLibre");
        bundle.putString("NOM_PAGE","Tache002");
        bundle.putString("TYPE_INTENT","accesbyparam");
        bundle.putStringArrayList("TAB_PARAM",new ArrayList<String>());


        setContentView(R.layout.annonces_libre);

        refreshFragment(bundle);

    }

    public void refreshFragment(Bundle bundleRefresh){
        bundleRefresh.putString("NOM_ACTIVITE","AnnoncesAccesLibre");
        Fragment fragmentAnnonces = ChoiceAnnonce.newInstance(bundleRefresh);
        android.support.v4.app.FragmentManager fgr = getSupportFragmentManager();// ????
        FragmentTransaction ft = fgr.beginTransaction();

        ft.replace(R.id.fragmentAnnoncesLibre,fragmentAnnonces);
        ft.addToBackStack(null);

        ft.commit();

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
                Intent intentAccueil = new Intent(AnnoncesAccesLibre.this, Home_screen.class);
                startActivity(intentAccueil);
                finish();
                return true;
            case R.id.action_goBack:

                    Intent intentRetour = new Intent(AnnoncesAccesLibre.this, Home_screen.class); // TODO
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
