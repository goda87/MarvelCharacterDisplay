package es.goda87.marvelcharacterdisplay;

import android.app.Application;

import es.goda87.marvelcharacterdisplay.data.characters.CharactersDataComponent;
import es.goda87.marvelcharacterdisplay.data.characters.DaggerCharactersDataComponent;

public class MarvelCharactersApplication extends Application {
    private CharactersDataComponent dataComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        dataComponent = DaggerCharactersDataComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();
    }

    public CharactersDataComponent getDataComponent() {
        return dataComponent;
    }
}
