package com.company.reactive.tobyYtb.pubSubOp;

import java.util.concurrent.Flow;
import java.util.function.Function;

public class DelegateSub<T> implements Flow.Subscriber<T> {

    private final Flow.Subscriber<T> sub;
    private final Function f;

    public DelegateSub(Flow.Subscriber<T> sub, Function f) {
        this.sub = sub;
        this.f = f;
    }


    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        sub.onSubscribe(subscription);
    }

    @Override
    public void onNext(T t) {
        sub.onNext((T) f.apply(t));
    }

    @Override
    public void onError(Throwable throwable) {
        sub.onError(throwable);
    }

    @Override
    public void onComplete() {
        sub.onComplete();
    }
}
