package shankhadeepghoshal.org.countrieslistapp.mvp.models.entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import shankhadeepghoshal.org.countrieslistapp.services.rest.IRestServiceDataFetcher;

import static org.junit.Assert.assertEquals;

public class CountriesFullEntityTest {
    private String data;
    private OkHttpClient okHttpClient;
    private Retrofit rf;

    @Before
    public  void setUp() throws Exception {
        data = new OkHttpClient()
                .newCall(new Request.Builder().url("https://restcountries.eu/rest/v2/all").build())
                .execute()
                .body()
                .string();
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        rf = new Retrofit.Builder()
                .baseUrl("https://restcountries.eu/rest/v2/")
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

/*
    @Test
    public void testJSONParsing() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<CountriesFullEntity> listCountries = mapper.readValue(data, new TypeReference<List<CountriesFullEntity>>() {});
            CountriesFullEntity testCountry = listCountries
                    .stream()
                    .filter(country -> country.getName().equals("Afghanistan"))
                    .limit(1)
                    .findFirst()
                    .get();

            assertEquals(testCountry.getName(),"Afghanistan");
            assertEquals(testCountry.getFlag(),"https://restcountries.eu/data/afg.svg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

    @Test
    public void testIRestService() {
        IRestServiceDataFetcher dataFetcher = rf.create(IRestServiceDataFetcher.class);
        Flowable<List<CountriesFullEntity>> responseData = dataFetcher.getListOfCountriesData();
        responseData.toObservable().subscribeOn(Schedulers.io())
                .doOnSubscribe(subscription -> System.out.println("Subscribed"))
                .doOnNext(countriesFullEntities -> {
                    System.out.println(countriesFullEntities.get(0).toString());
                    assertEquals(countriesFullEntities.get(0).getName(),"Afghanistan");
                }).blockingSubscribe(new Observer<List<CountriesFullEntity>>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("On Subscribe");
                //d.dispose();
            }

            @Override
            public void onNext(List<CountriesFullEntity> countriesFullEntities) {
                System.out.println(countriesFullEntities.get(0).toString());
                assertEquals(countriesFullEntities.get(0).getName(),"Afghanistan");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("error occurred");
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        });
        /*CountriesFullEntity afghanistan = responseData.blockingFirst().get(0);
        System.out.println(afghanistan.toString());
        assertEquals(afghanistan.getName(),"Afghanistan");*/
    }

    @After
    public void tearDown() throws Exception {
        data = null;
    }

    /**
     * This method is there in case the JDK used is 1.7 or less
     * @param listOfCountries De-serialized list of countries
     * @param filter Country name filter
     * @return CountriesFullEntity object that matches the filter parameter
     */
    private static CountriesFullEntity getCountry(List<CountriesFullEntity> listOfCountries, String filter) {
        for(CountriesFullEntity country : listOfCountries) {
            if(country.getName().equals(filter)) return country;
        }
        return new CountriesFullEntity();
    }
}