package com.ticonsultoria.tivendas.tivendas.Helper;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by mpire on 16/03/2018.
 */

public class toDate {

    public static Date from (String s){

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date data = new Date();

        try {
            data = formato.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.i("ERROR ", "from: " + e.getStackTrace().toString());
        }

        return data;
    }

    public static String string(Date date){
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formato.format(date);
    }

}
