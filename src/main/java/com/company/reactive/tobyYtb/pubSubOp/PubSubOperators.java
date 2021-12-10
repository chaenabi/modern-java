package com.company.reactive.tobyYtb.pubSubOp;

import lombok.SneakyThrows;

import java.util.Iterator;
import java.util.concurrent.Flow;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Reactive Streams - Operators
// Pub + Sub + Operators
// Publisher -> [Data1] -> Op1 -> [Data2] -> Op2 -> [Data3] -> Subscriber
/*
    pub -> [Data1] -> mapPub(Data1) -> [Data2] -> logSub
                   <- subscribe(sub)
                   -> onSubscribe(s)
                   -> onNext
                   -> onNext
                   -> onComplete

     1. map (d1 -> f -> d2)
 */

public class PubSubOperators {

    public static void main(String[] args) throws InterruptedException {
        Iterable<Integer> iterable = Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList());
        Flow.Publisher<Integer> pub = (subscriber) -> subscriber.onSubscribe(new Subscription1(subscriber, iterable));
        pub = mapPub(pub, i -> i * 10);
        Subscriber1<Integer> logSub = new Subscriber1<>();
        pub.subscribe(logSub);
    }

    private static Flow.Publisher<Integer> mapPub(Flow.Publisher<Integer> pub, Function<Integer, Integer> f) {
        return sub -> pub.subscribe(new DelegateSub<>(sub, f));
    }

    static class Subscriber1<T> implements Flow.Subscriber<T> {

        Flow.Subscription subscription;

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            System.out.println("[구독 시작]\nonSubscribe - " + subscription.getClass().getSimpleName());
            this.subscription = subscription;
            this.subscription.request(1);
        }

        @SneakyThrows
        @Override
        public void onNext(Object item) {
            System.out.println("onNext - " + item);
            Thread.sleep(500);
            this.subscription.request(1);
        }

        @Override
        public void onError(Throwable throwable) {
            System.out.println("onError - " + throwable.getMessage());
        }

        @Override
        public void onComplete() {
            System.out.println("onComplete - 완료");
        }
    }

    static class Subscription1 implements Flow.Subscription {

        final Flow.Subscriber<? super Integer> subscriber;
        final Iterable<? extends Integer> iterable;
        final Iterator<? extends Integer> iterator;

        public Subscription1(Flow.Subscriber<? super Integer> subscriber, Iterable<? extends Integer> iterable) {
            this.subscriber = subscriber;
            this.iterable = iterable;
            this.iterator = iterable.iterator();
        }

        @Override
        public void request(long n) {
            try {
                while (n-- > 0) {
                    if (iterator.hasNext()) subscriber.onNext(iterator.next());
                    else {
                        subscriber.onComplete();
                        break;
                    }
                }

            } catch (RuntimeException e) {
                subscriber.onError(e);
            }
        }

        @Override
        public void cancel() {

        }
    }
}


