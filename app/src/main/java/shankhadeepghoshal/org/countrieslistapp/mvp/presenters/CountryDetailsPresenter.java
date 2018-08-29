package shankhadeepghoshal.org.countrieslistapp.mvp.presenters;

import android.support.annotation.NonNull;

import org.reactivestreams.Subscription;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.entities.CountriesFullEntity;
import shankhadeepghoshal.org.countrieslistapp.mvp.view.CountryDetailsView;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.repository.countrydetails.CountryDetailsRepository;

public class CountryDetailsPresenter extends BasePresenter<CountryDetailsView> implements FlowableSubscriber<CountriesFullEntity> {

    private final CountryDetailsRepository countryDetailsRepository;

    private Subscription subscription;

    @Inject
    public CountryDetailsPresenter(CountryDetailsRepository countryDetailsRepository) {
        this.countryDetailsRepository = countryDetailsRepository;
    }

    public void getParticularCountry(@NonNull String countryName, @NonNull Boolean isInternetPresent) {
        Flowable<CountriesFullEntity> countriesFullEntityFlowable =
                this.countryDetailsRepository.getParticularCountry(countryName,isInternetPresent);
        this.subscribeToObserver(countriesFullEntityFlowable,this);
    }

    @Override
    public void onNext(CountriesFullEntity countriesFullEntity) {
        getInjectedView().onLoadParticularCountryData(countriesFullEntity);
        this.subscription.cancel();
    }

    @Override
    public void onError(Throwable e) {
        getInjectedView().onErrorEncountered(e.getMessage());
    }

    @Override
    public void onComplete() {
        if(this.subscription!=null) subscription.cancel();
    }

    @Override
    public void onSubscribe(Subscription d) {
        // TODO: Lets think of what to do
        this.subscription = d;
        d.request(Long.MAX_VALUE);
    }

}