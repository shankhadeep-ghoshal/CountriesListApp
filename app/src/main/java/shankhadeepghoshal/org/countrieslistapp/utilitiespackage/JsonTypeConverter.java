package shankhadeepghoshal.org.countrieslistapp.utilitiespackage;

import android.arch.persistence.room.TypeConverter;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import shankhadeepghoshal.org.countrieslistapp.mvp.entities.CurrenciesEntity;


public class JsonTypeConverter {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final TypeReference<List<String>> listStringTypeReference = new TypeReference<List<String>>() {};
    private static final TypeReference<List<CurrenciesEntity>> listCurrencyTypeReference = new TypeReference<List<CurrenciesEntity>>() {};

    @Nullable
    @TypeConverter
    public static List<String> stringToListOfString(String jsonString) {
        try {
            return mapper.readValue(jsonString,listStringTypeReference);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    @TypeConverter
    public static List<CurrenciesEntity> stringToListOfCurrencies(String jsonString) {
        try {
            return mapper.readValue(jsonString,listCurrencyTypeReference);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @TypeConverter
    public static String listStringToJsonString(List<String> stringList) {
        try {
            return mapper.writeValueAsString(stringList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    @TypeConverter
    public static String currenciesStringToJsonString(List<CurrenciesEntity> stringList) {
        try {
            return mapper.writeValueAsString(stringList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }
}