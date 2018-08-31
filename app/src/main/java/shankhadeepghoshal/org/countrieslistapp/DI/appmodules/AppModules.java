package shankhadeepghoshal.org.countrieslistapp.DI.appmodules;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import shankhadeepghoshal.org.countrieslistapp.services.localdatabase.CountriesLocalDb;

@Module
public class AppModules {
    private String baseUrlData;
    private Context ctx;

    public AppModules(String baseUrlData, Context ctx) {
        this.baseUrlData = baseUrlData;
        this.ctx = ctx;
    }

    @Singleton
    @Provides
    public JacksonConverterFactory provideJacksonConverterFactory() {
        return JacksonConverterFactory.create();
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClientData() {
        return new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    @Singleton
    @Provides
    public RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofitData(OkHttpClient okHttpClient,
                                        JacksonConverterFactory jacksonConverterFactory,
                                        RxJava2CallAdapterFactory rxJava2CallAdapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(baseUrlData)
                .addConverterFactory(jacksonConverterFactory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .client(okHttpClient)
                .build();
    }

    @Singleton
    @Provides
    public RoomDatabase.Builder<CountriesLocalDb> provideCountriesLocalDb(Context context){
        return Room.databaseBuilder(context,CountriesLocalDb.class,"Countries");
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return this.ctx;
    }
}