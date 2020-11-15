package com.Arreglalo.arreglalo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    /*private EditText et1;
    private EditText et2;
    private TextView tv;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void registro(View view){

        Intent regis    = new Intent(this, MainActivity2.class);
        startActivity(regis);
    }
    public void initSesion(View view){
        Intent intent = new Intent(this,initSesion.class);
        startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();

        // La actividad está a punto de hacerse visible.
    }
    @Override
    protected void onResume() {
        super.onResume();

        // La actividad se ha vuelto visible (ahora se "reanuda").
    }
    @Override
    protected void onPause() {
        super.onPause();

        // Enfocarse en otra actividad  (esta actividad está a punto de ser "detenida").
    }
    @Override
    protected void onStop() {
        super.onStop();

        // La actividad ya no es visible (ahora está "detenida")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // La actividad está a punto de ser destruida.
    }
}