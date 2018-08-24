package shankhadeepghoshal.org.countrieslistapp.mvp.presenter;

import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import shankhadeepghoshal.org.countrieslistapp.mvp.entities.CountriesFullEntity;
import shankhadeepghoshal.org.countrieslistapp.mvp.view.MainView;
import shankhadeepghoshal.org.countrieslistapp.services.localdatabase.CountriesLocalDbDAO;
import shankhadeepghoshal.org.countrieslistapp.services.rest.IRestServiceDataFetcher;

public class MainPresenter extends BasePresenter<MainView> implements FlowableSubscriber<List<CountriesFullEntity>> {

    @Inject IRestServiceDataFetcher countriesWSApi;
    @Inject CountriesLocalDbDAO countriesLocalDbDAO;

    @Inject
    public MainPresenter() {}

    public void getCountries() {
        Flowable<List<CountriesFullEntity>> listOfCountriesData = countriesLocalDbDAO.getCountriesList();
        if(listOfCountriesData == null) {
            listOfCountriesData = countriesWSApi.getListOfCountriesData();
            countriesLocalDbDAO.insertAllCountries(listOfCountriesData.blockingSingle());
        }
        subscribeToObserver(listOfCountriesData, this);
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