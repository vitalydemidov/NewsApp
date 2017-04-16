package ru.vitalydemidov.newsapp.sources;

import dagger.Component;
import ru.vitalydemidov.newsapp.AppComponent;
import ru.vitalydemidov.newsapp.util.PerActivity;

/**
 * This is a Dagger 2 component. It used to pass in all needed dependency to the
 * {@link SourcesActivity}.
 *
 * Because this component depends on the {@link AppComponent}, which is a singleton,
 * a scope must be specified. All fragment components use a custom scope for this purpose.
 */

@PerActivity
@Component(dependencies = AppComponent.class, modules = SourcesPresenterModule.class)
interface SourcesComponent {

    void inject(SourcesActivity activity);

}
