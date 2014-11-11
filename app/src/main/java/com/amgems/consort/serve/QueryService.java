package com.amgems.consort.serve;

import com.amgems.consort.consort.BuildConfig;
import com.amgems.consort.model.GraphMappings;
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
    private static Requestor requestor;

    public QueryService() {
        this(BuildConfig.SERVER_URL);
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

    public void connectSession(String user, String regId, Callback<List<Integer>> callback) {
        requestor.connectSession(user, regId, callback);
    }

    public void getGraph(String user, int session, Callback<GraphMappings> callback) {
        requestor.getGraph(user, session, callback);
    }

    public void updateState(String user, String data, Callback<String> callback) {
        requestor.updateState(user, data, callback);
    }
}
