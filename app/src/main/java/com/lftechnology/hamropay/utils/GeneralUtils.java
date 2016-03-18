package com.lftechnology.hamropay.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lftechnology.hamropay.HamroPayApplication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ManasShrestha on 2/12/16.
 */
public class GeneralUtils {

    /**
     * Converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp) {
        Resources resources = HamroPayApplication.getContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }

    /**
     * Converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px) {
        Resources resources = HamroPayApplication.getContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / (metrics.densityDpi / 160f);
    }

    /**
     * Returns current time in specified format
     *
     * @return {@link String} time
     */
    public static String getFormattedDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date = Calendar.getInstance().getTime();

        return simpleDateFormat.format(date);
    }

    /**
     * Validate number oif it begins with 9 or not
     *
     * @param number number to be validated as a string
     *
     * @return <ul>true</ul> if number matches else <ul>false</ul>
     */
    public static boolean numberValidation(String number) {

        return number.matches("9[0-9]{9}");
    }

    /**
     * get bitmap from storage uri and convert it to base 64 encoded string
     *
     * @param context {@link Context}
     * @param uri     {@link Uri} to storage
     * @return base64 encoded string
     */
    public static String getEncodedString(Context context, String uri) {
        Bitmap bitmap = null;
        try {
            Log.e("+++",""+uri);
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(uri));
//            Glide.with(context).load(uri).into(new SimpleTarget<Bitmap>(100, 100) {
//                @Override
//                public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
////                    callback.onDone(resource);
//                }
//
//                @Override
//                public void onLoadFailed(Exception e, Drawable errorDrawable) {
////                    callback.onDone(null);
//                }
//            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return encoded;
    }


    public static String convertToUpperCase(String s){
        final StringBuilder result = new StringBuilder(s.length());
        String[] words = s.split("\\s");
        for(int i=0,l=words.length;i<l;++i) {
            if(i>0) result.append(" ");
            result.append(Character.toUpperCase(words[i].charAt(0)))
                    .append(words[i].substring(1));

        }

        return result.toString();
    }

}
