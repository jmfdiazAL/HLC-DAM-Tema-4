package com.pms.a05_ud4_broadcast_notificaciones

import android.app.IntentService
import android.content.Intent
import android.util.Log
import java.net.MalformedURLException
import java.net.URL

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 *
 *
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
class MyIntentService : IntentService("MyIntentService") {
    override fun onHandleIntent(intent: Intent) {
        try {
            val result = DownloadFile(URL("http://www.amazon.com/somefile.pdf"))
            Log.d("IntentService", "Descargados $result bytes")

            //---envía un broadcast para informar a la actividad principal
            // que el fichero ha sido descargado---
            val broadcastIntent = Intent()
            broadcastIntent.putExtra("BYTES", result)
            broadcastIntent.action = "FILE_DOWNLOADED_ACTION"
            baseContext.sendBroadcast(broadcastIntent)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
    }

    private fun DownloadFile(url: URL): Int {
        try {
            //---simula el tiempo de descarga del fcihero---
            Thread.sleep(10000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return 100
    }
}