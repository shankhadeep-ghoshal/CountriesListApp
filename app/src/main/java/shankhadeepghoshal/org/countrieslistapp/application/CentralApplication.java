package shankhadeepghoshal.org.countrieslistapp.application;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import shankhadeepghoshal.org.countrieslistapp.DI.appcomponent.AppComponents;
import shankhadeepghoshal.org.countrieslistapp.DI.appcomponent.DaggerAppComponents;
import shankhadeepghoshal.org.countrieslistapp.DI.appmodules.AppModules;


public class CentralApplication extends Application {

    public static final String TAG_APPLICATION = "CentralApplication";

    static {
        System.loadLibrary("native-lib");
    }

    private native String getBaseUrl();

    private AppComponents appComponents;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponents();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Log.d(TAG_APPLICATION,"Application onCreate() returned");
    }

    private void initAppComponents() {
        this.appComponents = DaggerAppComponents.builder()
                .appModules(new AppModules(getBaseUrl(),this))
                .build();
        Log.d(TAG_APPLICATION,"App dependencies graph created");
    }

    public AppComponents getAppComponents() {
        return appComponents;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}