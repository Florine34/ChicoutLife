package com.example.flo.chicoutlife;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentToDoListAFaire extends Fragment {


    FirebaseAuth mAuth;
    private ListView ListViewaFaire = null;
    private ListView ListViewFait = null;

    private ArrayList<ToDo> tachesaFaire = new ArrayList<ToDo>();


    private ArrayAdapter<ToDo> listAdapteraFaire = null;

    DatabaseReference tbToDolist ;
    DatabaseReference racine ;
    FirebaseDatabase database;
    Context context;

    final Tache[] t = new Tache[1];

    View racineView;
    public static Fragment newInstance(){return new FragmentToDoListAFaire();}


    public static Fragment newInstance(Intent intent){

        return new FragmentToDoListAFaire();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup racineView =(ViewGroup) inflater.inflate(R.layout.to_do_list_a_faire,container,false);
        this.racineView = racineView;
        return racineView;
    }

    @Override
    public void onStart() {
        super.onStart();
        context = getContext();
        /*Ouverture base de donnees*/

        database = FirebaseDatabase.getInstance();
        tbToDolist = database.getReference("ToDoList");
        racine = tbToDolist.getParent();
        mAuth = FirebaseAuth.getInstance();
        mAuth.getCurrentUser();
        //  Log.d("passage"," il est passer dans onCreate");


        //Adapters qui liste les taches a faire  et fait de l'utilisateur
        ListViewaFaire = getActivity().findViewById(R.id.linearafaire2);
        ListViewFait = getActivity().findViewById(R.id.linearfait2);

        createAdaptersTaches();//Gestion des donnees des adapters
        // Log.d("passage"," qvqnt item click");
        /*Ecoute les item de l adapter pour changer la valeur dans le modele*/
        ListViewaFaire.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long id) {
                ToDo tache = listAdapteraFaire.getItem(position);

                /*Change donnees de la todolist de l'utilisateur*/

                racine.child("ToDoList").child(mAuth.getUid()).child("AFaire").child(tache.getCheminBdd()).removeValue();
                racine.child("ToDoList").child(mAuth.getUid()).child("Fait").child(tache.getCheminBdd()).setValue(true);
                //Log.d("passage"," usertodolist remove");
                tache.toggleChecked();//TODO voir si utile
                TacheViewHolder viewHolder = (TacheViewHolder) item.getTag();
                viewHolder.getCheckBox().setChecked(tache.isCheck());
            }
        });
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*Fonction qui remplit les adapters afaire et fait*/
    public void createAdaptersTaches(){
        // taches = (ArrayList<Tache>) getLastNonConfigurationInstance();

        racine.addValueEventListener(new ValueEventListener() {//Liste des taches de l user

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /*Re/initialise les listes pour les adapters*/
                tachesaFaire = new ArrayList<>();
                listAdapteraFaire = new TacheArrayAdapter(getActivity(),context,tachesaFaire);

                Log.d("passage"," il est passer dans on DataChange a faire");
                DataSnapshot tbToDoListUser = dataSnapshot.child("ToDoList").child(Objects.requireNonNull(mAuth.getUid()));//TODO mAuth.getUid() / modifier pour authentification user
                DataSnapshot aFaire = tbToDoListUser.child("AFaire");
                DataSnapshot dataTache = dataSnapshot.child("Taches");

                /*Cas pour les taches a faire*/
                for(DataSnapshot tacheaFaire : aFaire.getChildren()){

                    String cheminTacheAFaire = tacheaFaire.getKey();

                    DataSnapshot dataTacheChild = null;
                    if (cheminTacheAFaire != null) {
                        dataTacheChild = dataTache.child(cheminTacheAFaire);
                        Tache t = dataTacheChild.getValue(Tache.class);
                        tachesaFaire.add(new ToDo(t.getNom(),false,t.getType(),cheminTacheAFaire));

                    }

                }

                ListViewaFaire.setAdapter(listAdapteraFaire);
                Log.d("passage"," fin fct");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
