package shankhadeepghoshal.org.countrieslistapp.mvp.models.repository.countrieslist;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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
//        Flowable<List<CountriesFullEntity>> listOfCountriesData = this.countriesListApiRepository.getCountriesFromApi();
        /*if(listOfCountriesData == null) listOfCountriesData = this.countriesListLocalDbRepository.getCountriesFromLocalDb();
        else this.countriesListLocalDbRepository.updateLocalDb(listOfCountriesData);*/


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
        return this.countriesListApiRepository
                .getCountriesFromApi()
                .doOnSuccess(this.countriesListLocalDbRepository::updateLocalDb)
                .doOnError(throwable -> {
                    Log.d(TAG_CountriesListRepository,throwable.getMessage());
                    throwable.printStackTrace(); })
                .onErrorResumeNext(this.countriesListLocalDbRepository.getCountriesFromLocalDb());
/*
        return this.countriesListLocalDbRepository.getCountriesFromLocalDb().onErrorResumeNext
                (Maybe.create(emitter -> countriesListApiRepository.getCountriesFromApi().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).doOnSuccess
                        (countriesListLocalDbRepository::updateLocalDb)));
*/
    }

    public Maybe<List<CountriesFullEntity>> updateCountriesList(@NonNull Boolean isInternetThere) {
        Maybe<List<CountriesFullEntity>> newCountriesFullData;
        if(isInternetThere) {
            newCountriesFullData = this.countriesListApiRepository
                    .getCountriesFromApi();
                    this.countriesListLocalDbRepository.updateLocalDb(newCountriesFullData);
        } else newCountriesFullData = this.countriesListLocalDbRepository
                .getCountriesFromLocalDb();
        return newCountriesFullData;
    }
}