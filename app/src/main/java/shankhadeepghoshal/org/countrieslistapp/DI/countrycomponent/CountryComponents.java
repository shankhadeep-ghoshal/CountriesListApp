package shankhadeepghoshal.org.countrieslistapp.DI.countrycomponent;

import dagger.Component;
import shankhadeepghoshal.org.countrieslistapp.DI.appcomponent.AppComponents;
import shankhadeepghoshal.org.countrieslistapp.DI.countrymodule.CountryModule;
import shankhadeepghoshal.org.countrieslistapp.DI.scope.ScopePerActivity;
import shankhadeepghoshal.org.countrieslistapp.ui.countrieslist.CountriesListFrag;
import shankhadeepghoshal.org.countrieslistapp.ui.countrydetail.CountryDetailsFrag;

@ScopePerActivity
@Component(modules = CountryModule.class, dependencies = AppComponents.class)
public interface CountryComponents {
    void inject(CountriesListFrag view);
    void inject(CountryDetailsFrag view);
}