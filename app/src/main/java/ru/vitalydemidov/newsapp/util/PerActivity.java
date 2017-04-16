package ru.vitalydemidov.newsapp.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

import ru.vitalydemidov.newsapp.AppComponent;

/**
 * In Dagger 2, an unscoped component cannot depend on a scoped component. Because
 * {@link AppComponent} is a scoped component ({@code @Singleton}, we create a custom
 * scope to be used by all screen components. Additionally, a component with a specific scope
 * cannot have a sub component with the same scope.
 */

@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
