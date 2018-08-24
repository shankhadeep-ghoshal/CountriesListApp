package shankhadeepghoshal.org.countrieslistapp.ui.countrydetail;


import android.content.Context;
import android.os.Bundle;
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

import com.squareup.picasso.Picasso;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import shankhadeepghoshal.org.countrieslistapp.R;
import shankhadeepghoshal.org.countrieslistapp.mvp.entities.CountriesFullEntity;
import shankhadeepghoshal.org.countrieslistapp.ui.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountryDetailsFrag extends Fragment {
    private CountriesFullEntity countriesFullEntity;

    @BindView(R.id.CountryFlagDetails)
    AppCompatImageView countryFlagDetails;

    @BindView(R.id.CountryName)
    AppCompatTextView countryNameDetails;

    @BindView(R.id.CurrencyHolderRV)
    RecyclerView currencyHolderRV;
    @BindView(R.id.TimeZoneHolderRV)
    RecyclerView timezoneHolderRV;

    @Inject Picasso picasso;

    private CurrenciesRVAdapter currenciesRVAdapter;
    private TimeZoneRVAdapter timeZoneRVAdapter;


    public CountryDetailsFrag() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_country_details, container, false);
        if(v!=null) ButterKnife.bind(this,v);
        Bundle argumentsFromActivity = this.getArguments();
        if (argumentsFromActivity!=null){
            this.countriesFullEntity = (CountriesFullEntity) argumentsFromActivity.getSerializable("data");
            setUpLayoutBindings(savedInstanceState,
                    this.currenciesRVAdapter==null,
                    this.timeZoneRVAdapter==null);
        } else Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
        return v;
    }

    public static CountryDetailsFrag newInstance() {
        return new CountryDetailsFrag();
    }

    private void setUpLayoutBindings(@Nullable Bundle savedInstanceState, boolean currencyAdapterFlag, boolean timezoneAdapterFlag) {
        CountriesFullEntity holderInstance;
        if(savedInstanceState!=null) holderInstance = (CountriesFullEntity) savedInstanceState.getSerializable("data");
        else holderInstance = this.countriesFullEntity;
        if (holderInstance != null) {
            picasso.load(holderInstance.getFlag()).into(countryFlagDetails);
            countryNameDetails.setText(holderInstance.getName());
            if(currencyAdapterFlag)
                currenciesRVAdapter = new CurrenciesRVAdapter(holderInstance.getCurrencies(),
                        LayoutInflater.from(this.getContext()));
            if(timezoneAdapterFlag)
                timeZoneRVAdapter = new TimeZoneRVAdapter(holderInstance.getTimezones(),
                        LayoutInflater.from(this.getContext()));
        }
        setUpRVs();
    }

    private void setUpRVs() {
        this.currencyHolderRV = new RecyclerView(this.getContext());
        this.currencyHolderRV.setAdapter(this.currenciesRVAdapter);
        this.currencyHolderRV.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.currencyHolderRV
                .addItemDecoration(new DividerItemDecoration(this.currencyHolderRV.getContext(),
                        DividerItemDecoration.VERTICAL));

        this.timezoneHolderRV = new RecyclerView(this.getContext());
        this.timezoneHolderRV.setAdapter(this.timeZoneRVAdapter);
        this.timezoneHolderRV.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.timezoneHolderRV
                .addItemDecoration(new DividerItemDecoration(this.timezoneHolderRV.getContext(),
                        DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("data",this.countriesFullEntity);
    }
}