package es.goda87.marvelcharacterdisplay.data.characters.source.remote;

import java.util.ArrayList;
import java.util.List;

import es.goda87.marvel.api.MarvelApi;
import es.goda87.marvel.api.models.CharacterDataWrapper;
import es.goda87.marvel.api.models.Url;
import es.goda87.marvelcharacterdisplay.data.characters.model.Character;
import es.goda87.marvelcharacterdisplay.data.characters.source.CharactersDataSource;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


public class MarvelApiCharacterDataSource implements CharactersDataSource {

    private MarvelApi api;
    private String search;

    private List<Character> characters = new ArrayList<>();

    public MarvelApiCharacterDataSource(String publicKey, String privateKey) {
        api = new MarvelApi(publicKey, privateKey);
    }

    public void setSearch(String search) {
        this.search = search;
        characters.clear();
    }

    @Override
    public Flowable<List<Character>> getCharacters(Observable<Integer> lastItemShown) {

        return lastItemShown
                .observeOn(Schedulers.computation())
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer lastItemShown) throws Exception {
                        return lastItemShown >= characters.size() - 15;
                    }
                })
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer) throws Exception {
                        return characters.size();
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Function<Integer, Observable<CharacterDataWrapper>>() {
                    @Override
                    public Observable<CharacterDataWrapper> apply(@NonNull Integer lastItem) throws Exception {
                        Observable<CharacterDataWrapper> o = api.getCharacters(search, lastItem);
                        return o;
                    }
                })
                .map(new Function<Observable<CharacterDataWrapper>, CharacterDataWrapper>() {
                    @Override
                    public CharacterDataWrapper apply(@NonNull Observable<CharacterDataWrapper> listFlowable) throws Exception {
                        CharacterDataWrapper c = listFlowable.blockingFirst(null);
                        return c;
                    }
                })
                .map(new Function<CharacterDataWrapper, List<Character>>() {
                    @Override
                    public List<Character> apply(@NonNull CharacterDataWrapper characterDataWrapper) throws Exception {
                        List<es.goda87.marvel.api.models.Character> result = characterDataWrapper.getData().getResults();

                        if (result == null) { return characters; }

                        for (es.goda87.marvel.api.models.Character apiCharacter : result) {
                            Character character = apiToCoreCharacter(apiCharacter);
                            characters.add(character);
                        }
                        return characters;
                    }
                })
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Observable<Character> getCharacter(int id) {
        Character character = null;

        for (Character cachedCharacter : characters) {
            if (cachedCharacter.getId() == id) {
                character = cachedCharacter;
                break;
            }
        }

        return Observable.just(character);
    }

    @Override
    public Character getCharacterSync(int id) {
        Character character = null;
        for (Character cachedCharacter : characters) {
            if (cachedCharacter.getId() == id) {
                character = cachedCharacter;
                break;
            }
        }
        return character;
    }

    private Character apiToCoreCharacter(es.goda87.marvel.api.models.Character apiCharacter) {
        Character character = new Character();
        character.setId(apiCharacter.getId());
        character.setName(apiCharacter.getName());
        character.setDescription(apiCharacter.getDescription());
        character.setImagePath(apiCharacter.getThumbnail().getPath());
        character.setImageExtension(apiCharacter.getThumbnail().getExtension());
        character.setAvailableComics(apiCharacter.getComics().getAvailable());
        character.setAvailableEvents(apiCharacter.getEvents().getAvailable());

        setUrlsToCocreCharacter(character, apiCharacter);

        return character;
    }

    private void setUrlsToCocreCharacter(@NonNull Character core, @NonNull es.goda87.marvel.api.models.Character apiCharacter) {
        for (Url url : apiCharacter.getUrls()) {
            switch (url.getType()) {
                case "detail":
                    core.setUrlDetail(url.getUrl());
                    break;
                case "wiki":
                    core.setUrlWiki(url.getUrl());
                    break;
                case "comiclink":
                    core.setUrlCommics(url.getUrl());
            }
        }
    }
}
