package com.example.flo.chicoutlife;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static com.example.flo.chicoutlife.InfoActivity.intentInfoActivity;

public class FragmentsSwipeAdapter extends FragmentPagerAdapter {

    int NOMBRE_PAGES = 2;
    Intent intentInfoAnnonce;
    public FragmentsSwipeAdapter(FragmentManager fm) {
        super(fm);
    }
    public FragmentsSwipeAdapter(FragmentManager fm, Intent intent) {
        super(fm);
        this.intentInfoAnnonce= intent;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                return InfoActivity.newInstance(intentInfoAnnonce);
            case 1:
                return  ChoiceAnnonce.newInstance(intentInfoAnnonce);
            default :
                return null;
        }
    }

    @Override
    public int getCount() {
        return NOMBRE_PAGES;
    }

    public  CharSequence getPageTitle(int position){
        String nom = "Informations";
        if (position == 1){
            nom = "Annonces";
        }

        return nom;
    }
}
