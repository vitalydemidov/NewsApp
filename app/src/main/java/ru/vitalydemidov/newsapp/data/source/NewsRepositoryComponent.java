package ru.vitalydemidov.newsapp.data.source;

import javax.inject.Singleton;

import dagger.Component;
import ru.vitalydemidov.newsapp.NewsApp;
import ru.vitalydemidov.newsapp.api.ApiModule;

/**
 * This is a Dagger 2 component. Refer to {@link NewsApp} for the list of Dagger 2 components
 * used in this application.
 *
 * Even though Dagger allows annotating a {@link Component @Component} as a singleton, the code
 * itself must ensure only one instance of the class is created. This is done in {@link NewsApp}.
 */

@Singleton
@Component(modules = {NewsRepositoryModule.class, ApiModule.class})
public interface NewsRepositoryComponent {

    NewsRepository getNewsRepository();

}
