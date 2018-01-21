package es.goda87.marvelcharacterdisplay.data.characters.source;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.goda87.marvelcharacterdisplay.R;
import es.goda87.marvelcharacterdisplay.data.characters.source.remote.MarvelApiCharacterDataSource;
import es.goda87.marvelcharacterdisplay.data.characters.source.remote.Remote;

@Module
public class CharactersRepositoryModule {

    @Singleton
    @Provides
    @Remote
    CharactersDataSource provideCharactersRemoteDataSource(Context ctx) {
        return new MarvelApiCharacterDataSource(
                ctx.getResources().getString(R.string.MARVEL_PUBLICKEY),
                ctx.getResources().getString(R.string.MARVEL_PRIVATEKEY));
    }
}
