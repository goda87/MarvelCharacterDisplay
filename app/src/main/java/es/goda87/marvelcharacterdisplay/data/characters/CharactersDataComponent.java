package es.goda87.marvelcharacterdisplay.data.characters;


import javax.inject.Singleton;

import dagger.Component;
import es.goda87.marvelcharacterdisplay.ApplicationModule;
import es.goda87.marvelcharacterdisplay.data.characters.source.CharactersRepository;
import es.goda87.marvelcharacterdisplay.data.characters.source.CharactersRepositoryModule;


@Singleton
@Component(modules = {CharactersRepositoryModule.class, ApplicationModule.class})
public interface CharactersDataComponent {

    CharactersRepository getCharactersRepository();
}
