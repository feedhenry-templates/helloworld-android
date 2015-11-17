/**
 * Copyright 2015 Red Hat, Inc., and individual contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feedhenry.helloworld;

import org.json.fh.JSONArray;
import org.json.fh.JSONObject;

import com.feedhenry.sdk.FH;
import com.feedhenry.sdk.FHActCallback;
import com.feedhenry.sdk.FHResponse;
import com.feedhenry.sdk.api.FHCloudRequest;

public class FHAgent {


    public void cloudCall(FHActCallback fhCallback) {
        JSONObject param = new JSONObject("{hello: 'world'}");
        this.call("hello", param, fhCallback);
    }


    private void call(final String path, final JSONObject params, final FHActCallback callback) {

        try {
            FHCloudRequest request = FH.buildCloudRequest(path, "POST", null, params);
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