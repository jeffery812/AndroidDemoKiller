package com.max.tang.demokiller.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.service.DemoService;
import com.max.tang.demokiller.utils.log.Logger;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;

public class MessActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show();
            }
        });
    }

    public void startSortActivity(View view) {
        Logger.d("start sort activity");

        Intent intent = new Intent();
        intent.setAction("com.max");
        intent.setData(Uri.parse("type:bubble"));
        startActivity(intent);
    }


    public void call(View view) {
        String phoneNumber = "1234";
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    public void testRxAndroidJoin(View view) {
        //产生0,5,10,15,20数列
        System.out.print("test RxAndroid Join function");
        Observable<Long> observable1 = Observable.timer(0, 1000, TimeUnit.MILLISECONDS)
            .map(new Func1<Long, Long>() {
                @Override
                public Long call(Long aLong) {
                    return aLong * 5;
                }
            }).take(5);

        //产生0,10,20,30,40数列
        Observable<Long> observable2 = Observable.timer(5000, 1000, TimeUnit.MILLISECONDS)
            .map(new Func1<Long, Long>() {
                @Override
                public Long call(Long aLong) {
                    return aLong * 10;
                }
            }).take(5);


        Observable.combineLatest(observable1, observable2, new Func2<Long, Long, Long>() {
            @Override
            public Long call(Long aLong, Long aLong2) {
                return aLong+aLong2;
            }
        }).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                System.out.println("Sequence complete.");
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onNext(Long aLong) {
                System.out.println("Next: " + aLong);
            }
        });


    }

    public void testRxAndroidMerge(View view) {
        //产生0,5,10,15,20数列
        System.out.print("test RxAndroid Join function");
        Observable<Long> observable1 = Observable.timer(0, 1000, TimeUnit.MILLISECONDS)
            .map(new Func1<Long, Long>() {
                @Override
                public Long call(Long aLong) {
                    return aLong * 5;
                }
            }).take(5);

        //产生0,10,20,30,40数列
        Observable<Long> observable2 = Observable.timer(5000, 1000, TimeUnit.MILLISECONDS)
            .map(new Func1<Long, Long>() {
                @Override
                public Long call(Long aLong) {
                    return aLong * 10;
                }
            }).take(5);

        Observable.merge(observable1, observable2)
            .subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                System.out.println("Sequence complete.");
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onNext(Long aLong) {
                System.out.println("Next: " + aLong);
            }
        });


    }

    public void startService(View view){
        Intent startIntent = new Intent(this, DemoService.class);
        startService(startIntent);
    }
}
