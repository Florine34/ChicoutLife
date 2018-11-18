package com.example.flo.chicoutlife;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class ToDoListActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    private ListView ListViewaFaire = null;
    private ListView ListViewFait = null;

    private ArrayList<ToDo> tachesaFaire = new ArrayList<ToDo>();
    private ArrayList<ToDo> tachesFait = new ArrayList<>();

    private ArrayAdapter<ToDo> listAdapteraFaire = null;
    private ArrayAdapter<ToDo> listAdapterFait = null;
    DatabaseReference tbToDolist ;
    DatabaseReference racine ;
    FirebaseDatabase database;
    Context context;

    final Tache[] t = new Tache[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list_activity);
        context = this;
        /*Ouverture base de donnees*/

        database = FirebaseDatabase.getInstance();
        tbToDolist = database.getReference("ToDoList");
        racine = tbToDolist.getParent();
        mAuth = FirebaseAuth.getInstance();
        mAuth.getCurrentUser();
      //  Log.d("passage"," il est passer dans onCreate");


        //Adapters qui liste les taches a faire  et fait de l'utilisateur
        ListViewaFaire = findViewById(R.id.linearafaire2);
        ListViewFait = findViewById(R.id.linearfait2);

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
    //create the menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_goBack:
                Intent intentAccueil = new Intent(ToDoListActivity.this, Home_screen.class);
                startActivity(intentAccueil);
                finish();
                return true;

            case R.id.action_quit:
                finish();
                System.exit(0);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    /*Fonction qui remplit les adapters afaire et fait*/
    public void createAdaptersTaches(){
        // taches = (ArrayList<Tache>) getLastNonConfigurationInstance();

        racine.addValueEventListener(new ValueEventListener() {//Liste des taches de l user

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /*Re/initialise les listes pour les adapters*/
                tachesaFaire = new ArrayList<>();
                tachesFait = new ArrayList<>();
                listAdapteraFaire = new TacheArrayAdapter(context,tachesaFaire);
                listAdapterFait = new TacheArrayAdapter(context,tachesFait);
                Log.d("passage"," il est passer dans on DataChange");
                DataSnapshot tbToDoListUser = dataSnapshot.child("ToDoList").child(mAuth.getUid());//TODO mAuth.getUid() / modifier pour authentification user
                DataSnapshot aFaire = tbToDoListUser.child("AFaire");
                DataSnapshot fait = tbToDoListUser.child("Fait");
                DataSnapshot dataTache = dataSnapshot.child("Taches");

                /*Cas pour les taches a faire*/
                for(DataSnapshot tacheaFaire : aFaire.getChildren()){
                    Log.d("passage"," debut boucle tqchesqfqire");
                    String cheminTacheAFaire = tacheaFaire.getKey();
                    DataSnapshot dataTacheChild = dataTache.child(cheminTacheAFaire);

                    Tache t = dataTacheChild.getValue(Tache.class);
                    tachesaFaire.add(new ToDo(t.getNom(),false,t.getType(),cheminTacheAFaire));
                    Log.d("passage"," fin boucle tache a faire t.getNom" + t.getNom() + t.getType());
                }

                /*Cas pour les taches faites*/
                for(DataSnapshot tacheFait : fait.getChildren()){
                    Log.d("passage"," debut boucle tqchesfait");
                    String cheminTacheFait = tacheFait.getKey();
                    DataSnapshot dataTacheChild = dataTache.child(cheminTacheFait);
                    Tache t2 = dataTacheChild.getValue(Tache.class);
                    tachesFait.add(new ToDo(t2.getNom(),true,t2.getType(),cheminTacheFait));
                    Log.d("passage","finboucle tache fait");
                }

                ListViewaFaire.setAdapter(listAdapteraFaire);
                ListViewFait.setAdapter(listAdapterFait);
                Log.d("passage"," fin fct");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}