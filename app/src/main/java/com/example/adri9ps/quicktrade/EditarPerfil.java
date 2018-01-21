package com.example.adri9ps.quicktrade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adri9ps.quicktrade.R;
import com.example.adri9ps.quicktrade.RegistrarUsuario;
import com.example.adri9ps.quicktrade.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditarPerfil extends AppCompatActivity {

    private EditText nombreUPerfil;
    private EditText apellidosUPerfil;
    private EditText direccionUPerfil;
    private EditText usuarioPerfil;
    private Button btnModificarPerfil;
    private ListView lvPerfil;
    ArrayList<String> listadoUsuarios;
    private FirebaseAuth fba;
    FirebaseUser user;
    Usuario usuarioAEditar;
    String clave;

    DatabaseReference bbdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        nombreUPerfil = (EditText) findViewById(R.id.editTextNombreUsuarioPErfil);
        apellidosUPerfil = (EditText) findViewById(R.id.editTextApellidosUsuarioPerfil);
        //correoU = (EditText) findViewById(R.id.editTextCorreoUsuarioPErfil);
        usuarioPerfil = (EditText) findViewById(R.id.usuarioPErfil);
        direccionUPerfil = (EditText) findViewById(R.id.editTextDireccionUsuarioPErfil);
        //contraseñaU = (EditText) findViewById(R.id.editContraseña);
        btnModificarPerfil = (Button) findViewById(R.id.btnModificarPErfil);
        lvPerfil = (ListView) findViewById(R.id.listViewPErfil);

        //Obtener usuario actual
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Obtener BBDD FireBase
        bbdd = FirebaseDatabase.getInstance().getReference("usuarios");

        //Buscar al usuario de la sesión actual
        Query q = bbdd.orderByChild("usuario").equalTo((String) user.getDisplayName());
        q.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    usuarioAEditar = datasnapshot.getValue(Usuario.class);
                    clave = datasnapshot.getKey();


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        btnModificarPerfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String usuario = usuarioPerfil.getText().toString();

                if (!usuario.isEmpty()) {
                    Query q = bbdd.orderByChild(getString(R.string.campo_usuario)).equalTo(usuario);

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                                String clave = datasnapshot.getKey();

                                if (!nombreUPerfil.getText().toString().isEmpty()) {
                                    bbdd.child(clave).child(getString(R.string.campo_nombre)).setValue(nombreUPerfil.getText().toString());
                                }
                                if (!apellidosUPerfil.getText().toString().isEmpty()) {
                                    bbdd.child(clave).child(getString(R.string.campo_apellidos)).setValue(apellidosUPerfil.getText().toString());
                                }
                                if (!direccionUPerfil.getText().toString().isEmpty()) {
                                    bbdd.child(clave).child(getString(R.string.campo_direccion)).setValue(direccionUPerfil.getText().toString());
                                }
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    Toast.makeText(EditarPerfil.this, "Los datos se han modificado con éxito", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(EditarPerfil.this, "Error", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


}