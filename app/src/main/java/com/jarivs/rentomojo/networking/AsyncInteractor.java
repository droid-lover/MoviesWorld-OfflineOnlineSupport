package com.jarivs.rentomojo.networking;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jarivs.rentomojo.AppController;
import com.jarivs.rentomojo.utils.AppConstants;
import com.jarivs.rentomojo.utils.Sharedpreferences;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Sachin.
 */
public class AsyncInteractor implements IAsyncInteractor {


    String TAG = "ApiCall";


    public void validateCredentialsAsync(final OnRequestListener listener, final int pid, final
    String url) {
        Log.v("url-->>", "" + url.toString());
        getJsonObjectResponse(listener, pid, url);
    }


    public void getJsonObjectResponse(final OnRequestListener listener, final int pid, String url) {


        if (pid == AppConstants.TAG_ID_GET_MOVIES_DATA) {
            Log.i(TAG, "TAG_ID_GET_MOVIES_DATA" + url);
            AppController.getInstance().getRequestQueue().getCache().invalidate(url, true);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i(TAG, "TAG_ID_GET_MOVIES_DATA" + response.toString());
                            System.out.println(response);
                            try {
                                listener.onRequestCompletion(pid, response.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "TAG_ID_GET_MOVIES_DATA" + error.toString());
                            listener.onRequestCompletionError(pid, error.toString());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<String, String>();
                    header.put("Content-Type", "application/json; charset=UTF-8");
                    return header;
                }
            };
            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(stringRequest);
        }

    }
}