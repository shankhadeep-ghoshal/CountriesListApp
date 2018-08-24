package shankhadeepghoshal.org.countrieslistapp.mvp.view;

import shankhadeepghoshal.org.countrieslistapp.mvp.entities.CountriesFullEntity;

public interface CountryDetailsView extends BaseView {
    void onLoadParticularCountryData(CountriesFullEntity countriesFullEntity);
}
