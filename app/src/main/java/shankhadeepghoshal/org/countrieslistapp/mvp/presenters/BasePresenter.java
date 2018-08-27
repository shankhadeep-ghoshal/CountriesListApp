package shankhadeepghoshal.org.countrieslistapp.mvp.presenters;


import android.util.Log;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import shankhadeepghoshal.org.countrieslistapp.mvp.view.BaseView;

public class BasePresenter<T extends BaseView> {
    @Inject
    T injectedView;

    public static final String TAG_BASE_PRESENTER = "BasePresenter";

    protected T getInjectedView() {
        return injectedView;
    }

    protected <V> void subscribeToObserver(Flowable<V> flowable, FlowableSubscriber<V> flowableSubscriber) {
        flowable
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(subscription -> Log.d(TAG_BASE_PRESENTER,"Subscribed"))
                .doAfterNext(v -> Log.d(TAG_BASE_PRESENTER,"onNext() completed"))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(flowableSubscriber);
    }
}