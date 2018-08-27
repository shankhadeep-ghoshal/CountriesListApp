package shankhadeepghoshal.org.countrieslistapp.mvp.presenters;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import shankhadeepghoshal.org.countrieslistapp.services.rest.IRestServiceDataFetcher;

import static org.junit.Assert.*;

public class CountriesListPresenterTest {

    @Inject
    IRestServiceDataFetcher dataFetcher;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCountriesListPresenterNull() {
        assertNull(dataFetcher);
    }

    @Test
    public void getCountries() {
    }

    @Test
    public void updateCountriesList() {
    }
}