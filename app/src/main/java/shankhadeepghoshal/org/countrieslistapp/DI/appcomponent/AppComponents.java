package shankhadeepghoshal.org.countrieslistapp.DI.appcomponent;

import android.content.Context;

import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;
import shankhadeepghoshal.org.countrieslistapp.DI.appmodules.AppModules;

@Singleton
@Component(modules = AppModules.class)
public interface AppComponents {
    Retrofit supplyRetrofit();
    Context supplyContext();
    Picasso.Builder supplyPicassoBuilder();
}