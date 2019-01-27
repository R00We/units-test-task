package units.r00we.test_task.data;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    private static final String PARAMETER_ACCESS_TOKEN = "access_token";
    private final String token;

    public TokenInterceptor(String token) {
        this.token = token;
    }

    private Request buildRequest(@NonNull Request request) {

        HttpUrl originalHttpUrl = request.url();

        HttpUrl.Builder urlWithAccessToken = originalHttpUrl.newBuilder()
                .setQueryParameter(PARAMETER_ACCESS_TOKEN, token);

        HttpUrl httpUrl = urlWithAccessToken.build();

        request = request.newBuilder()
                .url(httpUrl)
                .build();

        return request;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = buildRequest(chain.request());
        return chain.proceed(request);


    }
}
