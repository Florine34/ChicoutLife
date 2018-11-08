package com.example.flo.chicoutlife;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
        ToDo tache = (ToDo) this.getItem(position);
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

            int id = getStringIdentifier(context, text);
            /*Checkbox*/
            checkBox.setTag(tache);
            checkBox.setText(context.getResources().getString(id));
            checkBox.setChecked(tache.isCheck());

            /*ImageButton*/
            imageButton.setId(id);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,Integer.toString(imageButton.getId()),LENGTH_LONG).show();
                    Intent intentAccueil = new Intent(context, MainActivity.class);
                    context.startActivity(intentAccueil);
                    ((Activity)context).finish();
                }
            });



        return  convertView;
    }

    public static int getStringIdentifier(Context context, String name) {
        return context.getResources().getIdentifier(name, "string", context.getPackageName());
    }
}
