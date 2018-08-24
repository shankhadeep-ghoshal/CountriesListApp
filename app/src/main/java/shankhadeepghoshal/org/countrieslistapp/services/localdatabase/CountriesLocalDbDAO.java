package shankhadeepghoshal.org.countrieslistapp.services.localdatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import shankhadeepghoshal.org.countrieslistapp.mvp.entities.CountriesFullEntity;

@Dao
public interface CountriesLocalDbDAO {
    @Query("SELECT name,flag FROM Countries")
    Flowable<List<CountriesFullEntity>> getCountriesList();

    @Query("SELECT * FROM Countries WHERE name LIKE :name LIMIT 1")
    Flowable<CountriesFullEntity> getCountryByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllCountries(List<CountriesFullEntity> countryList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSingleCountry(CountriesFullEntity singleCountry);

    @Delete
    void deleteSingleCountry(CountriesFullEntity singleCountry);

    @Delete
    void endOfTheWorld(List<CountriesFullEntity> theWorld);
}