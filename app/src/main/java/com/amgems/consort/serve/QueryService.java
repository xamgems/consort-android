package com.amgems.consort.serve;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * @author Sherman Pay.
 * @version 0.1, 11/8/14.
 */
public class QueryService {
    private static final String SERVER_URL = "http://attu4.cs.washington.edu:33333";
    private static Requestor requestor;

    public static void init() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SERVER_URL)
                .setConverter(new GsonConverter(gson))
                .build();
        requestor = restAdapter.create(Requestor.class);
    }

    public static void connectSession(String user, Callback<List<Integer>> callback) {
        requestor.connectSession(user, callback);
    }
}
