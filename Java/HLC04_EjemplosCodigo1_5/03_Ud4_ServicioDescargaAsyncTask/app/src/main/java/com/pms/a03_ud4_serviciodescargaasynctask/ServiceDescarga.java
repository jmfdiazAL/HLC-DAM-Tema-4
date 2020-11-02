package com.pms.a03_ud4_serviciodescargaasynctask;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class ServiceDescarga extends Service {
    public ServiceDescarga() {
    }

    // llamado al crearse el servicio
    @Override
    public void onCreate() {
        // Inicializaciones para el servicio
    }

    // llamado en servicios Enlazados -- bindService()
    @Override
    public IBinder onBind(Intent arg0) {

        return null;
    }

    // llamado en servicios Ejecutados -- startService()
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //simula la descarga de 4 archivos en una tarea asíncrona
        try {
            new DoBackgroundTask().execute(new URL(
                    "http://www.amazon.com/somefiles.pdf"), new URL(
                    "http://www.wrox.com/somefiles.pdf"), new URL(
                    "http://www.google.com/somefiles.pdf"), new URL(
                    "http://www.learn2develop.net/somefiles.pdf"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // no re-lanzar servicio iniciado cuando haya memoria suficiente
        return START_NOT_STICKY;
    }

    /**
     * Método que simula la descarga de un archivo -- tarea de larga duración
     *
     * @param url: url de descargar del archivo
     * @return: número de bytes descargados
     */
    private int DownloadFile(URL url) {
        try {
            // simula el tiempo necesario para la descarga del archivo
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // devuelve el valor en bytes simulados de la descarga--
        return 100;
    }

    /**
     * Clase que hereda de AsyncTask para implementar una tarea asíncrona.
     * La tarea es la simulación de la descarga de 4 archivos informando del progreso
     * durante la descarga y devolviendo el total de bytes descargados una vez finaliza ésta
     */
    private class DoBackgroundTask extends AsyncTask<URL, Integer, Long> {
        /**
         * Método que se ejecuta en un hilo secundario: simula la descargad de 4 archivos
         * @param urls: array de urls de los archivos de descarga
         * @return: totoal de bytes descargados
         */
        protected Long doInBackground(URL... urls) {
            //donde se sitúa el código de larga duración.
            int count = urls.length;
            long totalBytesDownloaded = 0;
            for (int i = 0; i < count; i++) {
                totalBytesDownloaded += DownloadFile(urls[i]);
                // ---calcula el porcentaje descargado
                // reportando el progreso---
                publishProgress((int) (((i + 1) / (float) count) * 100));
            }
            return totalBytesDownloaded;
        }

        /**
         * Informa en la UI del progreso de la descarga mediante una tostada
         * se invoca en el hilo de ejecución de la interfaz de usuario y se
         * llama cuando llama al método publishProgress()
         * @param progress: array con valores del progreso
         */
        protected void onProgressUpdate(Integer... progress) {
            Log.d("Descargando Archivos", String.valueOf(progress[0])
                    + "% descargado");
            Toast.makeText(getBaseContext(),
                    String.valueOf(progress[0]) + "% descargado",
                    Toast.LENGTH_SHORT).show();
        }

        /**
         * Se invoca en el hilo de ejecución de la interfaz de usario y se llama
         * cuando el método doInBackground() ha terminado de ejecutarse
         * @param result: valor que dvuelve doInBacground() al finalizar
         */
        protected void onPostExecute(Long result) {
            //muestra en una tostada los bytes descargados
            Toast.makeText(getBaseContext(),
                    "Descargados " + result + " bytes", Toast.LENGTH_LONG)
                    .show();
            //para el servicio
            stopSelf();
        }
    }


    // llamado al finalizar el servicio
    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Servicio Destruido", Toast.LENGTH_LONG).show();
    }
}
