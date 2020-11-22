package com.Arreglalo.arreglalo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class mapaintento1 extends AppCompatActivity implements OnMapReadyCallback, Response.Listener<JSONObject>,Response.ErrorListener  {
    private GoogleMap mMap;
    private Cliente cliente;
    String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    MapView mapView;
    private EditText direccion;
    private EditText ciudad;
    private EditText detalles;

    private ProgressDialog dialog;
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapaintento1);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        cliente = (Cliente)getIntent().getSerializableExtra("cliente");
        direccion = findViewById(R.id.ed_direccion);
        ciudad = findViewById(R.id.ed_ciudad);
        detalles = findViewById(R.id.ed_detalles);


        queue = Volley.newRequestQueue(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(5.683, -76.655);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Esta en Quibdo"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15f));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);



        mapView.onSaveInstanceState(outState);
    }
    public void reaady(View view) {

        cliente.setDireccion(direccion.getText().toString());
        cliente.setCiudad(ciudad.getText().toString());
        cliente.setDetalles(detalles.getText().toString());
        cliente.setCalificacion(5);
        cargarWebService();
        /*
        AQUI es donde se termina el registro del cliente y se deberia subir toda su inormacion a la
        base de datos
         */


    }

    private void cargarWebService() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando");
        dialog.show();
        String url1 = "https://arreglalo.co/recibirSol.php";


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url1,null,this,this);
        queue.add(jsonObjectRequest);



    }

    @Override
    public void onErrorResponse(VolleyError error) {
    dialog.hide();
        Toast.makeText(this,"Hubo un error vuelva a intentarlo",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this,"Usted ha sido registrado exitosamente",Toast.LENGTH_SHORT).show();
        dialog.hide();


        if(jsonObjectRequest.getUrl().equals("https://arreglalo.co/recibirSol.php")){
            JSONArray jsonArray = response.optJSONArray("usuario");
            JSONObject jsonObject= null;
            try {
                jsonObject  =jsonArray.getJSONObject(0);
                cliente.setId(jsonObject.optInt("Id_U")+1);
                Toast.makeText(this,cliente.getId()+"",Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String url = "https://arreglalo.co/index.php?nombre="+cliente.getNombre()+
                    "&numero="+cliente.getNumero()+
                    "&direccion="+cliente.getDireccion()+"%20"+cliente.getDetalles()+
                    "&correo="+cliente.getCorreo()+
                    "&ciudad="+cliente.getCiudad()+
                    "&contrasena="+cliente.getContrasena()+
                    "&calificacion="+cliente.getCalificacion()+
                    "&id="+cliente.getId();


            url=url.replace(" ","%20");
            //String url1 ="http://192.168.0.10/arreglalo/index.php?nombre=yo&numero=2344&direccion=yo&correo=yo&ciudad=yo&contrasena=yo&calificacion=5&id=5";
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
            queue.add(jsonObjectRequest);




        }else {
            Intent intent = new Intent(this, pincipalServices.class);
            intent.putExtra("cliente",(Serializable)cliente);
            Toast.makeText(this,cliente.toString(),Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }


    }
}