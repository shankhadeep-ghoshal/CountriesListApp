package shankhadeepghoshal.org.countrieslistapp.mvp.presenter;


import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import shankhadeepghoshal.org.countrieslistapp.mvp.view.BaseView;
import shankhadeepghoshal.org.countrieslistapp.services.localdatabase.CountriesLocalDb;
import shankhadeepghoshal.org.countrieslistapp.services.rest.IRestServiceDataFetcher;

public class BasePresenter<T extends BaseView> {
    @Inject protected T injectedView;
    @Inject
    protected IRestServiceDataFetcher countriesWSApi;
    @Inject
    protected CountriesLocalDb countriesLocalDb;

    public BasePresenter() {}

    public T getInjectedView() {
        return injectedView;
    }

    protected <V> void subscribeToObserver(Flowable<V> flowData, FlowableSubscriber<V> flowSubscriber) {
        flowData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(flowSubscriber);
    }
}