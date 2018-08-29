package shankhadeepghoshal.org.countrieslistapp.mvp.models.repository.countrieslist;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.entities.CountriesFullEntity;
import shankhadeepghoshal.org.countrieslistapp.services.rest.IRestServiceDataFetcher;

public class CountriesListApiRepository {
    private final IRestServiceDataFetcher restServiceDataFetcher; // Retrofit interface to get data from API

    @Inject
    public CountriesListApiRepository(IRestServiceDataFetcher restServiceDataFetcher) {
        this.restServiceDataFetcher = restServiceDataFetcher;
    }

    public Maybe<List<CountriesFullEntity>> getCountriesFromApi() {
        return restServiceDataFetcher
                .getListOfCountriesData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
