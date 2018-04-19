# RxAndroid

## Android Introduction To Reactive Programming – RxJava, RxAndroid Form basic.

### Introduction To Reactive Programming – RxJava, RxAndroid.


##### What is Reactive Programming :-
  Reactive Programming is basically event-based asynchronous programming.In simple words, 
  In Rx programming data flows emitted by one component and the underlying structure provided by 
  the Rx libraries will propagate those changes to another component those are registered to receive those data changes. 
  Long story short: Rx is made up of three key points.

     RX = OBSERVABLE + OBSERVER + SCHEDULERS
	 
	 
	 
 ## RxJava Basics: Observable, Observer :-
 
  - Observable: Observable is a data stream that do some work and emits data.

  - Observer: Observer is the counter part of Observable. It receives the data emitted by Observable.

  - Subscription: The bonding between Observable and Observer is called as Subscription. There can be multiple Observers subscribed to a single Observable.

  - Operator / Transformation: Operators modifies the data emitted by Observable before an observer receives them.

  - Schedulers: Schedulers decides the thread on which Observable should emit the data and on which Observer should receives the data i.e background thread, main thread etc.,
  
  
 ## Let's start with Reactive Programming In android studio.
  
  Adding Dependencies :-
  ```
 // RxJava
 implementation 'io.reactivex.rxjava2:rxjava:2.1.9'
 
 // RxAndroid
 implementation 'io.reactivex.rxjava2:rxandroid:2.0.1' 
```
 
 
 ## The Basic Steps :-
 
## Create an Observable that emits data :-	
```
 Observable<String> Data Observable = Observable.just("Android", "Ios", "Ipad", "mac", "pixel");
 ```

 
## Create an Observer that listen to Observable. :-
 
 
- onSubscribe(): Method will be called when an Observer subscribes to Observable.
- onNext(): This method will be called when Observable starts emitting the data.
- onError(): In case of any error, onError() method will be called.
 -onComplete(): When an Observable completes the emission of all the items, onComplete() will be called.
 
 
 ``` 
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
    
 
 Make Observer subscribe to Observable :-
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
				
			
			
  If you run the program, you can see the below output in your LogCat.
  
    onSubscribe
    DataItems: Android
    DataItems: Ios
    DataItems: Ipad
    DataItems: mac
    DataItems: pixel
    All items are emitted!
    ```
    
    Happy Hacking :)

