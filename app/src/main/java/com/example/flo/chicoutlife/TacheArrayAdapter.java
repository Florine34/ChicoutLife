package com.example.flo.chicoutlife;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;

import java.util.List;

public class TacheArrayAdapter extends ArrayAdapter<ToDo> {

    private LayoutInflater inflater;
    private  Context context;
    private FragmentActivity fragmentActivity;

    public TacheArrayAdapter(@NonNull Context context, List<ToDo> tacheList) {
        super(context, R.layout.liste_adapter,tacheList);
        this.context=context;

        inflater = LayoutInflater.from(context);
    }
    public TacheArrayAdapter(FragmentActivity activity, @NonNull Context context, List<ToDo> tacheList) {
        super(context, R.layout.liste_adapter,tacheList);
        this.context=context;
        this.fragmentActivity = activity;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ToDo tache = (ToDo) this.getItem(position);
        CheckBox checkBox;
        final ImageButton imageButton;


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

                    boolean tablette = fragmentActivity.getResources().getBoolean(R.bool.tablette);
                    switch (tache.getType()){
                        case 0://Cas page informations

                            if(tablette == true){
                                Log.d("passage" , "Dans TacheArrayAdapter Mode paysage et tablette que infos");
                                communicationActivityMain(tache,"1");

                            }else{
                                communicationActivityMain(tache,"1");
                                Log.d("passage" , "Mode portrait cas infos");

                            }
                            break;

                        case 1://Cas page les deux
                            if(tablette == true){
                                Log.d("passage" , "Mode paysage et tablette infos et annonces nomtache " +tache.getCheminBdd()+ "type" + tache.getType());
                                communicationActivityMain(tache,"2");

                            }else{
                                Log.d("passage" , "Mode portrait cas infos et annonces");
                                communicationActivityMain(tache,"2");

                            }
                    }
                }
            });



        return  convertView;
    }

    public static int getStringIdentifier(Context context, String name) {
        return context.getResources().getIdentifier(name, "string", context.getPackageName());
    }


    public void communicationActivityMain(ToDo tache , String nombrePages){
        Bundle bundle = new Bundle();
        bundle.putString("NOM_PAGE", tache.getCheminBdd());
        bundle.putString("NOMBRE_PAGE", nombrePages);
        bundle.putString("TYPE_INTENT", "accesbyintent");


        ToDoListInfosAnnonces activityMain = (ToDoListInfosAnnonces) fragmentActivity;
        activityMain.reinstanciatePageAdapter(bundle);

    }
}
