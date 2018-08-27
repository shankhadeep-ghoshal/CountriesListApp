package shankhadeepghoshal.org.countrieslistapp.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import shankhadeepghoshal.org.countrieslistapp.mvp.models.entities.CountriesFullEntity;

public class Frag2FragCommViewModel extends AndroidViewModel {
    private final MutableLiveData<CountriesFullEntity> singleCountryData = new MutableLiveData<>();
    private final MutableLiveData<List<CountriesFullEntity>> listOfCountriesData = new MutableLiveData<>();


    public Frag2FragCommViewModel(@NonNull Application application) {
        super(application);
    }

    public void setSingleCountryEntry(CountriesFullEntity countriesFullEntity) {
        singleCountryData.setValue(countriesFullEntity);
    }

    public void setListOfCountriesData(List<CountriesFullEntity> listOfCountriesData) {
        this.listOfCountriesData.setValue(listOfCountriesData);
    }

    public LiveData<CountriesFullEntity> getLiveDataSingleCountryData() {
        return this.singleCountryData;
    }

    public LiveData<List<CountriesFullEntity>> getLiveDataListOfCountriesData() {
        return this.listOfCountriesData;
    }
}