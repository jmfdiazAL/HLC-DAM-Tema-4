package com.pms.a04_ud4_intentservice_broadcast

import android.app.IntentService
import android.content.Intent
import android.util.Log
import java.net.MalformedURLException
import java.net.URL

class MyIntentService : IntentService("MyIntentService") {
    //este método se ejecuta en un hilo secundario. Aquí debe ir el código del servicio
    override fun onHandleIntent(intent: Intent) {
        try {
            val result = DownloadFile(URL("http://www.amazon.com/somefile.pdf"))
            Log.d("IntentService", "Descargados $result bytes")

            //---envía un broadcast para informar a la actividad
            // que el fichero ha sido descargado---
            val broadcastIntent = Intent()
            broadcastIntent.action = "FILE_DOWNLOADED_ACTION"
            baseContext.sendBroadcast(broadcastIntent)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
    }

    private fun DownloadFile(url: URL): Int {
        try {
            //---simula el tiempo de descarga del fichero---
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return 100
    }
}