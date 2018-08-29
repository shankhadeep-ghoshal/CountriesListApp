package shankhadeepghoshal.org.countrieslistapp.mvp.models.repository.countrieslist;

import android.annotation.SuppressLint;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.entities.CountriesFullEntity;
import shankhadeepghoshal.org.countrieslistapp.services.localdatabase.CountriesLocalDb;

public class CountriesListLocalDbRepository {
    private final CountriesLocalDb countriesLocalDb;

    @Inject
    CountriesListLocalDbRepository(CountriesLocalDb countriesLocalDb) {
        this.countriesLocalDb = countriesLocalDb;
    }

    Single<List<CountriesFullEntity>> getCountriesFromLocalDb() {
        return this.countriesLocalDb
                .getCountriesLocalDbDAO()
                .getCountriesList()
                .filter(countriesFullEntities ->  !countriesFullEntities.isEmpty())
                .toSingle();
    }

    void updateLocalDb(List<CountriesFullEntity> updatedCountriesList) {
        Single.fromCallable(() -> {
            countriesLocalDb.getCountriesLocalDbDAO().insertAllCountries(updatedCountriesList);
            return updatedCountriesList;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe();
        /*countriesLocalDb
                .getCountriesLocalDbDAO()
                .insertAllCountries(updatedCountriesList);*/
    }

    @SuppressLint("CheckResult")
    void updateLocalDb(Flowable<List<CountriesFullEntity>> updatedCountriesList) {
        updatedCountriesList.subscribeOn(Schedulers.single())
                .subscribe(countriesFullEntities -> countriesLocalDb
                .getCountriesLocalDbDAO().insertAllCountries(countriesFullEntities));
    }
}