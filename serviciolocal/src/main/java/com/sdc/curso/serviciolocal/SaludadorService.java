package com.sdc.curso.serviciolocal;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.widget.Toast;

import java.io.FileDescriptor;

public class SaludadorService extends Service {
    public SaludadorService() {
    }

    /**
     * IntentService hereda de Service e implementa onBind. La implementación
     * habitual es return null;
     */

    /*//  maneja el evento de arrancar el servicio (startService)
    //  crea hilo y arranca servicio
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    //  se ejecuta al hacer el stop del servicio
    @Override
    public void onDestroy() {
        super.onDestroy();
    }*/

    //responde a peticiones sobre el servicio a enlazar
    @Override
    public IBinder onBind(Intent intent) {
        //  los Stub son servicios remotos creados con AIDL
        //  creamos un Binder que impelmenta IBinde y nos vale
        //  hace de enlace y debe representar al servicio
        return new SaludadorBinder(this);
    }

    public String saludar(String nombre){
        return "Hola " + nombre + "!!!";
    }

    public void despedir(String nombre){
        //  podemos hacer el Toast por que los servicios se ejecutan en el Main Thread
        Toast.makeText(this, "Adiós " + nombre + "!!!!", Toast.LENGTH_SHORT).show();
    }
}
