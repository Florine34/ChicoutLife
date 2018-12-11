package com.example.flo.chicoutlife;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.R.drawable.ic_menu_send;
import static android.widget.Toast.LENGTH_LONG;

public class TacheArrayAdapter extends ArrayAdapter<ToDo> {

    private LayoutInflater inflater;
    Context context;
    int orientation;
    FragmentActivity activity;

    public TacheArrayAdapter(@NonNull Context context, List<ToDo> tacheList) {
        super(context, R.layout.liste_adapter,tacheList);
        this.context=context;

        inflater = LayoutInflater.from(context);
    }
    public TacheArrayAdapter(FragmentActivity activity, @NonNull Context context, List<ToDo> tacheList) {
        super(context, R.layout.liste_adapter,tacheList);
        this.context=context;
        this.activity = activity;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ToDo tache = (ToDo) this.getItem(position);
        CheckBox checkBox;
        final ImageButton imageButton;

        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        final int width = metrics.widthPixels;
        final int height = metrics.heightPixels;

        if (convertView == null){

            convertView = inflater.inflate(R.layout.liste_adapter,null);

            checkBox = (CheckBox) convertView.findViewById(R.id.checkboxtaches);
            imageButton = (ImageButton) convertView.findViewById(R.id.imagebuttontaches);

            convertView.setTag(new TacheViewHolder(checkBox,imageButton));

        }
        else{
            TacheViewHolder viewHolder = (TacheViewHolder) convertView.getTag();
            checkBox = viewHolder.getCheckBox();
            imageButton = viewHolder.getImageButton();

        }
            String text= tache.getToDoTache();


            int id = getStringIdentifier(context, text);
            /*Checkbox*/
            checkBox.setTag(tache);
            checkBox.setText(context.getResources().getString(id));
            checkBox.setChecked(tache.isCheck());

            /*ImageButton*/
            imageButton.setId(id);
            //TODO  recup nom tache trouver correspondance ds bdd info pusher consequence, type 0 1 2
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("passage" , "dans tacheArrayAdapter onCLICK");
                    int orientation = activity.getResources().getConfiguration().orientation;
                    switch (tache.getType()){
                        case 0://Cas page informations


                            if((width>800 || height > 800) && orientation == Configuration.ORIENTATION_LANDSCAPE){
                                //Mode tablette
                                    Log.d("passage" , "Mode paysage et tablette");
                                    Intent infosEtTodo = new Intent(context, ToDoListInfosAnnonces.class);
                                    lancementIntentClick( tache , infosEtTodo , "1");

                            }else{
                                //Mode Smartphone
                                Log.d("passage" , "Mode portrait");
                                Intent infoPagepOpen = new Intent(context,  ConteneurInfosAnnonces.class);
                                lancementIntentClick( tache , infoPagepOpen , "1");
                            }
                            break;
                        case 1://Cas page les deux

                          //  Intent annoncePagepOpen = new Intent(context,ConteneurInfosAnnonces.class);
                            if((width>800 || height > 800) && orientation == Configuration.ORIENTATION_LANDSCAPE) {
                                Intent annoncesInfosEtTODO = new Intent(context, ToDoListInfosAnnonces.class);
                                lancementIntentClick( tache , annoncesInfosEtTODO , "2");

                            }
                            else{
                                Intent annoncesEtInfos = new Intent(context, ConteneurInfosAnnonces.class);
                                lancementIntentClick( tache , annoncesEtInfos , "2");
                            }

                            break;

                    }
                }
            });



        return  convertView;
    }

    public static int getStringIdentifier(Context context, String name) {
        return context.getResources().getIdentifier(name, "string", context.getPackageName());
    }

    public void lancementIntentClick(ToDo tache , Intent intent , String nombrePages){
        intent.putExtra("NOM_PAGE", tache.getCheminBdd());
        intent.putExtra("NOMBRE_PAGE", nombrePages);
        intent.putExtra("TYPE_INTENT", "accesbyintent");

        context.startActivity(intent);
        ((Activity) context).finish();


    }
}
