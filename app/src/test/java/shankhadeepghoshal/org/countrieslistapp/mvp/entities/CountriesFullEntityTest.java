package shankhadeepghoshal.org.countrieslistapp.mvp.entities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import static org.junit.Assert.*;

public class CountriesFullEntityTest {
    private String data;

    @Before
    public  void setUp() throws Exception {
        data = new OkHttpClient()
                .newCall(new Request.Builder().url("https://restcountries.eu/rest/v2/all").build())
                .execute()
                .body()
                .string();
    }

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