package shankhadeepghoshal.org.countrieslistapp.DI.countrymodule;

import android.arch.persistence.room.RoomDatabase;

import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import shankhadeepghoshal.org.countrieslistapp.DI.scope.ScopePerActivity;
import shankhadeepghoshal.org.countrieslistapp.mvp.view.CountriesListView;
import shankhadeepghoshal.org.countrieslistapp.mvp.view.CountryDetailsView;
import shankhadeepghoshal.org.countrieslistapp.services.localdatabase.CountriesLocalDb;
import shankhadeepghoshal.org.countrieslistapp.services.rest.IRestServiceDataFetcher;

@Module
public class CountryModule {
    private final CountriesListView countriesListViewReference;
    private final CountryDetailsView countryDetailsViewReference;

    public CountryModule(CountriesListView countriesListViewReference) {
        this.countriesListViewReference = countriesListViewReference;
        this.countryDetailsViewReference = null;
    }

    public CountryModule(CountryDetailsView countryDetailsViewReference) {
        this.countryDetailsViewReference = countryDetailsViewReference;
        this.countriesListViewReference = null;
    }

    @ScopePerActivity
    @Provides
    IRestServiceDataFetcher provideDataFetcher(Retrofit retrofit) {
        return retrofit.create(IRestServiceDataFetcher.class);
    }

    @ScopePerActivity
    @Provides
    CountriesLocalDb provideCountriesLocalDb(RoomDatabase.Builder<CountriesLocalDb> databaseBuilder) {
        return databaseBuilder.build();
    }

    @ScopePerActivity
    @Provides
    Picasso providePicasso(Picasso.Builder picassoBuilder) {
        return picassoBuilder.build();
    }

    @ScopePerActivity
    @Provides
    CountriesListView provideCountriesListView() {
        return this.countriesListViewReference;
    }

    @ScopePerActivity
    @Provides
    CountryDetailsView provideCountriesDetailsView() {
        return this.countryDetailsViewReference;
    }
}