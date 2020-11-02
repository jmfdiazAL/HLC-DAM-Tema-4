package com.pms.a03b_ud4_serviciodescargaasynctask;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class ServiceDescarga extends Service {
    DoBackgroundTask descargaTask;

    // llamado al crearse el servicio
    public ServiceDescarga() {
    }

    @Override
    public void onCreate() {
        // Inicializaciones para el servicio
    }

    // llamado en servicios Enlazados -- bindService()
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // llamado en servicios Ejecutados -- startService()
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Comprobamos si se está ejecutando otro hilo, en caso de no ser así, realiza la descarga

        //si la descarga no se ha hecho nunca y si no está ejecutandose
        if(enEjecucion()==false) {

            //simula la descarga de 4 archivos
            try {
                descargaTask = new DoBackgroundTask();
                descargaTask.execute(new URL(
                        "http://www.amazon.com/somefiles.pdf"), new URL(
                        "http://www.wrox.com/somefiles.pdf"), new URL(
                        "http://www.google.com/somefiles.pdf"), new URL(
                        "http://www.learn2develop.net/somefiles.pdf"));

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }else{
            //Si esta ejecutandose, no hace nada, solo muestra la tostada
            Toast.makeText(getBaseContext(),
                    "No puedes volver a descargar, ya está descargando", Toast.LENGTH_SHORT).show();

        }
        // no re-lanzar servicio iniciado cuando haya memoria suficiente
        return START_NOT_STICKY;
    }

    /**
     * Método que simula la descarga de un archivo -- tarea de larga duración
     * @param url: url del archivo a descargar
     * @return: total de bytes descargados
     */
    private int DownloadFile(URL url) {
        try {
            // ---simula el tiempo necesario para la descarga de un archivo
            //durmiendo el hilo
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // devuelve el valor simulado de la descarga
        return 100;
    }


    private class DoBackgroundTask extends AsyncTask<URL, Integer, Long> {

        // Se ejeucta en un hilo de ejecución en segundo plano y es
        // donde se sitúa el código de larga duración.
        protected Long doInBackground(URL... urls) {
            int count = urls.length;
            long totalBytesDownloaded = 0;
            for (int i = 0; i < count; i++) {
                // ---calcula el porcentaje descargado y
                // reporta el progreso---llamando a onProgressUpdate()
                publishProgress((int) (((i + 1) / (float) count) * 100));
                if (isCancelled())
                    break;
                totalBytesDownloaded += DownloadFile(urls[i]);
            }
            return totalBytesDownloaded;
        }

        // se invoca en el hilo de ejecución de la interfaz de usuario y se
        // llama cuando invocamos al método publishProgress()
        protected void onProgressUpdate(Integer... progress) {
            Log.d("Descargando Archivos", String.valueOf(progress[0])
                    + "% descargado");
            Toast.makeText(getBaseContext(),
                    String.valueOf(progress[0]) + "% descargado",
                    Toast.LENGTH_SHORT).show();
        }

        // se invoca en el hilo de ejecución de la interfaz de usario y se llama
        //cuando el método doInBackground() ha terminado de ejecutarse
        protected void onPostExecute(Long result) {
            Toast.makeText(getBaseContext(),
                    "Descargados " + result + " bytes", Toast.LENGTH_SHORT)
                    .show();
            //detiene el servicio
            stopSelf();
            //Ponemos a null para poder iniciar la descarga de nuevo
            descargaTask = null;
        }



        // Es invocado cuando se cancela la tarea asíncrona
        @Override
        protected void onCancelled(Long result) {
            //Ponemos la descarga a null para poder iniciarla de nuevo
            descargaTask = null;
            Toast.makeText(getBaseContext(),
                    "Cancelado el hilo secundario. Descargados "+ result+"bytes",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    // llamado al finalizar el servicio
    @Override
    public void onDestroy() {
        super.onDestroy();
        //Además de parar el servicio, cancelamos el hilo
        if (enEjecucion()== true){
            descargaTask.cancel(true);
        }
        Toast.makeText(this, "Servicio Destruido", Toast.LENGTH_SHORT).show();
    }


    /**
     * Método para comprobar si se ha pulsado en el botón Comenzar descarga
     * @return: verdadero si se ha comenzado la descarga, falso en otro caso
     */
    public boolean enEjecucion (){

        return descargaTask !=null && (descargaTask.getStatus()== AsyncTask.Status.RUNNING);
    }
}
