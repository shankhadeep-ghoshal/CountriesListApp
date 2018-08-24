package shankhadeepghoshal.org.countrieslistapp.services.rest;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import shankhadeepghoshal.org.countrieslistapp.mvp.entities.CountriesFullEntity;

public interface IRestServiceDataFetcher {
    @GET("/all")
    Flowable<List<CountriesFullEntity>> getListOfCountriesData();
}