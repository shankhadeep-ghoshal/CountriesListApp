package shankhadeepghoshal.org.countrieslistapp.mvp.models.repository.countrieslist;

import android.annotation.SuppressLint;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.entities.CountriesFullEntity;
import shankhadeepghoshal.org.countrieslistapp.services.localdatabase.CountriesLocalDb;

public class CountriesListLocalDbRepository {
    private final CountriesLocalDb countriesLocalDb;

    @Inject
    CountriesListLocalDbRepository(CountriesLocalDb countriesLocalDb) {
        this.countriesLocalDb = countriesLocalDb;
    }

    Maybe<List<CountriesFullEntity>> getCountriesFromLocalDb() {
        return this.countriesLocalDb
                .getCountriesLocalDbDAO()
                .getCountriesList()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io());
    }

    void updateLocalDb(List<CountriesFullEntity> updatedCountriesList) {
        countriesLocalDb.getCountriesLocalDbDAO().insertAllCountries(updatedCountriesList);
    }

    @SuppressLint("CheckResult")
    void updateLocalDb(Maybe<List<CountriesFullEntity>> updatedCountriesList) {
        updatedCountriesList
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnSuccess(this::updateLocalDb);
    }
}