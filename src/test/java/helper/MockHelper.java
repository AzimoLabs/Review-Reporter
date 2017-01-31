package helper;

import okhttp3.Request;

/**
 * Created by F1sherKK on 18/01/17.
 */
public class MockHelper {

    public static Request.Builder getRequestDraft() {
        return new Request.Builder()
            .url("http://www.example.com");
    }

    public static Request getRequest() {
        return getRequestDraft().build();
    }
}
