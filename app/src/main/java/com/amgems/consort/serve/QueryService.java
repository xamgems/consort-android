package com.amgems.consort.serve;

import com.amgems.consort.model.Graph;
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
    private static final String DEFAULT_SERVER_URL = "http://attu4.cs.washington.edu:33333";
    private static Requestor requestor;

    public QueryService() {
        this(DEFAULT_SERVER_URL);
    }

    public QueryService(String serverUrl) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(serverUrl)
                .setConverter(new GsonConverter(gson))
                .build();
        requestor = restAdapter.create(Requestor.class);
    }

    public void connectSession(String user, Callback<List<Integer>> callback) {
        requestor.connectSession(user, callback);
    }

    public void getGraph(String user, int session, Callback<Graph> callback) {
        requestor.getGraph(user, session, callback);
    }
}
