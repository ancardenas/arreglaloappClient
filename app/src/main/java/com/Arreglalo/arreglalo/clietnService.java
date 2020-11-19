package com.Arreglalo.arreglalo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.Arreglalo.arreglalo.adapters.clienteAdapter;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class clietnService extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{
    ArrayList<Solicitud> solicituds;
    RecyclerView recyclerView;
    private ArrayList<Integer> id_s =new ArrayList<Integer>();
    Cliente cliente;
    private ProgressDialog dialog;
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    Solicitud solicitud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clietn_service);
        solicituds = new  ArrayList<>();
         id_s = (ArrayList<Integer>) getIntent().getSerializableExtra("listServices");
        queue = Volley.newRequestQueue(this);
        cliente = (Cliente) getIntent().getSerializableExtra("cliente");
        cargarWebService();


    }
    @Override
    protected void onResume() {

        super.onResume();
        cargarWebService();
        //Toast.makeText(this,"Resumiendo",Toast.LENGTH_SHORT).show();
    }
    public void cargarLista(){
        clienteAdapter adapter = new clienteAdapter(solicituds,getApplicationContext());
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter.setOnClickListener(v -> {


            solicitud = solicituds.get(recyclerView.getChildAdapterPosition(v));
            if (solicitud.isAcepted()&&!solicitud.isComplete()){
                Intent intent = new Intent(getApplicationContext(), cotizacion.class);
                intent.putExtra("cliente",((Serializable)cliente));
                intent.putExtra("solicitud",((Serializable)solicitud));
                startActivity(intent);
                finish();
            }else if(solicitud.isComplete()){
                Intent intent = new Intent(getApplicationContext(), fix_Data.class);
                intent.putExtra("cliente",((Serializable)cliente));
                intent.putExtra("solicitud",((Serializable)solicitud));
                startActivity(intent);

            }else {
                Toast.makeText(getApplicationContext(),"Los Fixers se encuentran revisando su solicitud",Toast.LENGTH_SHORT).show();
            }
            //

        });
        recyclerView.setAdapter(adapter);
    }

    public void cargarWebService(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando");
        String url = "https://arreglalo.co/listaSol1.php";
        //dialog.show();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        queue.add(jsonObjectRequest);

    }
    @Override
    public void onErrorResponse(VolleyError error) {
        //dialog.hide();
        Toast.makeText(this,"No fue posible encontrar servicios",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray jsonArray = response.optJSONArray("solicitud");
        solicituds = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = null;
                Solicitud solicitud = new Solicitud();
                jsonObject = jsonArray.getJSONObject(i);


                solicitud.setService(jsonObject.optString("Nom_S"));
                solicitud.setDetails(jsonObject.optString("Desc_S"));
                solicitud.setId(jsonObject.optInt("Id_S"));
                //Toast.makeText(this,jsonObject.optInt("Acepted")+" ",Toast.LENGTH_SHORT).show();
                boolean acep;

                if(jsonObject.optInt("Acepted")==1){
                    acep = true;
                }else{
                    acep = false;
                }
                solicitud.setAcepted(acep);
                boolean comp;

                if(jsonObject.optInt("Complete")==1){
                    comp = true;
                }else{
                    comp = false;
                }
                solicitud.setComplete(comp);
                //Toast.makeText(this,solicitud.isComplete()+" ",Toast.LENGTH_LONG);
                if (id_s.contains(solicitud.getId())){
                    solicituds.add(solicitud);
                }

               // Toast.makeText(this,solicitud.getService()+i,Toast.LENGTH_SHORT).show();
            }
           // dialog.hide();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cargarLista();
        //dialog.hide();

    }


}