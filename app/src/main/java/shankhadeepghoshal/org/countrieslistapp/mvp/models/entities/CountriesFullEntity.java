package shankhadeepghoshal.org.countrieslistapp.mvp.models.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shankhadeepghoshal.org.countrieslistapp.utilitiespackage.JsonTypeConverter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(tableName = "Countries", indices = {@Index(value = "flag",unique = true)})
@TypeConverters(JsonTypeConverter.class)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public @Data class CountriesFullEntity implements Serializable {
    /**
     * We are not talking into account the Russias of parallel universes.
     * We do not care.
     * That's for Dr.Cooper & Co. to figure our.
     * Till then the name of the country is the Primary Key
     */
    @PrimaryKey @NonNull
    private String name;
    private List<String> timezones;
    private List<CurrenciesEntity> currencies;
    @NonNull
    private String flag;
}