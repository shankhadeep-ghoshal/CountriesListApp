package shankhadeepghoshal.org.countrieslistapp.mvp.view;


import java.util.List;

import shankhadeepghoshal.org.countrieslistapp.mvp.models.entities.CountriesFullEntity;

public interface CountriesListView extends BaseView {
    void onLoadCountriesDataFull(List<CountriesFullEntity> countriesFullData);

}
