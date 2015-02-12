package com.sdc.curso.alarmmanager;

import android.app.IntentService;
import android.content.Intent;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class EjecutorTareaRepetida extends IntentService {


    public EjecutorTareaRepetida() {
        super("EjecutorTareaRepetida");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent intentResultado = new Intent(MainActivity.ACTION_TRATAR_RESULTADO);
        intentResultado.putExtra(MainActivity.EXTRA_RESULTADO, "Todo fue bien");
        sendBroadcast(intentResultado);
    }
}
