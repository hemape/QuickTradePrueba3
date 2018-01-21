package com.example.adri9ps.quicktrade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.adri9ps.quicktrade.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TodosUsuarios extends AppCompatActivity {

    private ListView usuarios;
    DatabaseReference bbdd;
    ArrayList<String> listadoUsuarios;
    private Button volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_usuarios);
        usuarios = (ListView) findViewById(R.id.listViewTodosUsuarios);
        volver = (Button) findViewById(R.id.btnAtras);

        bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios));
        bbdd.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayAdapter<String> adaptador;
                ArrayList<String> listado = new ArrayList<String>();
                listadoUsuarios = new ArrayList<String>();

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Usuario usuario = datasnapshot.getValue(Usuario.class);

                    String nombre = usuario.getNombre();
                    String usu = usuario.getUsuario();
                    listado.add(nombre);
                    listadoUsuarios.add(usu);
                }
                adaptador = new ArrayAdapter<String>(TodosUsuarios.this, android.R.layout.simple_list_item_1, listado);
                usuarios.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activity);

            }
        });

    }
}
