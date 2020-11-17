package com.Arreglalo.arreglalo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class fix_Data extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{
    private ProgressDialog dialog;
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private Solicitud solicitud;
    private String fecha;
    private TextView tx_hour,tx_date,tx_cost,tx_time,fixerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix__data);
        solicitud =(Solicitud) getIntent().getSerializableExtra("solicitud");
        tx_hour = findViewById(R.id.txt_hourfix);
        tx_date = findViewById(R.id.txt_datefix);
        tx_cost = findViewById(R.id.txt_costfix);
        tx_time = findViewById(R.id.txt_duracion);
        fixerId = findViewById(R.id.txt_fixId);
        queue = Volley.newRequestQueue(this);
        cargarWebService();
    }
    public void cargarWebService(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando");

        dialog.show();
        String url = "https://arreglalo.co/recibirSolCot.php?id="+solicitud.getId();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        queue.add(jsonObjectRequest);
        tx_hour.setText("A las:"+solicitud.getHora()+":"+solicitud.getMinuto());



    }
    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {


        JSONArray jsonArray = response.optJSONArray("usuario");
        JSONObject jsonObject= null;
        if(jsonObjectRequest.getUrl().equals("https://arreglalo.co/recibirSolCot.php?id="+solicitud.getId())){
            try {
                jsonObject  =jsonArray.getJSONObject(0);
                //Toast.makeText(getApplicationContext(),jsonObject.optString("Fecha")+" ",Toast.LENGTH_SHORT).show();
                fecha = jsonObject.opt("Fecha") + " ";
                solicitud.setAno(Integer.parseInt(fecha.substring(0,4)));
                solicitud.setMes(Integer.parseInt(fecha.substring(5,7)));
                solicitud.setDia(Integer.parseInt(fecha.substring(8,10)));
                solicitud.setHora(Integer.parseInt(fecha.substring(11,13)));
                solicitud.setMinuto(Integer.parseInt(fecha.substring(14,16)));
                tx_hour.setText(solicitud.getHora()+":"+solicitud.getMinuto());
                tx_date.setText(solicitud.getDia()+"-"+solicitud.getMes()+"-"+solicitud.getAno());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String url1 = "https://arreglalo.co/recibirDuracion.php?id="+solicitud.getId();
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url1,null,this,this);
            queue.add(jsonObjectRequest);

        }else if(jsonObjectRequest.getUrl().equals("https://arreglalo.co/recibirDuracion.php?id="+solicitud.getId())){
            try {
                jsonObject  =jsonArray.getJSONObject(0);
                solicitud.setDuracion(jsonObject.optInt("Duracion"));
                fixerId.setText(jsonObject.optString("Id_F1"));



            } catch (JSONException e) {
                e.printStackTrace();
            }
            int cost = 25000*solicitud.getDuracion();

            tx_cost.setText(cost+" ");
            tx_time.setText(solicitud.getDuracion()+" ");

            dialog.hide();
        }else {
            dialog.hide();
        }



    }
}