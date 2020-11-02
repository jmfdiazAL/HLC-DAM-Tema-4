package com.pms.a02_ud4_serviciodescargasinhilos;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public void onCreate() {
        //Inicializaciones para el servicio
    }

    /**
     * se invoca en servicios ejecutados -- startService()
     */

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "Servicio Arrancado", Toast.LENGTH_LONG).show();

        // Simula una tarea de larga duración. La descarga de archivos

        try {
            int result = DownloadFile(new URL("http://www.amazon.com/somefile.pdf"));
            Toast.makeText(this, "Descargado el fichero de " + result + " bytes",
                    Toast.LENGTH_LONG).show();

        } catch (MalformedURLException e) {
            Toast.makeText(this, "URL mal formada", Toast.LENGTH_LONG).show();
        }
        // No re-crea el servicio iniciado si se finaliza por falta de memoria
        return START_NOT_STICKY;
    }

    /**
     * Método que simula la descarga de un fichero
     *
     * @param url: url de la descarga
     * @return: entero que indica los bytes devueltos
     */
    private int DownloadFile(URL url) {
        try {
            // simula el tiempo requerido para descarga
            //---OBSERVA  QUE BLOQUEA UI para ms= 50000
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            Toast.makeText(this, "No se pudo completar la descarga", Toast.LENGTH_LONG).show();
        }
        // ---devuelve un valor simulando el tamaño del fichero descargado
        return 100;
    }


    /**
     * se invoca en servicios enlazados bindService()
     */
    @Override
    public IBinder onBind(Intent arg0) {
        // return null;
        return null;
    }

    /**
     * se invoca al finalizar el servicio
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Servicio Destruido", Toast.LENGTH_LONG).show();
    }

}
