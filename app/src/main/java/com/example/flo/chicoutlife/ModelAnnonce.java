package com.example.flo.chicoutlife;

import android.graphics.Bitmap;

public class ModelAnnonce {

    private   String modelTitre;
    private String modelDescription;
    private String cheminAnnonceBdd;
    private Bitmap imageAnnonce;

    public String getCheminAnnonceBdd() {
        return cheminAnnonceBdd;
    }

    public void setCheminAnnonceBdd(String cheminAnnonceBdd) {
        this.cheminAnnonceBdd = cheminAnnonceBdd;
    }


    public ModelAnnonce() {
    }

    public ModelAnnonce(String modelTitre, String modelDescription, Bitmap imageAnnonce,String cheminAnnonceBdd) {
        this.modelTitre = modelTitre;
        this.modelDescription = modelDescription;
        this.imageAnnonce = imageAnnonce;
        this.cheminAnnonceBdd = cheminAnnonceBdd;
    }

    public String getModelTitre() {
        return modelTitre;
    }

    public void setModelTitre(String modelTitre) {
        this.modelTitre = modelTitre;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }

    public Bitmap getImageAnnonce() {
        return imageAnnonce;
    }

    public void setImageAnnonce(Bitmap modelAnnonce) {
        this.imageAnnonce = modelAnnonce;
    }
}
