package com.ticonsultoria.tivendas.tivendas.Helper;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mpire on 16/03/2018.
 */

public class toDate {

    public static Date from (String s){
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        try {
            return formato.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.i("ERROR ", "from: " + e.getStackTrace().toString());
            return null;
        }
    }

}
