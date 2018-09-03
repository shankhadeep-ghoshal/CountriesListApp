package shankhadeepghoshal.org.countrieslistapp.services.rest;

import java.util.List;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Path;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.entities.CountriesFullEntity;

public interface IRestServiceDataFetcher {
    @GET("all")
    Maybe<List<CountriesFullEntity>> getListOfCountriesData();

    @GET("name/{name}")
    Maybe<List<CountriesFullEntity>> getParticularCountry(@Path("name") String name);
}