package shankhadeepghoshal.org.countrieslistapp.DI.countrymodule;

import android.arch.persistence.room.RoomDatabase;

import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import shankhadeepghoshal.org.countrieslistapp.DI.scope.ScopePerActivity;
import shankhadeepghoshal.org.countrieslistapp.mvp.view.MainView;
import shankhadeepghoshal.org.countrieslistapp.services.localdatabase.CountriesLocalDb;
import shankhadeepghoshal.org.countrieslistapp.services.rest.IRestServiceDataFetcher;

@Module
public class CountryModule {
    private final MainView mainViewReference;

    public CountryModule(MainView mainViewReference) {
        this.mainViewReference = mainViewReference;
    }

    @ScopePerActivity
    @Provides
    public IRestServiceDataFetcher provideDataFetcher(Retrofit retrofit) {
        return retrofit.create(IRestServiceDataFetcher.class);
    }

    @ScopePerActivity
    @Provides
    public CountriesLocalDb provideCountriesLocalDb(RoomDatabase.Builder<CountriesLocalDb> databaseBuilder) {
        return databaseBuilder.build();
    }

    @ScopePerActivity
    @Provides
    public Picasso providePicasso(Picasso.Builder picassoBuilder) {
        return picassoBuilder.build();
    }

    @ScopePerActivity
    @Provides
    public MainView provideMainView() {
        return this.mainViewReference;
    }
}