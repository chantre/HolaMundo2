package com.sdc.curso.parserxml;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lista = (ListView) findViewById(R.id.listView);

        InputStream is = getResources().openRawResource(R.raw.terremotos);


        ArrayAdapter<Terremoto> adapter = null;
        try {
            List<Terremoto> terremotos = obtenerTerremotos(is);
            adapter = new ArrayAdapter<Terremoto>(this, android.R.layout.simple_list_item_1, terremotos);
        } catch (XmlPullParserException e) {
            Log.e("ParserACME", "pull parse");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("ParserACME", "IO");
            e.printStackTrace();
        } catch (ParseException e) {
            Log.e("ParserACME", "parse");
            e.printStackTrace();
        }

        lista.setAdapter(adapter);
    }

    private List<Terremoto> obtenerTerremotos(InputStream is) throws XmlPullParserException, IOException, ParseException {
        //LinkedList<Terremoto> terremotos = new LinkedList<>();
        List<Terremoto> terremotos = new ArrayList<>();

        //  crearemos el xmlpullparser
        XmlPullParser parser = Xml.newPullParser();
        //  asignamos los datos a parsear
        parser.setInput(is, "UTF-8");//XmlPullParserException
        //  comenzamos a leer hasta el fin del documento
        //  inicializamos el parser
        int event = parser.getEventType();
        //  tb nos valdrá de variable de control ya que será null hasta que lleguemos a un entry
        Terremoto terremoto = null;
        while (event != XmlPullParser.END_DOCUMENT){
            //  ahora comprobamos el el. en el que estamos
            if (event == XmlPullParser.START_TAG){
                if(parser.getName() == ("entry")){
                    terremoto = new Terremoto();
                }else if(parser.getName().equals("id") && terremoto != null) {
                    String[] split = parser.nextText().split(":");
                    terremoto.setId(split[split.length - 2].concat(":").concat(split[split.length - 1]));
                }else if(parser.getName().equals("title") && terremoto != null) {
                    String titulo = parser.nextText();
                    terremoto.setTitulo(titulo);
                    terremoto.setMagnitud(Float.parseFloat(titulo.split(" ")[1]));
                    //terremoto.setMagnitud(split[split.length - 2].concat(":").concat(split[split.length - 1]));
                }else if(parser.getName().equals("updated") && terremoto != null) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    terremoto.setFechaActualizacion(formatter.parse(parser.nextText()));// ParseException
                }else if(parser.getName().equals("link") && terremoto != null) {
                    terremoto.setLink(parser.getAttributeValue(null, "href"));
                }else if(parser.getName().equals("point") && terremoto != null) {
                    String[] split = parser.nextText().split(" ");
                    /*terremoto.setLatitud(Float.parseFloat(parser.nextText().split(" ")[0]));
                    terremoto.setLongitud(Float.parseFloat(parser.nextText().split(" ")[1]));*/
                    terremoto.setLatitud(Float.parseFloat(split[0]));
                    terremoto.setLongitud(Float.parseFloat(split[1]));

                }
            } else if (event == XmlPullParser.END_TAG){
                if(parser.getName() == ("entry")){
                    terremotos.add(terremoto);
                    terremoto = null;
                }
            }
            //  al final de cada iteración
            event = parser.next();//    IOException
        }

        return terremotos;
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
}
