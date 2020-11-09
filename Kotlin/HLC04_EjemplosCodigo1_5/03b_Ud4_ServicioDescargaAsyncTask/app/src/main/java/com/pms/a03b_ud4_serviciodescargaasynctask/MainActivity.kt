package com.pms.a03b_ud4_serviciodescargaasynctask

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun arrancarServicio(view: View?) {
        startService(Intent(baseContext, ServiceDescarga::class.java))
    }

    fun pararServicio(view: View?) {
        stopService(Intent(baseContext, ServiceDescarga::class.java))
    }
}