package com.Arreglalo.arreglalo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import java.io.Serializable;

import androidx.appcompat.app.AppCompatActivity;

public class perm_dir extends AppCompatActivity {
    private Cliente cliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perm_dir);
        cliente = (Cliente)getIntent().getSerializableExtra("cliente");
    }
    public void click(View view){
        Intent intent = new Intent(this,mapaintento1.class);
        intent.putExtra("cliente",(Serializable)cliente);
        startActivity(intent);
        finish();
    }
}