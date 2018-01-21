package es.goda87.marvelcharacterdisplay.data.characters.source;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import es.goda87.marvelcharacterdisplay.data.characters.model.Character;
import es.goda87.marvelcharacterdisplay.data.characters.source.remote.Remote;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class CharactersRepository implements CharactersDataSource {

    private CharactersDataSource remoteDataSource;

    private List<Character> remoteCharacters = new ArrayList<>();

    @Inject
    public CharactersRepository(@Remote CharactersDataSource remote) {
        this.remoteDataSource = remote;
    }

    @Override
    public void setSearch(String search) {
        remoteDataSource.setSearch(search);
        remoteCharacters.clear();
    }

    @Override
    public Flowable<List<Character>> getCharacters(final Observable<Integer> scrollObservable) {

        Flowable<List<Character>> resultStream = Flowable.create(new FlowableOnSubscribe<List<Character>>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<List<Character>> emitter) throws Exception {
                networkBoundSource(emitter, scrollObservable);
            }
        }, BackpressureStrategy.BUFFER);
        return resultStream;
    }

    public void networkBoundSource(final FlowableEmitter<List<Character>> emitter, Observable<Integer> scrollObservable) {
        if (remoteDataSource != null) {
            Flowable<List<Character>> remoteStream = remoteDataSource.getCharacters(scrollObservable);
            remoteStream
                    .observeOn(Schedulers.computation())
                    .subscribe(
                            getProviderConsumer(emitter),
                            getProviderSubscriber(scrollObservable, emitter));
        }
    }

    private Consumer<Throwable> getProviderSubscriber(
            final Observable<Integer> scroll,
            final FlowableEmitter<List<Character>> emitter) {
        return new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Flowable<List<Character>> remoteStream = remoteDataSource.getCharacters(scroll);
                remoteStream
                        .observeOn(Schedulers.computation())
                        .subscribe(
                                getProviderConsumer(emitter),
                                getProviderSubscriber(scroll, emitter));
            }
        };
    }

    private Consumer<List<Character>> getProviderConsumer(final FlowableEmitter<List<Character>> emitter) {
        return new Consumer<List<Character>>() {
            @Override
            public void accept(@NonNull List<Character> characters) throws Exception {
                emitter.onNext(addCharacters(characters));
            }
        };
    }

    private List<Character> addCharacters(List<Character> characters) {
        remoteCharacters.addAll(characters);
        return remoteCharacters;
    }

    @Override
    public Observable<Character> getCharacter(final int id) {

        return Observable.just(id)
                .observeOn(Schedulers.computation())
                .map(new Function<Integer, Character>() {
                    @Override
                    public Character apply(@NonNull Integer integer) throws Exception {

                        for (Character c : remoteCharacters) {
                            if (integer == c.getId()) {
                                return c;
                            }
                        }
                        return null;
                    }
                });
    }

    @Override
    public Character getCharacterSync(int id) {
        for (Character c : remoteCharacters) {
            if (id == c.getId()) {
                return c;
            }
        }
        return null;
    }
}
