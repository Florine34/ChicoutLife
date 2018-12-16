package com.example.flo.chicoutlife;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;

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
        int id;

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

            id = getStringIdentifier(context, text);
            /*Checkbox*/
            checkBox.setTag(tache);
            checkBox.setText(context.getResources().getString(id));
            checkBox.setChecked(tache.isCheck());

            /*ImageButton*/
            imageButton.setId(id);
            //TODO  recup nom tache trouver correspondance ds bdd info pusher consequence, type 0 1 2
            ajoutListenerImageButton(imageButton,tache);

        return  convertView;
    }

    public void ajoutListenerImageButton(ImageView imageButton, final ToDo tache){
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (tache.getType()){
                    case 0://Cas page informations

                         communicationActivityMain(tache,"1");
                        break;

                    case 1://Cas page les deux

                        communicationActivityMain(tache,"2");
                        break;
                }
            }
        });

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
