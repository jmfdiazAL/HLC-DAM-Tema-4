package com.pms.a06_ud4_broadcastimagen;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class IntentServiceImagen extends IntentService {

    public IntentServiceImagen() {
        super("IntentServiceImagen");
    }

    //se ejecuta en un hilo secundario
    @Override
    protected void onHandleIntent(Intent intent) {

        //recupera en un Bundle la información pasada en el Intent
        Bundle bd = intent.getExtras();
        String stimg= bd.getString("URLIMAGEN");
        //obtiene la imagen a descargar
        Bitmap bmp = downloadImagen(stimg);

        //Transforma la imagen descargada en un Array byte[] para anexar al Intent
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray =stream.toByteArray();

        //---broadcast para informar a la actividad
        // que la descarga ha finalizado---
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("DESCARGA_IMAGEN");
        broadcastIntent.putExtra("IMG",byteArray);
        getBaseContext().sendBroadcast(broadcastIntent);

    }

    /**
     * Descarga una imagen
     * @param baseUrl: URL de la imagen a descargar
     * @return: imagen descargada como Bitmap
     */
    private Bitmap downloadImagen(String baseUrl) {
        Bitmap myBitmap = null;
        try
        {
            //Se define el objeto URL
            URL url = new URL(baseUrl);
            //Se obtiene y configura un objeto de conexión HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            //Recibimos la respuesta de la petición en formato InputStream
            InputStream input = connection.getInputStream();

            //Decodificamos el InputStream a un objeto BitMap
            myBitmap = BitmapFactory.decodeStream(input);

        }
        //se captura de forma genérica las posibles excepciones
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return myBitmap;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
}