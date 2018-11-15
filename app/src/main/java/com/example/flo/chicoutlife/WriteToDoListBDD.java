package com.example.flo.chicoutlife;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class WriteToDoListBDD {

    private RUser user;
    private FirebaseAuth mAuth;
    private DatabaseReference toDoListDatabase = FirebaseDatabase.getInstance().getReference("ToDoList");
    private DatabaseReference tacheDatabase = FirebaseDatabase.getInstance().getReference("Taches");

    private Map<String, String> mapAfaire;
    private Map<String, String> mapFait;

    private String[][] tab; // 0 = true 1 = false

    private int compteur = 0;

    public WriteToDoListBDD(RUser u){
        this.user = u;
    }

    /**
     * Fonction qui écrit les tache à faire suivant les renseigments pris par l'utilisateur
     *
     * Recuperer la base ToDoList
     * Créer un new objet ToDoList (java)
     * en faire un Map ... Update children (voir code renseignementActivity) (ou juste au dessus)
     */
    public void insertInDB(){


        if(user.getBoolEchangeUniversitaire() == true){
            //

        }
        else {
            //
        }

        if(user.getBoolPermisEtude() == true){
            // mettre les taches qu'il faut tache015
            putMapAfaire("Tache015", "false");
            compteur++;
        }
        else {
            //
            putMapFait("Tache015", "true");
            compteur++;
        }

        if(user.getBoolLocalisationChicout() == true){
            //
        }
        else{
            //
        }

        if(user.getBoolTravail() == true){
            //
        }
        else {
            //
        }

        switch (user.getDomaineEtude()){
            case "Sciences_fondamentales_biologie_chimie":{
                //
                break;
            }
            default:{
                break;
            }
        }

        //A continuer

        if(compteur == 1){
            writeNewToDoList(mapAfaire, mapFait);
        }

    }

    // Insère dans la base de données
    private void writeNewToDoList(Map<String, String> mafaire, Map<String, String> mfait) {

        // Get User's ID
        mAuth = FirebaseAuth.getInstance();

        String key = mAuth.getUid();

        ToDoList tdl = new ToDoList(mafaire, mfait);


        Map<String, Object> userToAdd = tdl.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, userToAdd);
        toDoListDatabase.updateChildren(childUpdates);
    }

    private void putMapAfaire(String nom, String valeur) {
        mapAfaire.put(nom, valeur);
    }

    private void putMapFait(String nom, String valeur) {
        mapFait.put(nom, valeur);
    }
}
