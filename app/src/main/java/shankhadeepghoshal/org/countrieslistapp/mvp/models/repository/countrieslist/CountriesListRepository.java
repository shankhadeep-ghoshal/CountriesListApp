package shankhadeepghoshal.org.countrieslistapp.mvp.models.repository.countrieslist;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.entities.CountriesFullEntity;

public class CountriesListRepository {
    private final CountriesListApiRepository countriesListApiRepository;
    private final CountriesListLocalDbRepository countriesListLocalDbRepository;

    @Inject
    public CountriesListRepository(CountriesListApiRepository countriesListApiRepository,
                                   CountriesListLocalDbRepository countriesListLocalDbRepository) {
        this.countriesListApiRepository = countriesListApiRepository;
        this.countriesListLocalDbRepository = countriesListLocalDbRepository;
    }

    public Flowable<List<CountriesFullEntity>> getCountries() {
        Flowable<List<CountriesFullEntity>> listOfCountriesData =
                this.countriesListLocalDbRepository.getCountriesFromLocalDb();
        if(listOfCountriesData == null) {
            listOfCountriesData = this.countriesListApiRepository.getCountriesFromApi();
            this.countriesListLocalDbRepository.updateLocalDb(listOfCountriesData);
        }
        return listOfCountriesData;
    }

    public Flowable<List<CountriesFullEntity>> updateCountriesList(@NonNull Boolean isInternetThere) {
        Flowable<List<CountriesFullEntity>> newCountriesFullData;
        if(isInternetThere) {
            newCountriesFullData = this.countriesListApiRepository.getCountriesFromApi();
            this.countriesListLocalDbRepository.updateLocalDb(newCountriesFullData);
        } else newCountriesFullData = this.countriesListLocalDbRepository.getCountriesFromLocalDb();
        return newCountriesFullData;
    }
}