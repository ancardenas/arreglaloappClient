package com.Arreglalo.arreglalo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

import androidx.appcompat.app.AppCompatActivity;

public class cod_seg extends AppCompatActivity {
    private Button button;
    private Cliente cliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cod_seg);
        button = findViewById(R.id.bt_cod);
        cliente = (Cliente) getIntent().getSerializableExtra("cliente");
    }
    public void btcod(View view){
        Intent intent = new Intent(this,per_data.class);
        intent.putExtra("cliente",(Serializable)cliente);
        startActivity(intent);
    }
}