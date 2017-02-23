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
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;

public class MessActivity extends AppCompatActivity {
    private static final String TAG = "MessActivity";

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
        Observable<Long> observable1 =
            Observable.timer(0, 1000, TimeUnit.MILLISECONDS).map(new Func1<Long, Long>() {
                @Override public Long call(Long aLong) {
                    return aLong * 5;
                }
            }).take(5);

        //产生0,10,20,30,40数列
        Observable<Long> observable2 =
            Observable.timer(5000, 1000, TimeUnit.MILLISECONDS).map(new Func1<Long, Long>() {
                @Override public Long call(Long aLong) {
                    return aLong * 10;
                }
            }).take(5);

        Observable.combineLatest(observable1, observable2, new Func2<Long, Long, Long>() {
            @Override public Long call(Long aLong, Long aLong2) {
                return aLong + aLong2;
            }
        }).subscribe(new Subscriber<Long>() {
            @Override public void onCompleted() {
                System.out.println("Sequence complete.");
            }

            @Override public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override public void onNext(Long aLong) {
                System.out.println("Next: " + aLong);
            }
        });
    }

    public void testRxAndroidMerge(View view) {
        //产生0,5,10,15,20数列
        System.out.print("test RxAndroid Join function");
        Observable<Long> observable1 =
            Observable.timer(0, 1000, TimeUnit.MILLISECONDS).map(new Func1<Long, Long>() {
                @Override public Long call(Long aLong) {
                    return aLong * 5;
                }
            }).take(5);

        //产生0,10,20,30,40数列
        Observable<Long> observable2 =
            Observable.timer(5000, 1000, TimeUnit.MILLISECONDS).map(new Func1<Long, Long>() {
                @Override public Long call(Long aLong) {
                    return aLong * 10;
                }
            }).take(5);

        Observable.merge(observable1, observable2).subscribe(new Subscriber<Long>() {
            @Override public void onCompleted() {
                System.out.println("Sequence complete.");
            }

            @Override public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override public void onNext(Long aLong) {
                System.out.println("Next: " + aLong);
            }
        });
    }

    public void startService(View view) {
        Intent startIntent = new Intent(this, DemoService.class);
        startIntent.putExtra("start_counter", 1000);
        startService(startIntent);
    }

    public void threadTest(View view) {
        //threadCase();
        runnableCase();
    }

    private void runnableCase() {

        new Thread(new Runnable() {
            @Override public void run() {
                int ticket = 60;
                Seller seller1 = new Seller("黄牛", ticket);
                //Seller seller2 = new Seller("大黄牛");

                // Runnable实现多线程可以达到资源共享目的
                Thread t1 = new Thread(seller1);
                Thread t2 = new Thread(seller1);
                Thread t3 = new Thread(seller1);
                Thread t4 = new Thread(seller1);
                Thread t5 = new Thread(seller1);
                Thread t6 = new Thread(seller1);

                double time1 = System.currentTimeMillis();
                t1.start();
                t2.start();
                t3.start();
                t4.start();
                t5.start();
                t6.start();

                try {
                    t1.join();
                    t2.join();
                    t3.join();
                    t4.join();
                    t5.join();
                    t6.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                double time2 = System.currentTimeMillis();

                Logger.d(String.format(Locale.getDefault(), "%d 张票, %d 个黄牛, 卖完花了: %.0f秒", ticket, 6, (time2-time1)/1000));
            }
        }).start();
    }

    private void threadCase() {
        WorkerThread t1 = new WorkerThread("斯德哥尔摩");
        WorkerThread t2 = new WorkerThread("上海");
        // run() 线程不可以穿插进行
        //t1.run();
        //t2.run();

        // start()可以协调系统的资源, 线程可以穿插进行
        t1.start();
        t2.start();
    }

    // ================================
    public class WorkerThread extends Thread {
        private WorkerThread(String name) {
            super(name);
        }

        @Override public void run() {
            super.run();
            for (int i = 0; i < 10; i++) {
                Logger.d(TAG, getName() + ": " + i);
            }
        }
    }

    public class Seller implements Runnable {
        private String name;
        private int ticket = 20;
        private int interval = 500;

        private Seller(final String name, final int ticket) {
            this.name = name;
            this.ticket = ticket;
            Logger.d(String.format(Locale.getDefault(), "总共 %d 张票", ticket));
        }

        @Override public void run() {

            final int count = ticket;
            long threadId = Thread.currentThread().getId();
            for (int i = 0; i < count; i++) {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (this.ticket > 0) {
                    Logger.d(TAG, name + "-" + threadId + ": " + ticket--);
                } else {
                    Logger.w(TAG, name + "-" + threadId + ": 没票啦, 提前下班啰");
                    break;
                }
            }
        }
    }
}
