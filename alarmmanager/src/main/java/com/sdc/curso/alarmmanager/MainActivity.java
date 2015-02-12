package com.sdc.curso.alarmmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    public static final String ACTION_TRATAR_RESULTADO = "com.sdc.curso.alarmmanager.TRATAR_RESULTADO";
    public static final String EXTRA_RESULTADO = "resultado";
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  obtenemos la ref del servicio
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, EjecutorTareaRepetida.class);
        pendingIntent = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        BroadcastReceiver tratamientoDelResultado = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String resultado = intent.getStringExtra(EXTRA_RESULTADO);
                Toast.makeText(MainActivity.this, "El resultado del servicio es " + resultado, Toast.LENGTH_SHORT).show();
            }
        };

        IntentFilter intentFilter = new IntentFilter(ACTION_TRATAR_RESULTADO);
        registerReceiver(tratamientoDelResultado, intentFilter);

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
        if (id == R.id.action_activar) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, 0/*el tiempo que tarda en inciarse una vez se activa*/,
                    1 * 60 * 1000/*cada minuto*/, pendingIntent);
            return true;
        }else if (id == R.id.action_desactivar) {// si cambia el parámetro por el que se lanza el alarmManager, lo que hacemos es
            //  cancelar el alarmManager. Lo mismo al salir de la app.
            alarmManager.cancel(pendingIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
