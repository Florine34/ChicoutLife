package com.example.flo.chicoutlife;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class RUser {

    private String pays;
    private String domaineEtude;
    private String progEtude;
    private String sessionAdmi;
    private int nbSession;
    private boolean permisEtude;
    private boolean echangeUni;
    private boolean localisationChicout;
    private boolean travailler;

    public RUser() {
    }

    public RUser(String m_pays, String m_domaineEtude, String m_progEtude, String m_sessionAdmi, int m_nbSession, boolean m_permisEtude, boolean m_echangeUni, boolean m_localisationChicout, boolean m_travailler) {
        this.pays = m_pays;
        this.domaineEtude = m_domaineEtude;
        this.progEtude = m_progEtude;
        this.sessionAdmi = m_sessionAdmi;
        this.nbSession = m_nbSession;
        this.permisEtude = m_permisEtude;
        this.echangeUni = m_echangeUni;
        this.localisationChicout = m_localisationChicout;
        this.travailler = m_travailler;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("pays", pays);
        result.put("domaineEtude", domaineEtude);
        result.put("progEtude", progEtude);
        result.put("sessionAdmi", sessionAdmi);
        result.put("nbSession", nbSession);
        result.put("permisEtude", permisEtude);
        result.put("echangeUni", echangeUni);
        result.put("localisationChicout", localisationChicout);
        result.put("travailler", travailler);

        return result;
    }


    public String getPays() {
        return pays;
    }

    public void setPays(String m_pays) {
        this.pays = m_pays;
    }

    public String getDomaineEtude() {
        return domaineEtude;
    }

    public void setDomaineEtude(String m_domaineEtude) {
        this.domaineEtude = m_domaineEtude;
    }

    public String getProgrammeEtude() {
        return progEtude;
    }

    public void setProgrammeEtude(String m_progEtude) {
        this.progEtude = m_progEtude;
    }

    public String getSessionAdmission() {
        return sessionAdmi;
    }

    public void setSessionAdmission(String m_sessionAdmi) {
        this.sessionAdmi = m_sessionAdmi;
    }

    public int getNbSession() {
        return nbSession;
    }

    public void setNbSession(int m_nbSession) {
        this.nbSession = m_nbSession;
    }

    public boolean getBoolPermisEtude() {
        return permisEtude;
    }

    public void setBoolPermisEtude(boolean m_permisEtude) {
        this.permisEtude = m_permisEtude;
    }

    public boolean getBoolEchangeUniversitaire() {
        return echangeUni;
    }

    public void setBoolEchangeUniversitaire(boolean m_echangeUni) {
        this.echangeUni = m_echangeUni;
    }

    public boolean getBoolLocalisationChicout() {
        return localisationChicout;
    }

    public void setBoolLocalisationChicout(boolean m_localisationChicout) {
        this.localisationChicout = m_localisationChicout;
    }

    public boolean getBoolTravail() {
        return travailler;
    }

    public void setBoolTravail(boolean m_travailler) {
        this.travailler = m_travailler;
    }

}
