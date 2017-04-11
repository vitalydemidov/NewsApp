package ru.vitalydemidov.newsapp.articles;

import dagger.Component;
import ru.vitalydemidov.newsapp.data.source.NewsRepositoryComponent;
import ru.vitalydemidov.newsapp.util.PerActivity;

/**
 * This is a Dagger 2 component. It used to pass in all needed dependency to the
 * {@link ArticlesActivity}.
 *
 * Because this component depends on the {@link NewsRepositoryComponent}, which is a singleton,
 * a scope must be specified. All fragment components use a custom scope for this purpose.
 */

@PerActivity
@Component(dependencies = NewsRepositoryComponent.class, modules = ArticlesPresenterModule.class)
interface ArticlesComponent {

    void inject(ArticlesActivity activity);

}
