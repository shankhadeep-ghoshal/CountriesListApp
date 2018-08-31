package shankhadeepghoshal.org.countrieslistapp.DI.countrymodule;

import android.arch.persistence.room.RoomDatabase;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import shankhadeepghoshal.org.countrieslistapp.DI.scope.ScopePerActivity;
import shankhadeepghoshal.org.countrieslistapp.mvp.view.CountriesListView;
import shankhadeepghoshal.org.countrieslistapp.mvp.view.CountryDetailsView;
import shankhadeepghoshal.org.countrieslistapp.services.localdatabase.CountriesLocalDb;
import shankhadeepghoshal.org.countrieslistapp.services.rest.IRestServiceDataFetcher;
import shankhadeepghoshal.org.countrieslistapp.ui.countrieslist.CountriesListFrag;
import shankhadeepghoshal.org.countrieslistapp.ui.countrydetail.CountryDetailsFrag;

@Module
public class CountryModule {
    private CountriesListFrag countriesListViewReference;
    private CountryDetailsFrag countryDetailsViewReference;

    public CountryModule(CountriesListFrag countriesListViewReference) {
        this.countriesListViewReference = countriesListViewReference;
    }

    public CountryModule(CountryDetailsFrag countryDetailsViewReference) {
        this.countryDetailsViewReference = countryDetailsViewReference;
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
    CountriesListView provideCountriesListView() {
        return this.countriesListViewReference;
    }

    @ScopePerActivity
    @Provides
    CountryDetailsView provideCountriesDetailsView() {
        return this.countryDetailsViewReference;
    }
}