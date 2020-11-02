package com.pms.a04_ud4_intentservice_broadcast;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;


public class MyIntentService extends IntentService {
    public MyIntentService() {
        super("MyIntentService");
    }

    //este método se ejecuta en un hilo secundario. Aquí debe ir el código del servicio
    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            int result =
                    DownloadFile(new URL("http://www.amazon.com/somefile.pdf"));
            Log.d("IntentService", "Descargados " + result + " bytes");

            //---envía un broadcast para informar a la actividad
            // que el fichero ha sido descargado---

            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("FILE_DOWNLOADED_ACTION");
            getBaseContext().sendBroadcast(broadcastIntent);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private int DownloadFile(URL url) {
        try {
            //---simula el tiempo de descarga del fichero---
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 100;
    }
}
