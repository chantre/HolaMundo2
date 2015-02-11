package com.sdc.curso.intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SaludadorIntentService extends IntentService {


    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_SALUDAR = "com.sdc.curso.intentservice.action.SALUDAR";
    private static final String ACTION_DESPEDIR = "com.sdc.curso.intentservice.action.DESPEDIR";

    // TODO: Rename parameters
    public static final String EXTRA_PARAM_NOMBRE = "nombre";
    public static final String ACTION_BROADCAST_SALUDAR = "com.sdc.curso.intentservice.action.broadcast.SALUDAR";
    public static final String ACTION_BROADCAST_DESPEDIR = "com.sdc.curso.intentservice.action.broadcast.DESPEDIR";
    //private static final String EXTRA_PARAM2 = "com.sdc.curso.intentservice.extra.PARAM2";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionSaludar(Context context, String nombre) {
        Intent intent = new Intent(context, SaludadorIntentService.class);
        intent.setAction(ACTION_SALUDAR);
        intent.putExtra(EXTRA_PARAM_NOMBRE, nombre);
        //intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionDespedir(Context context, String nombre/*, String param2*/) {
        Intent intent = new Intent(context, SaludadorIntentService.class);
        intent.setAction(ACTION_DESPEDIR);
        intent.putExtra(EXTRA_PARAM_NOMBRE, nombre);
        //intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public SaludadorIntentService() {
        super("SaludadorIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SALUDAR.equals(action)) {
                final String nombre = intent.getStringExtra(EXTRA_PARAM_NOMBRE);
                //final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                saludar(nombre);
            } else if (ACTION_DESPEDIR.equals(action)) {
                final String nombre = intent.getStringExtra(EXTRA_PARAM_NOMBRE);
                //final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                despedir(nombre);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void saludar(String nombre/*, String param2*/) {
        //  No funciona por que no podemos acceder desde aqui a al UI
        //  neceistamos un listener : un Broadcast -> listener de intents y otros eventos
        //Toast.makeText(this, "Hola " + nombre, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ACTION_BROADCAST_SALUDAR);
        intent.putExtra(EXTRA_PARAM_NOMBRE, nombre);
        sendBroadcast(intent);
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void despedir(String nombre/*, String param2*/) {
        // TODO: Handle action Baz
        //throw new UnsupportedOperationException("Not yet implemented");
        //Toast.makeText(this, "Adiós " + nombre, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ACTION_BROADCAST_DESPEDIR);
        intent.putExtra(EXTRA_PARAM_NOMBRE, nombre);
        sendBroadcast(intent);
    }
}
