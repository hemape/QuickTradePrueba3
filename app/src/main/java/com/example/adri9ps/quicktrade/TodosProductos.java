package com.example.adri9ps.quicktrade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.adri9ps.quicktrade.model.Producto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TodosProductos extends AppCompatActivity {

    private ListView lvTec,lvCoches,lvHogar;


    DatabaseReference bbddP;
    ArrayAdapter<String> adaptadorProductosHogar;
    ArrayAdapter<String> adaptadorProductosCoche;
    ArrayAdapter<String> adaptadorProductosTec;
    ArrayList<String> listadoProductosCoche = new ArrayList<String>();
    ArrayList<String> lisadoProductosHogar = new ArrayList<String>();
    ArrayList<String> listadoProductosTec = new ArrayList<String>();
    private FirebaseAuth fba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_productos);

        lvTec = (ListView) findViewById(R.id.listViewTecnologia);
        lvCoches = (ListView) findViewById(R.id.listViewCoches);
        lvHogar = (ListView) findViewById(R.id.listViewHogar);
        fba = FirebaseAuth.getInstance();


        final String claveUsu = fba.getCurrentUser().getUid();
        bbddP = FirebaseDatabase.getInstance().getReference("Productos");


        cargarProductosTecnologia();
        cargarProductosCoches();
        cargarProductosHogar();
    }

    public void cargarProductosTecnologia(){
        Query q =  bbddP.orderByChild("categoria").equalTo("Tecnologia");

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Carga Valores encontrados



                //Obtenemos nombres de productos
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Producto prod = datasnapshot.getValue(Producto.class);
                    String nombreProductoTec = prod.getNombre();

                    listadoProductosTec.add(nombreProductoTec);
                    adaptadorProductosTec = new ArrayAdapter<String>(TodosProductos.this, android.R.layout.simple_list_item_1, listadoProductosTec);
                    lvTec.setAdapter(adaptadorProductosTec);
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void cargarProductosCoches(){
        Query q =  bbddP.orderByChild("categoria").equalTo("Coches");

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Carga Valores encontrados



                //Obtenemos nombres de productos
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Producto prod = datasnapshot.getValue(Producto.class);
                    String nombreProductoCoche = prod.getNombre();

                    listadoProductosCoche.add(nombreProductoCoche);
                    adaptadorProductosCoche = new ArrayAdapter<String>(TodosProductos.this, android.R.layout.simple_list_item_1, listadoProductosCoche);
                    lvCoches.setAdapter(adaptadorProductosCoche);
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void cargarProductosHogar(){
        Query q =  bbddP.orderByChild("categoria").equalTo("Hogar");

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Carga Valores encontrados



                //Obtenemos nombres de productos
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Producto prod = datasnapshot.getValue(Producto.class);
                    String nombreProductoHogar = prod.getNombre();

                    lisadoProductosHogar.add(nombreProductoHogar);
                    adaptadorProductosHogar = new ArrayAdapter<String>(TodosProductos.this, android.R.layout.simple_list_item_1, lisadoProductosHogar);
                    lvHogar.setAdapter(adaptadorProductosHogar);
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
