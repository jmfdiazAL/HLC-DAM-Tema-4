package com.pms.a06_ud4_broadcastimagen

import android.app.IntentService
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.IBinder
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 *
 *
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
class IntentServiceImagen : IntentService("IntentServiceImagen") {
    //se ejecuta en un hilo secundario
    override fun onHandleIntent(intent: Intent) {

        //recupera en un Bundle la información pasada en el Intent
        val bd = intent.extras
        val stimg = bd.getString("URLIMAGEN")
        //obtiene la imagen a descargar
        val bmp = downloadImagen(stimg)

        //Transforma la imagen descargada en un Array byte[] para anexar al Intent
        val stream = ByteArrayOutputStream()
        bmp!!.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()

        //---broadcast para informar a la actividad
        // que la descarga ha finalizado---
        val broadcastIntent = Intent()
        broadcastIntent.action = "DESCARGA_IMAGEN"
        broadcastIntent.putExtra("IMG", byteArray)
        baseContext.sendBroadcast(broadcastIntent)
    }

    /**
     * Descarga una imagen
     * @param baseUrl: URL de la imagen a descargar
     * @return: imagen descargada como Bitmap
     */
    private fun downloadImagen(baseUrl: String): Bitmap? {
        var myBitmap: Bitmap? = null
        try {
            //Se define el objeto URL
            val url = URL(baseUrl)
            //Se obtiene y configura un objeto de conexión HttpURLConnection
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            //Recibimos la respuesta de la petición en formato InputStream
            val input = connection.inputStream

            //Decodificamos el InputStream a un objeto BitMap
            myBitmap = BitmapFactory.decodeStream(input)
        } //se captura de forma genérica las posibles excepciones
        catch (e: Exception) {
            e.printStackTrace()
        }
        return myBitmap
    }

    override fun onBind(arg0: Intent): IBinder? {
        // TODO Auto-generated method stub
        return null
    }
}