package shankhadeepghoshal.org.countrieslistapp.ui.countrieslist;

import java.util.List;

import shankhadeepghoshal.org.countrieslistapp.mvp.entities.CountriesFullEntity;

public interface GetDataFromHoldingActivity {
    void setDataToAdapter(List<CountriesFullEntity> countriesFullEntities);
    void setErrorMessage(String message);
}
