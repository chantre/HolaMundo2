package com.sdc.curso.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.sdc.curso.noticiasutils.NoticiasConstantes;

public class NoticiasContentProvider extends ContentProvider {
    public static final String TABLA_NOTICIAS = "Noticias";
    private SQLiteDatabase db;
    //  para la creacion de uris,tenemos el UriMatcher que viene siendo un Bundle
    //  Es estático, una vez creado no se toca
    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);//se inicializa vacía con el NO_MATCH

    private static final int CODE_NOTICIAS = 1;

    private static final int CODE_NOTICIA = 2;

    private static final int CODE_AUTORES = 3;

    private static final int CODE_AUTOR = 4;



    //  en el initializer static
    static{
        //content://com.curso.android.contentprovider/Noticia -> si llega eso, devuelve 1
        matcher.addURI(NoticiasConstantes.AUTHORITY, NoticiasConstantes.ENTIDAD_NOTICIA, CODE_NOTICIAS);// se obvia el protocolo
        //content://com.curso.android.contentprovider/Noticia/{id} -> si llega eso, devuelve 1
        matcher.addURI(NoticiasConstantes.AUTHORITY, NoticiasConstantes.ENTIDAD_NOTICIA + "/#", CODE_NOTICIA);//#:numérico; *: String

        //content://com.curso.android.contentprovider/Autor -> si llega eso, devuelve 3
        matcher.addURI(NoticiasConstantes.AUTHORITY, NoticiasConstantes.ENTIDAD_AUTOR, CODE_AUTORES);
        //content://com.curso.android.contentprovider/Autor/{id} -> si llega eso, devuelve 1
        matcher.addURI(NoticiasConstantes.AUTHORITY, NoticiasConstantes.ENTIDAD_AUTOR + "/*", CODE_AUTOR);
    }

    /*public NoticiasContentProvider() {
    }*/

    //  para poder tener el context
    @Override
    public boolean onCreate() {
        NoticiasSQLiteOpenHelper noticiasSQLiteOpenHelper = new NoticiasSQLiteOpenHelper(getContext(), "Noticias.s3db"/*puede tener otro nombre ya que está en otra app*/,
                null, getContext().getResources().getInteger(R.integer.versionDataBase));
        db = noticiasSQLiteOpenHelper.getWritableDatabase();

        return true;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int codigo = matcher.match(uri);
        switch (codigo){
            case CODE_NOTICIAS:
                //   el id se obtiene por que es autogenerado, en otro caso
                //  tendriamos que inspeccionar el ContentValuies que nos llega
                long id = db.insert(TABLA_NOTICIAS, NoticiasConstantes.CAMPO_TITULAR, values);
                return ContentUris.withAppendedId(uri, id);
            case CODE_NOTICIA:
                throw new UnsupportedOperationException("Not yet implemented");
            case CODE_AUTORES://en funcion de si soporto o no inserciones
                throw new UnsupportedOperationException("Not yet implemented");
            case CODE_AUTOR:
                throw new UnsupportedOperationException("Not yet implemented");
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (matcher.match(uri)){
            case CODE_NOTICIAS://damos opcion de borrar toda la tabla
                //  si por ejemplo los params fueran null o en el where
                //  se indicara que el id != -1
                //return db.delete("Noticias", selection, selectionArgs);
                throw new UnsupportedOperationException("Not yet implemented");
            case CODE_NOTICIA://se suele implementar el borrado si se pasa el id
                //  primero recuperamos el id del uri
                String id = uri.getLastPathSegment();

                String whereClause = NoticiasConstantes.CAMPO_ID + " = ?";
                String[] whereArgs = {id};
                return db.delete(TABLA_NOTICIAS, whereClause, whereArgs);
            case CODE_AUTORES:
                throw new UnsupportedOperationException("Not yet implemented");
            case CODE_AUTOR:
                throw new UnsupportedOperationException("Not yet implemented");
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        switch (matcher.match(uri)){
            case CODE_NOTICIAS://damos opcion de borrar toda la tabla
                //  si por ejemplo los params fueran null o en el where
                //  se indicara que el id != -1
                //return db.delete("Noticias", selection, selectionArgs);
                throw new UnsupportedOperationException("Not yet implemented");
            case CODE_NOTICIA://se suele implementar la actualización si se pasa el id
                //  primero recuperamos el id del uri
                String id = uri.getLastPathSegment();

                String whereClause = NoticiasConstantes.CAMPO_ID + " = ?";
                String[] whereArgs = {id};
                return db.update(TABLA_NOTICIAS, values, whereClause, whereArgs);
            case CODE_AUTORES:
                throw new UnsupportedOperationException("Not yet implemented");
            case CODE_AUTOR:
                throw new UnsupportedOperationException("Not yet implemented");
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (matcher.match(uri)){
            case CODE_NOTICIAS://damos opcion de recuperar toda la tabla
                //  pero limitando el número de los datos devueltos
                return db.query(false, TABLA_NOTICIAS, projection, selection, selectionArgs, null, null, sortOrder, "50");
            case CODE_NOTICIA:
                //  primero recuperamos el id del uri
                String id = uri.getLastPathSegment();

                String whereClause = NoticiasConstantes.CAMPO_ID + " = ?";
                String[] whereArgs = {id};
                return db.query(false, TABLA_NOTICIAS, projection, whereClause, whereArgs, null, null, sortOrder, null);
            case CODE_AUTORES:
                throw new UnsupportedOperationException("Not yet implemented");
            case CODE_AUTOR:
                throw new UnsupportedOperationException("Not yet implemented");
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    // esta pensado
    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)){
            case CODE_NOTICIAS:
                return "vnd.android.cursor.dir/vnd." + NoticiasConstantes.AUTHORITY + "." + NoticiasConstantes.ENTIDAD_NOTICIA;
            case CODE_NOTICIA:
                return "vnd.android.cursor.item/vnd." + NoticiasConstantes.AUTHORITY + "." + NoticiasConstantes.ENTIDAD_NOTICIA;
            case CODE_AUTORES:
                return "vnd.android.cursor.dir/vnd." + NoticiasConstantes.AUTHORITY + "." + NoticiasConstantes.ENTIDAD_AUTOR;
            case CODE_AUTOR:
                return "vnd.android.cursor.item/vnd." + NoticiasConstantes.AUTHORITY + "." + NoticiasConstantes.ENTIDAD_AUTOR;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }
}
