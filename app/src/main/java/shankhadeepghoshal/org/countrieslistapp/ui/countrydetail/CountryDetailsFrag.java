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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ahmadrosid.svgloader.SvgLoader;

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

    private static final String TAG_COUNTRY_DETAIL_FRAG = "FragmentCountryDetail";

    private Frag2FragCommViewModel frag2FragCommViewModel;
    private CountriesFullEntity countriesFullEntity;

    @BindView(R.id.CountryFlagDetails)
    AppCompatImageView countryFlagDetails;

    @BindView(R.id.CountryName)
    AppCompatTextView countryNameDetails;

    @BindView(R.id.CurrencyHolderRV)
    RecyclerView currencyHolderRV;
    @BindView(R.id.TimeZoneHolderRV)
    RecyclerView timezoneHolderRV;

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
            this.listeningActivity = (IFragmentToFragmentMediator) context;
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

        setUpLayoutBindings();

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
        outState.putString("imageUrl",this.countriesFullEntity.getFlag());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        restoreRVInstanceState(savedInstanceState, this.currencyHolderRV);
        restoreRVInstanceState(savedInstanceState, this.timezoneHolderRV);
        if (savedInstanceState != null) {
            placeCountryFlagImageInImageView(savedInstanceState.getString("imageUrl"));
        }
    }

    @Override
    public void onLoadParticularCountryData(CountriesFullEntity countriesFullEntity) {
        setUpDataForUI(countriesFullEntity);
        Log.d(TAG_COUNTRY_DETAIL_FRAG,countriesFullEntity.toString());
        this.countriesFullEntity = countriesFullEntity;
        this.frag2FragCommViewModel.setSingleCountryEntry(countriesFullEntity);
        this.listeningActivity.makeSwipeAnimationStopAfterUpdate();
    }

    @Override
    public void onErrorEncountered(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        this.listeningActivity.makeSwipeAnimationStopAfterUpdate();
    }

    @Override
    public void onPerformUpdateAction() {
        callToPresenterToUpdateSingleSpecifiedCountryOnInternetPresent(Objects
                .requireNonNull(this.frag2FragCommViewModel
                        .getLiveDataSingleCountryData()
                        .getValue())
                .getName());
    }

    private void setUpLayoutBindings() {
        this.frag2FragCommViewModel
                .getLiveDataSingleCountryData()
                .observe(this, countriesFullEntity1 -> {
                    if(countriesFullEntity1!=null)
                        setUpDataForUI(countriesFullEntity1);
                        setUpRVs();
                });
    }

    private void setUpDataForUI(CountriesFullEntity holderInstance) {
        placeCountryFlagImageInImageView(holderInstance.getFlag());
        this.countryNameDetails.setText(holderInstance.getName());
            this.currenciesRVAdapter = new CurrenciesRVAdapter(holderInstance.getCurrencies());
            this.timeZoneRVAdapter = new TimeZoneRVAdapter(holderInstance.getTimezones());
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

    private void placeCountryFlagImageInImageView(String flag) {
        SvgLoader
                .pluck()
                .with(this.getActivity())
                .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher_round)
                .load(flag, countryFlagDetails);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }
}