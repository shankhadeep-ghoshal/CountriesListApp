package shankhadeepghoshal.org.countrieslistapp.DI.appcomponent;

import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;
import shankhadeepghoshal.org.countrieslistapp.DI.appmodules.AppModules;
import shankhadeepghoshal.org.countrieslistapp.services.localdatabase.CountriesLocalDb;

@Singleton
@Component(modules = AppModules.class)
public interface AppComponents {
    Retrofit supplyRetrofit();
    Context supplyContext();
    Picasso.Builder supplyPicassoBuilder();
    RoomDatabase.Builder<CountriesLocalDb> supplyCountriesLocalDbBuilder();
   /* void inject(CountriesListPresenter countriesListPresenter);
    void inject(CountryDetailsPresenter countryDetailsPresenter);*/
}