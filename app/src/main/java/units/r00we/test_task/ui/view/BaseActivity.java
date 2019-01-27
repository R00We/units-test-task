package units.r00we.test_task.ui.view;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;

import units.r00we.test_task.App;
import units.r00we.test_task.di.ApplicationComponent;
import units.r00we.test_task.di.PresentationComponent;
import units.r00we.test_task.di.PresentationModule;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    private PresentationComponent presentationComponent;


    protected PresentationComponent getPresentationCompomemt() {
        if (presentationComponent == null) {
            presentationComponent = getApplicationComponent().newPresentationComponent(new PresentationModule(this));
        }
        return presentationComponent;
    }

    private ApplicationComponent getApplicationComponent() {
        return ((App)getApplication()).getApplicationModule();
    }
}
