package com.sdc.curso.noticiasutils;

import android.net.Uri;

/**
 * Created by mañá on 11/02/2015.
 */
public interface NoticiasConstantes {

    //  Ctes que referncias los campos de la entidad
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_TITULAR = "Titular";
    public static final String CAMPO_FECHA = "Fecha";
    public static final String CAMPO_AUTOR = "Autor";
    public static final String CAMPO_CONTENIDO = "Contenido";

    //  Ctes para el ContentProvider
    public static final String AUTHORITY = "com.curso.android.contentprovider";
    public static final String ENTIDAD_NOTICIA = "Noticia";
    public static final String ENTIDAD_AUTOR = "Autor";

    public static final Uri URI_NOTICIA = Uri.parse("content://com.curso.android.contentprovider/Noticia");
    public static final Uri URI_AUTOR = Uri.parse("content://com.curso.android.contentprovider/Autor");

}
