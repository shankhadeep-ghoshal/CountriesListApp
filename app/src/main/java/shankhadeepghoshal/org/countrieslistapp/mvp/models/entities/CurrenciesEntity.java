package shankhadeepghoshal.org.countrieslistapp.mvp.models.entities;

import android.arch.persistence.room.TypeConverters;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shankhadeepghoshal.org.countrieslistapp.utilitiespackage.JsonTypeConverter;

@JsonIgnoreProperties(ignoreUnknown = true)
@TypeConverters(JsonTypeConverter.class)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public @Data class CurrenciesEntity implements Serializable{
    private String code;
    private String name;
    private String symbol;
}