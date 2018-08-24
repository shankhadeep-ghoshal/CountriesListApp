package shankhadeepghoshal.org.countrieslistapp.DI.countrycomponent;

import dagger.Component;
import shankhadeepghoshal.org.countrieslistapp.DI.appcomponent.AppComponents;
import shankhadeepghoshal.org.countrieslistapp.DI.countrymodule.CountryModule;
import shankhadeepghoshal.org.countrieslistapp.DI.scope.ScopePerActivity;
import shankhadeepghoshal.org.countrieslistapp.mvp.view.CountriesListView;
import shankhadeepghoshal.org.countrieslistapp.mvp.view.CountryDetailsView;

@ScopePerActivity
@Component(modules = CountryModule.class, dependencies = AppComponents.class)
public interface CountryComponents {
    void inject(CountriesListView view);
    void inject(CountryDetailsView view);
}