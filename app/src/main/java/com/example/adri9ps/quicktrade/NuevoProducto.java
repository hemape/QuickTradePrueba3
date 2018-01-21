package com.example.adri9ps.quicktrade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.adri9ps.quicktrade.model.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NuevoProducto extends AppCompatActivity {

    private EditText nombreProducto;
    private EditText descripcionProducto;
    private EditText precioProducto;
    private Spinner categoriaProducto;
    private Button btnNuevoProducto;
    private Button btnModificarProducto;
    private ListView lvMisProductos;
    private String categoria;
    private FirebaseAuth fba;
    FirebaseUser user;
    ArrayAdapter<String> adaptadorMisProductos;
    ArrayList<String> listadoMisProductos = new ArrayList<String>();
    DatabaseReference bbdd;
    DatabaseReference bbddP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);

        nombreProducto = (EditText) findViewById(R.id.editTextNombreProducto);
        descripcionProducto = (EditText) findViewById(R.id.editDescripcionProducto);
        precioProducto = (EditText) findViewById(R.id.editPrecioProducto);
        categoriaProducto = (Spinner) findViewById(R.id.spinnerCategorias);
        btnNuevoProducto = (Button) findViewById(R.id.btnNuevoProducto);
        btnModificarProducto = (Button) findViewById(R.id.btnModificarProducto);
        lvMisProductos = (ListView) findViewById(R.id.listViewMisProductos);

        fba = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();


        bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios));
        final String claveUsu = fba.getCurrentUser().getUid();
        bbddP = FirebaseDatabase.getInstance().getReference(("Productos"));


        bbddP.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cargarProductosDelUsuarioLogueado();

//                ArrayAdapter<String> adaptadorMisProductos;
//                ArrayList<String> listadoMisProductos = new ArrayList<String>();
//
//                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
//                    String prodDeUsuario = datasnapshot.getValue(Producto.class).getCodigo_usuario_logueado().toString();
//                    Producto prod = datasnapshot.getValue(Producto.class);
//
//                    String nombreProducto = prod.getNombre();
//                    if(prodDeUsuario.equals(user.getUid().toString())) {
//                        listadoMisProductos.add(nombreProducto);
//                    }
//
//
//                }
//                adaptadorMisProductos = new ArrayAdapter<String>(NuevoProducto.this, android.R.layout.simple_list_item_1, listadoMisProductos);
//                lvMisProductos.setAdapter(adaptadorMisProductos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categoria_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categoriaProducto.setAdapter(adapter);


        categoriaProducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                categoria = (String) adapterView.getItemAtPosition(pos);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnNuevoProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (nombreProducto.getText().toString().isEmpty() || descripcionProducto.getText().toString().isEmpty() ||
                        precioProducto.getText().toString().isEmpty()) {
                    Toast.makeText(NuevoProducto.this, "Faltan datos por rellenar", Toast.LENGTH_SHORT).show();
                } else {
                    boolean valido = true;

                    if (valido) {


                        Producto prod = new Producto(nombreProducto.getText().toString(), descripcionProducto.getText().toString(), categoria, precioProducto.getText().toString(), claveUsu);
                        final String claveP = bbddP.push().getKey();

                        bbddP.child(claveP).setValue(prod);
                        Toast.makeText(NuevoProducto.this, "Producto añadido", Toast.LENGTH_SHORT).show();


                    }

                }
            }
        });
    }

    public void cargarProductosDelUsuarioLogueado() {

        Query q = bbddP.orderByChild("codigo_usuario_logueado").equalTo(user.getUid().toString());

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Carga Valores encontrados


                //Obtenemos nombres de productos
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Producto prod = datasnapshot.getValue(Producto.class);
                    String nombreProductoHogar = prod.getNombre();

                    listadoMisProductos.add(nombreProductoHogar);
                    adaptadorMisProductos = new ArrayAdapter<String>(NuevoProducto.this, android.R.layout.simple_list_item_1, listadoMisProductos);
                    lvMisProductos.setAdapter(adaptadorMisProductos);
                    adaptadorMisProductos.notifyDataSetChanged();                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}



