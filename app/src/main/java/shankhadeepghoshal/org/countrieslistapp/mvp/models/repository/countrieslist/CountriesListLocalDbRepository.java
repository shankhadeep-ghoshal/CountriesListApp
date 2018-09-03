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
import shankhadeepghoshal.org.countrieslistapp.mvp.models.repository.LocalDbRepository;
import shankhadeepghoshal.org.countrieslistapp.services.localdatabase.CountriesLocalDb;

public class CountriesListLocalDbRepository {
    private final LocalDbRepository countriesLocalDb;

    @Inject
    CountriesListLocalDbRepository(LocalDbRepository countriesLocalDb) {
        this.countriesLocalDb = countriesLocalDb;
    }

    Maybe<List<CountriesFullEntity>> getCountriesFromLocalDb() {
        return this.countriesLocalDb.getCountriesFromLocalDb();
    }

    void updateLocalDbCountriesList(List<CountriesFullEntity> updatedCountriesList) {
        countriesLocalDb.insertAllCountries(updatedCountriesList);
    }
}