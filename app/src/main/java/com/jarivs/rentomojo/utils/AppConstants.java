package com.jarivs.rentomojo.utils;

import java.util.List;

public class AppConstants {


    public static int SUCCESS_CODE = 200;

    public static int TAG_ID_GET_MOVIES_DATA = 101;


    /*
     *  Enum to get URL and TAG names
     */
    public enum URL {

        GET_MOVIES_DATA("/movies.json");

        private String url;

        public String baseUrl = "https://api.androidhive.info/json";

        URL(String url) {

            this.url = baseUrl + url;
        }

        public String getUrl() {
            return url;
        }
    }

}
