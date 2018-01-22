package com.example.adri9ps.quicktrade;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adri9ps.quicktrade.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class    RegistrarUsuario extends AppCompatActivity {

    private EditText nombreU;
    private EditText apellidosU;
    private EditText correoU;
    private EditText direccionU;
    private EditText usuarioU;
    private EditText contraseñaU;
    private Button btnNuevoUsuario;
    private Button btnModificar;
    private ListView lv;
    ArrayList<String> listadoUsuarios;
    private FirebaseAuth fba;
    FirebaseUser user;

    DatabaseReference bbdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);


        nombreU = (EditText) findViewById(R.id.editTextNombreUsuario);
        apellidosU = (EditText) findViewById(R.id.editTextApellidosUsuario);
        correoU = (EditText) findViewById(R.id.editTextCorreoUsuario);
        usuarioU = (EditText) findViewById(R.id.editUsuario);
        direccionU = (EditText) findViewById(R.id.editTextDireccionUsuario);
        contraseñaU = (EditText) findViewById(R.id.editContraseña);
        btnNuevoUsuario = (Button) findViewById(R.id.btnNuevoUsuario);
        btnModificar = (Button) findViewById(R.id.btnModificar);
        lv = (ListView) findViewById(R.id.listView);



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
                adaptador = new ArrayAdapter<String>(RegistrarUsuario.this, android.R.layout.simple_list_item_1, listado);
                lv.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnNuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (nombreU.getText().toString().isEmpty() || apellidosU.getText().toString().isEmpty() ||
                        correoU.getText().toString().isEmpty() || direccionU.getText().toString().isEmpty()
                        || usuarioU.getText().toString().isEmpty() || contraseñaU.getText().toString().isEmpty()) {
                    Toast.makeText(RegistrarUsuario.this, "Faltan datos por rellenar", Toast.LENGTH_SHORT).show();
                } else {
                    boolean valido = true;

                    for (int i = 0; i < listadoUsuarios.size(); i++) {
                        if (usuarioU.getText().toString().equals(listadoUsuarios.get(i))) {
                            Toast.makeText(RegistrarUsuario.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                            valido = false;
                        }
                    }
                    if (valido) {
                        String email = correoU.getText().toString();
                        String password = contraseñaU.getText().toString();
                        registrar(email,password);


                    }

                }
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String usuario = usuarioU.getText().toString();

                if (!usuario.isEmpty()) {
                    Query q = bbdd.orderByChild(getString(R.string.campo_usuario)).equalTo(usuario);

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                                String clave = datasnapshot.getKey();

                                if (!nombreU.getText().toString().isEmpty()) {
                                    bbdd.child(clave).child(getString(R.string.campo_nombre)).setValue(nombreU.getText().toString());
                                }
                                if (!apellidosU.getText().toString().isEmpty()) {
                                    bbdd.child(clave).child(getString(R.string.campo_apellidos)).setValue(apellidosU.getText().toString());
                                }
                                if (!direccionU.getText().toString().isEmpty()) {
                                    bbdd.child(clave).child(getString(R.string.campo_direccion)).setValue(direccionU.getText().toString());
                                }
                                if (!correoU.getText().toString().isEmpty()) {
                                    bbdd.child(clave).child(getString(R.string.campo_correo)).setValue(correoU.getText().toString());
                                }
                                if (!contraseñaU.getText().toString().isEmpty()) {
                                    bbdd.child(clave).child(getString(R.string.campo_contraseña)).setValue(contraseñaU.getText().toString());
                                }


                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    Toast.makeText(RegistrarUsuario.this, "Los datos se han modificado con éxito", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegistrarUsuario.this, "Error", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    private void registrar(String email, String password){

        fba = FirebaseAuth.getInstance();

        fba.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = fba.getCurrentUser();
                            // If sign in succes, display a message to the user.
                            Toast.makeText(RegistrarUsuario.this, "Authentication succes."+user.getUid(),
                                    Toast.LENGTH_SHORT).show();
                            insertaUsuarios();
                            Intent activity = new Intent(getApplicationContext(), InicioSesion.class);
                            startActivity(activity);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegistrarUsuario.this, "Authentication failed." + task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                            Log.d("#TEMP",task.getException().toString());

                        }

                        // ...
                    }
                });

    }

    private void insertaUsuarios(){
        String clave = bbdd.push().getKey();
        Usuario u = new Usuario(nombreU.getText().toString(), apellidosU.getText().toString(), correoU.getText().toString(), direccionU.getText().toString(), usuarioU.getText().toString(), contraseñaU.getText().toString());
        bbdd.child(clave).setValue(u);


        Toast.makeText(RegistrarUsuario.this, "Usuario añadido", Toast.LENGTH_SHORT).show();
    }


}


