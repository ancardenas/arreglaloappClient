package com.Arreglalo.arreglalo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class cotizacion extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{
    private Cliente cliente;
    private Solicitud solicitud;
    private TextView tx_service;
    private TextView tx_details;
    private TextView tx_time;
    private TextView tx_total;
    private TextView tx_cost;
    private TextView tx_date;
    private TextView tx_hour;


    private ProgressDialog dialog;
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotizacion);
        solicitud =  (Solicitud) getIntent().getSerializableExtra("solicitud");
        cliente = (Cliente) getIntent().getSerializableExtra("cliente");
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
        tx_date.setText("Tu fixer llegara el:"+solicitud.getDia()+"-"+solicitud.getMes()+"-"+solicitud.getAno());

        queue = Volley.newRequestQueue(this);

    }
    public void  click (View view){
        Intent intent = new Intent(this,Facil.class);
        /*
        AQUI es donde se deberia subir la solicitud a la base de datos
        el cual corresponde a la variable solicitud
         */
        cargarWebService();
        startActivity(intent);
    }

    private void cargarWebService() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("CARGAAAA");
        dialog.show();
        String url = "https://arreglalo.000webhostapp.com/insertSolicitud.php?id=1" +
                "&tipo="+solicitud.getService() +
                "&desc="+solicitud.getDetails() +
                "&uid="+cliente.getId() +
                "&fecha="+solicitud.getAno() +"-"+solicitud.getMes() +"-"+solicitud.getDia()+"%20"+solicitud.getHora()+":"+solicitud.getMinuto()+":00";

        url=url.replace(" ","%20");
        //String url1 ="http://192.168.0.10/arreglalo/index.php?nombre=yo&numero=2344&direccion=yo&correo=yo&ciudad=yo&contrasena=yo&calificacion=5&id=5";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        queue.add(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        dialog.hide();
        Toast.makeText(this,"MAMA NO LO LOGRE",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this,"MAMA LO LOGRE",Toast.LENGTH_SHORT).show();
        dialog.hide();
    }
}