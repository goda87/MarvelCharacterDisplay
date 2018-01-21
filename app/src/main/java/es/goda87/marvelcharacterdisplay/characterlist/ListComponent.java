package es.goda87.marvelcharacterdisplay.characterlist;

import dagger.Component;
import es.goda87.marvelcharacterdisplay.dagger.FragmentScoped;
import es.goda87.marvelcharacterdisplay.data.characters.CharactersDataComponent;

@FragmentScoped
@Component(dependencies = CharactersDataComponent.class, modules = CharacterDetailNavigatorModule.class)
public interface ListComponent {

    void inject(ListActivity activity);
}
