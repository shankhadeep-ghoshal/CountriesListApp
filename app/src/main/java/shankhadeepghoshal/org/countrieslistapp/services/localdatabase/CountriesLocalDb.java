package shankhadeepghoshal.org.countrieslistapp.services.localdatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import shankhadeepghoshal.org.countrieslistapp.mvp.entities.CountriesFullEntity;
import shankhadeepghoshal.org.countrieslistapp.utilitiespackage.JsonTypeConverter;

@Database(entities = CountriesFullEntity.class, version = 1, exportSchema = false)
@TypeConverters(value = JsonTypeConverter.class)
public abstract class CountriesLocalDb extends RoomDatabase {
    public abstract CountriesLocalDbDAO getCountriesLocalDbDAO();
}