package com.example.flo.chicoutlife;

public class WriteToDoListBDD {

    private RUser user;

    public WriteToDoListBDD(RUser u){
        this.user = u;
    }


/*
        String key = rUserDatabase.push().getKey();
        RUser user = new RUser(pays, domaineEtude, progEtude, sessionAdmi, nbSession, permisEtude, echangeUni, localisationChicout, travailler);

        Map<String, Object> postValues = user.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, postValues);
        rUserDatabase.updateChildren(childUpdates);
 */
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
            // mettre les taches qu'il faut
        }
        else {
            //
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

    }
}
