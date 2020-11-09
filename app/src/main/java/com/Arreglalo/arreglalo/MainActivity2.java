package com.Arreglalo.arreglalo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    private Cliente cliente;
    private Button button;
    private Button btsms;
    private CheckBox ck;
    private EditText num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btsms = findViewById(R.id.btsms);
        button =findViewById(R.id.bt1);
        btsms.setEnabled(false);
        button.setEnabled(false);
        ck = findViewById(R.id.ckb1);
        num =  (EditText) findViewById(R.id.ed_number);
        cliente = new Cliente();


    }

    public void clikc(View view){
        String i = (num.getText().toString());

        cliente.setNumero(i);

        Intent intent = new Intent(this, cod_seg.class);
        intent.putExtra("cliente",(Serializable) cliente);
        startActivity(intent);
    }
    public void uncheck(View view){
        if (ck.isChecked() ){
            button.setEnabled(true);
            btsms.setEnabled(true);
        }else {
            Toast.makeText(this,"Ingrese un numero valido y vuelva a intentar", Toast.LENGTH_SHORT).show();
            button.setEnabled(false);
            ck.setChecked(false);

        }
    }
}