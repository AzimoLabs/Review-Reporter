package com.azimo.tool.slack;

import com.azimo.tool.slack.model.SlackMessage;
import com.azimo.tool.slack.response.SlackPostMessageResponse;
import com.google.gson.Gson;
import okhttp3.*;
import rx.Observable;

import java.io.IOException;

/**
 * Created by F1sherKK on 24/01/17.
 */
public class SlackService {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient slackOkHttp;
    private Gson gson;

    public SlackService(OkHttpClient slackOkHttp, Gson gson) {
        this.slackOkHttp = slackOkHttp;
        this.gson = gson;
    }

    public Observable<SlackPostMessageResponse> postMessage(String url, SlackMessage message) {
        return Observable.create(subscriber -> {
            String jsonMessage = gson.toJson(message);

            RequestBody body = RequestBody.create(JSON, jsonMessage);
            Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

            try {
                Response response = slackOkHttp.newCall(request).execute();
                subscriber.onNext(new SlackPostMessageResponse(response.body().string()));
            } catch (IOException e) {
                e.printStackTrace();
                subscriber.onError(e);
            }

            subscriber.onCompleted();
        });
    }

}
