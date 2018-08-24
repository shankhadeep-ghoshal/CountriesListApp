package shankhadeepghoshal.org.countrieslistapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import shankhadeepghoshal.org.countrieslistapp.R;
import shankhadeepghoshal.org.countrieslistapp.mvp.entities.CountriesFullEntity;
import shankhadeepghoshal.org.countrieslistapp.ui.countrydetail.CountryDetailsFrag;

public class MainActivity extends AppCompatActivity implements IFragmentToFragmentMediator {
    @Inject
    Picasso picasso;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
    }

    @Override
    public void invokeDetailsFragmentOnListItemCLickedInListFragment(CountriesFullEntity countriesFullEntity) {
        CountryDetailsFrag countryDetailsFrag = CountryDetailsFrag.newInstance();
        Bundle data = new Bundle();
        data.putSerializable("data",countriesFullEntity);
        countryDetailsFrag.setArguments(data);
        getSupportFragmentManager().beginTransaction().add(countryDetailsFrag,null).commit();
    }

    @Override
    public void invokeDetailsFragmentOnListItemClickedInListFragmentViewModel() {
        CountryDetailsFrag countryDetailsFrag = CountryDetailsFrag.newInstance();
        Bundle data = new Bundle();
        countryDetailsFrag.setArguments(data);
        getSupportFragmentManager().beginTransaction().add(countryDetailsFrag,null).commit();
    }

    @Override
    public Picasso getPicasso() {
        return this.picasso;
    }
}