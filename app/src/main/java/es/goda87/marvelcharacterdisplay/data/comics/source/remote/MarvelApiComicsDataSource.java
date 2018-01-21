package es.goda87.marvelcharacterdisplay.data.comics.source.remote;

import java.util.ArrayList;
import java.util.List;

import es.goda87.marvel.api.MarvelApi;
import es.goda87.marvel.api.models.ComicDataWrapper;
import es.goda87.marvelcharacterdisplay.data.comics.model.Comic;
import es.goda87.marvelcharacterdisplay.data.comics.source.ComicsDataSource;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by goda87 on 20.01.18.
 */

public class MarvelApiComicsDataSource implements ComicsDataSource {

    private MarvelApi api;
    private int characterId = 1009610;

    private List<Comic> comics = new ArrayList<>();

    public MarvelApiComicsDataSource(String publicKey, String privateKey) {
        this.api = new MarvelApi(publicKey, privateKey);
    }

    @Override
    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    @Override
    public Flowable<List<Comic>> getComics(Observable<Integer> lastItemShown) {
        return lastItemShown
                .observeOn(Schedulers.computation())
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer lastItemShown) throws Exception {
                        return lastItemShown >= comics.size() - 15;
                    }
                })
                .distinctUntilChanged()
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer) throws Exception {
                        return comics.size();
                    }
                })
                .distinct()
                .observeOn(Schedulers.io())
                .map(new Function<Integer, Observable<ComicDataWrapper>>() {
                    @Override
                    public Observable<ComicDataWrapper> apply(@NonNull Integer lastItem) throws Exception {
                        Observable<ComicDataWrapper> o = api.getComics(characterId, lastItem);
                        o.subscribe(new Consumer<ComicDataWrapper>() {
                            @Override
                            public void accept(ComicDataWrapper comicDataWrapper) throws Exception {
                                comicDataWrapper.getAttributionText();
                            }
                        });
                        return o;
                    }
                })

                .map(new Function<Observable<ComicDataWrapper>, ComicDataWrapper>() {
                    @Override
                    public ComicDataWrapper apply(@NonNull Observable<ComicDataWrapper> listFlowable) throws Exception {



                        ComicDataWrapper c = listFlowable.blockingFirst(null);
                        return c;
                    }
                })
                .map(new Function<ComicDataWrapper, List<Comic>>() {
                    @Override
                    public List<Comic> apply(@NonNull ComicDataWrapper comicDataWrapper) throws Exception {
                        List<es.goda87.marvel.api.models.Comic> result = comicDataWrapper.getData().getResults();

                        if (result == null) { return comics; }

                        for (es.goda87.marvel.api.models.Comic apiComic : result) {
                            Comic comic = apiToCoreComic(apiComic);
                            comics.add(comic);
                        }
                        return comics;
                    }
                })
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    private Comic apiToCoreComic(es.goda87.marvel.api.models.Comic apiComic) {
        Comic comic = new Comic();
        comic.setId(apiComic.getId());
        comic.setName(apiComic.getTitle());
        comic.setDescription(apiComic.getDescription());
        comic.setImagePath(apiComic.getThumbnail().getPath());
        comic.setImageExtension(apiComic.getThumbnail().getExtension());
        return comic;
    }
}
