package ru.vitalydemidov.newsapp.articles;

import dagger.BindsInstance;
import dagger.Component;
import ru.vitalydemidov.newsapp.AppComponent;
import ru.vitalydemidov.newsapp.util.PerActivity;

/**
 * This is a Dagger 2 component. It used to pass in all needed dependencies to the
 * {@link ArticlesActivity}.
 *
 * Because this component depends on the {@link AppComponent}, which is a singleton,
 * a scope must be specified. All fragment components use a custom scope for this purpose.
 */

@PerActivity
@Component(
        dependencies = {
                AppComponent.class
        },
        modules = {
                ArticlesModule.class
        }
)
interface ArticlesComponent {

    void inject(ArticlesActivity activity);


    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder sourceId(String sourceId);

        Builder appComponent(AppComponent appComponent);

        ArticlesComponent build();
    }

}
