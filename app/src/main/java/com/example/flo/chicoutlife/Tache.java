package com.example.flo.chicoutlife;

public class Tache {
    private String nom;
    private String idbutton;
    private int type;

    public Tache() {
    }

    public Tache(String nom , String idbutton, int type) {
        this.nom = nom;
        this.idbutton = idbutton;
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIdbutton() {
        return idbutton;
    }

    public void setIdbutton(String idbutton) {
        this.idbutton = idbutton;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
