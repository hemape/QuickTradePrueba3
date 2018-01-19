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
    private TextView usuarioPerfil;
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
        usuarioPerfil = (TextView) findViewById(R.id.txtUsuario);
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
                //Cargamos datos usuario actual
                cargarUsuario(usuarioAEditar);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
    }

    //Eventos botones
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnModificarPErfil:

                if (validarCampos()){
                    //Comprobamos que datos han sido cambiados

                    //Nombre
                    if (!nombreUPerfil.getText().toString().equals(usuarioAEditar.getNombre())){
                        cambiarValor("nombre",nombreUPerfil.getText().toString());
                       // Toast.makeText(this, getString(R.string.edit_name_changed) + editNombre.getText().toString(), Toast.LENGTH_SHORT).show();
                    }

                    //Apedillos
                    if (!apellidosUPerfil.getText().toString().equals(usuarioAEditar.getApellido())){
                        cambiarValor("apellido",apellidosUPerfil.getText().toString());
                        //Toast.makeText(this, getString(R.string.edit_surnames_changed) + editApedillos.getText().toString(), Toast.LENGTH_SHORT).show();
                    }

                    //Dirección
                    if (!direccionUPerfil.getText().toString().equals(usuarioAEditar.getDireccion())){
                        cambiarValor("direccion",direccionUPerfil.getText().toString());
                        //Toast.makeText(this, getString(R.string.edit_direction_changed) + " " + editDireccion.getText().toString(), Toast.LENGTH_SHORT).show();
                    }



                }
                break;
        }
    }

    private void cambiarValor(String campo, String valor) {
        //Procedemos a cambiar el valor
        bbdd.child(clave).child(campo).setValue(valor);
    }

    //Cargar datos del usuario
    private void cargarUsuario(Usuario user){
        usuarioPerfil.setText("@" + usuarioAEditar.getUsuario());
        nombreUPerfil.setText(usuarioAEditar.getNombre());
        apellidosUPerfil.setText(usuarioAEditar.getApellido());
        direccionUPerfil.setText(usuarioAEditar.getDireccion());

        Toast.makeText(this, "Datos de " + user.getUsuario() + " cargados correctamente", Toast.LENGTH_SHORT).show();
    }

    //Validar campos introducidos
    private boolean validarCampos(){
        //Evalua campos no vacios
        if (nombreUPerfil.getText().toString().isEmpty() || apellidosUPerfil.getText().toString().isEmpty() ||  direccionUPerfil.getText().toString().isEmpty()){
            //Toast.makeText(getApplicationContext(),getString(R.string.error_input_values_empty),Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}