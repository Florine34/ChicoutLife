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

public class FragmentToDoListFait extends Fragment {

    private View racineView;

    FirebaseAuth mAuth;
    private ListView ListViewFait = null;

    private ArrayList<ToDo> tachesFait = new ArrayList<>();

    private ArrayAdapter<ToDo> listAdapterFait = null;
    DatabaseReference tbToDolist ;
    DatabaseReference racine ;
    FirebaseDatabase database;
    Context context;

    public static Fragment newInstance(){return new FragmentToDoListFait();}


    public static Fragment newInstance(Intent intent){

        return new FragmentToDoListFait();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup racineView =(ViewGroup) inflater.inflate(R.layout.to_do_list_fait,container,false);
        this.racineView = racineView;
        return racineView;

    }

    @Override
    public void onStart() {
        super.onStart();
        context = racineView.getContext();
        database = FirebaseDatabase.getInstance();
        tbToDolist = database.getReference("ToDoList");
        racine = tbToDolist.getParent();
        mAuth = FirebaseAuth.getInstance();
        mAuth.getCurrentUser();

        //Adapters qui liste les taches fait de l'utilisateur

        ListViewFait = racineView.findViewById(R.id.linearfait2);

        createAdaptersTaches();//Gestion des donnees de l'adapteur

        ListViewFait.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long id) {

                ToDo tache = listAdapterFait.getItem(position);

                /*Change donnees de la todolist de l'utilisateur*/
                racine.child("ToDoList").child(mAuth.getUid()).child("Fait").child(tache.getCheminBdd()).removeValue();
                racine.child("ToDoList").child(mAuth.getUid()).child("AFaire").child(tache.getCheminBdd()).setValue(false);

                tache.toggleChecked();
                TacheViewHolder viewHolder = (TacheViewHolder) item.getTag();
                viewHolder.getCheckBox().setChecked(tache.isCheck());
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*Fonction qui remplit les adapters afaire et fait*/
    public void createAdaptersTaches(){

        racine.addValueEventListener(new ValueEventListener() {//Liste des taches de l user

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /*Re/initialise les listes pour les adapters*/

                tachesFait = new ArrayList<>();
                listAdapterFait = new TacheArrayAdapter(getActivity(),context,tachesFait);

                DataSnapshot tbToDoListUser = dataSnapshot.child("ToDoList").child(mAuth.getUid());//TODO mAuth.getUid() / modifier pour authentification user
                DataSnapshot fait = tbToDoListUser.child("Fait");
                DataSnapshot dataTache = dataSnapshot.child("Taches");


                /*Cas pour les taches faites*/
                for(DataSnapshot tacheFait : fait.getChildren()){

                    String cheminTacheFait = tacheFait.getKey();
                    DataSnapshot dataTacheChild = dataTache.child(cheminTacheFait);
                    Tache t2 = dataTacheChild.getValue(Tache.class);
                    tachesFait.add(new ToDo(t2.getNom(),true,t2.getType(),cheminTacheFait));

                }

                ListViewFait.setAdapter(listAdapterFait);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
