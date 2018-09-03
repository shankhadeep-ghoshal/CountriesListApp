package shankhadeepghoshal.org.countrieslistapp.mvp.models.repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.entities.CountriesFullEntity;
import shankhadeepghoshal.org.countrieslistapp.services.rest.IRestServiceDataFetcher;

public class ApiRepository {
    private final IRestServiceDataFetcher restServiceDataFetcher;

    @Inject
    public ApiRepository(IRestServiceDataFetcher restServiceDataFetcher) {
        this.restServiceDataFetcher = restServiceDataFetcher;
    }

    public Maybe<List<CountriesFullEntity>> getCountriesFromApi() {
        return restServiceDataFetcher.getListOfCountriesData();
    }

    public Maybe<CountriesFullEntity> getParticularCountry(String countryName) {
        return this.restServiceDataFetcher
                .getParticularCountry(countryName)
                .map(countriesFullEntities -> countriesFullEntities.get(0));
    }
}