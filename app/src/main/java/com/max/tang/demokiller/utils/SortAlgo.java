package com.max.tang.demokiller.utils;

import com.max.tang.demokiller.view.SortView;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by zhihuitang on 2016-11-29.
 */

public class SortAlgo {
    static public void bubbleSort(final SortView view, final List<Integer> data) {
        /*
        for( int i = 0; i < data.size(); i++ ){
            for (int j = i+1; j < data.size(); j++) {
                if(data.get(i) > data.get(j)) {
                    int tmp = data.get(i);
                    data.set(i, data.get(j));
                    data.set(j, tmp);
                    view.updateUI(data);
                }

            }
        }
        */

        Observable.range(0, data.size())
            .concatMap(new Func1<Integer, Observable<Integer>>() {
                @Override public Observable<Integer> call(Integer i) {
                    return Observable.range(0, data.size()- i );
                }
            })
            .zipWith(Observable.interval(50, TimeUnit.MILLISECONDS), new Func2<Integer, Long, Integer>() {
                @Override public Integer call(Integer i, Long x2) {
                    return i;
                }
            })
            .map(new Func1<Integer, Integer>() {
                @Override public Integer call(Integer j) {
                    if( j+1 < data.size() && data.get(j) > data.get(j+1)) {
                        int tmp = data.get(j);
                        data.set(j, data.get(j+1));
                        data.set(j+1, tmp);
                    }
                    return j;
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<Integer>() {
                @Override public void onCompleted() {
                    view.finish("Bubble Sort");
                }

                @Override public void onError(Throwable e) {
                    view.finish("Bubble Sort failed");
                    e.printStackTrace();
                }

                @Override public void onNext(Integer integer) {
                    view.updateUI(data);

                }
            });
    }
}
