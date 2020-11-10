package com.Arreglalo.arreglalo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;
import java.util.Timer;

public class confirmService extends AppCompatActivity {
    private String service;
    private TextView textView;
    private EditText det;
    private String details;
    private Button btFecha;
    private Button btHora;
    private EditText fecha;
    private EditText hour;
    private int dia,mes,ano;
    private int d,m,a,hora,minutos;
    private Cliente cliente;
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
    }
    public void click(View view){
        Intent intent = new Intent(this,finalService.class);
        details = det.getText().toString();
        Solicitud solicitud = new Solicitud(d,m,a,hora,minutos,service,details,cliente);

        intent.putExtra("service",service);
        intent.putExtra("d",d);
        intent.putExtra("m",m);
        intent.putExtra("a",a);
        intent.putExtra("hour",hora);
        intent.putExtra("minute",minutos);
        intent.putExtra("solicitud", (Serializable) solicitud);
        intent.putExtra("cliente",(Serializable) cliente);
        startActivity(intent);
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
}