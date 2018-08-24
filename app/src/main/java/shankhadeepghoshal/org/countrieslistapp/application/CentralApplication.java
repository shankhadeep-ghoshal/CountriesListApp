package shankhadeepghoshal.org.countrieslistapp.application;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import shankhadeepghoshal.org.countrieslistapp.DI.appcomponent.AppComponents;
import shankhadeepghoshal.org.countrieslistapp.DI.appcomponent.DaggerAppComponents;
import shankhadeepghoshal.org.countrieslistapp.DI.appmodules.AppModules;


public class CentralApplication extends Application {

    static {
        System.loadLibrary("native-lib");
    }

    private native String getBaseUrl();

    private AppComponents appComponents;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponents();
    }

    private void initAppComponents() {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        this.appComponents = DaggerAppComponents.builder()
                .appModules(new AppModules(getBaseUrl(), this))
                .build();
    }

    public AppComponents getAppComponents() {
        return appComponents;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}