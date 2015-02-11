package com.sdc.curso.basededatos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mañá on 10/02/2015.
 */
public class NoticiasDAO implements IDao<Integer, Noticia>{

    private SQLiteDatabase db;

    public NoticiasDAO(SQLiteDatabase db) {
        this.db = db;
    }


    @Override
    public void insertar(Noticia entidad) {
        db.insert(Noticia.TABLA, Noticia.CAMPO_TITULAR, toContentValues(entidad));
    }

    @Override
    public void borrar(Noticia entidad) {
        /*String whereClause = Noticia.CAMPO_ID + " = ?";
        String[] whereArgs = {String.valueOf(entidad.getId())};
        db.delete(Noticia.TABLA, whereClause, whereArgs);*/
        borrarPor(entidad.getId());
    }

    @Override
    public void borrarPor(Integer id) {
        String whereClause = Noticia.CAMPO_ID + " = ?";
        String[] whereArgs = {String.valueOf(id)};
        db.delete(Noticia.TABLA, whereClause, whereArgs);
    }

    @Override
    public void editar(Noticia entidad) {
        String whereClause = Noticia.CAMPO_ID + " = ?";
        String[] whereArgs = {String.valueOf(entidad.getId())};
        db.update(Noticia.TABLA,toContentValues(entidad), whereClause, whereArgs);
    }

    @Override
    public Noticia consultar(Integer id) {
        String[] proyeccion = {Noticia.CAMPO_ID,Noticia.CAMPO_TITULAR,Noticia.CAMPO_FECHA,Noticia.CAMPO_AUTOR,Noticia.CAMPO_CONTENIDO};
        String whereClause = Noticia.CAMPO_ID + " = ?";
        String[] whereArgs = {String.valueOf(id)};
        Cursor cursor = db.query(false, Noticia.TABLA, proyeccion, whereClause, whereArgs, null, null, null, null);
        return toList(cursor).get(0);
    }

    @Override
    public List<Noticia> consultar() {
        String[] proyeccion = {Noticia.CAMPO_ID,Noticia.CAMPO_TITULAR,Noticia.CAMPO_FECHA,Noticia.CAMPO_AUTOR,Noticia.CAMPO_CONTENIDO};
        Cursor cursor = db.query(false, Noticia.TABLA, proyeccion, null, null, null, null, null, null);
        return toList(cursor);
    }

    @Override
    public ContentValues toContentValues(Noticia entidad) {
        ContentValues contentValues = new ContentValues();
        if (entidad.getTitular() != null) {
            contentValues.put(Noticia.CAMPO_TITULAR, entidad.getTitular());
        }else{
            contentValues.putNull(Noticia.CAMPO_TITULAR);
        }
        if (entidad.getFecha() != null) {
            contentValues.put(Noticia.CAMPO_FECHA, entidad.getFecha().getTime());
        }else{
            contentValues.putNull(Noticia.CAMPO_FECHA);
        }
        if (entidad.getAutor() != null) {
            contentValues.put(Noticia.CAMPO_AUTOR, entidad.getAutor());
        }else{
            contentValues.putNull(Noticia.CAMPO_AUTOR);
        }
        if (entidad.getContenido() != null) {
            contentValues.put(Noticia.CAMPO_CONTENIDO, entidad.getContenido());
        }else{
            contentValues.putNull(Noticia.CAMPO_CONTENIDO);
        }
        return contentValues;
    }

    @Override
    public List<Noticia> toList(Cursor cursor) {
        LinkedList<Noticia> resultado = new LinkedList<>();
        if(cursor.moveToFirst()){
            do {
                Noticia noticia = new Noticia();
                if(cursor.getColumnIndex(Noticia.CAMPO_ID) != -1) {
                    noticia.setId(cursor.getInt(cursor.getColumnIndex(Noticia.CAMPO_ID)));
                }
                if(cursor.getColumnIndex(Noticia.CAMPO_TITULAR) != -1) {
                    noticia.setTitular(cursor.getString(cursor.getColumnIndex(Noticia.CAMPO_TITULAR)));
                }
                if(cursor.getColumnIndex(Noticia.CAMPO_FECHA) != -1) {
                    noticia.setFecha(new Date(cursor.getLong(cursor.getColumnIndex(Noticia.CAMPO_FECHA))));
                }
                if(cursor.getColumnIndex(Noticia.CAMPO_AUTOR) != -1) {
                    noticia.setAutor(cursor.getString(cursor.getColumnIndex(Noticia.CAMPO_AUTOR)));
                }
                if(cursor.getColumnIndex(Noticia.CAMPO_CONTENIDO) != -1) {
                    noticia.setContenido(cursor.getString(cursor.getColumnIndex(Noticia.CAMPO_CONTENIDO)));
                }
                resultado.add(noticia);
            } while (cursor.moveToNext());
        }

        return resultado;
    }
}