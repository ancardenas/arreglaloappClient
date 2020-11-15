package com.Arreglalo.arreglalo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
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

public class initSesion extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{
    private ProgressDialog dialog;
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private Cliente cliente;

    private EditText correo, contrasena;

    private boolean correcto = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_sesion);
        correo = findViewById(R.id.ed_correoIn);
        contrasena = findViewById(R.id.ed_contrasenaIn);
        cliente = new Cliente();
        queue = Volley.newRequestQueue(this);

    }
    public void click(View view){
        cargarWebService();
        //Declaramos un Handler que hace de unión entre el hilo principal y el secundario
        Handler handler = new Handler();

//Llamamos al método postDelayed
        handler.postDelayed(new Runnable() {
            public void run() {
       //#código que se ejecuta tras el "delay"
                if (correcto){
                    Toast.makeText(getApplicationContext(),"SI ES USUARIO",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),pincipalServices.class);
                    intent.putExtra("cliente",(Serializable) cliente);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"NO ES USUARIO",Toast.LENGTH_SHORT).show();
                }
            }
        }, 1000); // 2 segundos de "delay"

    }





    public void cargarWebService(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando");
        String url = "https://arreglalo.000webhostapp.com/initSesion.php?correo="+correo.getText().toString() +
                "&contrasena="+contrasena.getText().toString();
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

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject!=null){
            correcto=true;
            cliente.setId(jsonObject.optInt("Id_U"));
            cliente.setNombre(jsonObject.optString("Nom_U"));
            cliente.setCorreo(jsonObject.optString("Mail_U"));
            cliente.setNumero(jsonObject.optString("Tel_U"));
            cliente.setDireccion(jsonObject.optString("Dir_U"));
            cliente.setCalificacion(jsonObject.optDouble("Calif_U"));
            cliente.setCiudad(jsonObject.optString("City_U"));
            cliente.setContrasena(jsonObject.optString("Cont_U"));
        }else{
            correcto=false;

        }

    }

}