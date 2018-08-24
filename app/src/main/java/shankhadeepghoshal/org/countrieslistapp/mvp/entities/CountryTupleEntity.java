package shankhadeepghoshal.org.countrieslistapp.mvp.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public @Data class CountryTupleEntity {
    private String name;
    private String flag;
}