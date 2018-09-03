package shankhadeepghoshal.org.countrieslistapp.mvp.models.repository.countrieslist;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.entities.CountriesFullEntity;

public class CountriesListRepository {
    private final CountriesListApiRepository countriesListApiRepository;
    private final CountriesListLocalDbRepository countriesListLocalDbRepository;
    private static final String TAG_CountriesListRepository = "CountriesListRepository";

    @Inject
    public CountriesListRepository(CountriesListApiRepository countriesListApiRepository,
                                   CountriesListLocalDbRepository countriesListLocalDbRepository) {
        this.countriesListApiRepository = countriesListApiRepository;
        this.countriesListLocalDbRepository = countriesListLocalDbRepository;
    }

    public Maybe<List<CountriesFullEntity>> getCountries() {
        return this.countriesListApiRepository
                .getCountriesFromApi()
                .doOnSuccess(this.countriesListLocalDbRepository::updateLocalDbCountriesList)
                .doOnError(throwable -> {
                    Log.d(TAG_CountriesListRepository,throwable.getMessage());
                    throwable.printStackTrace(); })
                .onErrorResumeNext(this.countriesListLocalDbRepository.getCountriesFromLocalDb());
    }

    public Maybe<List<CountriesFullEntity>> updateCountriesList(@NonNull Boolean isInternetThere) {
        Maybe<List<CountriesFullEntity>> newCountriesFullData;
        if(isInternetThere) {
            newCountriesFullData = this.countriesListApiRepository
                    .getCountriesFromApi()
            .doOnSuccess(this.countriesListLocalDbRepository::updateLocalDbCountriesList);
        } else
            newCountriesFullData = this.countriesListLocalDbRepository.getCountriesFromLocalDb();
        return newCountriesFullData;
    }
}