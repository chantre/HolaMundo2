package com.sdc.curso.contentprovider;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mañá on 10/02/2015.
 */
public class NoticiasSQLiteOpenHelper extends SQLiteOpenHelper {

    private Context context;

    public NoticiasSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        this(context, name, factory, version, null);
        //this.context = context;
    }

    public NoticiasSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        int script = R.array.scriptCreateNoticias;
        ejecutarScript(db, script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion){
            case 1:
                ejecutarScript(db, R.array.scriptUpdateNoticias_1_3);
                break;
            case 2:
                ejecutarScript(db, R.array.scriptUpdateNoticias_2_3);
                break;
        }
    }

    private void ejecutarScript(SQLiteDatabase db, int script) {
        String[] scripts = context.getResources().getStringArray(script);

        db.beginTransaction();
        try {
            for (String sentencia : scripts) {
                db.execSQL(sentencia);
            }
            db.setTransactionSuccessful();//Marca la tx para commit
        }finally {
            db.endTransaction();//  si no se hizo commit, se considera rollback
        }
    }
}
