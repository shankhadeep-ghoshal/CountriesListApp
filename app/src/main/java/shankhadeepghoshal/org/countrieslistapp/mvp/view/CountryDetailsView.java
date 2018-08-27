package shankhadeepghoshal.org.countrieslistapp.mvp.view;

import shankhadeepghoshal.org.countrieslistapp.mvp.models.entities.CountriesFullEntity;

public interface CountryDetailsView extends BaseView {
    void onLoadParticularCountryData(CountriesFullEntity countriesFullEntity);
}
