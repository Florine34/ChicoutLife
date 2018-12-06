package com.example.flo.chicoutlife;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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

    public TacheArrayAdapter(@NonNull Context context, List<ToDo> tacheList) {
        super(context, R.layout.liste_adapter,tacheList);
        this.context=context;

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

            //trouver moyen clique
        }
        else{
            TacheViewHolder viewHolder = (TacheViewHolder) convertView.getTag();
            checkBox = viewHolder.getCheckBox();
            imageButton = viewHolder.getImageButton();

        }
            String text= tache.getToDoTache();
        Log.d("passage","dans array adapter text :" + text);

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

                    switch (tache.getType()){
                        case 0://Cas page informations

                            Intent infoPagepOpen = new Intent(context,  ConteneurInfosAnnonces.class);
                            infoPagepOpen.putExtra("NOM_PAGE", tache.getCheminBdd());
                            infoPagepOpen.putExtra("NOMBRE_PAGE", "1");
                            context.startActivity(infoPagepOpen);
                            ((Activity)context).finish();


                            break;
                        case 1://Cas page les deux

                            Intent annoncePagepOpen = new Intent(context,ConteneurInfosAnnonces.class);


                            //TODO Faire par rapport a la tache dans InfosPages , ajouter les taches


                            annoncePagepOpen.putExtra("NOM_PAGE", tache.getCheminBdd());
                            annoncePagepOpen.putExtra("NOMBRE_PAGE", "2");

                            context.startActivity(annoncePagepOpen);
                            ((Activity)context).finish();

                            break;
                        case 2://Cas des deux

                            break;

                        default:
                            break;
                    }

                    /*
                    Toast.makeText(context,Integer.toString(imageButton.getId()),LENGTH_LONG).show();
                    Intent intentAccueil = new Intent(context, MainActivity.class);
                    context.startActivity(intentAccueil);
                    ((Activity)context).finish();*/
                }
            });



        return  convertView;
    }

    public static int getStringIdentifier(Context context, String name) {
        return context.getResources().getIdentifier(name, "string", context.getPackageName());
    }
}
