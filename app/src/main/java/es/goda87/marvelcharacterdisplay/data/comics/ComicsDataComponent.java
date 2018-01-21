package es.goda87.marvelcharacterdisplay.data.comics;

import javax.inject.Singleton;

import dagger.Component;
import es.goda87.marvelcharacterdisplay.ApplicationModule;
import es.goda87.marvelcharacterdisplay.data.comics.source.ComicsRepository;
import es.goda87.marvelcharacterdisplay.data.comics.source.ComicsRepositoryModule;

/**
 * Created by goda87 on 20.01.18.
 */

@Singleton
@Component(modules = {ComicsRepositoryModule.class, ApplicationModule.class})
public interface ComicsDataComponent {

    ComicsRepository getComicsRepository();
}
