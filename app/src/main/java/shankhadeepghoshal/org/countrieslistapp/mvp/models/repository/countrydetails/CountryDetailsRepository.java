package shankhadeepghoshal.org.countrieslistapp.mvp.models.repository.countrydetails;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Maybe;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.entities.CountriesFullEntity;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.repository.ApiRepository;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.repository.LocalDbRepository;

public class CountryDetailsRepository {
    private final ApiRepository countriesWSApi;
    private final LocalDbRepository countriesLocalDb;

    @Inject
    public CountryDetailsRepository(ApiRepository countriesWSApi, LocalDbRepository countriesLocalDb) {
        this.countriesWSApi = countriesWSApi;
        this.countriesLocalDb = countriesLocalDb;
    }

    public Maybe<CountriesFullEntity> getParticularCountry(@NonNull String countryName, @NonNull
            Boolean isInternetPresent) {
        Maybe<CountriesFullEntity> countriesFullEntityFlowable;
        if(isInternetPresent){
            countriesFullEntityFlowable = this.countriesWSApi
                    .getParticularCountry(countryName)
                    .doOnSuccess(countriesLocalDb::insertSingleCountry);
        } else countriesFullEntityFlowable = this.countriesLocalDb
                    .getCountryByName(countryName);
        return countriesFullEntityFlowable;
    }
}