package shankhadeepghoshal.org.countrieslistapp.ui.countrydetail;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import shankhadeepghoshal.org.countrieslistapp.DI.countrycomponent.DaggerCountryComponents;
import shankhadeepghoshal.org.countrieslistapp.DI.countrymodule.CountryModule;
import shankhadeepghoshal.org.countrieslistapp.R;
import shankhadeepghoshal.org.countrieslistapp.application.CentralApplication;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.entities.CountriesFullEntity;
import shankhadeepghoshal.org.countrieslistapp.mvp.presenters.CountryDetailsPresenter;
import shankhadeepghoshal.org.countrieslistapp.mvp.view.CountryDetailsView;
import shankhadeepghoshal.org.countrieslistapp.ui.Frag2FragCommViewModel;
import shankhadeepghoshal.org.countrieslistapp.ui.IFragmentToFragmentMediator;
import shankhadeepghoshal.org.countrieslistapp.ui.MainActivity;
import shankhadeepghoshal.org.countrieslistapp.utilitiespackage.DetectInternetConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountryDetailsFrag extends Fragment implements CountryDetailsView {
    // private CountriesFullEntity countriesFullEntity;
    private Frag2FragCommViewModel frag2FragCommViewModel;

    @BindView(R.id.CountryFlagDetails)
    AppCompatImageView countryFlagDetails;

    @BindView(R.id.CountryName)
    AppCompatTextView countryNameDetails;

    @BindView(R.id.CurrencyHolderRV)
    RecyclerView currencyHolderRV;
    @BindView(R.id.TimeZoneHolderRV)
    RecyclerView timezoneHolderRV;

    @Inject
    Picasso picasso;
    @Inject
    CountryDetailsPresenter countryDetailsPresenter;

    private CurrenciesRVAdapter currenciesRVAdapter;
    private TimeZoneRVAdapter timeZoneRVAdapter;
    private IFragmentToFragmentMediator listeningActivity;

    private Unbinder unbinder;

    public CountryDetailsFrag() {}

    public static CountryDetailsFrag newInstance() {
        return new CountryDetailsFrag();
    }

    @Override
    public void onAttach(Context context) {
        DaggerCountryComponents
                .builder()
                .appComponents(((CentralApplication)context.getApplicationContext())
                        .getAppComponents())
                .countryModule(new CountryModule(this))
                .build()
                .inject(this);
        super.onAttach(context);
        try{
            this.listeningActivity = (MainActivity) context;
            this.frag2FragCommViewModel = ViewModelProviders.of((MainActivity) context).get(Frag2FragCommViewModel.class);
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement IFragmentToFragmentMediator");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_country_details, container, false);
        if(v!=null) this.unbinder = ButterKnife.bind(this,v);

        setUpLayoutBindings(this.currenciesRVAdapter==null,
                this.timeZoneRVAdapter==null);

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // TODO: Save the entire CountryObject and call the bindView method onViewStateRestored()
        Parcelable pcCurrency = this.currencyHolderRV.getLayoutManager().onSaveInstanceState();
        Parcelable pcTimeZone = this.timezoneHolderRV.getLayoutManager().onSaveInstanceState();
        outState.putParcelable("currencyData",pcCurrency);
        outState.putParcelable("timezoneData",pcTimeZone);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        restoreRVInstanceState(savedInstanceState, this.currencyHolderRV);
        restoreRVInstanceState(savedInstanceState, this.timezoneHolderRV);
    }

    @Override
    public void onLoadParticularCountryData(CountriesFullEntity countriesFullEntity) {
        setUpDataForUI(currenciesRVAdapter==null,timeZoneRVAdapter==null,countriesFullEntity);
        this.frag2FragCommViewModel.setSingleCountryEntry(countriesFullEntity);
    }

    @Override
    public void onErrorEncountered(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPerformUpdateAction() {
        callToPresenterToUpdateSingleSpecifiedCountryOnInternetPresent(Objects
                .requireNonNull(this.frag2FragCommViewModel
                        .getLiveDataSingleCountryData()
                        .getValue())
                .getName());
    }

    private void setUpLayoutBindings(boolean currencyAdapterFlag, boolean timezoneAdapterFlag) {
        setUpRVs();
        this.frag2FragCommViewModel
                .getLiveDataSingleCountryData()
                .observe(this, countriesFullEntity1 -> {
                    if(countriesFullEntity1!=null)
                        setUpDataForUI(currencyAdapterFlag, timezoneAdapterFlag, countriesFullEntity1);
                });
    }

    private void setUpDataForUI(boolean currencyAdapterFlag, boolean timezoneAdapterFlag, CountriesFullEntity holderInstance) {
        this.picasso.load(holderInstance.getFlag()).into(this.countryFlagDetails);
        this.countryNameDetails.setText(holderInstance.getName());
        if(currencyAdapterFlag)
            this.currenciesRVAdapter = new CurrenciesRVAdapter(holderInstance.getCurrencies(),
                    LayoutInflater.from(this.getContext()));
        if(timezoneAdapterFlag)
            this.timeZoneRVAdapter = new TimeZoneRVAdapter(holderInstance.getTimezones(),
                    LayoutInflater.from(this.getContext()));
    }

    private void setUpRVs() {
        this.currencyHolderRV.setAdapter(this.currenciesRVAdapter);
        this.currencyHolderRV.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.currencyHolderRV
                .addItemDecoration(new DividerItemDecoration(this.currencyHolderRV.getContext(),
                        DividerItemDecoration.VERTICAL));

        this.timezoneHolderRV.setAdapter(this.timeZoneRVAdapter);
        this.timezoneHolderRV.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.timezoneHolderRV
                .addItemDecoration(new DividerItemDecoration(this.timezoneHolderRV.getContext(),
                        DividerItemDecoration.VERTICAL));
    }

    private void callToPresenterToUpdateSingleSpecifiedCountryOnInternetPresent(@NonNull String countryName) {
        this.countryDetailsPresenter
                .getParticularCountry(countryName,
                DetectInternetConnection.isInternetAvailable(Objects.requireNonNull(this.getContext())));
    }

    private void restoreRVInstanceState(Bundle savedInstance, RecyclerView recyclerView) {
        if(savedInstance!=null && recyclerView!=null) {
            recyclerView
                    .getLayoutManager()
                    .onRestoreInstanceState(savedInstance.getParcelable("currencyData"));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }
}