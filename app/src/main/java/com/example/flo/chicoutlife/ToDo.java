package com.example.flo.chicoutlife;

public class ToDo {

    private String tache;
    private boolean check;
    private int type;
    private String cheminBdd   ;

    public ToDo() {
    }

    public ToDo(String tache,boolean check,int type,String cheminBdd){
        this.tache = tache;
        this.check = check;
        this.type = type;
        this.cheminBdd = cheminBdd;
    }
    public String getToDoTache() {
        return tache;
    }

    public void setToDoTache(String tache) {
        this.tache = tache;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void toggleChecked() {
        check = !check;
    }

    public String getCheminBdd() {
        return cheminBdd;
    }

    public void setCheminBdd(String cheminBdd) {
        this.cheminBdd = cheminBdd;
    }

    public String getTache() {
        return tache;
    }

    public void setTache(String tache) {
        this.tache = tache;
    }
}
