package com.pms.a01_serviciomusica;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class ServicioMusica extends Service {
    public ServicioMusica() {
    }

    //objeto MediaPlayer para la reproducción de música
    MediaPlayer reproductor;

    @Override
    public void onCreate(){
        Toast.makeText(this, "Servicio creado",Toast.LENGTH_SHORT).show();
        //obtiene el recurso de audio de res/raw/audio.mid
        reproductor = MediaPlayer.create(this, R.raw.queen_i_want_to_break_free);

    }

    //iniciar servicio

    /**
     * Método que se ejecuta al invocar el servicio ejecutado con startService(intent)
     * @param intencion: Un objeto Intent que se indicó en la llamada startService(Intent).
     * @param flags: Información sobre como comienza la solicitud. Puede ser 0, START_FLAG_REDELIVERY
     *             o START_FLAG_RETRY.
     *             Un valor distinto de 0 se utiliza para reiniciar un servicio tras detectar algún problema
     * @param idArranque:Un entero único representando la solicitud de arranque específica.
     *                  Usar este mismo estero en el método stopSelfResult(int idArranque
     * @return: describe cómo debe comportarse el sistema cuando el proceso del servicio sea matado una vez que el servicio ya se ha inicializado.
     * Esto puede ocurrir en situaciones de baja memoria
     */
    @Override
    public int onStartCommand(Intent intencion, int flags, int idArranque){
        Toast.makeText(this, "Servicio arrancado " + idArranque,Toast.LENGTH_SHORT).show();
        reproductor.start();
        //el sistema trata de crear de nuevo el servicio cuando disponga de memoria suficiente
        return START_STICKY;
    }

    //parar servicio
    @Override
    public void onDestroy(){
        Toast.makeText(this, "Servicio detenido",Toast.LENGTH_SHORT).show();
        reproductor.stop();

    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
}
