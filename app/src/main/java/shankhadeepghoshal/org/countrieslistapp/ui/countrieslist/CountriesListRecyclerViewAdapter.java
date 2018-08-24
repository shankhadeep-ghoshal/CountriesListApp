package shankhadeepghoshal.org.countrieslistapp.ui.countrieslist;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import shankhadeepghoshal.org.countrieslistapp.R;
import shankhadeepghoshal.org.countrieslistapp.mvp.entities.CountriesFullEntity;

public class CountriesListRecyclerViewAdapter extends RecyclerView.Adapter<CountriesListRecyclerViewAdapter.CountriesListViewHolder>{
    private List<CountriesFullEntity> countriesFullEntityList;
    private LayoutInflater layoutInflater;
    private Picasso picasso;
    private CountriesListRVClickListener countriesListRVClickListener;
    private int currentPosition;

    public CountriesListRecyclerViewAdapter(List<CountriesFullEntity> countriesFullEntityList, LayoutInflater layoutInflater, Picasso picasso) {
        this.countriesFullEntityList = countriesFullEntityList;
        this.layoutInflater = layoutInflater;
        this.picasso = picasso;
        this.currentPosition = 0;
    }

    @NonNull
    @Override
    public CountriesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CountriesListViewHolder(layoutInflater.inflate(R.layout.countries_list_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesListViewHolder holder, int position) {
        CountriesFullEntity countriesFullEntity = countriesFullEntityList.get(position);
        picasso.load(countriesFullEntity.getFlag()).into(holder.countryIcon);
        holder.nameOfCountry.setText(countriesFullEntity.getName());
    }

    @Override
    public int getItemCount() {
        return this.countriesFullEntityList.size();
    }

    public void setItemClickListener(CountriesListRVClickListener itemClickListener) {
        this.countriesListRVClickListener = itemClickListener;
    }

    public CountriesFullEntity getCountriesFullEntityAtPosition(int position) {
        return this.countriesFullEntityList.get(position);
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
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
        }

        @Override
        public void onClick(View v) {
            countriesListRVClickListener.onListItemClicked(v,getAdapterPosition());
        }
    }

    public interface CountriesListRVClickListener {
        void onListItemClicked(View view, int position);
    }
}