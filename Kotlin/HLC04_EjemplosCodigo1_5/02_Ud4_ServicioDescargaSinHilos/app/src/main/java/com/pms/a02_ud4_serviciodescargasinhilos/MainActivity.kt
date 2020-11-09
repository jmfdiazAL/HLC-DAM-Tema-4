package com.pms.a02_ud4_serviciodescargasinhilos

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //onClick() boton 'arrancarServicio'
    fun arrancarServicio(view: View?) {
        startService(Intent(baseContext, MyService::class.java))
    }

    //onClick() botón 'pararServicio'
    fun pararServicio(view: View?) {
        stopService(Intent(baseContext, MyService::class.java))
    }
}