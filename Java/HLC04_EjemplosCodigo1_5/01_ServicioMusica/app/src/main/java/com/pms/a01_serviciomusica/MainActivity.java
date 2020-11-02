package com.pms.a01_serviciomusica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button arrancar = (Button) findViewById(R.id.btnArrancar);
        Button detener = (Button) findViewById(R.id.btnDetener);

        //iniciar el servicio de música
        arrancar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startService(new Intent(getBaseContext(), ServicioMusica.class));
            }
        });

        //detener el servicio de música
        detener.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                stopService(new Intent(getBaseContext(), ServicioMusica.class));
            }
        });
    }
}
