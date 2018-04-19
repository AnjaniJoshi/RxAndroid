package com.teemark.www.demoreactive;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Disposable disposable;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         *key ponits
         * Observable: Observable is a data stream that do some work and emits data.
         * Observer: Observer is the counter part of Observable. It receives the data emitted by Observable.
         * Subscription: The bonding between Observable and Observer is called as Subscription. There can be multiple Observers subscribed to a single Observable.
         * Operator / Transformation: Operators modifies the data emitted by Observable before an observer receives them.
         * Scdulers: Schedulers decides the thread on which Observable should emit the data and on which Observer should receives the data i.e background thread, main thread etc.,
         *
         * */


        // observable
        Observable<String> animalsObservable = getDataObservable();

        // observer
        Observer<String> animalsObserver = getDataObserver();

        // observer subscribing to observable
        animalsObservable
                //This tell the Observable to run the task on a background thread.
                .subscribeOn(Schedulers.io())
                //This tells the Observer to receive the data on android UI thread so that you can take any UI related actions.
                .observeOn(AndroidSchedulers.mainThread())
               //  filters the data by applying a conditional statement
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.toLowerCase().startsWith("A");
                    }
                })
                .subscribeWith(animalsObserver);

}


    private Observer<String> getDataObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                // Method will be called when an Observer subscribes to Observable.
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                // This method will be called when Observable starts emitting the data.
                Log.d(TAG, "DataItems: " + s);
            }

            @Override
            public void onError(Throwable e) {
                // In case of any error, onError() method will be called.
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                // When an Observable completes the emission of all the items, onComplete() will be called.
                Log.d(TAG, "All items are emitted!");
            }
        };

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Disposable is used and calling disposable.dispose() in onDestroy() will un-subscribe the Observer.
        // don't send events once the activity is destroyed.
        disposable.dispose();
    }


    private Observable<String> getDataObservable() {
        return Observable.just("Android", "Ios", "Ipad", "mac", "pixel");
    }
}
