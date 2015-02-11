package com.sdc.curso.basededatos;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

/**
 * Created by mañá on 10/02/2015.
 */
public interface IDao<K,E> {
    void insertar(E entidad);
    void borrar(E id);
    void borrarPor(K id);
    void editar(E entidad);
    E consultar(K id);
    List<E> consultar();
    //  mapeo de Objeto a Registro
    ContentValues toContentValues(E entidad);
    //  mapeo de Registro a Objeto
    List<E> toList(Cursor cursor);
}
