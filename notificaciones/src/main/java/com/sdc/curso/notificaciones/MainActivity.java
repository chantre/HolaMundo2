package com.sdc.curso.notificaciones;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    public static final int ID_NOTIFICACION_PROGRESO = 1;
    public static final int ID_NOTIFICACION_IMAGEN = 2;
    public static final int ID_NOTIFICACION_BOTON = 3;
    private Notification.Builder builderProgreso;
    private Notification.Builder builderImagen;
    private Notification.Builder builderBotones;

    private NotificationManager notifManager;// = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        builderProgreso = new Notification.Builder(this);
        builderProgreso
                .setSmallIcon(android.R.drawable.ic_menu_myplaces)
                .setTicker("Descargando...")
                .setContentTitle("Descargando el fichero terremotos.xml");
                /*.setProgress(100, 0, false);*///indeterminate: true.-no sabemos cuando acabará y no se usanlos otros dos

        Notification.BigPictureStyle style = new Notification.BigPictureStyle();
        style
                .bigLargeIcon(BitmapFactory.decodeResource(getResources(),android.R.drawable.ic_dialog_email))
                .setBigContentTitle("nueva foto de perfil")
                .bigPicture(BitmapFactory.decodeResource(getResources(),android.R.drawable.ic_dialog_map));
                //.setSummaryText("descripcion de la foto de perfil");

        builderImagen = new Notification.Builder(this);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builderImagen
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setTicker("Actualizando foto de perfil")
                //.setContentTitle("Nueva imagen")
                //.setLargeIcon(BitmapFactory.decodeResource(getResources(),android.R.drawable.stat_sys_download))
                .setStyle(style)
                .setAutoCancel(true)//desaparece al pinchar en ella
                .setContentIntent(pendingIntent) ;//permite definir la accion que se lanzará al pinchar en la notificacion
        //  PendingIntent es un intent pendiente: permite encapsular una intención
        //  que se lanzará en otro momento. La lanza el que crea la intención

        builderBotones = new Notification.Builder(this);
        builderBotones
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setTicker("Reproductor de música")
                .addAction(android.R.drawable.ic_media_play, "", pendingIntent)
                .addAction(android.R.drawable.ic_media_pause, "", pendingIntent)
                .addAction(android.R.drawable.ic_media_next, "", pendingIntent);
        //
        notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notif_progreso) {
            /*Notification notifProgreso = builderProgreso.build();
            notifManager.notify(ID_NOTIFICACION_PROGRESO, notifProgreso);*/
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i = 0; i <= 10; i++){
                        builderProgreso.setProgress(100, i * 10, false);
                        Notification notifProgreso = builderProgreso.build();
                        notifManager.notify(ID_NOTIFICACION_PROGRESO, notifProgreso);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //  aqui podriamos notificar que se acabó la descarga
                }
            }).start();
            return true;
        }else if (id == R.id.action_notif_imagen) {

            Notification notifImagen = builderImagen.build();
            notifManager.notify(ID_NOTIFICACION_IMAGEN, notifImagen);
            return true;
        } else if (id == R.id.action_notif_botones) {

            Notification notifBotones = builderBotones.build();

            notifManager.notify(ID_NOTIFICACION_BOTON, notifBotones);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
