package com.pms.a01_serviciomusica

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val arrancar = findViewById<View>(R.id.btnArrancar) as Button
        val detener = findViewById<View>(R.id.btnDetener) as Button

        //iniciar el servicio de música
        arrancar.setOnClickListener { startService(Intent(baseContext, ServicioMusica::class.java)) }

        //detener el servicio de música
        detener.setOnClickListener { stopService(Intent(baseContext, ServicioMusica::class.java)) }
    }
}