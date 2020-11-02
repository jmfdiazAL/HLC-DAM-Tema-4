package com.pms.a02_ud4_serviciodescargasinhilos;

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
    //onClick() boton 'arrancarServicio'
    public void arrancarServicio(View view) {
        startService(new Intent(getBaseContext(), MyService.class));
    }
    //onClick() botón 'pararServicio'
    public void pararServicio(View view) {
        stopService(new Intent(getBaseContext(), MyService.class));
    }
}
