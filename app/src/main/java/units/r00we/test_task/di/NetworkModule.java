package units.r00we.test_task.di;

import android.app.Application;
import android.content.Context;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import units.r00we.test_task.Constants;
import units.r00we.test_task.data.ApiRepository;
import units.r00we.test_task.data.ApiService;
import units.r00we.test_task.data.IApiRepository;
import units.r00we.test_task.data.entity.Issue;
import units.r00we.test_task.data.TokenInterceptor;

@Module
public class NetworkModule {

    @Provides
    OkHttpClient getOkHttpClient(Application application) {

        File httpCacheDirectory = new File(application.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024;

        return new OkHttpClient.Builder()
                .addInterceptor(new TokenInterceptor(Constants.TOKEN))
                .cache(new Cache(httpCacheDirectory, cacheSize))
                .build();
    }

    @Provides
    ApiService getGitHubService(OkHttpClient client) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        return retrofit.create(ApiService.class);
    }

    @Provides
    IApiRepository getApiRepository(ApiService apiService){
        return new ApiRepository(Constants.USER, Constants.REPOSITORY, apiService);
    }

}
