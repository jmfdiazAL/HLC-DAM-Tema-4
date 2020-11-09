package com.pms.a03_ud4_serviciodescargaasynctask

import android.app.Service
import android.content.Intent
import android.os.AsyncTask
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import java.net.MalformedURLException
import java.net.URL

class ServiceDescarga : Service() {
    // llamado al crearse el servicio
    override fun onCreate() {
        // Inicializaciones para el servicio
    }

    // llamado en servicios Enlazados -- bindService()
    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    // llamado en servicios Ejecutados -- startService()
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        //simula la descarga de 4 archivos en una tarea asíncrona
        try {
            DoBackgroundTask().execute(URL(
                    "http://www.amazon.com/somefiles.pdf"), URL(
                    "http://www.wrox.com/somefiles.pdf"), URL(
                    "http://www.google.com/somefiles.pdf"), URL(
                    "http://www.learn2develop.net/somefiles.pdf"))
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        // no re-lanzar servicio iniciado cuando haya memoria suficiente
        return START_NOT_STICKY
    }

    /**
     * Método que simula la descarga de un archivo -- tarea de larga duración
     *
     * @param url: url de descargar del archivo
     * @return: número de bytes descargados
     */
    private fun DownloadFile(url: URL): Int {
        try {
            // simula el tiempo necesario para la descarga del archivo
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        // devuelve el valor en bytes simulados de la descarga--
        return 100
    }

    /**
     * Clase que hereda de AsyncTask para implementar una tarea asíncrona.
     * La tarea es la simulación de la descarga de 4 archivos informando del progreso
     * durante la descarga y devolviendo el total de bytes descargados una vez finaliza ésta
     */
    private inner class DoBackgroundTask : AsyncTask<URL?, Int?, Long>() {
        /**
         * Método que se ejecuta en un hilo secundario: simula la descargad de 4 archivos
         * @param urls: array de urls de los archivos de descarga
         * @return: totoal de bytes descargados
         */
        protected override fun doInBackground(vararg urls: URL?): Long? {
            //donde se sitúa el código de larga duración.
            val count = urls.size
            var totalBytesDownloaded: Long = 0
            for (i in 0 until count) {
                totalBytesDownloaded += DownloadFile(urls[i]!!).toLong()
                // ---calcula el porcentaje descargado
                // reportando el progreso---
                publishProgress(((i + 1) / count.toFloat() * 100).toInt())
            }
            return totalBytesDownloaded
        }

        /**
         * Informa en la UI del progreso de la descarga mediante una tostada
         * se invoca en el hilo de ejecución de la interfaz de usuario y se
         * llama cuando llama al método publishProgress()
         * @param progress: array con valores del progreso
         */
        protected override fun onProgressUpdate(vararg progress: Int?) {
            Log.d("Descargando Archivos", progress[0].toString() + "% descargado")
            Toast.makeText(baseContext,
                    progress[0].toString() + "% descargado",
                    Toast.LENGTH_SHORT).show()
        }

        /**
         * Se invoca en el hilo de ejecución de la interfaz de usario y se llama
         * cuando el método doInBackground() ha terminado de ejecutarse
         * @param result: valor que dvuelve doInBacground() al finalizar
         */
        override fun onPostExecute(result: Long) {
            //muestra en una tostada los bytes descargados
            Toast.makeText(baseContext,
                    "Descargados $result bytes", Toast.LENGTH_LONG)
                    .show()
            //para el servicio
            stopSelf()
        }
    }

    // llamado al finalizar el servicio
    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Servicio Destruido", Toast.LENGTH_LONG).show()
    }
}