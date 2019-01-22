package units.r00we.test_task.di;

import android.arch.paging.DataSource;
import android.arch.paging.PagedList;
import android.arch.paging.RxPagedListBuilder;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import units.r00we.test_task.Constants;
import units.r00we.test_task.network.ApiService;
import units.r00we.test_task.network.Issue;
import units.r00we.test_task.network.IssuesDataSource;

@Module
public class NetworkModule {

    @Provides
    ApiService getGitHubService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        return retrofit.create(ApiService.class);
    }

    @Provides
    CompositeDisposable getCompositeDisposable() {
        return new CompositeDisposable();
    }


    //todo подумать, скорее всего этот конфиг в UIModule должен быть
    @Provides
    @Named("IssuePagedListConfig")
    PagedList.Config getPagedListConfig(){
        return new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(30)
                .setPrefetchDistance(10)
                .build();
    }

    @Provides
    @Named("IssueDataSource")
    DataSource.Factory<Integer, Issue> getIssueDataSourceFactory(ApiService apiService, CompositeDisposable compositeDisposable) {
        return new IssuesDataSource.Factory(Constants.User, Constants.Repo, apiService, compositeDisposable);
    }


    @Provides
    RxPagedListBuilder<Integer, Issue> getRxPagedListBuilder(@Named("IssueDataSource") DataSource.Factory<Integer, Issue>  dataSourceFactory,
                                             @Named("IssuePagedListConfig") PagedList.Config pagedListConfig) {
        return new RxPagedListBuilder<>(dataSourceFactory, pagedListConfig);
    }

}
