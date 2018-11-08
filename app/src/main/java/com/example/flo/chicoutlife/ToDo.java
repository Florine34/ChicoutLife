package com.example.flo.chicoutlife;

public class ToDo {

    private String tache;
    private boolean check;
    private int id;

    public ToDo() {
    }

    public ToDo(String tache,boolean check){
        this.tache = tache;
        this.check = check;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void toggleChecked() {
        check = !check;
    }
}
