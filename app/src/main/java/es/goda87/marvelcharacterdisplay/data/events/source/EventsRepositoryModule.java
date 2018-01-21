package es.goda87.marvelcharacterdisplay.data.events.source;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.goda87.marvelcharacterdisplay.R;
import es.goda87.marvelcharacterdisplay.data.events.source.remote.MarvelApiEventsDataSource;

/**
 * Created by goda87 on 20.01.18.
 */
@Module
public class EventsRepositoryModule {

    @Singleton
    @Provides
    EventsDataSource provideEventsRemoteDataSource(Context ctx) {
        return new MarvelApiEventsDataSource(
                ctx.getResources().getString(R.string.MARVEL_PUBLICKEY),
                ctx.getResources().getString(R.string.MARVEL_PRIVATEKEY));
    }
}
