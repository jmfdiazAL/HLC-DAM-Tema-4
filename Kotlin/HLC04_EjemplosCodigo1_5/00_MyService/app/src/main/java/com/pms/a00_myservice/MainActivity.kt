package com.pms.a00_myservice

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * Método onClick del botón Iniciar Servicio
     *
     * @param view: botón
     */
    fun iniciarServicio(view: View?) {
        val `is` = Intent(baseContext, MyService::class.java)
        //Inicia el servicio Ejecutado asociado al Intent explícito
        startService(`is`)
    }

    /**
     * Método onClick del botón Parar Servicio
     *
     * @param view: botón
     */
    fun pararServicio(view: View?) {
        val `is` = Intent(baseContext, MyService::class.java)
        //Para o detiene el servicio explícitamente
        stopService(`is`)
    }
}