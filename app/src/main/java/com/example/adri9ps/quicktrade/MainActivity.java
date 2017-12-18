package com.example.adri9ps.quicktrade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adri9ps.quicktrade.model.Usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText nombreU;
    private EditText apellidosU;
    private EditText correoU;
    private EditText direccionU;
    private Button btnNuevoUsuario;

    DatabaseReference bbdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombreU = (EditText) findViewById(R.id.editTextNombreUsuario);
        apellidosU = (EditText) findViewById(R.id.editTextApellidosUsuario);
        correoU = (EditText) findViewById(R.id.editTextCorreoUsuario);
        direccionU = (EditText) findViewById(R.id.editTextDireccionUsuario);
        btnNuevoUsuario = (Button) findViewById(R.id.btnNuevoUsuario);

        bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios));

        btnNuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nombreU.getText().toString().isEmpty() || apellidosU.getText().toString().isEmpty() ||
                        correoU.getText().toString().isEmpty() || direccionU.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"Faltan datos por rellenar",Toast.LENGTH_SHORT).show();
                }else{

                    String clave = bbdd.push().getKey();
                    Usuario u = new Usuario(nombreU.getText().toString(),apellidosU.getText().toString(),correoU.getText().toString(),direccionU.getText().toString());
                    bbdd.child(clave).setValue(u);

                    Toast.makeText(MainActivity.this,"Usuario añadido",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
