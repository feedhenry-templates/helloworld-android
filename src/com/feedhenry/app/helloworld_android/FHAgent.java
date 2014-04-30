package com.feedhenry.helloworld_app_android.app;

import com.feedhenry.sdk.FH;
import com.feedhenry.sdk.FHActCallback;
import com.feedhenry.sdk.FHResponse;
import com.feedhenry.sdk.api.FHActRequest;

import org.json.fh.JSONArray;
import org.json.fh.JSONObject;

public class FHAgent {


    public void cloudCall(FHActCallback fhActCallback) {

        JSONObject param = new JSONObject("{hello: 'world'}");
        this.call("hello", param, fhActCallback);
    }


    private void call(final String act, final JSONObject params, final FHActCallback callback) {

        try {
            FHActRequest request = FH.buildActRequest(act, params);
            request.executeAsync(callback);
        } catch (Exception e) {
            e.printStackTrace();
            callback.fail(getFakedErrorResponse(e.getMessage()));
        }
    }


    protected FHResponse getFakedErrorResponse(String errMessage) {

        FHResponse fakedErrorResponse = new FHResponse(new JSONObject(), new JSONArray(), new Throwable(errMessage), errMessage);
        return fakedErrorResponse;
    }
}