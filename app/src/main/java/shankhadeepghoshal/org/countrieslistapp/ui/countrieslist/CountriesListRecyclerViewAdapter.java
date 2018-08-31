package shankhadeepghoshal.org.countrieslistapp.ui.countrieslist;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahmadrosid.svgloader.SvgLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import shankhadeepghoshal.org.countrieslistapp.R;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.entities.CountriesFullEntity;

public class CountriesListRecyclerViewAdapter extends RecyclerView.Adapter<CountriesListRecyclerViewAdapter.CountriesListViewHolder>{
    private List<CountriesFullEntity> countriesFullEntityList;
    private final LayoutInflater layoutInflater;
    private CountriesListRVClickListener countriesListRVClickListener;
    private AppCompatActivity appCompatActivity;
    private int currentPosition;

    CountriesListRecyclerViewAdapter(List<CountriesFullEntity> countriesFullEntityList,
                                     AppCompatActivity appCompatActivity,
                                     LayoutInflater layoutInflater) {
        this.countriesFullEntityList = countriesFullEntityList;
        this.layoutInflater = layoutInflater;
        this.appCompatActivity = appCompatActivity;
        this.currentPosition = 0;
    }

    @NonNull
    @Override
    public CountriesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CountriesListViewHolder(this.layoutInflater.inflate(R.layout.countries_list_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesListViewHolder holder, int position) {
        CountriesFullEntity countriesFullEntity = this.countriesFullEntityList.get(position);
        holder.bind(countriesFullEntity,this.appCompatActivity);
    }

    @Override
    public int getItemCount() {
        return this.countriesFullEntityList.size();
    }

    void setItemClickListener(CountriesListRVClickListener itemClickListener) {
        this.countriesListRVClickListener = itemClickListener;
    }

    CountriesFullEntity getCountriesFullEntityAtPosition(int position) {
        return this.countriesFullEntityList.get(position);
    }

    void setCountriesFullEntityList(List<CountriesFullEntity> countriesFullEntityList) {
        this.countriesFullEntityList = countriesFullEntityList;
        notifyDataSetChanged();
    }

    int getCurrentPosition() {
        return this.currentPosition;
    }

    void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    class CountriesListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.NameOfCountry)
        AppCompatTextView nameOfCountry;
        @BindView(R.id.CountryIcon)
        AppCompatImageView countryIcon;
        CountriesListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        void bind(CountriesFullEntity countriesFullEntity, AppCompatActivity appCompatActivity) {
            SvgLoader
                    .pluck()
                    .with(appCompatActivity)
                    .setPlaceHolder(R.mipmap.ic_launcher,R.mipmap.ic_launcher_round)
                    .load(countriesFullEntity.getFlag(), this.countryIcon);
            this.nameOfCountry.setText(countriesFullEntity.getName());
        }

        @Override
        public void onClick(View v) {
            countriesListRVClickListener.onListItemClicked(v,getAdapterPosition());
        }
    }

    interface CountriesListRVClickListener {
        void onListItemClicked(View view, int position);
    }
}