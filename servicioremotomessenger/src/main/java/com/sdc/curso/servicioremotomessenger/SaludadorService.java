package com.sdc.curso.servicioremotomessenger;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;

public class SaludadorService extends Service {
    public static final int ACTION_SALUDAR = 1;
    public static final int ACTION_DESPEDIR = 2;
    public static final String EXTRA_PARAM_NOMBRE = "nombre";

    public SaludadorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        //  vamos a devolver un handler
        Messenger messenger = new Messenger(new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String nombre = msg.getData().getString(EXTRA_PARAM_NOMBRE);
                switch (msg.what){
                    case ACTION_SALUDAR:
                        saludar(nombre);
                        break;
                    case ACTION_DESPEDIR:
                        despedir(nombre);
                        break;
                }
            }
        });

        return messenger.getBinder();
    }
    public void saludar(String nombre){
        Toast.makeText(this, "Hola " + nombre + "!!!!", Toast.LENGTH_SHORT).show();
    }

    public void despedir(String nombre){
        //  podemos hacer el Toast por que los servicios se ejecutan en el Main Thread
        Toast.makeText(this, "Adiós " + nombre + "!!!!", Toast.LENGTH_SHORT).show();
    }
}
