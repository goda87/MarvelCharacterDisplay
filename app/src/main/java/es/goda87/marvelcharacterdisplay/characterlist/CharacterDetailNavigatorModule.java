package es.goda87.marvelcharacterdisplay.characterlist;

import dagger.Module;
import dagger.Provides;

@Module
public class CharacterDetailNavigatorModule {

    private final CharacterDetailNavigator navigator;

    CharacterDetailNavigatorModule(CharacterDetailNavigator navigator) {
        this.navigator = navigator;
    }

    @Provides
    CharacterDetailNavigator provideCharacterDetailNavigator() {
        return navigator;
    }
}
