package com.mytest.rxjava;

import android.view.View;

import com.mytest.R;
import com.mytest.base.BaseActivity;
import com.orhanobut.logger.Logger;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxjavaActivity extends BaseActivity {

    @Override
    public int initLayout() {
        return R.layout.activity_rxjava;
    }

    @Override
    public void initIntent() {

    }

    @Override
    public void addListener() {

    }

    @Override
    public void initData() {
        //被观察者
        //Observable中文意思就是被观察者，通过create方法生成对象，里面放的参数ObservableOnSubscribe<T>
        // ，可以理解为一个计划表，泛型T是要操作对象的类型，重写subscribe方法，里面写具体的计划，本文
        // 例子就是推送连载1、连载2和连载3，在subscribe中的ObservableEmitter<String>对象的Emitter是发
        // 射器的意思。ObservableEmitter有三种发射的方法，分别是void onNext(T value)、void onError(Throwable error)、
        // void onComplete()，onNext方法可以无限调用，Observer（观察者）所有的都能接收到，onError和onComplete是
        // 互斥的，Observer（观察者）只能接收到一个，OnComplete可以重复调用，但是Observer（观察者）只会接收一
        // 次，而onError不可以重复调用，第二次调用就会报异常。
//        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext("连载1");
//                emitter.onNext("连载2");
//                emitter.onNext("连载3");
//                emitter.onComplete();
//                emitter.onError(new Throwable("hello"));
//            }
//        });
        //just
//        Observable<String> observable = Observable.just("连载1","连载2","连载3");
        //from
        String[] words = new String[]{"连载1","连载2","连载3"};
        Observable<String> observable = Observable.fromArray(words);

        //创建Observer(观察者)
        //通过new创建接口，并实现其内部的方法，看方法其实就应该差不多知道干嘛的，onNext、onError、onComplete
        // 都是跟被观察者发射的方法一一对应的，这里就相当于接收了。onSubscribe（Disposable d）里面的
        // Disposable对象要说一下，Disposable英文意思是可随意使用的，这里就相当于读者和连载小说的订阅
        // 关系，如果读者不想再订阅该小说了，可以调用 mDisposable.dispose()取消订阅，此时连载小说更新的
        // 时候就不会再推送给读者了。
        Observer<String> observer = new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                Logger.d("onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Logger.d("onNext -- " + s);
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("onError -- " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Logger.d("onComplete");
            }
        };
        //观察者和被观察者建立关系
        observable.subscribe(observer);

        //链式编程
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

            }
        })

                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在io线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void onClick(View view) {

    }
}
