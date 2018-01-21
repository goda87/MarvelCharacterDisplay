package es.goda87.marvelcharacterdisplay.characterdetail;


import dagger.Component;
import es.goda87.marvelcharacterdisplay.dagger.FragmentScoped;
import es.goda87.marvelcharacterdisplay.data.characters.CharactersDataComponent;

@FragmentScoped
@Component(dependencies = CharactersDataComponent.class, modules = WebNavigatorModule.class)
public interface DetailComponent {

    void inject(DetailActivity detailActivity);

}
