package com.jarivs.rentomojo.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Toast;

public class NetworkStatus {
    private final Context mContext;

    public NetworkStatus(Context context) {
        this.mContext = context;

    }

    ConnectivityManager connectivityManager;
    NetworkInfo wifiInfo, mobileInfo;
    boolean connected = false;

    public boolean isOnline() {
        try {
            connectivityManager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager
                    .getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable()
                    && networkInfo.isConnectedOrConnecting();
            return connected;

        } catch (Exception e) {

        }
        return connected;
    }

    public static void showInternetSettings(final Context mContext) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("No Internet Connection");
        alertDialog
                .setMessage("No network available. Please check you internet connection.");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_WIRELESS_SETTINGS);
                        mContext.startActivity(intent);
                        ((Activity) mContext).finish();

                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ((Activity) mContext).finish();
                    }
                });
        alertDialog.show();
    }

    public static boolean checkNetworkStatus(Context context) {

        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(context
                .CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    public static void ShowToast(final Context mContext, String message) {

        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
}
