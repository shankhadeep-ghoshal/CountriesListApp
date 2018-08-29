package shankhadeepghoshal.org.countrieslistapp.mvp.models.repository.countrieslist;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.functions.Predicate;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.entities.CountriesFullEntity;

import static io.reactivex.Flowable.concatArray;
import static io.reactivex.Flowable.concatArrayEager;

public class CountriesListRepository {
    private final CountriesListApiRepository countriesListApiRepository;
    private final CountriesListLocalDbRepository countriesListLocalDbRepository;

    @Inject
    public CountriesListRepository(CountriesListApiRepository countriesListApiRepository,
                                   CountriesListLocalDbRepository countriesListLocalDbRepository) {
        this.countriesListApiRepository = countriesListApiRepository;
        this.countriesListLocalDbRepository = countriesListLocalDbRepository;
    }

    @SuppressWarnings("unchecked")
    public Flowable<List<CountriesFullEntity>> getCountries() {
//        Flowable<List<CountriesFullEntity>> listOfCountriesData = this.countriesListApiRepository.getCountriesFromApi();
        /*if(listOfCountriesData == null) listOfCountriesData = this.countriesListLocalDbRepository.getCountriesFromLocalDb();
        else this.countriesListLocalDbRepository.updateLocalDb(listOfCountriesData);*/

        return concatArray(this.countriesListLocalDbRepository
                .getCountriesFromLocalDb()
                .filter(countriesFullEntities -> !countriesFullEntities.isEmpty())
                .toFlowable(),
                this.countriesListApiRepository
                .getCountriesFromApi()
                .doOnNext(countriesListLocalDbRepository::updateLocalDb));
/*
        return this.countriesListLocalDbRepository
                .getCountriesFromLocalDb()
                .filter(countriesFullEntities -> !countriesFullEntities.isEmpty())
                .toSingle()
                .onErrorResumeNext(throwable ->
                        countriesListApiRepository
                        .getCountriesFromApi()
                        .doAfterNext(countriesListLocalDbRepository::updateLocalDb).singleOrError()
                )
                .toFlowable();
*/
                /*.flatMap(countriesFullEntities -> {
                    if(countriesFullEntities == null || countriesFullEntities.size()<1)
                       return this.countriesListApiRepository.getCountriesFromApi()
                                .doAfterNext(countriesListLocalDbRepository::updateLocalDb);
                    else return Flowable.just(countriesFullEntities);
                });*/
//        return listOfCountriesData;
    }

    public Flowable<List<CountriesFullEntity>> updateCountriesList(@NonNull Boolean isInternetThere) {
        Flowable<List<CountriesFullEntity>> newCountriesFullData;
        if(isInternetThere) {
            newCountriesFullData = this.countriesListApiRepository.getCountriesFromApi();
            this.countriesListLocalDbRepository.updateLocalDb(newCountriesFullData);
        } else newCountriesFullData = this.countriesListLocalDbRepository
                .getCountriesFromLocalDb()
                .toFlowable();
        return newCountriesFullData;
    }
}