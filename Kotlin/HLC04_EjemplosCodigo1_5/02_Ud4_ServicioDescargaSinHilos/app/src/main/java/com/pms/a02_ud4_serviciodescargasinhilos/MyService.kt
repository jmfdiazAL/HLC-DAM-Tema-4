package com.pms.a02_ud4_serviciodescargasinhilos

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import java.net.MalformedURLException
import java.net.URL

class MyService : Service() {
    override fun onCreate() {
        //Inicializaciones para el servicio
    }

    /**
     * se invoca en servicios ejecutados -- startService()
     */
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Servicio Arrancado", Toast.LENGTH_LONG).show()

        // Simula una tarea de larga duración. La descarga de archivos
        try {
            val result = DownloadFile(URL("http://www.amazon.com/somefile.pdf"))
            Toast.makeText(this, "Descargado el fichero de $result bytes",
                    Toast.LENGTH_LONG).show()
        } catch (e: MalformedURLException) {
            Toast.makeText(this, "URL mal formada", Toast.LENGTH_LONG).show()
        }
        // No re-crea el servicio iniciado si se finaliza por falta de memoria
        return START_NOT_STICKY
    }

    /**
     * Método que simula la descarga de un fichero
     *
     * @param url: url de la descarga
     * @return: entero que indica los bytes devueltos
     */
    private fun DownloadFile(url: URL): Int {
        try {
            // simula el tiempo requerido para descarga
            //---OBSERVA  QUE BLOQUEA UI para ms= 50000
            Thread.sleep(50000)
        } catch (e: InterruptedException) {
            Toast.makeText(this, "No se pudo completar la descarga", Toast.LENGTH_LONG).show()
        }
        // ---devuelve un valor simulando el tamaño del fichero descargado
        return 100
    }

    /**
     * se invoca en servicios enlazados bindService()
     */
    override fun onBind(arg0: Intent): IBinder? {
        // return null;
        return null
    }

    /**
     * se invoca al finalizar el servicio
     */
    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Servicio Destruido", Toast.LENGTH_LONG).show()
    }
}