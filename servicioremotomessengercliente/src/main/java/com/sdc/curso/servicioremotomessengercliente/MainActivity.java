package com.sdc.curso.servicioremotomessengercliente;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    public static final int ACTION_SALUDAR = 1;
    public static final String EXTRA_PARAM_NOMBRE = "nombre";
    public static final int ACTION_DESPEDIR = 2;
    private Messenger messenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        if (id == R.id.action_binding) {
            Intent intent = new Intent("com.sdc.curso.servicioremotomessenger.SERVICIO_SALUDADOR_BIND");
            ServiceConnection serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    //  persistimos el servicio ya que si no se cerraria y no podemos
                    //  hacer saludar ni despedir
                    //IBinder saludadorService = service;
                    ///*ISaludadorService*/ saludadorService = (ISaludadorService) service;
                    messenger = new Messenger(service);
                    /*Message mensaje = Message.obtain(null, ACTION_SALUDAR);
                    Bundle data = new Bundle();
                    data.putString(EXTRA_PARAM_NOMBRE, "Pepe");
                    mensaje.setData(data);
                    messenger.send(mensaje);*/
                }
                //  la desconexion se produce al perder el objeto: No lo podemos
                //  desconectar nosotros
                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            };
            //  debe ser Explicita
            //Intent intent = new Intent(this, SaludadorService.class);
            bindService(intent, serviceConnection, BIND_AUTO_CREATE);//este flag permite despreocuparse de la
            // creación/arranque del servicio
            return true;
        } else if (id == R.id.action_saludar) {
            Message mensaje = Message.obtain(null, ACTION_SALUDAR);
            Bundle data = new Bundle();
            data.putString(EXTRA_PARAM_NOMBRE, "Pepe");
            mensaje.setData(data);
            try {
                /***************************************************************/
                //  No tenemos respuesta del servicio ya que habría que implementar
                //  BroadcastReceiver
                messenger.send(mensaje);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return true;
        } else if (id == R.id.action_despedir) {
            Message mensaje = Message.obtain(null, ACTION_DESPEDIR);
            Bundle data = new Bundle();
            data.putString(EXTRA_PARAM_NOMBRE, "Pepe");
            mensaje.setData(data);
            try {
                messenger.send(mensaje);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}