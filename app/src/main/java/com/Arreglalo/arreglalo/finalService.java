package com.Arreglalo.arreglalo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import java.io.Serializable;

public class finalService extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{
    private Solicitud solicitud;
    private Cliente cliente;
    private String fecha;

    private ProgressDialog dialog;
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_service);
        solicitud = (Solicitud) getIntent().getExtras().getSerializable("solicitud");
        cliente = (Cliente) getIntent().getSerializableExtra("cliente");
        queue = Volley.newRequestQueue(this);
        //cargarWebService();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(finalService.this, pincipalServices.class);
                intent.putExtra("cliente",((Serializable)cliente));
                intent.putExtra("solicitud",((Serializable)solicitud));
                //startActivity(intent);
                finish();
            }
        },5000);
    }
    public void cargarWebService(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando");
        String url = "https://arreglalo.co/recibirSolCot.php?id="+solicitud.getId();
        dialog.show();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        queue.add(jsonObjectRequest);
    }
    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        dialog.hide();

        JSONArray jsonArray = response.optJSONArray("usuario");
        JSONObject jsonObject= null;
        try {
            jsonObject  =jsonArray.getJSONObject(0);
            Toast.makeText(getApplicationContext(),jsonObject.opt("Fecha")+" ",Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}