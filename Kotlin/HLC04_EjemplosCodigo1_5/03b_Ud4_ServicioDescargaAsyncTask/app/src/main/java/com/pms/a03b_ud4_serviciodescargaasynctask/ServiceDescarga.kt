package com.pms.a03b_ud4_serviciodescargaasynctask

import android.app.Service
import android.content.Intent
import android.os.AsyncTask
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import java.net.MalformedURLException
import java.net.URL

class ServiceDescarga  // llamado al crearse el servicio
    : Service() {
    var descargaTask: DoBackgroundTask? = null
    override fun onCreate() {
        // Inicializaciones para el servicio
    }

    // llamado en servicios Enlazados -- bindService()
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    // llamado en servicios Ejecutados -- startService()
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        //Comprobamos si se está ejecutando otro hilo, en caso de no ser así, realiza la descarga

        //si la descarga no se ha hecho nunca y si no está ejecutandose
        if (enEjecucion() == false) {

            //simula la descarga de 4 archivos
            try {
                descargaTask = DoBackgroundTask()
                descargaTask!!.execute(URL(
                        "http://www.amazon.com/somefiles.pdf"), URL(
                        "http://www.wrox.com/somefiles.pdf"), URL(
                        "http://www.google.com/somefiles.pdf"), URL(
                        "http://www.learn2develop.net/somefiles.pdf"))
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
        } else {
            //Si esta ejecutandose, no hace nada, solo muestra la tostada
            Toast.makeText(baseContext,
                    "No puedes volver a descargar, ya está descargando", Toast.LENGTH_SHORT).show()
        }
        // no re-lanzar servicio iniciado cuando haya memoria suficiente
        return START_NOT_STICKY
    }

    /**
     * Método que simula la descarga de un archivo -- tarea de larga duración
     * @param url: url del archivo a descargar
     * @return: total de bytes descargados
     */
    private fun DownloadFile(url: URL): Int {
        try {
            // ---simula el tiempo necesario para la descarga de un archivo
            //durmiendo el hilo
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        // devuelve el valor simulado de la descarga
        return 100
    }

    inner class DoBackgroundTask : AsyncTask<URL?, Int?, Long>() {
        // Se ejeucta en un hilo de ejecución en segundo plano y es
        // donde se sitúa el código de larga duración.
        protected override fun doInBackground(vararg urls: URL?): Long? {
            val count = urls.size
            var totalBytesDownloaded: Long = 0
            for (i in 0 until count) {
                // ---calcula el porcentaje descargado y
                // reporta el progreso---llamando a onProgressUpdate()
                publishProgress(((i + 1) / count.toFloat() * 100).toInt())
                if (isCancelled) break
                totalBytesDownloaded += DownloadFile(urls[i]!!).toLong()
            }
            return totalBytesDownloaded
        }

        // se invoca en el hilo de ejecución de la interfaz de usuario y se
        // llama cuando invocamos al método publishProgress()
        protected override fun onProgressUpdate(vararg progress: Int?) {
            Log.d("Descargando Archivos", progress[0].toString() + "% descargado")
            Toast.makeText(baseContext,
                    progress[0].toString() + "% descargado",
                    Toast.LENGTH_SHORT).show()
        }

        // se invoca en el hilo de ejecución de la interfaz de usario y se llama
        //cuando el método doInBackground() ha terminado de ejecutarse
        override fun onPostExecute(result: Long) {
            Toast.makeText(baseContext,
                    "Descargados $result bytes", Toast.LENGTH_SHORT)
                    .show()
            //detiene el servicio
            stopSelf()
            //Ponemos a null para poder iniciar la descarga de nuevo
            descargaTask = null
        }

        // Es invocado cuando se cancela la tarea asíncrona
        override fun onCancelled(result: Long) {
            //Ponemos la descarga a null para poder iniciarla de nuevo
            descargaTask = null
            Toast.makeText(baseContext,
                    "Cancelado el hilo secundario. Descargados " + result + "bytes",
                    Toast.LENGTH_SHORT)
                    .show()
        }
    }

    // llamado al finalizar el servicio
    override fun onDestroy() {
        super.onDestroy()
        //Además de parar el servicio, cancelamos el hilo
        if (enEjecucion() == true) {
            descargaTask!!.cancel(true)
        }
        Toast.makeText(this, "Servicio Destruido", Toast.LENGTH_SHORT).show()
    }

    /**
     * Método para comprobar si se ha pulsado en el botón Comenzar descarga
     * @return: verdadero si se ha comenzado la descarga, falso en otro caso
     */
    fun enEjecucion(): Boolean {
        return descargaTask != null && descargaTask!!.status == AsyncTask.Status.RUNNING
    }
}