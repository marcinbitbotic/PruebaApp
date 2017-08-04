package com.pruebaapp.app.utils;

import android.provider.SyncStateContract;
import android.text.format.DateFormat;

import com.pruebaapp.app.constants.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Marcin on 3/8/17.
 */

public class DateUtils {

    public static Date parseDateFromString(String date){

        SimpleDateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT);
        Date myDate = null;
        try {
            myDate = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return myDate;

    }

    public static String DateToString(Date date){

        String dateString = DateFormat.format(Constants.DATE_FORMAT, date).toString();

        return dateString;

    }


}
