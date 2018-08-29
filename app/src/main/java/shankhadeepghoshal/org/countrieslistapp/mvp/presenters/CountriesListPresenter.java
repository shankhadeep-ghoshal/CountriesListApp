package shankhadeepghoshal.org.countrieslistapp.mvp.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.entities.CountriesFullEntity;
import shankhadeepghoshal.org.countrieslistapp.mvp.view.CountriesListView;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.repository.countrieslist.CountriesListRepository;

public class CountriesListPresenter extends BasePresenter<CountriesListView>
        implements MaybeObserver<List<CountriesFullEntity>> {

    private static final String TAG_ListPresenter = "CountriesListPresenter";

    private final CountriesListRepository countriesListRepository;

    private Disposable disposable;

    @Inject
    public CountriesListPresenter(CountriesListRepository countriesListRepository) {
        this.countriesListRepository = countriesListRepository;
    }

    public void getCountries() {
        Maybe<List<CountriesFullEntity>> listOfCountriesData = this.countriesListRepository
                .getCountries();
        subscribeToObserver(listOfCountriesData, this);
    }

    public void updateCountriesList(@NonNull Boolean isInternetThere) {
        Maybe<List<CountriesFullEntity>> newCountriesFullData = this.countriesListRepository
                .updateCountriesList(isInternetThere);
        subscribeToObserver(newCountriesFullData,this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onSuccess(List<CountriesFullEntity> countriesFullEntities) {
        Log.d(TAG_ListPresenter,"Executing onNext()");
        getInjectedView().onLoadCountriesDataFull(countriesFullEntities);
    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG_ListPresenter,"Executing onError()",e);
        getInjectedView().onErrorEncountered(e.getMessage());
    }

    @Override
    public void onComplete() {
        if(!disposable.isDisposed()) disposable.dispose();
    }
}