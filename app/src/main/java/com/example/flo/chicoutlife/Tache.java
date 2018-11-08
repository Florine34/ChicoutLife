package com.example.flo.chicoutlife;

public class Tache {
    private String cheminString;
    private String idbutton;
    private int type;

    public Tache() {
    }

    public Tache(String cheminString, String idbutton, int type) {
        this.cheminString = cheminString;
        this.idbutton = idbutton;
        this.type = type;
    }

    public String getCheminString() {
        return cheminString;
    }

    public void setCheminString(String cheminString) {
        this.cheminString = cheminString;
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
