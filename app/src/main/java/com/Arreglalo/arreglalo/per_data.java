package com.Arreglalo.arreglalo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.Serializable;

import androidx.appcompat.app.AppCompatActivity;

public class per_data extends AppCompatActivity {
    private EditText nombre;
    private EditText apellido;
    private EditText email;
    private CheckBox ck_tyc;
    private CheckBox ck_tdp;
    private Button sig;
    private Cliente cliente;
    private EditText contrasena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_data);
        cliente = (Cliente) getIntent().getSerializableExtra("cliente");
        nombre = findViewById(R.id.ed_nombre);
        apellido = findViewById(R.id.ed_apellidos);
        email = findViewById(R.id.Email);
        contrasena = findViewById(R.id.ed_contrasena);
        ck_tdp =findViewById(R.id.ck_tdp);
        ck_tyc = findViewById(R.id.ck_tyc);
        sig = findViewById(R.id.bt_sig);
        sig.setEnabled(false);
    }
    public void uncheck(View view){
        String texto= nombre.getText().toString();
        if (ck_tyc.isChecked() && ck_tdp.isChecked()&& !texto.isEmpty()){
            sig.setEnabled(true);
        }else {
            //Toast.makeText(this,"Ingrese un numero valido y vuelva a intentar",Toast.LENGTH_SHORT).show();
            sig.setEnabled(false);


        }
    }
    public void click(View view){
        Intent intent= new Intent(this, perm_dir.class);
        cliente.setNombre(nombre.getText().toString()  + " "+apellido.getText().toString());
        cliente.setCorreo(email.getText().toString());
        cliente.setContrasena(contrasena.getText().toString());


        intent.putExtra("cliente",(Serializable)cliente);

        startActivity(intent);
        finish();
    }
}