package com.Arreglalo.arreglalo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class pincipalServices extends AppCompatActivity {
    private String service;
    private Cliente cliente;
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pincipal_services);
        cliente = (Cliente)getIntent().getSerializableExtra("cliente");
        search = findViewById(R.id.ed_search);
        search.setText(cliente.getNombre());
    }
    public void click(View view){

        switch (view.getId()){
            case R.id.Griferia: service = "Griferia"; break;
            case R.id.Filtraciones: service = "Filtraciones"; break;
            case R.id.repTub: service = "Reparacion de Tuberias"; break;
            case R.id.Desagüe: service = "Desagüe"; break;
            case R.id.Desatasco: service = "Desatasco"; break;
            case R.id.Pintura: service = "Pintura General"; break;
            case R.id.Resanes: service = "Resanes"; break;
            case R.id.colg_obj: service = "Colgar un Objeto"; break;
            case R.id.est_gas: service = "Estufa de Gas"; break;
            case R.id.Chapas: service = "Chapas"; break;
            case R.id.montMob: service = "Montaje de Mobiliario"; break;
            case R.id.bisagra: service="Bisagras";break;
            case R.id.repGenelectr: service = "Reparaciones Generales Electricas";break;
            default: service = "Default";
        }
        Intent intent = new Intent(this,confirmService.class);
        intent.putExtra("cliente",cliente);
        intent.putExtra("service",service);
        startActivity(intent);
    }
    public String getService(){
        return service;
    }
}