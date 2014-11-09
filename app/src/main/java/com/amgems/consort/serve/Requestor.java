package com.amgems.consort.serve;

import com.amgems.consort.model.Graph;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * @author Sherman Pay.
 * @version 0.1, 11/8/14.
 */
public interface Requestor {
    @FormUrlEncoded
    @POST("/SessionServer")
    void connectSession(@Field("user") String user, Callback<List<Integer>> callback);

    @FormUrlEncoded
    @POST("/GraphServer")
    void getGraph(@Field("session") int session, Callback<Graph> callback);
}
