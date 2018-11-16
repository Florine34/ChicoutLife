package com.example.flo.chicoutlife;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class WriteToDoListBDD {

    private RUser user;
    private FirebaseAuth mAuth;
    private DatabaseReference toDoListDatabase = FirebaseDatabase.getInstance().getReference("ToDoList");

    private HashMap<String, String> mapAfaire = new HashMap<>();
    private HashMap<String, String> mapFait = new HashMap<>();

    public WriteToDoListBDD(RUser u){
        this.user = u;
    }

    public void sortData(){

        // Pour tout le monde
        putMapAfaire("Tache001", "false"); // Course
        putMapAfaire("Tache002", "false"); // Appartement
        putMapAfaire("Tache003", "false"); // Vetement hiver
        putMapAfaire("Tache004", "false"); // Inscription
        putMapAfaire("Tache005", "false"); // Asso
        putMapAfaire("Tache006", "false"); // Bus
        putMapAfaire("Tache007", "false"); // Carte etudiante
        putMapAfaire("Tache008", "false"); // Casier
        putMapAfaire("Tache010", "false"); // Compte bancaire

        // Suivant le pays Ramq ou pas ?
        switch (user.getPays()){
            case "France":{
                //
                break;
            }
            default:{
                break;
            }
        }

        // !Chicout + !Canadien = Avion + Itinéraire (suivant d'ou il vient : pays !Canada)
        if(user.getBoolLocalisationChicout() == false && user.getPays() != "Canada"){
            putMapAfaire("Tache013", "false"); // Avion
            putMapAfaire("Tache014", "false"); // Itinéraire après l'avion

            // ! Canada ! Canadien 1 session = CAQ + AVE
            if (user.getNbSession() == 1){
                putMapAfaire("Tache016", "false"); // CAQ
                putMapAfaire("Tache018", "false"); // AVE
            }
            // ! Canada ! Canadien >1 session = CAQ + PE + Accueil+
            else if(user.getNbSession() > 1){
                putMapAfaire("Tache016", "false"); // CAQ
                putMapAfaire("Tache015", "false"); // Permis Etude
                putMapAfaire("Tache017", "false"); // Accueil+

                // PE + Job = NAS
                if(user.getBoolTravail() == true){
                    putMapAfaire("Tache011", "false"); // NAS
                }
            }
        }

        // Canadien pas à chicout
        if(user.getBoolLocalisationChicout() == false && user.getPays() == "Canada"){
            putMapAfaire("Tache014", "false"); // Itinéraire après l'avion
        }

        // Echange Universitaire
        if(user.getBoolEchangeUniversitaire() == true){
            // Papier de l'université d'echange + Bourse ?
        }

        writeNewToDoList(mapAfaire, mapFait);
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
