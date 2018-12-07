package com.example.flo.chicoutlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class ToDoListInfosAnnonces extends AppCompatActivity {
    private ViewPager pages;
    private PagerAdapter adapterPages;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist_et_infoannonces);
        pages = findViewById(R.id.viewPagerOrientation);

        Intent intent = new Intent();
        intent.putExtra("NOMBRE_PAGE","2");
        intent.putExtra("NOM_PAGE" ,"Tache002");
        intent.putExtra("TYPE_INTENT","accesbyintent");
        adapterPages = new FragmentsSwipeAdapter(getSupportFragmentManager(),intent);
        pages.setAdapter(adapterPages);
    }
}
