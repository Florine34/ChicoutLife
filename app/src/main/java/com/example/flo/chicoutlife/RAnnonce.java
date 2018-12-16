package com.example.flo.chicoutlife;


import com.google.firebase.database.Exclude;
import java.util.HashMap;
import java.util.Map;

public class RAnnonce {
    private String dateAjout;
    private String description;
    private String idVendeur;
    private String image;
    private String prix;
    private String titre;


    public RAnnonce(String m_dateAjout, String m_descr, String m_idVendeur, String m_image, String m_prix,String m_titre){
        this.dateAjout = m_dateAjout;
        this.description = m_descr;
        this.idVendeur = m_idVendeur;
        this.image = m_image;
        this.prix = m_prix;
        this.titre = m_titre;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("DateAjout", dateAjout);
        result.put("Description", description);
        result.put("IdVendeur", idVendeur);
        result.put("Image", image);
        result.put("Prix", prix);
        result.put("Titre", titre);

        return result;
    }
}
