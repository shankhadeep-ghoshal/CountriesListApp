package shankhadeepghoshal.org.countrieslistapp.mvp.presenter;

import org.reactivestreams.Subscription;

import javax.inject.Inject;

import io.reactivex.FlowableSubscriber;
import shankhadeepghoshal.org.countrieslistapp.mvp.entities.CountriesFullEntity;
import shankhadeepghoshal.org.countrieslistapp.mvp.view.MainView;
import shankhadeepghoshal.org.countrieslistapp.services.localdatabase.CountriesLocalDbDAO;
import shankhadeepghoshal.org.countrieslistapp.services.rest.IRestServiceDataFetcher;

public class MainPresenter extends BasePresenter<MainView> implements FlowableSubscriber<CountriesFullEntity> {

    @Inject IRestServiceDataFetcher countriesWSApi;
    @Inject CountriesLocalDbDAO countriesLocalDbDAO;

    @Override
    public void onSubscribe(Subscription s) {
        // TODO: Dunno what to do here...
    }

    @Override
    public void onNext(CountriesFullEntity countriesFullEntity) {
        getInjectedView().onLoadCountriesDataSingle(countriesFullEntity);
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