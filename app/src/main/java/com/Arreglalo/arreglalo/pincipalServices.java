package com.Arreglalo.arreglalo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class pincipalServices extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{
    private String service;
    private Cliente cliente;
    //private TextView search;


    private ProgressDialog dialog;
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private ArrayList<Solicitud> solicituds;
    private ArrayList<Integer> id_s =new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pincipal_services);
        cliente = (Cliente)getIntent().getSerializableExtra("cliente");
        //search = findViewById(R.id.ed_search);
        //search.setText(cliente.getNombre());
        queue = Volley.newRequestQueue(this);
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
        intent.putExtra("listServices",id_s);
        startActivity(intent);
    }
    public String getService(){
        return service;
    }

    public void update(View view){
        cargarWebService();
        /*new AlertDialog.Builder(this)
                .setTitle("Su solicitud ha sido cotizada")
                .setMessage("Un Fixer ha cotizado su solicitud desea observarla").setPositiveButton("Ver", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(),"Holi soy un dialogo",Toast.LENGTH_SHORT).show();

            }
        }).show();*/


    }

    public void cargarWebService(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando");
        String url = "https://arreglalo.co/clientList.php?id="+cliente.getId();

        dialog.show();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        queue.add(jsonObjectRequest);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        dialog.hide();
        Toast.makeText(this,"No fue posible encontrar servicios",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray jsonArray = response.optJSONArray("solicitud");
        if(jsonObjectRequest.getUrl().equals("https://arreglalo.co/clientList.php?id="+cliente.getId())){
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = null;
                Solicitud solicitud = new Solicitud();
                jsonObject = jsonArray.getJSONObject(i);
               /* solicitud.setService(jsonObject.optString("Nom_S"));
                solicitud.setDetails(jsonObject.optString("Desc_S"));
                solicitud.setId(jsonObject.optInt("Id_S"));
                solicitud.setAcepted(jsonObject.optBoolean("Acepted"));

                solicituds.add(solicitud);*/
                if(jsonObject!=null){
                id_s.add(jsonObject.optInt("Id_S1"));
                //Toast.makeText(this,id_s.get(i)+" ",Toast.LENGTH_SHORT).show();
                    }
            }
            dialog.hide();
        } catch (JSONException e) {
            e.printStackTrace();
        }
            Intent intent = new Intent(getApplicationContext(),clietnService.class);
            intent.putExtra("cliente",cliente);
            intent.putExtra("service",service);
            intent.putExtra("listServices",id_s);
            startActivity(intent);
        }
    }



}