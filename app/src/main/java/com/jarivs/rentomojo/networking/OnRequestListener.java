package com.jarivs.rentomojo.networking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface OnRequestListener {

    void onRequestCompletion(int pid, String responseJson) throws JSONException;
    void onRequestCompletionError(int pid, String error);

}
