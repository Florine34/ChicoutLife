package com.example.flo.chicoutlife;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.support.v4.util.ArrayMap;

public class ChoiceAnnonce extends Fragment {

    RecyclerView recyclerView;
    VerticalAdapter verticalAdapter;
    private List<ModelAnnonce> modelsAnnonces;

    FirebaseDatabase database;
    DatabaseReference annonces;
    DatabaseReference infoPages;
    FirebaseStorage storage ;
    private StorageReference mStorageRef;
    private View racine;
    private static Intent intentAnnonce;
    ArrayList<String> tableauParametre;
    static Bundle bundleAnnonce;
    static ToDoListInfosAnnonces activityParent;

    public static Fragment newInstance(){return new ChoiceAnnonce();}


    public static Fragment newInstance(Intent intent){
        intentAnnonce = intent;
        return new ChoiceAnnonce();}

    public static Fragment newInstance(Bundle bundle){
        bundleAnnonce= bundle;
        return new ChoiceAnnonce();}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.choix_annonces);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup racineView =(ViewGroup) inflater.inflate(R.layout.choix_annonces,container,false);
        racine = racineView;
        return racine;
    }

    @Override
    public void onStart() {
        super.onStart();
        database = FirebaseDatabase.getInstance();
        storage =  FirebaseStorage.getInstance();

        activityParent = (ToDoListInfosAnnonces) getActivity();//TODO getparent ???
        remplirTableauParametres();

        /*Ajout d'un listener pour recharger la page quand des parametres sont choisi*/
        ajoutBoutonRecherche() ;

        //TODO faire en recevant des parametres cocher selon type si non general
        recyclerView = (RecyclerView) racine.findViewById(R.id.linearMiniaturesAnnonces);

        modelsAnnonces = fillWithAnnonce();

    }

    public  List<ModelAnnonce> fillWithAnnonce(){

        //Annonces choisie a afficher
        annonces = database.getReference("Annonces");
        annonces.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(final DataSnapshot annonce : dataSnapshot.getChildren()){

                    modelsAnnonces = new ArrayList<>();
                    verticalAdapter = new VerticalAdapter(modelsAnnonces,getContext());

                    DataSnapshot dataTags = annonce.child("Tags");
                    ArrayMap mapTags= new ArrayMap<>();


                    //Liste des tags de l'annonce en cours
                    for(DataSnapshot Tag : dataTags.getChildren()){
                            mapTags.put((String) Tag.getKey() , Tag.getValue());
                    }


                    String cheminImg = (String)annonce.child("Image").getValue();
                    if(!cheminImg.equals("")) {
                        mStorageRef = storage.getReference();

                            StorageReference imageRef = mStorageRef.child(cheminImg);

                            if (annonceValidateByParameter(mapTags, tableauParametre)) {

                                final long ONE_MEGABYTE = 4096 * 4096;
                                imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {

                                        /*Recuperation des donnes textes dans la base de donnee*/
                                        String modelTitre = (String) annonce.child("Titre").getValue();
                                        String modelDescription = (String) annonce.child("Description").getValue();
                                        if (modelDescription.length() > 150) {
                                            modelDescription = modelDescription.substring(0, 149);
                                            modelDescription = modelDescription + "...";
                                        }
                                        ;

                                        /*Recuperation du chemin de l'annonce pour la redirection lors du OnClick*/
                                        String cheminAnnonceBdd = annonce.getKey();
                                        Bitmap imageAnnonce = null;

                                        /*Recuperation de l'image dans la base de donnee*/
                                        imageAnnonce = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                        imageAnnonce = Bitmap.createScaledBitmap(imageAnnonce, 360, 240, false);
                                        ModelAnnonce model = new ModelAnnonce(modelTitre, modelDescription, imageAnnonce, cheminAnnonceBdd);
                                        modelsAnnonces.add(model);

                                        /*Sert pour le recyclerView , sinon la taille est initialiser a zero et le recycler ne se remplit pas*/
                                        verticalAdapter.notifyDataSetChanged();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle any errors
                                        Log.e("passage", "failure dans choiceAnnonce " + exception.getMessage());
                                    }
                                });
                            }



                    }
                }
                Log.d("passage","taille din datachange" + modelsAnnonces.size());

                LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(verticalLayoutManager);
                recyclerView.setAdapter(verticalAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //TODO remplir avec bdd realtime et storage
        return modelsAnnonces;
    }

    public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.MyViewHolder>{

        List<ModelAnnonce> verticalList ;
        Context context;

        public VerticalAdapter(List<ModelAnnonce> verticalList,Context context){
            this.verticalList = verticalList;
            this.context = context;
        }

        @Override
        public int getItemCount() {
            return verticalList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            ImageView imageViewAnnonce;
            TextView titre;
            TextView description;

            public MyViewHolder(View view){
                super(view);
                 /*Lier avec les view du xml*/
                imageViewAnnonce =(ImageView) view.findViewById(R.id.imageMiniatureAnnonce);
                titre = (TextView) view.findViewById(R.id.titreMiniatureAnnonce);
                description = (TextView) view.findViewById(R.id.descriptionMiniatureAnnonce);
            }
        }


        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.d("passage","est onCreateViewHolder");
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_miniature_annonce,parent,false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

            /*Set des donnes sur le holder*/
            holder.imageViewAnnonce.setImageBitmap(verticalList.get(position).getImageAnnonce());
            holder.titre.setText(verticalList.get(position).getModelTitre());
            holder.description.setText(verticalList.get(position).getModelDescription());

            /*Ajout du OnClick pour rediriger vers l'annonce*/
            final ModelAnnonce min = verticalList.get(position);
            holder.imageViewAnnonce.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context ,Annonce.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//sinon erreur
                    intent.putExtra("CHEMIN_ANNONCE",verticalList.get(position).getCheminAnnonceBdd());
                    intent.putExtra("NOM_PAGE",intentAnnonce.getStringExtra("NOM_PAGE"));
                   // intent.putExtra("NOM_PAGE", min.getCheminAnnonceBdd());
                    context.startActivity(intent);

                    }
            }
            );

        }
    }

    public void remplirTableauParametres(){

        //Parametres de la page de base choisie

        if (bundleAnnonce.getString("TYPE_INTENT").equals("accesbyintent")) {
            tableauParametre = new ArrayList<String>();
            infoPages = database.getReference("InfosPages");
            infoPages.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    DataSnapshot tagsPage = dataSnapshot.child(bundleAnnonce.getString("NOM_PAGE")).child("Tags");
                    for (DataSnapshot dataTagsPage : tagsPage.getChildren()) {
                        tableauParametre.add(dataTagsPage.getKey());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{
            //Parametres choisie par l'utilisateur

            tableauParametre = bundleAnnonce.getStringArrayList("TAB_PARAM");
        }

    }
    public boolean annonceValidateByParameter(ArrayMap arrayMap , ArrayList<String> parametreRequest){


       //Parametre de l'objet a afficher

        int nbrParam = parametreRequest.size();
        int i;

        Log.d("passage","dansAnnonceValidateByParameter taille param " + parametreRequest.size());
        for(i= 0 ; i<nbrParam; i++){
//            Log.d("passage" , "Dans annonceValidateByParameter" +arrayMap.get(parametreRequest.get(i)).getClass());
            if(!(boolean)arrayMap.get(parametreRequest.get(i)) ){
                return false;
            }
        }
        return true;
    }

    public void ajoutBoutonRecherche(){
        Button boutonRecherche = racine.findViewById(R.id.idRechercherAnnonces);
        boutonRecherche.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {

                Bundle bundleRecherche = new Bundle();

                bundleRecherche.putString("NOM_PAGE",bundleAnnonce.getString("NOM_PAGE"));
                bundleRecherche.putString("NOMBRE_PAGE",bundleAnnonce.getString("NOMBRE_PAGE"));
                bundleRecherche.putString("TYPE_INTENT","accesbyparam");

                List<CheckBox> items = new ArrayList<CheckBox>();
                items.add((CheckBox)racine.findViewById(R.id.checkBoxTagAppartement));
                items.add((CheckBox)racine.findViewById(R.id.checkBoxTagElectronique));
                items.add((CheckBox)racine.findViewById(R.id.checkBoxTagNourriture));
                items.add((CheckBox)racine.findViewById(R.id.checkBoxTagVetements));

                tableauParametre = new ArrayList<String>();
                for (CheckBox item : items){
                    if(item.isChecked())
                        tableauParametre.add((String)item.getContentDescription());
                }
                bundleRecherche.putStringArrayList("TAB_PARAM",tableauParametre);
                activityParent.reinstanciatePageAdapter(bundleRecherche);

            }
        });

    }

}
