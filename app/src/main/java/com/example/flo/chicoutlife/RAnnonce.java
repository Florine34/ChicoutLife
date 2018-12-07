package com.example.flo.chicoutlife;

import android.graphics.Bitmap;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class RAnnonce {
    private String dateAjout;
    private String description;
    private String idVendeur;
    private String image;
    private String prix;
    private boolean tag_appartement;
    private boolean tag_electronque;
    private boolean tag_nourriture;
    private boolean tag_vetements;
    private String titre;

    public RAnnonce(){

    }

    public RAnnonce(String m_dateAjout, String m_descr, String m_idVendeur, String m_image, String m_prix, boolean m_tag_appartement, boolean m_tag_electo, boolean m_tag_tag_nourriture, boolean m_tag_vetement, String m_titre){
        this.dateAjout = m_dateAjout;
        this.description = m_descr;
        this.idVendeur = m_idVendeur;
        this.image = m_image;
        this.prix = m_prix;
        this.tag_appartement = m_tag_appartement;
        this.tag_electronque = m_tag_electo;
        this.tag_nourriture = m_tag_tag_nourriture;
        this.tag_vetements = m_tag_vetement;
        this.titre = m_titre;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("dateAjout", dateAjout);
        result.put("description", description);
        result.put("idVendeur", idVendeur);
        result.put("image", image);
        result.put("prix", prix);
        result.put("tag_appartement", tag_appartement);
        result.put("tag_electronique", tag_electronque);
        result.put("tag_nourriture", tag_nourriture);
        result.put("tag_vetements", tag_vetements);
        result.put("titre", titre);

        return result;
    }
}
