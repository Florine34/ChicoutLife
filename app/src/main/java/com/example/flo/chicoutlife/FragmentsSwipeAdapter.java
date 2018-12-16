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

    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private Bundle bundleFragment;

    public FragmentsSwipeAdapter(FragmentManager fm) {
        super(fm);
    }


    public FragmentsSwipeAdapter(FragmentManager fm, Bundle bundle) {
        super(fm);
        this.bundleFragment = bundle;

        Log.d("passage","Dans FragmentsSwipeAdapter.constructor(bundle) " + bundleFragment.get("NOM_PAGE"));
        Log.d("passage","Dans FragmentsSwipeAdapter.constructor(bundle) " + bundleFragment.get("NOMBRE_PAGE"));
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("passage","Dans FragmentsSwipeAdapter dans getItem " );
        return fragmentList.get(position);

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


    public void addFragmentList(Fragment newFragment){
        fragmentList.add(newFragment);
    }

    public void removeFragmentAllList(){
        fragmentList = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        Log.d("passage","Dans FragmentsSwipeAdapter.getItemPosition" );
        if (fragmentList.contains(object)) return fragmentList.indexOf(object);
        else return POSITION_NONE;
    }


}
