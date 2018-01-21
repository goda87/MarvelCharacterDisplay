package es.goda87.marvelcharacterdisplay.base;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;

import es.goda87.marvelcharacterdisplay.data.characters.model.Character;
import es.goda87.marvelcharacterdisplay.data.characters.source.CharactersDataSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


/**
 * Abstract class for View Models that expose a single {@link Character}.
 */
public abstract class CharacterViewModel extends BaseObservable {

    protected final ObservableField<Character> characterObservable = new ObservableField<>();

    CharactersDataSource charactersDataSource;

    private boolean mIsDataLoading;

    public CharacterViewModel(CharactersDataSource charactersDataSource) {
        this.charactersDataSource = charactersDataSource;
    }

    public void start(Integer id) {
        if (id != null) {
            mIsDataLoading = true;

            charactersDataSource.getCharacter(id).subscribe(new Consumer<Character>() {
                @Override
                public void accept(@NonNull Character character) throws Exception {
                    characterObservable.set(character);
                    mIsDataLoading = false;
                    notifyChange(); // For the @Bindable properties
                }
            });
        }
    }

    public void startSync(Integer id) {
        if (id != null) {
            Character character = charactersDataSource.getCharacterSync(id);
            characterObservable.set(character);
            notifyChange();
        }
    }

    public void setCharacter(Character character) {
        characterObservable.set(character);
    }

    @Bindable
    public String getName() {
        return characterObservable.get().getName();
    }

    @Bindable
    public String getDescription() {
        return characterObservable.get().getDescription();
    }

    @Bindable
    public String getThumbnail() {
        return characterObservable.get().thumbnailPath();
    }

    @Bindable
    public String getProfileImage() {
        return characterObservable.get().bigPicturePath();
    }

    public void characterClicked() {}

}
