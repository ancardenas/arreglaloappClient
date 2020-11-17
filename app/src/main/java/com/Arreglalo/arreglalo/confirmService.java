package com.Arreglalo.arreglalo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.OnMapReadyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;
import java.util.Timer;

public class confirmService extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    private String service;
    private TextView textView;
    private EditText det;
    private String details;
    private Button btFecha;
    private Button btHora;
    private TextView fecha;
    private TextView hour;
    private int dia,mes,ano;
    private int d,m,a,hora,minutos;
    private Cliente cliente;
    private Solicitud solicitud;
private int id;
    private boolean carge=false;

    private ProgressDialog dialog;
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_service);
        textView = findViewById(R.id.services);
        service = getIntent().getStringExtra("service");
        textView.setText(service);
        btFecha = findViewById(R.id.btfecha);
        btHora = findViewById(R.id.bthora);
        fecha = findViewById(R.id.fecha);
        hour = findViewById(R.id.hora);
        det = findViewById(R.id.det);
        cliente = (Cliente) getIntent().getSerializableExtra("cliente");
        queue = Volley.newRequestQueue(this);
        cargarWebService();
        //Declaramos un Handler que hace de unión entre el hilo principal y el secundario
        Handler handler = new Handler();

//Llamamos al método postDelayed
        handler.postDelayed(new Runnable() {
            public void run() {
       //#código que se ejecuta tras el "delay"
                carge=true;
            }
        }, 2000); // 2 segundos de "delay"
    }
    public void click(View view){
        Intent intent = new Intent(this,finalService.class);
        details = det.getText().toString();
        solicitud = new Solicitud(d,m,a,hora,minutos,service,details,cliente);
        solicitud.setId(id);

        intent.putExtra("solicitud", (Serializable) solicitud);
        intent.putExtra("cliente",(Serializable) cliente);


        cargarWebService();
        startActivity(intent);
        finish();
    }
    public void fechaHora(View view){
        if (view==btFecha){
        final Calendar calendar = Calendar.getInstance();

        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH);
        ano = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fecha.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                d=dayOfMonth;
                m=month+1;
                a=year;
                }
            },dia,mes,ano);
            datePickerDialog.updateDate(ano,mes,dia);
            datePickerDialog.show();
        }


        if (view==btHora){
            TimePickerDialog timePickerDialog= new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    hour.setText(hourOfDay+":"+minute);
                    hora=hourOfDay;
                    minutos=minute;
                }
            },hora,minutos,false);
            timePickerDialog.updateTime(hora,minutos);
            timePickerDialog.show();
        }
    }
    private void cargarWebService() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando");
        dialog.show();
        /*String url = "https://arreglalo.000webhostapp.com/insertSolicitud.php?id="+solicitud.getId() +
                "&tipo="+solicitud.getService() +
                "&desc="+solicitud.getDetails() +
                "&uid="+cliente.getId() +
                "&fecha="+solicitud.getAno() +"-"+solicitud.getMes() +"-"+solicitud.getDia()+"%20"+solicitud.getHora()+":"+solicitud.getMinuto()+":00";

        url=url.replace(" ","%20");*/
        //String url1 ="http://192.168.0.10/arreglalo/index.php?nombre=yo&numero=2344&direccion=yo&correo=yo&ciudad=yo&contrasena=yo&calificacion=5&id=5";
        String url1 = "https://arreglalo.co/recibirSol1.php";
        if (!carge){
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url1,null,this,this);
            queue.add(jsonObjectRequest);
        }else {
            String url = "https://arreglalo.co/insertSolicitud.php?id="+solicitud.getId() +
                    "&tipo="+solicitud.getService() +
                    "&desc="+solicitud.getDetails() +
                    "&uid="+cliente.getId() +
                    "&fecha="+solicitud.getAno() +"-"+solicitud.getMes() +"-"+solicitud.getDia()+"%20"+solicitud.getHora()+":"+solicitud.getMinuto()+":00";

            url=url.replace(" ","%20");
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);

            queue.add(jsonObjectRequest);
        }






    }

    @Override
    public void onErrorResponse(VolleyError error) {
        dialog.hide();
        //Toast.makeText(this,"MAMA NO LO LOGRE",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        //Toast.makeText(this,"MAMA LO LOGRE",Toast.LENGTH_SHORT).show();
        dialog.hide();
        JSONArray jsonArray = response.optJSONArray("usuario");
        JSONObject jsonObject= null;
        if (!carge) {
            try {
                jsonObject = jsonArray.getJSONObject(0);
                id=(jsonObject.optInt("Id_S") + 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(),id+" ",Toast.LENGTH_SHORT).show();
        }
    }
}