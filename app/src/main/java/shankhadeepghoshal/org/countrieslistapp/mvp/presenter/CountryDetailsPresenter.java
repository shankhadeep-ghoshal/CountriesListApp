package shankhadeepghoshal.org.countrieslistapp.mvp.presenter;

import android.support.annotation.NonNull;

import org.reactivestreams.Subscription;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import shankhadeepghoshal.org.countrieslistapp.mvp.entities.CountriesFullEntity;
import shankhadeepghoshal.org.countrieslistapp.mvp.view.CountryDetailsView;

public class CountryDetailsPresenter extends BasePresenter<CountryDetailsView> implements FlowableSubscriber<CountriesFullEntity> {
    @Inject
    public CountryDetailsPresenter() {super();}

    public void getParticularCountry(@NonNull String countryName,@NonNull Boolean isInternetPresent) {
        Flowable<CountriesFullEntity> countriesFullEntityFlowable;
        if(isInternetPresent){
            countriesFullEntityFlowable = super.countriesWSApi.getParticularCountry(countryName);
            super.countriesLocalDb.getCountriesLocalDbDAO().insertSingleCountry(countriesFullEntityFlowable.blockingSingle());
        } else
            countriesFullEntityFlowable = super.countriesLocalDb.getCountriesLocalDbDAO().getCountryByName(countryName);
        super.subscribeToObserver(countriesFullEntityFlowable,this);
    }

    @Override
    public void onSubscribe(Subscription s) {
        // TODO : Don't know what to do...
    }

    @Override
    public void onNext(CountriesFullEntity countriesFullEntity) {
        getInjectedView().onLoadParticularCountryData(countriesFullEntity);
    }

    @Override
    public void onError(Throwable t) {
        getInjectedView().onErrorEncountered(t.getMessage());
    }

    @Override
    public void onComplete() {
        // TODO: Some kind of waiting sign coming up...
    }
}
