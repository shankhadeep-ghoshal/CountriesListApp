package shankhadeepghoshal.org.countrieslistapp.mvp.presenter;

import android.support.annotation.NonNull;

import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import shankhadeepghoshal.org.countrieslistapp.mvp.entities.CountriesFullEntity;
import shankhadeepghoshal.org.countrieslistapp.mvp.view.CountriesListView;

public class CountriesListPresenter extends BasePresenter<CountriesListView> implements FlowableSubscriber<List<CountriesFullEntity>> {

    @Inject
    public CountriesListPresenter() {super();}

    public void getCountries() {
        Flowable<List<CountriesFullEntity>> listOfCountriesData = super.countriesLocalDb.getCountriesLocalDbDAO().getCountriesList();
        if(listOfCountriesData == null) {
            listOfCountriesData = super.countriesWSApi.getListOfCountriesData();
            super.countriesLocalDb
                    .getCountriesLocalDbDAO()
                    .insertAllCountries(listOfCountriesData.blockingSingle());
        }
        subscribeToObserver(listOfCountriesData, this);
    }

    public void updateCountriesList(@NonNull Boolean isInternetThere) {
        Flowable<List<CountriesFullEntity>> newCountriesFullData;
        if(isInternetThere) {
            newCountriesFullData = super.countriesWSApi.getListOfCountriesData();
            super.countriesLocalDb.getCountriesLocalDbDAO().insertAllCountries(newCountriesFullData.blockingSingle());
        } else newCountriesFullData = super.countriesLocalDb.getCountriesLocalDbDAO().getCountriesList();
        subscribeToObserver(newCountriesFullData,this);
    }

    @Override
    public void onSubscribe(Subscription s) {
        // TODO: Dunno what to do here...
    }

    @Override
    public void onNext(List<CountriesFullEntity> countriesFullEntities) {
        getInjectedView().onLoadCountriesDataFull(countriesFullEntities);
    }

    @Override
    public void onError(Throwable t) {
        getInjectedView().onErrorEncountered(t.getMessage());
    }

    @Override
    public void onComplete() {
        // TODO : Do something here, dunno what...
    }
}