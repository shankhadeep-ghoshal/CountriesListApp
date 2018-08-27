package shankhadeepghoshal.org.countrieslistapp.mvp.models.repository.countrieslist;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.entities.CountriesFullEntity;
import shankhadeepghoshal.org.countrieslistapp.services.localdatabase.CountriesLocalDb;

public class CountriesListLocalDbRepository {
    private final CountriesLocalDb countriesLocalDb;

    @Inject
    public CountriesListLocalDbRepository(CountriesLocalDb countriesLocalDb) {
        this.countriesLocalDb = countriesLocalDb;
    }

    public Flowable<List<CountriesFullEntity>> getCountriesFromLocalDb() {
        return this.countriesLocalDb
                .getCountriesLocalDbDAO()
                .getCountriesList();
    }

    public void updateLocalDb(Flowable<List<CountriesFullEntity>> updatedCountriesList) {
        countriesLocalDb
                .getCountriesLocalDbDAO()
                .insertAllCountries(updatedCountriesList.blockingSingle());
    }
}