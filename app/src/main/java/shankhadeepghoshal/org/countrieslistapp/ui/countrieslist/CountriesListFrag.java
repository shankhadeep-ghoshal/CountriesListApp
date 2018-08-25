package shankhadeepghoshal.org.countrieslistapp.ui.countrieslist;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import shankhadeepghoshal.org.countrieslistapp.DI.countrycomponent.DaggerCountryComponents;
import shankhadeepghoshal.org.countrieslistapp.R;
import shankhadeepghoshal.org.countrieslistapp.mvp.entities.CountriesFullEntity;
import shankhadeepghoshal.org.countrieslistapp.mvp.presenter.CountriesListPresenter;
import shankhadeepghoshal.org.countrieslistapp.mvp.view.CountriesListView;
import shankhadeepghoshal.org.countrieslistapp.ui.Frag2FragCommViewModel;
import shankhadeepghoshal.org.countrieslistapp.ui.IFragmentToFragmentMediator;
import shankhadeepghoshal.org.countrieslistapp.ui.MainActivity;
import shankhadeepghoshal.org.countrieslistapp.utilitiespackage.DetectInternetConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountriesListFrag extends Fragment implements CountriesListView {

    @Inject
    Picasso picasso;

    @Inject
    CountriesListPresenter countriesListPresenter;

    private List<CountriesFullEntity> countriesFullEntityList;

    private IFragmentToFragmentMediator listeningActivity;
    private Frag2FragCommViewModel frag2FragCommViewModel;

    @BindView(R.id.CountriesEntireHolderRV)
    RecyclerView countriesEntireHolderRV;

    private CountriesListRecyclerViewAdapter countriesListRecyclerViewAdapter;

    public CountriesListFrag() {
        // Required empty public constructor
    }

    public static CountriesListFrag getInstance() {
        return new CountriesListFrag();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            this.listeningActivity = (MainActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement IFragmentToFragmentMediator");

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //noinspection ConstantConditions
        DaggerCountryComponents.builder()
                .appComponents(((MainActivity)getActivity()).provideAppComponents())
                .build();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initialize();
        setUpListItemClickListener();

        frag2FragCommViewModel
                .getLiveDataListOfCountriesData()
                .observe(this,countriesFullEntities ->
                        countriesListRecyclerViewAdapter.setCountriesFullEntityList(countriesFullEntityList));

        return inflater.inflate(R.layout.fragment_countries_list, container, false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentPosition",this.countriesListRecyclerViewAdapter.getCurrentPosition());
        outState.putParcelable("rvCurrentPosition",this.countriesEntireHolderRV.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState!=null) {
            manageViewModelAndDetailsFragmentInvoker(savedInstanceState.getInt("currentPosition"));
            this.countriesEntireHolderRV
                    .getLayoutManager()
                    .onRestoreInstanceState(savedInstanceState
                            .getParcelable("rvCurrentPosition"));
        }
    }

    @Override
    public void onLoadCountriesDataFull(List<CountriesFullEntity> countriesFullData) {
        this.frag2FragCommViewModel.setListOfCountriesData(countriesFullData);
    }

    @Override
    public void onErrorEncountered(String errorMessage) {
        this.frag2FragCommViewModel.showToastMessage(errorMessage);
    }

    @Override
    public void onPerformUpdateAction() {
        callToPresenterToUpdateListOfCountriesOnInternetPresent();
    }

    private void initialize() {
        this.frag2FragCommViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(Frag2FragCommViewModel.class);
        this.countriesEntireHolderRV = new RecyclerView(this.getContext());
        this.countriesListRecyclerViewAdapter = new CountriesListRecyclerViewAdapter(this.countriesFullEntityList,
                LayoutInflater.from(this.getContext()), this.picasso);

        this.countriesEntireHolderRV.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.countriesEntireHolderRV
                .addItemDecoration(new DividerItemDecoration(this.countriesEntireHolderRV.getContext()
                        ,DividerItemDecoration.VERTICAL));
        this.countriesEntireHolderRV.setAdapter(this.countriesListRecyclerViewAdapter);
        this.countriesListPresenter.getCountries();
    }

    private void setUpListItemClickListener() {
        this.countriesListRecyclerViewAdapter.setItemClickListener((view, position) -> {
            manageViewModelAndDetailsFragmentInvoker(position);
        });
    }

    private void manageViewModelAndDetailsFragmentInvoker(int position) {
        frag2FragCommViewModel
                .setSingleCountryEntry(countriesListRecyclerViewAdapter.getCountriesFullEntityAtPosition(position));

        countriesListRecyclerViewAdapter.setCurrentPosition(position);
        listeningActivity.invokeDetailsFragmentOnListItemClickedInListFragmentViewModel();
    }

    private void callToPresenterToUpdateListOfCountriesOnInternetPresent() {
        this.countriesListPresenter.updateCountriesList(DetectInternetConnection.isInternetAvailable(this.getContext()));
    }
}