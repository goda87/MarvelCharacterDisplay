package es.goda87.marvelcharacterdisplay.characterlist;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import es.goda87.marvelcharacterdisplay.data.characters.model.Character;
import es.goda87.marvelcharacterdisplay.data.characters.source.CharactersRepository;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class ListViewModel extends BaseObservable {

    public final ObservableList<Character> items = new ObservableArrayList<>();

    private CharactersRepository manager;
    private Disposable disposable = null;

    @Inject
    public ListViewModel(CharactersRepository charactersRepository) {
        this.manager = charactersRepository;
    }

    void onActivityDestroyed() {
        // Clear references to avoid potential memory leaks.
        if (disposable != null) {
            disposable.dispose();
        }
    }

    void setSearch(String search) {
        manager.setSearch(search);
        items.clear();
    }

    void getCharacters(Observable<Integer> scrollObservable) {
         disposable = manager.getCharacters(scrollObservable.startWith(0))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Character>>() {
                    @Override
                    public void accept(@NonNull List<Character> characters) throws Exception {
                        updateList(characters);
                    }
                });
    }

    private synchronized void updateList(@NonNull List<Character> characters) {

        for (Character c : characters) {
            int index = items.indexOf(c);
            if (index > -1) {
                items.remove(index);
                items.add(c);
            } else {
                items.add(c);
            }
        }

        Collections.sort(items, new Comparator<Character>() {
            @Override
            public int compare(Character character, Character t1) {
                return character.getName().compareToIgnoreCase(t1.getName());
            }
        });
    }
}
