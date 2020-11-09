package com.pms.a04_ud4_intentservice_broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    //objeto intentFilter que se usará para filtrar el intent emitido por un BroadcastReceiver
    var intentFilter: IntentFilter? = null

    //llamado al crearse la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //Se ejecuta una vez que la Activity ha terminado de cargarse en el dispositivo
    //y el usuario empieza a interactuar con la aplicación.
    public override fun onResume() {
        super.onResume()
        //objeto intentFilter que se usará para filtrar el intent emitido por un BroadcastReceiver
        //---IntentFilter para filtrar el intent de archivo descargado ---
        intentFilter = IntentFilter()
        intentFilter!!.addAction("FILE_DOWNLOADED_ACTION")

        //---registrar el receptor ---
        registerReceiver(intentReceiver, intentFilter)
    }

    //se ejecuta cuando la Activity pierde el foco, pero sigue siendo visible
    public override fun onPause() {
        super.onPause()
        //--- anular el registro del recpetor ---
        unregisterReceiver(intentReceiver)
    }

    //onClick() botón 'Arrancar Servicio'
    fun arrancarServicio(view: View?) {
        startService(Intent(baseContext, MyIntentService::class.java))
    }

    //Cuando se recibe el intent, invoca una instancia de la clase BroadcastReceiver
    // que hemos definido
    //En este caso, se muestra "Fichero descargado",
    // pero se pueden pasar datos del servicio a la actividad mediante el intent
    private val intentReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Toast.makeText(baseContext, "Fichero descargado", Toast.LENGTH_SHORT).show()
        }
    }
}