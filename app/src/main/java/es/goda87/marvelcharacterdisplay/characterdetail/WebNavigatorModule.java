package es.goda87.marvelcharacterdisplay.characterdetail;

import dagger.Module;
import dagger.Provides;

/**
 * Created by goda87 on 19.01.18.
 */
@Module
public class WebNavigatorModule {

    private final WebNavigator navigator;

    WebNavigatorModule(WebNavigator navigator) {
        this.navigator = navigator;
    }

    @Provides
    WebNavigator provideCharacterDetailNavigator() {
        return navigator;
    }
}
