package es.goda87.marvelcharacterdisplay.data.characters.source;

import java.util.List;

import es.goda87.marvelcharacterdisplay.data.characters.model.Character;
import io.reactivex.Flowable;
import io.reactivex.Observable;


public interface CharactersDataSource {

    Flowable<List<Character>> getCharacters(Observable<Integer> lastItemShown);

    void setSearch(String search);

    Observable<Character> getCharacter(int id);
    Character getCharacterSync(int id);
}
