package shankhadeepghoshal.org.countrieslistapp.mvp.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.entities.CountriesFullEntity;
import shankhadeepghoshal.org.countrieslistapp.mvp.view.CountryDetailsView;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.repository.countrydetails.CountryDetailsRepository;

public class CountryDetailsPresenter extends BasePresenter<CountryDetailsView> implements
        MaybeObserver<CountriesFullEntity> {

    private final CountryDetailsRepository countryDetailsRepository;

    private Disposable disposable;

    @Inject
    public CountryDetailsPresenter(CountryDetailsRepository countryDetailsRepository) {
        this.countryDetailsRepository = countryDetailsRepository;
    }

    public void getParticularCountry(@NonNull String countryName, @NonNull Boolean isInternetPresent) {
        Maybe<CountriesFullEntity> countriesFullEntityFlowable =
                this.countryDetailsRepository.getParticularCountry(countryName,isInternetPresent);
        this.subscribeToObserver(countriesFullEntityFlowable,this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onSuccess(CountriesFullEntity countriesFullEntity) {
        getInjectedView().onLoadParticularCountryData(countriesFullEntity);
    }

    @Override
    public void onError(Throwable e) {
        getInjectedView().onErrorEncountered(e.getMessage());
    }

    @Override
    public void onComplete() {
        if(!this.disposable.isDisposed()) this.disposable.dispose();
    }

/*
    @Override
    public void onNext(CountriesFullEntity countriesFullEntity) {
        getInjectedView().onLoadParticularCountryData(countriesFullEntity);
        this.disposable.cancel();
    }

    @Override
    public void onError(Throwable e) {
        getInjectedView().onErrorEncountered(e.getMessage());
    }

    @Override
    public void onComplete() {
        if(this.disposable!=null) disposable.cancel();
    }

    @Override
    public void onSubscribe(Subscription d) {
        // TODO: Lets think of what to do
        this.disposable = d;
        d.request(Long.MAX_VALUE);
    }
*/
}