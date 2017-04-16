package ru.vitalydemidov.newsapp;

import javax.inject.Singleton;

import dagger.Component;
import ru.vitalydemidov.newsapp.api.ApiModule;
import ru.vitalydemidov.newsapp.data.source.NewsRepository;
import ru.vitalydemidov.newsapp.data.source.NewsRepositoryModule;
import ru.vitalydemidov.newsapp.util.schedulers.BaseSchedulerProvider;

/**
 * This is a Dagger 2 component. Refer to {@link NewsApp} for the list of Dagger 2 components
 * used in this application.
 *
 * Even though Dagger allows annotating a {@link Component @Component} as a singleton, the code
 * itself must ensure only one instance of the class is created. This is done in {@link NewsApp}.
 */

@Singleton
@Component(modules = {
        AppModule.class,
        ApiModule.class,
        NewsRepositoryModule.class
})
public interface AppComponent {

    NewsRepository getNewsRepository();

    BaseSchedulerProvider getBaseSchedulerProvider();

}
