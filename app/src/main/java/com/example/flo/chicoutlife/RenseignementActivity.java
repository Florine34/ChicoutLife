package com.example.flo.chicoutlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe qui gère :
 *      la page Renseignement (départ/arrivée)
 *      la page Renseignement_Arrivée
 * => Faire une condition au Oncreate pour savoir quelle page on fait
 */

public class RenseignementActivity extends AppCompatActivity {
    public static final String INDEX_RENSEIGNEMENT = "Index";
    private Spinner spinnerPay, spinnerDomaine, spinProg;
    private RadioGroup radioGroup;
    private Switch switchPE, switchEchange, switchIci, switchTravail;
    private Button btnSubmit;
    private ArrayAdapter<CharSequence> adapter;
    private DatabaseReference rUserDatabase = FirebaseDatabase.getInstance().getReference("RUser");;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().getBooleanExtra(RenseignementActivity.INDEX_RENSEIGNEMENT, false) == true){ // Renseignement arrive
            setContentView(R.layout.renseignement_arrive_activity);

            final Button buttonValider = findViewById(R.id.valider);
            buttonValider.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intentRenseignement = new Intent(RenseignementActivity.this, ToDoListActivity.class);
                    intentRenseignement.putExtra(INDEX_RENSEIGNEMENT, false);
                    startActivity(intentRenseignement);
                    finish();
                }
            });

            spinnerDomaine = (Spinner) findViewById(R.id.spinnerDomaineEtude);

            // Gere quand on selectionne le domaine on doit afficher la liste de programme qui correspond
            spinnerDomaine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String res = String.valueOf(spinnerDomaine.getSelectedItem());
                    spinProg = (Spinner) findViewById(R.id.spinnerProgramme);
                    List<String> list = new ArrayList<String>();
                    switch (res){
                        case "Sciences_fondamentales_biologie_chimie":
                            Log.i("DOMAINE","Domaine choisit : Sciences");
                            adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.Sciences_fondamentales_biologie_chimie, android.R.layout.simple_spinner_item);
                            break;
                        case "Sciences_économiques_et_administratives":
                            Log.i("DOMAINE","Domaine choisit : Info");
                            adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.Sciences_économiques_et_administratives, android.R.layout.simple_spinner_item);
                            break;
                        case "Sciences_de_l_éducation":
                            Log.i("DOMAINE","Domaine choisit : Sport");
                            adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.Sciences_de_l_éducation, android.R.layout.simple_spinner_item);
                            break;
                        case "Sciences_humaines_et_socials":
                            Log.i("DOMAINE","Domaine choisit : Histoire");
                            adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.Sciences_humaines_et_socials, android.R.layout.simple_spinner_item);
                            break;
                        case "Sciences_appliquées":
                            Log.i("DOMAINE","Domaine choisit : Sciences");
                            adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.Sciences_appliquées, android.R.layout.simple_spinner_item);
                            break;
                        case "Arts_et_lettres":
                            Log.i("DOMAINE","Domaine choisit : Info");
                            adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.Arts_et_lettres, android.R.layout.simple_spinner_item);
                            break;
                        case "Informatique_et_mathématique":
                            Log.i("DOMAINE","Domaine choisit : Sport");
                            adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.Informatique_et_mathématique, android.R.layout.simple_spinner_item);
                            break;
                        case "Ecole_de_langue_française_et_de_culture_québécoise":
                            Log.i("DOMAINE","Domaine choisit : Histoire");
                            adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.Ecole_de_langue_française_et_de_culture_québécoise, android.R.layout.simple_spinner_item);
                            break;
                        case "Ecole_des_arts_numériques_de_l_animation_et_du_design_nad":
                            Log.i("DOMAINE","Domaine choisit : Sport");
                            adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.Ecole_des_arts_numériques_de_l_animation_et_du_design_nad, android.R.layout.simple_spinner_item);
                            break;
                        case "Sciences_de_la_santé":
                            Log.i("DOMAINE","Domaine choisit : Histoire");
                            adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.Sciences_de_la_santé, android.R.layout.simple_spinner_item);
                            break;
                        default:
                            break;
                    }
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinProg.setAdapter(adapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            addListenerOnButton();

        }else{
            setContentView(R.layout.renseignement_activity);

            final ImageButton buttonDepart = findViewById(R.id.arrive);
            buttonDepart.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intentRenseignement = new Intent(RenseignementActivity.this, RenseignementActivity.class);
                    intentRenseignement.putExtra(INDEX_RENSEIGNEMENT, false);
                    startActivity(intentRenseignement);
                    finish();
                }
            });

            final ImageButton buttonArrive = findViewById(R.id.arrive);
            buttonArrive.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intentRenseignement = new Intent(RenseignementActivity.this, RenseignementActivity.class);
                    intentRenseignement.putExtra(INDEX_RENSEIGNEMENT, true);
                    startActivity(intentRenseignement);
                    finish();
                }
            });
        }
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinnerPay = (Spinner) findViewById(R.id.spinnerPays);
        spinnerDomaine = (Spinner) findViewById(R.id.spinnerDomaineEtude);
        spinProg = (Spinner) findViewById(R.id.spinnerProgramme);

        radioGroup = (RadioGroup) findViewById(R.id.sessionArrivee);

        switchPE = (Switch) findViewById(R.id.permisEtude);
        switchEchange = (Switch) findViewById(R.id.echangeSwitch);
        switchIci = (Switch) findViewById(R.id.iciSwitch);
        switchTravail = (Switch) findViewById(R.id.travailSwitch);

        btnSubmit = (Button) findViewById(R.id.valider);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                RadioButton radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());

                EditText numberSession = (EditText) findViewById(R.id.numberText);

                /**
                 * A la place du toast il faut insérer les informations dans la base pour chaque user
                 * Pour le moment il insère les users avec des Id incorrectes donc il faudra changer pour avoir l'id de l'utilisateur
                 */
                writeNewRUser(String.valueOf(spinnerPay.getSelectedItem()), String.valueOf(spinnerDomaine.getSelectedItem()), String.valueOf(spinProg.getSelectedItem()), String.valueOf(radioButton.getText()),
                        Integer.parseInt(numberSession.getText().toString()), switchPE.isChecked(), switchEchange.isChecked(), switchIci.isChecked(), switchTravail.isChecked());

                Intent intentRenseignement = new Intent(RenseignementActivity.this, ToDoListActivity.class);
                intentRenseignement.putExtra(INDEX_RENSEIGNEMENT, false);
                startActivity(intentRenseignement);
                finish();
            }

        });
    }

    private void writeNewRUser(String pays, String domaineEtude, String progEtude, String sessionAdmi, int nbSession, boolean permisEtude, boolean echangeUni, boolean localisationChicout, boolean travailler) {

        String key = rUserDatabase.push().getKey(); // Changer pour utiliser l'id utilisateur
        RUser user = new RUser(pays, domaineEtude, progEtude, sessionAdmi, nbSession, permisEtude, echangeUni, localisationChicout, travailler);

        Map<String, Object> postValues = user.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, postValues);
        rUserDatabase.updateChildren(childUpdates);
    }

    //create the menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_goBack:
                Intent intentAccueil = new Intent(RenseignementActivity.this, MainActivity.class);
                startActivity(intentAccueil);
                finish();
                return true;

            case R.id.action_quit:
                finish();
                System.exit(0);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
