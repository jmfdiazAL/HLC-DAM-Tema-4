package com.pms.a06_ud4_broadcastimagen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //objeto intentFilter que se usará para filtrar el intent emitido por un BroadcastReceiver
    IntentFilter intentFilter;
    //URL de la imagen

    private final String IMG_URL =
            "http://www.juntadeandalucia.es/averroes/centros-tic/04004620/moodle2/pluginfile.php/4277/mod_label/intro/pancarta.jpg";

    //visor de imágenes
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //asigna el visor de la imagen
        mImageView = (ImageView) findViewById(R.id.mImageView);

    }

    //se registra el receptor
    @Override
    public void onResume(){
        super.onResume();
        //---intent para filtrar intent de la descarga ---
        intentFilter = new IntentFilter();
        intentFilter.addAction("DESCARGA_IMAGEN");

        //---registro del receptor ---
        registerReceiver(bReceiver, intentFilter);
    }

   // onclick del botón para descargar imagen
    public void iniciar(View view) {

        //limpia o borra la imagen
        mImageView.setImageResource(0);
        //Intent explícito para lanzar el servicio de descarga de la Imagen
        Intent is=new Intent(getApplicationContext(),IntentServiceImagen.class);
        //se pasa como clave-valor la URL de la imagen a descargar
        is.putExtra("URLIMAGEN",IMG_URL);
        //inicia o lanza el servicio con el Intent is
        startService(is);
    }

    //Receptor de anuncios
    private BroadcastReceiver bReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive (Context context, Intent intent){

            //se recupera la información del Intent recibido en un array byte[]
            byte[] imagenbyte =intent.getByteArrayExtra("IMG");
            //decodifica el array byte[] en un Bitamp
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagenbyte,0,imagenbyte.length);
            //muestra la imagen en el visor
            mImageView.setImageBitmap(bitmap);

            Toast.makeText(getBaseContext(), "Servicio Finalizado", Toast.LENGTH_SHORT).show();
        }
    };



}
