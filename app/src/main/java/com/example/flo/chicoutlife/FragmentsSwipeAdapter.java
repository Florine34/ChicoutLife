package com.example.flo.chicoutlife;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.android.gms.common.server.converter.StringToIntConverter;

import static com.example.flo.chicoutlife.InfoActivity.intentInfoActivity;

public class FragmentsSwipeAdapter extends FragmentPagerAdapter {

    int nombrePages = 2;
    Intent intentInfoAnnonce;
    public FragmentsSwipeAdapter(FragmentManager fm) {
        super(fm);
    }
    public FragmentsSwipeAdapter(FragmentManager fm, Intent intent) {
        super(fm);
        this.intentInfoAnnonce= intent;
        this.nombrePages = (Integer.parseInt(intentInfoAnnonce.getStringExtra("NOMBRE_PAGE")));
    }

    @Override
    public Fragment getItem(int position) {

        if (nombrePages == 2) {
            switch (position) {

                case 0:
                    return InfoActivity.newInstance(intentInfoAnnonce);
                case 1:
                    return ChoiceAnnonce.newInstance(intentInfoAnnonce);
                default:
                    return null;
            }
        } else if (nombrePages == 1) {
            switch (position) {

                case 0:
                    return InfoActivity.newInstance(intentInfoAnnonce);
                default:
                    return null;
            }
        }
        return  null;
    }

    @Override
    public int getCount() {
        return nombrePages;
    }

    public  CharSequence getPageTitle(int position){
        String nom = "Informations";
        if (position == 1){
            nom = "Annonces";
        }

        return nom;
    }
}
