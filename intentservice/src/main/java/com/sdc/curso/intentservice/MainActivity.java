package com.sdc.curso.intentservice;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

            /**
             * @param context The Context in which the receiver is running.
             * @param intent  The Intent being received.
             */
            @Override
            public void onReceive(Context context, Intent intent) {
                String nombre = intent.getStringExtra(SaludadorIntentService.EXTRA_PARAM_NOMBRE);

                final String action = intent.getAction();
                switch (intent.getAction()) {
                    case SaludadorIntentService.ACTION_BROADCAST_SALUDAR:
                        Toast.makeText(MainActivity.this, "Hola " + nombre, Toast.LENGTH_SHORT).show();
                        break;
                    case SaludadorIntentService.ACTION_BROADCAST_DESPEDIR:
                        Toast.makeText(MainActivity.this, "Adiós " + nombre, Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SaludadorIntentService.ACTION_BROADCAST_SALUDAR);
        intentFilter.addAction(SaludadorIntentService.ACTION_BROADCAST_DESPEDIR);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(broadcastReceiver, intentFilter);
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
        if (id == R.id.action_saludar) {
            SaludadorIntentService.startActionSaludar(this, "Pepe");
            return true;
        }else if (id == R.id.action_despedir){
            SaludadorIntentService.startActionDespedir(this, "Pepe");
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }
}
