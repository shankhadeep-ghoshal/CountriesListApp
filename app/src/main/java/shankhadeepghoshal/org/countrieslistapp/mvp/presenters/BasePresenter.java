package shankhadeepghoshal.org.countrieslistapp.mvp.presenters;


import android.util.Log;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import shankhadeepghoshal.org.countrieslistapp.mvp.view.BaseView;

public class BasePresenter<T extends BaseView> {
    @Inject
    T injectedView;

    private static final String TAG_BASE_PRESENTER = "BasePresenter";

    T getInjectedView() {
        return injectedView;
    }

    <V> void subscribeToObserver(Maybe<V> flowable, MaybeObserver<V> maybeObserver) {
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription -> Log.d(TAG_BASE_PRESENTER,"Subscribed"))
                .doOnSuccess(v -> Log.d(TAG_BASE_PRESENTER,"onNext() completed"))
                .subscribe(maybeObserver);
    }
}