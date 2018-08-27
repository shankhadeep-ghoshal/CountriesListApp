package shankhadeepghoshal.org.countrieslistapp.mvp.models.repository.countrieslist;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.entities.CountriesFullEntity;
import shankhadeepghoshal.org.countrieslistapp.services.rest.IRestServiceDataFetcher;

public class CountriesListApiRepository {
    private final IRestServiceDataFetcher restServiceDataFetcher;

    @Inject
    public CountriesListApiRepository(IRestServiceDataFetcher restServiceDataFetcher) {
        this.restServiceDataFetcher = restServiceDataFetcher;
    }

    public Flowable<List<CountriesFullEntity>> getCountriesFromApi() {
        return restServiceDataFetcher.getListOfCountriesData();
    }
}
