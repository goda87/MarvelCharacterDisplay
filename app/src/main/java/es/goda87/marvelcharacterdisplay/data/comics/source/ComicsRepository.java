package es.goda87.marvelcharacterdisplay.data.comics.source;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import es.goda87.marvelcharacterdisplay.data.comics.model.Comic;
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

public class ComicsRepository implements ComicsDataSource {

    private ComicsDataSource remoteDataSource;

    private List<Comic> remoteComics = new ArrayList<>();

    @Inject
    public ComicsRepository(ComicsDataSource remote) {
        this.remoteDataSource = remote;
    }

    @Override
    public Flowable<List<Comic>> getComics(final Observable<Integer> scrollObservable) {

        Flowable<List<Comic>> resultStream = Flowable.create(new FlowableOnSubscribe<List<Comic>>() {
            @Override
            public void subscribe(FlowableEmitter<List<Comic>> emitter) throws Exception {
                networkBoundSource(emitter, scrollObservable);
            }
        }, BackpressureStrategy.BUFFER);
        return resultStream;
    }

    @Override
    public void setCharacterId(int characterId) {
        remoteDataSource.setCharacterId(characterId);
        remoteComics.clear();
    }

    public void networkBoundSource(final FlowableEmitter<List<Comic>> emitter, Observable<Integer> scrollObservable) {
        if (remoteDataSource != null) {
            Flowable<List<Comic>> remoteStream = remoteDataSource.getComics(scrollObservable);
            remoteStream
                    .observeOn(Schedulers.computation())
                    .subscribe(
                            getProviderConsumer(emitter),
                            getProviderSubscriber(scrollObservable, emitter));
        }
    }

    private Consumer<Throwable> getProviderSubscriber(
            final Observable<Integer> scroll,
            final FlowableEmitter<List<Comic>> emitter) {
        return new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Flowable<List<Comic>> remoteStream = remoteDataSource.getComics(scroll);
                remoteStream
                        .observeOn(Schedulers.computation())
                        .subscribe(
                                getProviderConsumer(emitter),
                                getProviderSubscriber(scroll, emitter));
            }
        };
    }

    private Consumer<List<Comic>> getProviderConsumer(final FlowableEmitter<List<Comic>> emitter) {
        return new Consumer<List<Comic>>() {
            @Override
            public void accept(@NonNull List<Comic> comics) throws Exception {
                emitter.onNext(addComics(comics));
            }
        };
    }

    private List<Comic> addComics(List<Comic> comics) {
        remoteComics.addAll(comics);
        return remoteComics;
    }
}
