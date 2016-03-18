package com.lftechnology.hamropay.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.lftechnology.hamropay.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
public class GeneralUtility {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private static final String PHONE_PATTERN = "\\d{10}";

    private static ProgressDialog progressDialog;

    public static boolean validateEmail(String email) {
        Matcher matcher = Pattern.compile(EMAIL_PATTERN).matcher(email);
        return matcher.matches();
    }

    public static boolean validateEmpty(String text) {
        return text.isEmpty();
    }

    public static boolean validatePhone(String phoneNumber) {
        Matcher matcher = Pattern.compile(PHONE_PATTERN).matcher(phoneNumber);
        return matcher.matches();
    }

    public static void showProgressDialog(Context context, String message) {
        progressDialog = new ProgressDialog(context,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(false);
        progressDialog.incrementProgressBy(5);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void stopProgressDialog() {
        progressDialog.dismiss();
    }


    private static final String FORMAT = "MM/dd/yyyy";

    public static String getTransactionTime(long diffInSecondsMain) {

        double diffInSeconds = Math.abs(Calendar.getInstance().getTimeInMillis() - diffInSecondsMain)/1000;

        String[] remainingTimeWithUnit = new String[2];
        DecimalFormat format = new DecimalFormat("#");
        double sec = (diffInSeconds < 60 ? diffInSeconds % 60 : diffInSeconds);

        if (sec < 0 || sec < 60) {
            if (sec < 0)
                sec = 0;
            return format.format(sec) + "s";
        }

        double min = (diffInSeconds = (Math.round(diffInSeconds / 60))) < 60 ? diffInSeconds % 60
                : diffInSeconds;

        if (min < 60) {
            return format.format(min) + "m";
        }
        double hrs = (diffInSeconds = (Math.round(diffInSeconds / 60))) < 24 ? diffInSeconds % 24
                : diffInSeconds;

        if (hrs < 24) {
            return format.format(hrs) + "h";
        }
        Calendar calendar =Calendar.getInstance();
        calendar.setTimeInMillis(diffInSecondsMain);
        return new SimpleDateFormat(FORMAT).format(calendar.getTime());
    }
}
