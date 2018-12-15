package com.example.flo.chicoutlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.server.converter.StringToIntConverter;

import java.util.ArrayList;
import java.util.List;

import static com.example.flo.chicoutlife.InfoActivity.intentInfoActivity;

public class FragmentsSwipeAdapter extends FragmentPagerAdapter {

    private int nombrePages = 2;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private Intent intentInfoAnnonce;
    private Bundle bundleFragment;

    public FragmentsSwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    public FragmentsSwipeAdapter(FragmentManager fm, Intent intent) {
        super(fm);
        this.intentInfoAnnonce= intent;
        this.nombrePages = (Integer.parseInt(intentInfoAnnonce.getStringExtra("NOMBRE_PAGE")));
        Log.d("passage","Dans fragmentsSwipeAdapter " + intentInfoAnnonce.getStringExtra("NOM_PAGE"));
    }

    public FragmentsSwipeAdapter(FragmentManager fm, Bundle bundle) {
        super(fm);
        this.bundleFragment = bundle;
        this.nombrePages = (Integer.parseInt(bundleFragment.getString("NOMBRE_PAGE")));
        Log.d("passage","Dans FragmentsSwipeAdapter.constructor(bundle) " + bundleFragment.get("NOM_PAGE"));

    }

    @Override
    public Fragment getItem(int position) {

        return fragmentList.get(position);
       /* if (nombrePages == 2) {
            Log.d("passage","Dans FragmentsSwipeAdapter dans getItem cas NOMBRE PAGE 2" + bundleFragment.get("NOM_PAGE"));
            switch (position) {

                case 0:
                    Log.d("passage","Dans FragmentsSwipeAdapter.getItem. NOMBRE PAGE 2 return InfoActivity" + bundleFragment.get("NOM_PAGE"));
                    return InfoActivity.newInstance(bundleFragment);
                case 1:
                    Log.d("passage","Dans FragmentsSwipeAdapter.getItem. NOMBRE PAGE 2 return ChoiceAnnonce" + bundleFragment.get("NOM_PAGE"));
                    return ChoiceAnnonce.newInstance(bundleFragment);
                default:
                    return null;
            }
        } else if (nombrePages == 1) {
            Log.d("passage","Dans FragmentsSwipeAdapter dans getItem cas NOMBRE PAGE 1" +  bundleFragment.get("NOM_PAGE"));
            switch (position) {

                case 0:
                    return InfoActivity.newInstance(bundleFragment);
                default:
                    return null;
            }
        }
        return  null;*/
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public  CharSequence getPageTitle(int position){
        String nom = "Informations";
        if (position == 1){
            nom = "Annonces";
        }

        return nom;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      //  Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
        // get the tags set by FragmentPagerAdapter
        switch (position) {
            case 0:
                //String firstTag = createdFragment.getTag();
                return InfoActivity.newInstance(bundleFragment);

            case 1:
                return ChoiceAnnonce.newInstance(bundleFragment);

        }
        // ... save the tags somewhere so you can reference them later
       return null;
        // return createdFragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (position >= getCount()) {
            FragmentManager manager = ((Fragment) object).getFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.remove((Fragment) object);
            trans.commit();
        }
        notifyDataSetChanged();
    }

    public void addFragmentList(Fragment newFragment){
        fragmentList.add(newFragment);
        notifyDataSetChanged();
    }
    public void removeFragmentAllList(){
        fragmentList = new ArrayList<>();
        notifyDataSetChanged();
    }



}
