package shankhadeepghoshal.org.countrieslistapp.ui;

import android.content.pm.ActivityInfo;
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
    private Configuration config;
    private CountryDetailsFrag countryDetailsFrag;
    private CountriesListFrag countriesListFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        swipeRefreshLayout.setOnRefreshListener(this);

        this.config = getResources().getConfiguration();

        Log.d(TAG_MAIN_ACTIVITY,"Main Activity View Created");
        countriesListFrag = new CountriesListFrag();

        if(config.smallestScreenWidthDp<600){

            if(savedInstanceState!=null) {
                this.countryDetailsFrag = (CountryDetailsFrag) getSupportFragmentManager()
                        .getFragment(savedInstanceState,TAG_DETAILS_FRAGMENT);
                if(this.countryDetailsFrag!=null) performFragmentTransaction(countryDetailsFrag,
                        TAG_DETAILS_FRAGMENT,
                        R.id.SmallScreenPortraitFragmentCanvas,
                        false,false);
            } else
                performFragmentTransaction(countriesListFrag,
                    TAG_LIST_FRAGMENT,
                    R.id.SmallScreenPortraitFragmentCanvas,
                    true, false);

            Log.d(TAG_MAIN_ACTIVITY,"Main Activity < 600 and portrait");

        } else {
            countryDetailsFrag = CountryDetailsFrag.newInstance();
            performFragmentTransaction(countriesListFrag,
                    TAG_LIST_FRAGMENT,
                    R.id.ListCountriesFragmentCanvas,
                    true, false);

            performFragmentTransaction(countryDetailsFrag,
                    TAG_DETAILS_FRAGMENT,
                    R.id.DetailsCountryFragmentCanvas,
                    true, false);

            Log.d(TAG_MAIN_ACTIVITY,"Main Activity large screen created");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (config.smallestScreenWidthDp < 600
                && getSupportFragmentManager().getBackStackEntryCount()>0)
            if (this.countryDetailsFrag != null){
                getSupportFragmentManager().putFragment(outState, TAG_DETAILS_FRAGMENT, this.countryDetailsFrag);
            }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fm = getSupportFragmentManager();
        if(getSupportFragmentManager().getBackStackEntryCount() > 1) {
            Fragment f = fm.findFragmentByTag(TAG_DETAILS_FRAGMENT);
            if (f instanceof  CountryDetailsFrag) fm.popBackStack();
        }
    }

    @Override
    public void onRefresh() {
        // TODO : Code the refreshing data callback here
        if(this.config.smallestScreenWidthDp < 600) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                this.countriesListFrag = (CountriesListFrag) returnNonNullRunningFragmentByTagName(TAG_LIST_FRAGMENT);
                makeViewsSignalUpdateOfData(this.countryDetailsFrag);
            }
            else {
                this.countryDetailsFrag = (CountryDetailsFrag) returnNonNullRunningFragmentByTagName(TAG_DETAILS_FRAGMENT);
                makeViewsSignalUpdateOfData(this.countriesListFrag);
            }
        } else {
            this.countriesListFrag = (CountriesListFrag) returnNonNullRunningFragmentByTagName(TAG_LIST_FRAGMENT);
            this.countryDetailsFrag = (CountryDetailsFrag) returnNonNullRunningFragmentByTagName(TAG_DETAILS_FRAGMENT);
            makeViewsSignalUpdateOfData(this.countryDetailsFrag);
            makeViewsSignalUpdateOfData(this.countriesListFrag);
        }
    }

    @Override
    public void invokeDetailsFragmentOnListItemCLickedInListFragment(CountriesFullEntity countriesFullEntity) {
        CountryDetailsFrag countryDetailsFrag = CountryDetailsFrag.newInstance();
        Bundle data = new Bundle();
        data.putSerializable("data",countriesFullEntity);
        countryDetailsFrag.setArguments(data);
        dealWithDetailsFragmentInflate(countryDetailsFrag);
    }

    @Override
    public void invokeDetailsFragmentOnListItemClickedInListFragmentViewModel() {
        this.countryDetailsFrag = CountryDetailsFrag.newInstance();
        dealWithDetailsFragmentInflate(countryDetailsFrag);
    }

    @Override
    public void makeSwipeAnimationStopAfterUpdate() {
        if(this.swipeRefreshLayout.isRefreshing()) this.swipeRefreshLayout.setRefreshing(false);
    }

    private void makeViewsSignalUpdateOfData(BaseView view) {
        if(view != null) view.onPerformUpdateAction();
    }

    private Fragment returnNonNullRunningFragmentByTagName(String tagName) {
        Fragment fragmentForProcessing = getSupportFragmentManager().findFragmentByTag(tagName);
        if(fragmentForProcessing!=null && fragmentForProcessing.isVisible()) return fragmentForProcessing;
        return null;
    }

    private void dealWithDetailsFragmentInflate(CountryDetailsFrag countryDetailsFrag) {
        if(config.smallestScreenWidthDp<600){
            performFragmentTransaction(countryDetailsFrag,
                    TAG_DETAILS_FRAGMENT,
                    R.id.SmallScreenPortraitFragmentCanvas,
                    false, true);
            Log.d(TAG_MAIN_ACTIVITY,"Switched to Details Fragment small screen");
        } else{
            performFragmentTransaction(countryDetailsFrag,
                    TAG_DETAILS_FRAGMENT,
                    R.id.DetailsCountryFragmentCanvas,
                    true, false);
            Log.d(TAG_MAIN_ACTIVITY,"Switched to Details Fragment Big Screen");
        }
    }

    /**
     * @param targetFragment Instance of the Fragment that will be inflated
     * @param tag Tag associated with the fragment
     * @param resourceId The layout resource ID where the fragment will be inflated
     * @param addOrReplaceFlag if true then fragment will be added, if false then replaced
     * @param backStackFlag if true then add to back stack, if false then don't
     */
    private void performFragmentTransaction(Fragment targetFragment, String tag, int resourceId, boolean addOrReplaceFlag, boolean
            backStackFlag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(!addOrReplaceFlag)fragmentTransaction.replace(resourceId,targetFragment,tag);
        else fragmentTransaction.add(resourceId,targetFragment,tag);
        if(backStackFlag)fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}