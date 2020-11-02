package com.pms.a03_ud4_serviciodescargaasynctask;

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
    public void arrancarServicio(View view) {
        startService(new Intent(getBaseContext(), ServiceDescarga.class));
    }

    public void pararServicio(View view) {
        stopService(new Intent(getBaseContext(), ServiceDescarga.class));
    }
}
