package com.example.flo.chicoutlife;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.LinearLayout;

public class ToDoListInfosAnnonces extends AppCompatActivity {
    private ViewPager pages;
    private PagerAdapter adapterPages;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        Log.d("passage","Dans ToDoListInfosAnnonces width " + width + " height " + height);
        if (width > 1500 || height > 1500){
            //code for big screen (like tablet)
            setContentView(R.layout.todolist_et_infoannonces);
            pages = findViewById(R.id.viewPagerOrientation);

            Intent intent = new Intent();
            intent.putExtra("NOMBRE_PAGE","2");
            intent.putExtra("NOM_PAGE" ,"Tache002");
            intent.putExtra("TYPE_INTENT","accesbyintent");
            adapterPages = new FragmentsSwipeAdapter(getSupportFragmentManager(),intent);
            pages.setAdapter(adapterPages);
        }else{
            //code for small screen (like smartphone)
            setContentView(R.layout.just_todo_list_main_activity);
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // TODO A changer faire avec la liste a scinder en deux
                LinearLayout linearLayout = findViewById(R.id.fragment_todolist);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);


            } else {
                // In portrait

            }

        }

    }
}
