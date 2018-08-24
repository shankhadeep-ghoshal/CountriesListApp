package shankhadeepghoshal.org.countrieslistapp.ui;

import com.squareup.picasso.Picasso;

import shankhadeepghoshal.org.countrieslistapp.mvp.entities.CountriesFullEntity;

public interface IFragmentToFragmentMediator {
    void invokeDetailsFragmentOnListItemCLickedInListFragment(CountriesFullEntity countriesFullEntity);
    void invokeDetailsFragmentOnListItemClickedInListFragmentViewModel();
    Picasso getPicasso();
}