package com.Arreglalo.arreglalo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class cotizacion extends AppCompatActivity {
    private Solicitud solicitud;
    private TextView tx_service;
    private TextView tx_details;
    private TextView tx_time;
    private TextView tx_total;
    private TextView tx_cost;
    private TextView tx_date;
    private TextView tx_hour;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotizacion);
        solicitud =  (Solicitud) getIntent().getSerializableExtra("solicitud");
        tx_service = findViewById(R.id.txt_service);
        tx_details = findViewById(R.id.txt_details);
        tx_time = findViewById(R.id.txt_time);
        tx_total = findViewById(R.id.txt_total);
        tx_cost = findViewById(R.id.txt_cost);
        tx_date = findViewById(R.id.txt_date);
        tx_hour = findViewById(R.id.txt_hour);
        tx_service.setText("Tipo de servicio:"+solicitud.getService());
        tx_details.setText(solicitud.getDetails());
        tx_hour.setText("A las:"+solicitud.getHora()+":"+solicitud.getMinuto());
        int cost = 25000*3;

        tx_cost.setText(cost+" ");
        tx_time.setText("Duracion: 3 horas");
        tx_date.setText("Tu fixer llegara el:"+solicitud.getDia()+"/"+solicitud.getMes()+"/"+solicitud.getAno());

    }
    public void  click (View view){
        Intent intent = new Intent(this,Facil.class);
        /*
        AQUI es donde se deberia subir la solicitud a la base de datos
        el cual corresponde a la variable solicitud
         */
        startActivity(intent);
    }
}