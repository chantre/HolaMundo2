package com.sdc.curso.asynctask;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by mañá on 06/02/2015.
 */
//  AsyncTask no acepta datos primitivos
public class DescargaImagenAsyncTask extends AsyncTask<String, Integer, Bitmap>{

    private ProgressDialog dialogo;
    private ImageView imageView;

    //  inyeccion con constructor
    public DescargaImagenAsyncTask(ProgressDialog dialogo, ImageView imageView) {
        this.dialogo = dialogo;
        this.imageView = imageView;
    }

    //  unico metodo dela claswe que se ejecuta en el hilo secundario
    //por lo que debe implementar la tarea de larga duracion por completo
    //  2 tareas:
    //  1.-Descarga imagen
    //  2.-Convertir imagen a Bitmap
    @Override
    protected Bitmap doInBackground(String... params) {
        //2 opciones
        //URLConnection de Java (recomendado ahora; vale para REST) o HttpClient de Apache
        try {
            URL url = new URL(params[0]);
            //  lo que da lo casteamos
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // da un buffer de bytes
            InputStream is = connection.getInputStream();
            //  tamaño de la imagen
            int sizeImage = connection.getContentLength();
            //  ahora leemos
            byte[] buffer = new byte[1024];
            byte[] imagen = new byte[sizeImage];


            for (int bytesTotalesLeidos = 0; bytesTotalesLeidos < sizeImage; ){
                int bytesLeidos = is.read(buffer);
                //  para ir guardando lo que leo
                System.arraycopy(buffer, 0, imagen, bytesTotalesLeidos, bytesLeidos);

                bytesTotalesLeidos += bytesLeidos;
                //  publicamos el progreso
                publishProgress(bytesTotalesLeidos * 100/sizeImage);
            }
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagen, 0, sizeImage);

            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {//connection
            e.printStackTrace();
        }


        return null;
    }

    /**
     * Runs on the UI thread before {@link #doInBackground}.
     *
     * @see #onPostExecute
     * @see #doInBackground
     */
    //  se usa para preparar la UI
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialogo.show();
        dialogo.setProgress(0);
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p/>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param bitmap The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        imageView.setImageBitmap(bitmap);
        dialogo.hide();
    }

    /**
     * Runs on the UI thread after {@link #publishProgress} is invoked.
     * The specified values are the values passed to {@link #publishProgress}.
     *
     * @param values The values indicating progress.
     * @see #publishProgress
     * @see #doInBackground
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        //establecer el progreso que nos envían en el dialog
        dialogo.setProgress(values[0]);
    }
}
