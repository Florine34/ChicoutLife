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
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

public class ToDoListInfosAnnonces extends AppCompatActivity {
    private ViewPager pages;
    private PagerAdapter adapterPages;
    private boolean tablette;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.main_activity_contenu_arrivee);
        tablette = getResources().getBoolean(R.bool.tablette);
        Log.d("passage","ToDoListInfosAnnonces dans on Create bool tablette " + tablette);
        if(tablette){

            Bundle bundle = getIntent().getExtras();

            pages = findViewById(R.id.viewPagerOrientation);
            adapterPages = new FragmentsSwipeAdapter(getSupportFragmentManager(), bundle);
            ((FragmentsSwipeAdapter) adapterPages).addFragmentList(InfoActivity.newInstance(bundle));
            if(bundle.get("NOMBRE_PAGES") == "2") {
                ((FragmentsSwipeAdapter) adapterPages).addFragmentList(ChoiceAnnonce.newInstance(bundle));
            }
            pages.setAdapter(adapterPages);
        }
        else{
            Log.d("passage" , "Dans ToDoListInfosAnnonces.OnCreate fragments support manager?" + getSupportFragmentManager().getFragments().size());

        }


    }
    public void reinstanciatePageAdapter(Bundle bundle){

        Log.d("passage","est dans ToDoListInfosAnnocnes dans reinstanciatePageAdapter");
        boolean tablette = getResources().getBoolean(R.bool.tablette);
        if(!tablette) {

            findViewById(R.id.viewPagerOrientation).setVisibility(View.VISIBLE);
            findViewById(R.id.nomsPages).setVisibility(View.VISIBLE);
            findViewById(R.id.titletodo).setVisibility(View.GONE);
            findViewById(R.id.fragments_todolist).setVisibility(View.GONE);
            pages=findViewById(R.id.viewPagerOrientation);
        }
        else{
           /* findViewById(R.id.viewPagerOrientation).setVisibility(View.GONE);
            findViewById(R.id.nomsPages).setVisibility(View.GONE);
            findViewById(R.id.titletodo).setVisibility(View.VISIBLE);
            findViewById(R.id.fragments_todolist).setVisibility(View.VISIBLE);
            pages=findViewById(R.id.viewPagerOrientation);*/

        }
        adapterPages = new FragmentsSwipeAdapter(getSupportFragmentManager(), bundle);
        //adapterPages.destroyItem(R.layout.info_activity,0,);
        /*FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.layout.info_activity,InfoActivity.newInstance(bundle));*/
        ((FragmentsSwipeAdapter) adapterPages).removeFragmentAllList();
        ((FragmentsSwipeAdapter) adapterPages).addFragmentList(InfoActivity.newInstance(bundle));
        Log.d("passage", "est dans ToDoListInfosAnnocnes.reinstanciatePageAdapter " + bundle.getString("NOMBRE_PAGE"));
        if (bundle.getString("NOMBRE_PAGE") == "2") {
            ((FragmentsSwipeAdapter) adapterPages).addFragmentList(ChoiceAnnonce.newInstance(bundle));
        }

        pages.setAdapter(adapterPages);
        adapterPages.startUpdate(pages);
        Log.d("passage", "Dans ToDoListInfosAnnonces.reinstanciatePageAdapter nombre fragments support manager?" + getSupportFragmentManager().getFragments().size());

        Log.d("passage","est dans ToDoListInfosAnnocnes dans reinstanciatePageAdapter changement effectuer");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*getSupportFragmentManager().
        ((FragmentsSwipeAdapter) adapterPages).removeFragmentAllList();
        pages.setAdapter(adapterPages);
        adapterPages.startUpdate(pages);*/


    }
}


/*
        Log.d("passage","Dans ToDoListInfosAnnonces width " + width + " height " + height);
        if (width > 800 || height > 800){
            //code for big screen (like tablet)

            int orientation = getResources().getConfiguration().orientation;
            Log.d("passage","Dans ToDoListInfosAnnonces cas tablette" + "type orientation : " + orientation);
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                // TODO A changer faire avec la liste a scinder en deux
                Log.d("passage","Dans ToDoListInfosAnnonces cas tablette  cas PORTRAIT");
                setContentView(R.layout.just_todo_list_main_activity);
                Log.d("passage","Dans ToDoListInfosAnnonces fin setContentView");

            } else {

                setContentView(R.layout.todolist_et_infoannonces);
                pages = findViewById(R.id.viewPagerOrientation);
                Intent intent = new Intent();
                if (getIntent() == null) {

                    intent.putExtra("NOMBRE_PAGE", "2");
                    intent.putExtra("NOM_PAGE", "Tache002");
                    intent.putExtra("TYPE_INTENT", "accesbyintent");
                } else {
                    intent = getIntent();
                }

                adapterPages = new FragmentsSwipeAdapter(getSupportFragmentManager(), intent);
                pages.setAdapter(adapterPages);

            }
        }else{
            //code for small screen (like smartphone)
            setContentView(R.layout.just_todo_list_main_activity);
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // TODO A changer faire avec la liste a scinder en deux
                LinearLayout linearLayout = findViewById(R.id.fragment_todolist);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);


            }

        }*/