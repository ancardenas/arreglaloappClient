package com.Arreglalo.arreglalo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.io.Serializable;

public class finalService extends AppCompatActivity {
    private Solicitud solicitud;
    private Cliente cliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_service);
        solicitud = (Solicitud) getIntent().getExtras().getSerializable("solicitud");
        cliente = (Cliente) getIntent().getSerializableExtra("cliente");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(finalService.this, cotizacion.class);
                intent.putExtra("cliente",((Serializable)cliente));
                intent.putExtra("solicitud",((Serializable)solicitud));
                startActivity(intent);
                finish();
            }
        },5000);
    }
}