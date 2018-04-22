package com.jarivs.rentomojo.utils;

/*
 *  Writtern By Sachin Rajput
 * */


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jarivs.rentomojo.R;


public class Utils {

    private static ProgressDialog progress;

    public interface dialogInterface {
        void dialogClick();
    }


    public static ProgressDialog progressSimple;


    public static String refreshedFirebaseTokenValue = "";

    public static void showProgress(Context context) {
        progress = new ProgressDialog(context);
        progress.setMessage("Please Wait");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);

        // if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        Drawable drawable = new ProgressBar(context).getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimaryDark),
                PorterDuff.Mode.SRC_IN);
        progress.setIndeterminateDrawable(drawable);
        //  }

        progress.show();
    }

    public static void showProgress(Context context, String message) {
        try {
            progressSimple = new ProgressDialog(context);
            progressSimple.setMessage(message);
            progressSimple.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressSimple.setCancelable(false);
            progressSimple.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopProgress(Context context) {
        try {
            progress.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
     * display Toast message
     */

    public static void showToast(Context context, String str) {
        Toast.makeText(context, "" + str, Toast.LENGTH_SHORT).show();

    }


    /*
     *Check Internet availability
     */
    public static boolean isInternetAvailable(Context context) {
        boolean isInternetAvailable = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager
                    .getActiveNetworkInfo();
            isInternetAvailable = networkInfo != null && networkInfo.isAvailable()
                    && networkInfo.isConnectedOrConnecting();
            if (isInternetAvailable)
                Utils.showToast(context, "Please connect to internet");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isInternetAvailable;
    }

}
