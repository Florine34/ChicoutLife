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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class ToDoListActivity extends AppCompatActivity {

    private ListView ListViewaFaire = null;
    private ListView ListViewFait = null;

    private ArrayList<ToDo> tachesaFaire = new ArrayList<ToDo>();
    private ArrayList<ToDo> tachesFait = new ArrayList<>();

    private ArrayAdapter<ToDo> listAdapteraFaire = null;
    private ArrayAdapter<ToDo> listAdapterFait = null;
    DatabaseReference tbRacine ;
    FirebaseDatabase database;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list_activity);
        context = this;
        /*Ouverture base de donnees*/
        database = FirebaseDatabase.getInstance();/*A revoir ou placer correctement*/
        tbRacine = database.getReference("inf872-chicoutlife");/*A revoir ou placer correctement*/

        /*Cas pour A FAIRE */

        //Adapter qui liste les taches a faire de l'utilisateur

        ListViewaFaire = findViewById(R.id.linearafaire2);
        ListViewFait = findViewById(R.id.linearfait2);
        createAdaptersTaches();

        /*Ecoute les item de l adapter pour changer la valeur dans le modele*/
        ListViewaFaire.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long id) {
                /*TODO changer dans la base de donnees m pas toggle check*/
                ToDo tache = listAdapteraFaire.getItem(position);
                tache.toggleChecked();
                TacheViewHolder viewHolder = (TacheViewHolder) item.getTag();
                viewHolder.getCheckBox().setChecked(tache.isCheck());
            }
        });
        ListViewFait.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long id) {
                /*TODO changer dans la base de donnees m pas toggle check*/
                ToDo tache = listAdapterFait.getItem(position);
                tache.toggleChecked();
                TacheViewHolder viewHolder = (TacheViewHolder) item.getTag();
                viewHolder.getCheckBox().setChecked(tache.isCheck());
            }
        });

        /* cas pour FAIRE */

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
                Intent intentAccueil = new Intent(ToDoListActivity.this, MainActivity.class);
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

    public void createAdaptersTaches(){



        // taches = (ArrayList<Tache>) getLastNonConfigurationInstance();
        /*lister les questions */

        tachesaFaire = new ArrayList<>();
        tachesFait = new ArrayList<>();

        tbRacine.addValueEventListener(new ValueEventListener() {//Liste des taches de l user

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listAdapteraFaire = new TacheArrayAdapter(context,tachesaFaire);
                listAdapterFait = new TacheArrayAdapter(context,tachesFait);

                DataSnapshot tbToDoList = dataSnapshot.child("ToDoList").child("iduser1");//TODO modifier pour authentification user
                DataSnapshot aFaire = tbToDoList.child("AFaire");
                DataSnapshot fait = dataSnapshot.child("Fait");

                Log.d("test avant fct" , "test avant fct afaire "+aFaire.child("Tache001").toString());
                Log.d("test avant fct" , "test avant fct afaire "+tbToDoList.getChildrenCount());
                for(DataSnapshot tacheaFaire : aFaire.getChildren()){
                    String cheminTacheAFaire = tacheaFaire.getKey();
                    Log.d("test avant fct" , "test noom taches recup "+tacheaFaire.getKey());
                    DataSnapshot tachebdd = dataSnapshot.child(cheminTacheAFaire);
                    Tache t =tachebdd.getValue(Tache.class);
                    tachesaFaire.add(new ToDo(t.getCheminString(),false));
                }
                for(DataSnapshot tacheFait : fait.getChildren()){
                    String cheminTacheFait = tacheFait.toString();

                    DataSnapshot tache2bdd = dataSnapshot.child(cheminTacheFait);
                    Tache t2 =tache2bdd.getValue(Tache.class);
                    tachesaFaire.add(new ToDo(t2.getCheminString(),false));
                }


               // Log.d("test avant fct" , "test avant fct ");
                //for (DataSnapshot question : dataSnapshot.getChildren()){
                  //  String cheminTachebdd = question.getKey();
                   // DatabaseReference idtaches = database.getReference(cheminTachebdd);

                    //Log.d("test ERREUR" , "test qpres q");
                    //Log.d("test"," mess"+q);
                    /*tmp test , TODO ajouter differents chemin page et valeur bdd pour repartir*/
                    //if(q!=null)
                    //tachesaFaire.add(new ToDo(q.getCheminString(),false));
                    //tachesFait.add(new ToDo(q.getCheminString(),true));

                //}
                ListViewaFaire.setAdapter(listAdapteraFaire);
                ListViewFait.setAdapter(listAdapterFait);
               // listAdapter = new TacheArrayAdapter(context,taches);
                //ppListView.setAdapter(listAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
