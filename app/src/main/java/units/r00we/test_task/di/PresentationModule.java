package units.r00we.test_task.di;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import units.r00we.test_task.Constants;
import units.r00we.test_task.data.ApiRepository;
import units.r00we.test_task.ui.IssueListContract;
import units.r00we.test_task.data.entity.CommentsAdapter;
import units.r00we.test_task.ui.presenter.IssueListPresenter;
import units.r00we.test_task.utils.DateFormatter;

@Module
public class PresentationModule {

    private final Activity activity;

    public PresentationModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    Context getContext() {
        return activity;
    }

    @Provides
    Resources getResources(Context context) {
        return context.getResources();
    }

    @Provides
    LinearLayoutManager getLinearLayout(Context context) {
        return new LinearLayoutManager(context);
    }

    @Provides
    DividerItemDecoration getDividerItemDecoration(Context context, LinearLayoutManager linearLayoutManager) {
        return new DividerItemDecoration(context, linearLayoutManager.getOrientation());
    }

    @Provides
    CommentsAdapter getCommentsAdapter(ApiRepository apiRepository){
        return new CommentsAdapter(Constants.NEED_COLLAPSED_SIZE,Constants.SIZE_IF_COLLAPSED);
    }

    @Provides
    IssueListContract.Presenter getIssueListPresenter(CompositeDisposable compositeDisposable,
                                                      DateFormatter dateFormatter,
                                                      ApiRepository apiRepository) {
        return new IssueListPresenter(compositeDisposable, dateFormatter, apiRepository);
    }

    @Provides
    CompositeDisposable getCompositeDisposable() {
        return new CompositeDisposable();
    }

}
