package shankhadeepghoshal.org.countrieslistapp.mvp.view;


import java.util.List;

import shankhadeepghoshal.org.countrieslistapp.mvp.entities.CountriesFullEntity;

public interface MainView extends BaseView {
    void onLoadCountriesDataFull(List<CountriesFullEntity> countriesFullData);
    void onLoadCountriesDataSingle(CountriesFullEntity countriesFullEntity);
    void onErrorEncountered(String errorMessage);
}
