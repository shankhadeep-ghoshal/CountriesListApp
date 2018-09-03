package shankhadeepghoshal.org.countrieslistapp.mvp.models.repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.entities.CountriesFullEntity;
import shankhadeepghoshal.org.countrieslistapp.services.localdatabase.CountriesLocalDb;

public class LocalDbRepository {
    private final CountriesLocalDb countriesLocalDb;

    @Inject
    public LocalDbRepository(CountriesLocalDb countriesLocalDb) {
        this.countriesLocalDb = countriesLocalDb;
    }

    public Maybe<List<CountriesFullEntity>> getCountriesFromLocalDb() {
        return this.countriesLocalDb
                .getCountriesLocalDbDAO()
                .getCountriesList()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io());
    }

    public Maybe<CountriesFullEntity> getCountryByName(String countryName) {
        return this.countriesLocalDb.getCountriesLocalDbDAO()
                .getCountryByName(countryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void insertSingleCountry(CountriesFullEntity countriesFullEntity) {
        Maybe.create(emitter ->
                this.countriesLocalDb.getCountriesLocalDbDAO()
                        .insertSingleCountry(countriesFullEntity))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe();
    }

    public void insertAllCountries(List<CountriesFullEntity> countriesFullEntities) {
        Maybe.create(emitter ->
                this.countriesLocalDb.getCountriesLocalDbDAO()
                        .insertAllCountries(countriesFullEntities))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe();
    }
}