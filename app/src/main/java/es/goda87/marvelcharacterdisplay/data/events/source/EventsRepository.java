package es.goda87.marvelcharacterdisplay.data.events.source;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import es.goda87.marvelcharacterdisplay.data.events.model.Event;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by goda87 on 20.01.18.
 */

public class EventsRepository implements EventsDataSource {

    private EventsDataSource remoteDataSource;

    private List<Event> remoteEvents = new ArrayList<>();

    @Inject
    public EventsRepository(EventsDataSource remote) {
        this.remoteDataSource = remote;
    }

    @Override
    public Flowable<List<Event>> getEvents(final Observable<Integer> scrollObservable) {

        Flowable<List<Event>> resultStream = Flowable.create(new FlowableOnSubscribe<List<Event>>() {
            @Override
            public void subscribe(FlowableEmitter<List<Event>> emitter) throws Exception {
                networkBoundSource(emitter, scrollObservable);
            }
        }, BackpressureStrategy.BUFFER);
        return resultStream;
    }

    @Override
    public void setCharacterId(int characterId) {
        remoteDataSource.setCharacterId(characterId);
        remoteEvents.clear();
    }

    public void networkBoundSource(final FlowableEmitter<List<Event>> emitter, Observable<Integer> scrollObservable) {
        if (remoteDataSource != null) {
            Flowable<List<Event>> remoteStream = remoteDataSource.getEvents(scrollObservable);
            remoteStream
                    .observeOn(Schedulers.computation())
                    .subscribe(
                            getProviderConsumer(emitter),
                            getProviderSubscriber(scrollObservable, emitter));
        }
    }

    private Consumer<Throwable> getProviderSubscriber(
            final Observable<Integer> scroll,
            final FlowableEmitter<List<Event>> emitter) {
        return new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Flowable<List<Event>> remoteStream = remoteDataSource.getEvents(scroll);
                remoteStream
                        .observeOn(Schedulers.computation())
                        .subscribe(
                                getProviderConsumer(emitter),
                                getProviderSubscriber(scroll, emitter));
            }
        };
    }

    private Consumer<List<Event>> getProviderConsumer(final FlowableEmitter<List<Event>> emitter) {
        return new Consumer<List<Event>>() {
            @Override
            public void accept(@NonNull List<Event> events) throws Exception {
                emitter.onNext(addEvents(events));
            }
        };
    }

    private List<Event> addEvents(List<Event> events) {
        remoteEvents.addAll(events);
        return remoteEvents;
    }
}
