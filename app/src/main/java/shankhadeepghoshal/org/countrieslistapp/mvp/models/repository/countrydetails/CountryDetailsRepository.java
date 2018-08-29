package shankhadeepghoshal.org.countrieslistapp.mvp.models.repository.countrydetails;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Maybe;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.entities.CountriesFullEntity;
import shankhadeepghoshal.org.countrieslistapp.services.localdatabase.CountriesLocalDb;
import shankhadeepghoshal.org.countrieslistapp.services.rest.IRestServiceDataFetcher;

public class CountryDetailsRepository {
    private final IRestServiceDataFetcher countriesWSApi;
    private final CountriesLocalDb countriesLocalDb;

    @Inject
    public CountryDetailsRepository(IRestServiceDataFetcher countriesWSApi, CountriesLocalDb countriesLocalDb) {
        this.countriesWSApi = countriesWSApi;
        this.countriesLocalDb = countriesLocalDb;
    }

    public Maybe<CountriesFullEntity> getParticularCountry(@NonNull String countryName, @NonNull
            Boolean isInternetPresent) {
        Maybe<CountriesFullEntity> countriesFullEntityFlowable;
        if(isInternetPresent){
            countriesFullEntityFlowable = this.countriesWSApi
                    .getParticularCountry(countryName)
                    .doOnSuccess(countriesFullEntity -> countriesLocalDb.getCountriesLocalDbDAO().insertSingleCountry(countriesFullEntity));
        } else countriesFullEntityFlowable = this.countriesLocalDb
                    .getCountriesLocalDbDAO()
                    .getCountryByName(countryName);
        return countriesFullEntityFlowable;
    }
}