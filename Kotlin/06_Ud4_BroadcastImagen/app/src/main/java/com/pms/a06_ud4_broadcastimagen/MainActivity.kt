package com.pms.a06_ud4_broadcastimagen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    //objeto intentFilter que se usará para filtrar el intent emitido por un BroadcastReceiver
    var intentFilter: IntentFilter? = null

    //URL de la imagen
    private val IMG_URL = "http://www.juntadeandalucia.es/averroes/centros-tic/04004620/moodle2/pluginfile.php/4277/mod_label/intro/pancarta.jpg"

    //visor de imágenes
    private var mImageView: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //asigna el visor de la imagen
        mImageView = findViewById<View>(R.id.mImageView) as ImageView
    }

    //se registra el receptor
    public override fun onResume() {
        super.onResume()
        //---intent para filtrar intent de la descarga ---
        intentFilter = IntentFilter()
        intentFilter!!.addAction("DESCARGA_IMAGEN")

        //---registro del receptor ---
        registerReceiver(bReceiver, intentFilter)
    }

    // onclick del botón para descargar imagen
    fun iniciar(view: View?) {

        //limpia o borra la imagen
        mImageView!!.setImageResource(0)
        //Intent explícito para lanzar el servicio de descarga de la Imagen
        val `is` = Intent(applicationContext, IntentServiceImagen::class.java)
        //se pasa como clave-valor la URL de la imagen a descargar
        `is`.putExtra("URLIMAGEN", IMG_URL)
        //inicia o lanza el servicio con el Intent is
        startService(`is`)
    }

    //Receptor de anuncios
    private val bReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            //se recupera la información del Intent recibido en un array byte[]
            val imagenbyte = intent.getByteArrayExtra("IMG")
            //decodifica el array byte[] en un Bitamp
            val bitmap = BitmapFactory.decodeByteArray(imagenbyte, 0, imagenbyte.size)
            //muestra la imagen en el visor
            mImageView!!.setImageBitmap(bitmap)
            Toast.makeText(baseContext, "Servicio Finalizado", Toast.LENGTH_SHORT).show()
        }
    }
}