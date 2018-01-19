package com.ticonsultoria.tivendas.tivendas.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by mpire on 19/01/2018.
 */

public class DbGateway {

    private static DbGateway gw;
    private SQLiteDatabase db;

    private DbGateway (Context context) {
        CriaBanco criaBanco = new CriaBanco(context);
        db = criaBanco.getWritableDatabase();
    }

    public static DbGateway getInstance(Context context) {
        if (gw == null) {
            gw = new DbGateway(context);
        }
        return gw;
    }

    public SQLiteDatabase getDatabase() {
        return this.db;
    }

}
