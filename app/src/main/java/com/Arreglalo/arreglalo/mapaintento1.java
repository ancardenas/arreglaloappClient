package com.Arreglalo.arreglalo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;

public class mapaintento1 extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Cliente cliente;
    String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    MapView mapView;
    private EditText direccion;
    private EditText ciudad;
    private EditText detalles;
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
    public void reaady(View view){
        Intent intent = new Intent(this,pincipalServices.class);
        cliente.setDireccion(direccion.getText().toString());
        cliente.setCiudad(ciudad.getText().toString());
        cliente.setDetalles(detalles.getText().toString());
        /*
        AQUI es donde se termina el registro del cliente y se deberia subir toda su inormacion a la
        base de datos
         */
        intent.putExtra("cliente",(Serializable)cliente);
        startActivity(intent);
    }

}