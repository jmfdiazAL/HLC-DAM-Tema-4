package com.pms.a05_ud4_broadcast_notificaciones

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
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
        //---IntentFilter para filtrar intent de archivo descargado ---
        intentFilter = IntentFilter()
        intentFilter!!.addAction("FILE_DOWNLOADED_ACTION")

        //---registrar el receptor ---
        registerReceiver(intentReceiver, intentFilter)
    }

    //Se ejecuta cuando el sistema arranca una nueva Activity
    //que necesitará los recursos del sistema centrados en ella,
    // por lo que esta Activity quedará pausada
    public override fun onPause() {
        super.onPause()
        //--- anular el registro del recpetor ---
        unregisterReceiver(intentReceiver)
    }

    //onClick() botón 'Arrancar Servicio'
    fun arrancarServicio(view: View?) {
        startService(Intent(baseContext, MyIntentService::class.java))
    }

    //Cuando se recibe el intent, invoca una instancia de la clase BroadcastReceiver que hemos definido
    //En este caso, se muestra "Fichero descargado", pero se pueden pasar datos del servicio
    // a la actividad mediante el intent
    private val intentReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Toast.makeText(baseContext, "Fichero descargado", Toast.LENGTH_LONG).show()
            //Se extrae información del Intent
            val bytes = intent.getIntExtra("BYTES", 0)

            // Creamos la Notificación


            // obtenemos una referencia al servicio NotificationManager del sistema.
            val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            //Si el API del dispositivo >= API 26 (oreo)
            //https://developer.android.com/training/notify-user/build-notification#java
            //identificador del canal
            val CHANNEL_ID = "Mi canal"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(CHANNEL_ID, "canal descarga", NotificationManager.IMPORTANCE_DEFAULT)
                channel.description = "Fichero descargado"
                nm.createNotificationChannel(channel)
            }


            //Intent que se manda al sistema pendiente de una ejecución futura -- se asociará a la notifiación
            //acción que debe realizarse al seleccionar la notifiación => mostrar la actividad
            val intencionPendiente = PendingIntent.getActivity(context, 0, Intent(context, MyIntentService::class.java), 0)
            //configuración de la notifiación
            val noti = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentIntent(intencionPendiente)
                    .setTicker("Descargado")
                    .setContentTitle("Fichero descargado: $bytes bytes")
                    .setSmallIcon(R.drawable.ic_launcher)
                    .addAction(R.drawable.ic_launcher, "DESCARGA", intencionPendiente)
                    .setVibrate(longArrayOf(100, 250, 100, 500))
                    .build()
            //publica la notificación para mostrarla en la barra de estado
            nm.notify(1, noti)
        }
    }
}