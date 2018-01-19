package com.example.adri9ps.quicktrade;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioSesion extends AppCompatActivity {

    private EditText correoLogin, contraseñaLogin;
    private Button iniciarSesion, registrar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        correoLogin = (EditText) findViewById(R.id.editCorreoLogin);
        contraseñaLogin = (EditText) findViewById(R.id.editContraseñaLogin);
        iniciarSesion = (Button) findViewById(R.id.btnIniciarSesion);
        registrar = (Button) findViewById(R.id.btnRegistrar);
        mAuth = FirebaseAuth.getInstance();

        FirebaseAuth.getInstance().signOut();

        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(correoLogin.getText().toString(), contraseñaLogin.getText().toString());

            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rU = new Intent(InicioSesion.this, RegistrarUsuario.class);
                startActivity(rU);
            }
        });



    }

    private void login(final String email, String password) {



        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent main = new Intent(InicioSesion.this, MainActivity.class);
                            startActivity(main);
                            Toast.makeText(InicioSesion.this,"Correcto", Toast.LENGTH_SHORT).show();


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(InicioSesion.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }




}
