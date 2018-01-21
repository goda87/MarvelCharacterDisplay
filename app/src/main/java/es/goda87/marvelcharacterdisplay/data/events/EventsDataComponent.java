package es.goda87.marvelcharacterdisplay.data.events;

import javax.inject.Singleton;

import dagger.Component;
import es.goda87.marvelcharacterdisplay.ApplicationModule;
import es.goda87.marvelcharacterdisplay.data.events.source.EventsRepository;
import es.goda87.marvelcharacterdisplay.data.events.source.EventsRepositoryModule;

/**
 * Created by goda87 on 20.01.18.
 */

@Singleton
@Component(modules = {EventsRepositoryModule.class, ApplicationModule.class})
public interface EventsDataComponent {

    EventsRepository getEventsRepository();
}
