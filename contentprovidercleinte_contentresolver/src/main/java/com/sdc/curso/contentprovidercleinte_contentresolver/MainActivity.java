package com.sdc.curso.contentprovidercleinte_contentresolver;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sdc.curso.noticiasutils.NoticiasConstantes;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContentResolver resolver = getContentResolver();

        Noticia noticia = new Noticia(0, "Noticia importante: desde el ContentResolver",
                new Date(), "Pepe", "Varias lineas de texto");
        //  tiene los mismos metodos que el CP
        ContentValues values = toContentValues(noticia);//new ContentValues();
        //  el uri que devuelve es del formato que nos devuelve en el insert del CP
        Uri uriInsert = resolver.insert(NoticiasConstantes.URI_NOTICIA, values);

        Toast.makeText(this, uriInsert.toString(), Toast.LENGTH_SHORT).show();
        String[] proyeccion = {NoticiasConstantes.CAMPO_ID,NoticiasConstantes.CAMPO_TITULAR,NoticiasConstantes.CAMPO_FECHA,NoticiasConstantes.CAMPO_AUTOR,NoticiasConstantes.CAMPO_CONTENIDO};
        Cursor cursor = resolver.query(uriInsert, proyeccion, null, null, null);

        Noticia noticia1 = toList(cursor).get(0);
        Toast.makeText(this, noticia1.toString(), Toast.LENGTH_SHORT).show();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ContentValues toContentValues(Noticia entidad) {
        ContentValues contentValues = new ContentValues();
        if (entidad.getTitular() != null) {
            contentValues.put(NoticiasConstantes.CAMPO_TITULAR, entidad.getTitular());
        }else{
            contentValues.putNull(NoticiasConstantes.CAMPO_TITULAR);
        }
        if (entidad.getFecha() != null) {
            contentValues.put(NoticiasConstantes.CAMPO_FECHA, entidad.getFecha().getTime());
        }else{
            contentValues.putNull(NoticiasConstantes.CAMPO_FECHA);
        }
        if (entidad.getAutor() != null) {
            contentValues.put(NoticiasConstantes.CAMPO_AUTOR, entidad.getAutor());
        }else{
            contentValues.putNull(NoticiasConstantes.CAMPO_AUTOR);
        }
        if (entidad.getContenido() != null) {
            contentValues.put(NoticiasConstantes.CAMPO_CONTENIDO, entidad.getContenido());
        }else{
            contentValues.putNull(NoticiasConstantes.CAMPO_CONTENIDO);
        }
        return contentValues;
    }

    public List<Noticia> toList(Cursor cursor) {
        LinkedList<Noticia> resultado = new LinkedList<>();
        if(cursor.moveToFirst()){
            do {
                Noticia noticia = new Noticia();
                if(cursor.getColumnIndex(NoticiasConstantes.CAMPO_ID) != -1) {
                    noticia.setId(cursor.getInt(cursor.getColumnIndex(NoticiasConstantes.CAMPO_ID)));
                }
                if(cursor.getColumnIndex(NoticiasConstantes.CAMPO_TITULAR) != -1) {
                    noticia.setTitular(cursor.getString(cursor.getColumnIndex(NoticiasConstantes.CAMPO_TITULAR)));
                }
                if(cursor.getColumnIndex(NoticiasConstantes.CAMPO_FECHA) != -1) {
                    noticia.setFecha(new Date(cursor.getLong(cursor.getColumnIndex(NoticiasConstantes.CAMPO_FECHA))));
                }
                if(cursor.getColumnIndex(NoticiasConstantes.CAMPO_AUTOR) != -1) {
                    noticia.setAutor(cursor.getString(cursor.getColumnIndex(NoticiasConstantes.CAMPO_AUTOR)));
                }
                if(cursor.getColumnIndex(NoticiasConstantes.CAMPO_CONTENIDO) != -1) {
                    noticia.setContenido(cursor.getString(cursor.getColumnIndex(NoticiasConstantes.CAMPO_CONTENIDO)));
                }
                resultado.add(noticia);
            } while (cursor.moveToNext());
        }

        return resultado;
    }
}
