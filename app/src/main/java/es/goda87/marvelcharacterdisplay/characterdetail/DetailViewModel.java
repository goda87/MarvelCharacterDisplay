package es.goda87.marvelcharacterdisplay.characterdetail;

import android.databinding.Bindable;

import java.lang.ref.WeakReference;

import javax.annotation.Nullable;
import javax.inject.Inject;

import es.goda87.marvelcharacterdisplay.base.CharacterViewModel;
import es.goda87.marvelcharacterdisplay.data.characters.source.CharactersRepository;


public class DetailViewModel extends CharacterViewModel {

    @Nullable
    private WeakReference<WebNavigator> navigator;

    @Inject
    public DetailViewModel(CharactersRepository charactersDataSource, WebNavigator navigator) {
        super(charactersDataSource);
        this.navigator = new WeakReference<WebNavigator>(navigator);
    }

    public void linkClicked(String url) {
        if (navigator != null && navigator.get() != null) {
            navigator.get().openWeb(url);
        }
    }

    @Bindable
    public String getLinkDetails() {
        return characterObservable.get().getUrlDetail();
    }

    @Bindable
    public String getLinkWiki() {
        return characterObservable.get().getUrlWiki();
    }

    @Bindable
    public String getLinkComics() {
        return characterObservable.get().getUrlComics();
    }

    @Bindable
    public int getNumberOfComics() {
        if (characterObservable.get() == null) return 0;
        return characterObservable.get().getAvailableComics();
    }

    @Bindable
    public int getNumberOfEvents() {
        if (characterObservable.get() == null) return 0;
        return characterObservable.get().getAvailableEvents();
    }
}
