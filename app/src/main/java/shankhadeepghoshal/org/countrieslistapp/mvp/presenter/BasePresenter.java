package shankhadeepghoshal.org.countrieslistapp.mvp.presenter;


import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import shankhadeepghoshal.org.countrieslistapp.mvp.view.BaseView;

public class BasePresenter<T extends BaseView> {
    @Inject protected T injectedView;

    public T getInjectedView() {
        return injectedView;
    }

    protected <V> void subscribeToObserver(Flowable<V> flowData, FlowableSubscriber<V> flowSubscriber) {
        flowData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(flowSubscriber);
    }
}