package com.Arreglalo.arreglalo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity implements com.android.volley.Response.Listener<JSONObject>, Response.ErrorListener {
    private Cliente cliente;
    private Button button;
    private Button btsms;
    private CheckBox ck;
    private EditText num;


    private ProgressDialog dialog;
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btsms = findViewById(R.id.btsms);
        button =findViewById(R.id.bt1);
        btsms.setEnabled(false);
        button.setEnabled(false);
        ck = findViewById(R.id.ckb1);
        num =  (EditText) findViewById(R.id.ed_number);
        cliente = new Cliente();

        queue = Volley.newRequestQueue(this);
        cargarWebService();
    }

    public void clikc(View view){
        String i = (num.getText().toString());

        cliente.setNumero(i);
        //cliente.setId(2);
        Intent intent = new Intent(this, cod_seg.class);
        intent.putExtra("cliente",(Serializable) cliente);
        startActivity(intent);
    }


    public void cargarWebService(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando");
        String url = "https://arreglalo.co/recibirSol.php";
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
    Cliente cliente1 = new Cliente();
        JSONArray jsonArray = response.optJSONArray("usuario");
        JSONObject jsonObject= null;
        try {
            jsonObject  =jsonArray.getJSONObject(0);
            cliente.setId(jsonObject.optInt("Id_U")+1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void uncheck(View view){
        if (ck.isChecked() ){
            button.setEnabled(true);
            btsms.setEnabled(true);
        }else {
            Toast.makeText(this,"Ingrese un numero valido y vuelva a intentar", Toast.LENGTH_SHORT).show();
            button.setEnabled(false);
            ck.setChecked(false);

        }
    }


}