package com.pms.a00_myservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * Método onClick del botón Iniciar Servicio
     *
     * @param view: botón
     */
    public void iniciarServicio(View view) {
        Intent is = new Intent(getBaseContext(), MyService.class);
        //Inicia el servicio Ejecutado asociado al Intent explícito
        startService(is);
    }

    /**
     * Método onClick del botón Parar Servicio
     *
     * @param view: botón
     */
    public void pararServicio(View view) {
        Intent is = new Intent(getBaseContext(), MyService.class);
        //Para o detiene el servicio explícitamente
        stopService(is);
    }
}
