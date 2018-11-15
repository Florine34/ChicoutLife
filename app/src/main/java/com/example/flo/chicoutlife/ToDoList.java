package com.example.flo.chicoutlife;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ToDoList {

    private Map<String, String> afaire;
    private Map<String, String> fait;

    public ToDoList(){

    }

    public ToDoList(Map<String, String> afaire, Map<String, String> fait){
        this.afaire = afaire;
        this.fait = fait;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("AFaire", afaire);
        result.put("Fait", fait);

        return result;
    }


}
