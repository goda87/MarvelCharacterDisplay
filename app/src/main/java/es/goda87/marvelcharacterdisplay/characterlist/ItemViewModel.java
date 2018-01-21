package es.goda87.marvelcharacterdisplay.characterlist;

import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

import es.goda87.marvelcharacterdisplay.base.CharacterViewModel;
import es.goda87.marvelcharacterdisplay.data.characters.source.CharactersDataSource;

/**
 * Created by goda87 on 24/08/17.
 */

public class ItemViewModel extends CharacterViewModel {

    // This navigator is s wrapped in a WeakReference to avoid leaks because it has references to an
    // activity. There's no straightforward way to clear it for each item in a list adapter.
    @Nullable
    private WeakReference<CharacterDetailNavigator> navigator;

    public ItemViewModel(CharactersDataSource charactersDataSource, CharacterDetailNavigator navigator) {
        super(charactersDataSource);
        this.navigator = new WeakReference<CharacterDetailNavigator>(navigator);
    }

    /**
     * Called by the Data Binding library when the row is clicked.
     */
    @Override
    public void characterClicked() {
        Integer characterId = characterObservable.get().getId();
        if (characterId == null) {
            // Click happened before task was loaded, no-op.
            return;
        }
        if (navigator != null && navigator.get() != null) {
            navigator.get().openItemDetailsPage(characterId);
        }
    }
}
