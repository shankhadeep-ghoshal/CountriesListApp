package shankhadeepghoshal.org.countrieslistapp.ui.countrieslist;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import shankhadeepghoshal.org.countrieslistapp.DI.countrycomponent.DaggerCountryComponents;
import shankhadeepghoshal.org.countrieslistapp.DI.countrymodule.CountryModule;
import shankhadeepghoshal.org.countrieslistapp.R;
import shankhadeepghoshal.org.countrieslistapp.application.CentralApplication;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.entities.CountriesFullEntity;
import shankhadeepghoshal.org.countrieslistapp.mvp.presenters.CountriesListPresenter;
import shankhadeepghoshal.org.countrieslistapp.mvp.view.CountriesListView;
import shankhadeepghoshal.org.countrieslistapp.ui.Frag2FragCommViewModel;
import shankhadeepghoshal.org.countrieslistapp.ui.IFragmentToFragmentMediator;
import shankhadeepghoshal.org.countrieslistapp.ui.MainActivity;
import shankhadeepghoshal.org.countrieslistapp.utilitiespackage.DetectInternetConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountriesListFrag extends Fragment implements CountriesListView {

    public static final String TAG_LIST_FRAGMENT = "ListFragment";

    @Inject
    CountriesListPresenter countriesListPresenter;

    private IFragmentToFragmentMediator listeningActivity;
    private Frag2FragCommViewModel frag2FragCommViewModel;

    @BindView(R.id.CountriesEntireHolderRV)
    RecyclerView countriesEntireHolderRV;

    private CountriesListRecyclerViewAdapter countriesListRecyclerViewAdapter;
    private Unbinder unbinder;
    private AppCompatActivity appCompatActivity;

    private int smallestScreenWidthInDp;

    public CountriesListFrag() {}

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
            this.appCompatActivity = (AppCompatActivity) context;
            this.frag2FragCommViewModel = ViewModelProviders
                    .of((MainActivity) context)
                    .get(Frag2FragCommViewModel.class);
            this.smallestScreenWidthInDp = context.getResources().getConfiguration()
                    .smallestScreenWidthDp;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement IFragmentToFragmentMediator");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_countries_list, container, false);
        if(v!=null) this.unbinder = ButterKnife.bind(this,v);

        initialize();
        this.countriesListPresenter.getCountries();
        setUpListItemClickListener();

        frag2FragCommViewModel
                .getLiveDataListOfCountriesData()
                .observe(this,countriesFullEntities ->{
                    countriesListRecyclerViewAdapter.setCountriesFullEntityList
                            (countriesFullEntities);
                    if(savedInstanceState == null) {
                        if (this.smallestScreenWidthInDp >=600){
                            manageViewModelAndDetailsFragmentInvoker(0);
                        }
                    }
                });

        Log.d(TAG_LIST_FRAGMENT,"List Fragment View Created");

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.countriesEntireHolderRV!=null)
        outState.putParcelable("rvCurrentPosition",this.countriesEntireHolderRV.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(this.smallestScreenWidthInDp < 600 && savedInstanceState != null){
            this.countriesEntireHolderRV
                    .getLayoutManager()
                    .onRestoreInstanceState(savedInstanceState
                            .getParcelable("rvCurrentPosition"));
        }
    }

    @Override
    public void onLoadCountriesDataFull(List<CountriesFullEntity> countriesFullData) {
        Log.d(TAG_LIST_FRAGMENT,"Data Received in List Fragment");
        Log.d(TAG_LIST_FRAGMENT,countriesFullData.get(0).toString());
        this.frag2FragCommViewModel.setListOfCountriesData(countriesFullData);
    }

    @Override
    public void onErrorEncountered(String errorMessage) {
        Toast.makeText(getContext(), errorMessage,Toast.LENGTH_SHORT).show();
        this.listeningActivity.makeSwipeAnimationStopAfterUpdate();
    }

    @Override
    public void onPerformUpdateAction() {
        callToPresenterToUpdateListOfCountriesOnInternetPresent(this.getContext());
        this.listeningActivity.makeSwipeAnimationStopAfterUpdate();
    }

    private void initialize() {
        this.countriesListRecyclerViewAdapter = new CountriesListRecyclerViewAdapter(new ArrayList<>(),
                this.appCompatActivity,
                LayoutInflater.from(this.getContext()));

        this.countriesEntireHolderRV.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.countriesEntireHolderRV
                .addItemDecoration(new DividerItemDecoration(this.countriesEntireHolderRV.getContext()
                        ,DividerItemDecoration.VERTICAL));
        this.countriesEntireHolderRV.setAdapter(this.countriesListRecyclerViewAdapter);
        Log.d(TAG_LIST_FRAGMENT,"List Fragment init");
    }

    private void setUpListItemClickListener() {
        this.countriesListRecyclerViewAdapter.setItemClickListener((view, position) -> {
            manageViewModelAndDetailsFragmentInvoker(position);
        });
    }

    private void manageViewModelAndDetailsFragmentInvoker(int position) {
        this.frag2FragCommViewModel
                .setSingleCountryEntry(this.countriesListRecyclerViewAdapter
                        .getCountriesFullEntityAtPosition(position));

        this.countriesListRecyclerViewAdapter.setCurrentPosition(position);
        this.listeningActivity.invokeDetailsFragmentOnListItemClickedInListFragmentViewModel();
    }

    private void callToPresenterToUpdateListOfCountriesOnInternetPresent(Context context) {
        this.countriesListPresenter.updateCountriesList(DetectInternetConnection.isInternetAvailable(context));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }
}