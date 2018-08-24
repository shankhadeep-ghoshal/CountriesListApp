package shankhadeepghoshal.org.countrieslistapp.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import shankhadeepghoshal.org.countrieslistapp.mvp.entities.CountriesFullEntity;

public class Frag2FragCommViewModel extends ViewModel {
    private MutableLiveData<CountriesFullEntity> countryData = new MutableLiveData<>();

    public void setCountryEntry(CountriesFullEntity countriesFullEntity) {
        countryData.setValue(countriesFullEntity);
    }

    public LiveData<CountriesFullEntity> getLiveDataCountryData() {
        return countryData;
    }
}