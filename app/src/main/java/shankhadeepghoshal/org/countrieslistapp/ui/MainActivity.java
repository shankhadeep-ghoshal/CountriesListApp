package shankhadeepghoshal.org.countrieslistapp.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import shankhadeepghoshal.org.countrieslistapp.R;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.entities.CountriesFullEntity;
import shankhadeepghoshal.org.countrieslistapp.mvp.view.BaseView;
import shankhadeepghoshal.org.countrieslistapp.ui.countrieslist.CountriesListFrag;
import shankhadeepghoshal.org.countrieslistapp.ui.countrydetail.CountryDetailsFrag;

public class MainActivity extends AppCompatActivity implements IFragmentToFragmentMediator, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG_MAIN_ACTIVITY = "MainActivity";

    public static final String TAG_LIST_FRAGMENT = "COUNTRY_LIST";
    public static final String TAG_DETAILS_FRAGMENT = "COUNTRY_DETAILS";

    @BindView(R.id.MainActSwipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        Configuration config = getResources().getConfiguration();
        Log.d(TAG_MAIN_ACTIVITY,"Main Activity View Created");
        if(config.smallestScreenWidthDp<600){
            CountriesListFrag countriesListFrag = new CountriesListFrag();
            conductFragmentTransaction(countriesListFrag,TAG_LIST_FRAGMENT, true, true);
            Log.d(TAG_MAIN_ACTIVITY,"Main Activity < 600 and portrait");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fm = getSupportFragmentManager();
        if(getSupportFragmentManager().getBackStackEntryCount() > 1) {
            Fragment f = fm.findFragmentByTag(TAG_DETAILS_FRAGMENT);
            if (f instanceof  CountryDetailsFrag) fm.popBackStack();
            else this.finish();
        }
    }

    @Override
    public void onRefresh() {
        // TODO : Code the refreshing data callback here
        Fragment countryListFragment = returnNonNullRunningFragmentByTagName(TAG_LIST_FRAGMENT);
        Fragment countryDetailsFragment = returnNonNullRunningFragmentByTagName(TAG_DETAILS_FRAGMENT);
        makeViewsSignalUpdateOfData((CountriesListFrag)countryListFragment);
        makeViewsSignalUpdateOfData((CountryDetailsFrag)countryDetailsFragment);
    }

    @Override
    public void invokeDetailsFragmentOnListItemCLickedInListFragment(CountriesFullEntity countriesFullEntity) {
        CountryDetailsFrag countryDetailsFrag = CountryDetailsFrag.newInstance();
        Bundle data = new Bundle();
        data.putSerializable("data",countriesFullEntity);
        countryDetailsFrag.setArguments(data);
        conductFragmentTransaction(countryDetailsFrag, TAG_DETAILS_FRAGMENT, false, true);
    }

    @Override
    public void invokeDetailsFragmentOnListItemClickedInListFragmentViewModel() {

        CountryDetailsFrag countryDetailsFrag =
                (CountryDetailsFrag) getSupportFragmentManager().findFragmentByTag(TAG_DETAILS_FRAGMENT);

        if(countryDetailsFrag!=null && countryDetailsFrag.isVisible())
        conductFragmentTransaction(countryDetailsFrag, TAG_DETAILS_FRAGMENT, false, true);
    }

/*
    public AppComponents provideAppComponents() {
        return ((CentralApplication)getApplication()).getAppComponents();
    }
*/

    private void makeViewsSignalUpdateOfData(BaseView view) {
        if(view != null) view.onPerformUpdateAction();
    }

    private Fragment returnNonNullRunningFragmentByTagName(String tagName) {
        Fragment fragmentForProcessing = getSupportFragmentManager().findFragmentByTag(tagName);
        if(fragmentForProcessing!=null && fragmentForProcessing.isVisible()) return fragmentForProcessing;
        return null;
    }

    /**
     * @param targetFragment - fragment instance to be shown
     * @param tag - fragment tag
     * @param addOrReplaceFlag If true then it means that it's the first time a fragment is being added
     * @param backStackFlag If true then add to back stack else don't
     */
    @SuppressWarnings("SameParameterValue")
    private void conductFragmentTransaction(Fragment targetFragment, String tag, boolean addOrReplaceFlag, boolean backStackFlag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(addOrReplaceFlag)fragmentTransaction.replace(R.id.fragmentCanvas,targetFragment,tag);
        else fragmentTransaction.add(R.id.fragmentCanvas,targetFragment,tag);
        if(backStackFlag)fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}