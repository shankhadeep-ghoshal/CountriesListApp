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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import shankhadeepghoshal.org.countrieslistapp.DI.countrycomponent.DaggerCountryComponents;
import shankhadeepghoshal.org.countrieslistapp.R;
import shankhadeepghoshal.org.countrieslistapp.mvp.entities.CountriesFullEntity;
import shankhadeepghoshal.org.countrieslistapp.mvp.presenter.MainPresenter;
import shankhadeepghoshal.org.countrieslistapp.mvp.view.MainView;
import shankhadeepghoshal.org.countrieslistapp.ui.Frag2FragCommViewModel;
import shankhadeepghoshal.org.countrieslistapp.ui.IFragmentToFragmentMediator;
import shankhadeepghoshal.org.countrieslistapp.ui.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountriesListFrag extends Fragment implements MainView {

    @Inject
    Picasso picasso;

    @Inject
    MainPresenter mainPresenter;

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
        DaggerCountryComponents.builder()
                .appComponents(((MainActivity)getActivity()).provideAppComponents())
                .build();
        this.mainPresenter.getCountries();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initialize();
        setUpListItemClickListener();

        if(savedInstanceState!=null)
            manageViewModelAndDetailsFragmentInvoker(savedInstanceState.getInt("currentPosition"));
        else manageViewModelAndDetailsFragmentInvoker(0);

        return inflater.inflate(R.layout.fragment_countries_list, container, false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentPosition",this.countriesListRecyclerViewAdapter.getCurrentPosition());
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
    }

    private void setUpListItemClickListener() {
        this.countriesListRecyclerViewAdapter.setItemClickListener((view, position) -> {
            manageViewModelAndDetailsFragmentInvoker(position);
        });
    }

    private void manageViewModelAndDetailsFragmentInvoker(int position) {
        frag2FragCommViewModel
                .setCountryEntry(countriesListRecyclerViewAdapter.getCountriesFullEntityAtPosition(position));

        countriesListRecyclerViewAdapter.setCurrentPosition(position);
        listeningActivity.invokeDetailsFragmentOnListItemClickedInListFragmentViewModel();
    }

    @Override
    public void onLoadCountriesDataFull(List<CountriesFullEntity> countriesFullData) {
        this.countriesFullEntityList = countriesFullData;
    }

    @Override
    public void onErrorEncountered(String errorMessage) {
        Toast.makeText(this.getContext(),errorMessage,Toast.LENGTH_SHORT).show();
    }
}