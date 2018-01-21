package es.goda87.marvelcharacterdisplay.data.comics.source;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.goda87.marvelcharacterdisplay.R;
import es.goda87.marvelcharacterdisplay.data.comics.source.remote.MarvelApiComicsDataSource;

/**
 * Created by goda87 on 20.01.18.
 */
@Module
public class ComicsRepositoryModule {

    @Singleton
    @Provides
    ComicsDataSource provideComicsRemoteDataSource(Context ctx) {
        return new MarvelApiComicsDataSource(
                ctx.getResources().getString(R.string.MARVEL_PUBLICKEY),
                ctx.getResources().getString(R.string.MARVEL_PRIVATEKEY));
    }
}
