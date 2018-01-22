package com.example.adri9ps.quicktrade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.adri9ps.quicktrade.R;
import com.example.adri9ps.quicktrade.model.Producto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TodosFavoritos extends AppCompatActivity {

    private ListView favoTec, favoCoches, favoHogar;
    private FirebaseAuth fba;
    DatabaseReference bbddP;

    ArrayAdapter<String> adaptadorProductosHogar;
    ArrayAdapter<String> adaptadorProductosCoche;
    ArrayAdapter<String> adaptadorProductosTec;
    ArrayList<String> listadoProductosCoche = new ArrayList<String>();
    ArrayList<String> lisadoProductosHogar = new ArrayList<String>();
    ArrayList<String> listadoProductosTec = new ArrayList<String>();
    ArrayList<String> listadoProductosCocheFavoritos = new ArrayList<String>();
    ArrayList<String> listadoProductosHogarFavoritos = new ArrayList<String>();
    ArrayList<String> listadoProductosTecFavoritos = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_favoritos);

        favoTec = (ListView) findViewById(R.id.listViewTecnologiaFavorita);
        favoHogar = (ListView) findViewById(R.id.listViewHogarFavoritos);
        favoCoches = (ListView) findViewById(R.id.listViewCochesFavoritos);

        final String claveUsu = fba.getCurrentUser().getUid();
        bbddP = FirebaseDatabase.getInstance().getReference("Productos");

        cargarProductosFavoritosTecnologia();
        cargarProductosFavoritosCoches();
        cargarProductosFavoritosHogar();
    }

    public void cargarProductosFavoritosTecnologia() {
        Query q = bbddP.orderByChild("categoria").equalTo("Tecnologia");

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Carga Valores encontrados


                //Obtenemos nombres de productos
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Producto prod = datasnapshot.getValue(Producto.class);
                    String nombreProductoTec = prod.getNombre();

                    listadoProductosTecFavoritos.add(nombreProductoTec);
                    adaptadorProductosTec = new ArrayAdapter<String>(TodosFavoritos.this, android.R.layout.simple_list_item_1, listadoProductosTecFavoritos);
                    favoTec.setAdapter(adaptadorProductosTec);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void cargarProductosFavoritosCoches() {
        Query q = bbddP.orderByChild("categoria").equalTo("Coches");

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Carga Valores encontrados


                //Obtenemos nombres de productos
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Producto prod = datasnapshot.getValue(Producto.class);
                    String nombreProductoCoche = prod.getNombre();

                    listadoProductosCocheFavoritos.add(nombreProductoCoche);
                    adaptadorProductosCoche = new ArrayAdapter<String>(TodosFavoritos.this, android.R.layout.simple_list_item_1, listadoProductosCocheFavoritos);
                    favoCoches.setAdapter(adaptadorProductosCoche);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void cargarProductosFavoritosHogar(){
        Query q =  bbddP.orderByChild("categoria").equalTo("Hogar");

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Carga Valores encontrados



                //Obtenemos nombres de productos
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Producto prod = datasnapshot.getValue(Producto.class);
                    String nombreProductoHogar = prod.getNombre();

                    listadoProductosHogarFavoritos.add(nombreProductoHogar);
                    adaptadorProductosHogar = new ArrayAdapter<String>(TodosFavoritos.this, android.R.layout.simple_list_item_1, listadoProductosHogarFavoritos);
                    favoHogar.setAdapter(adaptadorProductosHogar);
                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
