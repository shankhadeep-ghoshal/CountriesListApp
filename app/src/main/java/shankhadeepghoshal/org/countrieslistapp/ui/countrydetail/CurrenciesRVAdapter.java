package shankhadeepghoshal.org.countrieslistapp.ui.countrydetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import shankhadeepghoshal.org.countrieslistapp.R;
import shankhadeepghoshal.org.countrieslistapp.mvp.models.entities.CurrenciesEntity;

public class CurrenciesRVAdapter extends RecyclerView.Adapter<CurrenciesRVAdapter.CurrenciesViewHolder> {
    private List<CurrenciesEntity> currenciesEntities;

    CurrenciesRVAdapter(List<CurrenciesEntity> currenciesEntities) {
        this.currenciesEntities = currenciesEntities;
    }

    @NonNull
    @Override
    public CurrenciesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CurrenciesViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.currency_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CurrenciesViewHolder holder, int position) {
        CurrenciesEntity currencyData = currenciesEntities.get(position);
        holder.bind(currencyData);
    }

    @Override
    public int getItemCount() {
        return this.currenciesEntities.size();
    }

    public void updateData(List<CurrenciesEntity> updatedCurrencyList) {
        this.currenciesEntities = updatedCurrencyList;
        notifyDataSetChanged();
    }

    class CurrenciesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.CurrencyCode)
        AppCompatTextView currencyCodeTv;
        @BindView(R.id.CurrencyName)
        AppCompatTextView currencyNameTV;
        @BindView(R.id.CurrencySymbol)
        AppCompatTextView currencySymbolTV;

        CurrenciesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bind(CurrenciesEntity currenciesEntity) {
            currencyCodeTv.setText(currenciesEntity.getCode());
            currencyNameTV.setText(currenciesEntity.getName());
            currencySymbolTV.setText(currenciesEntity.getSymbol());
        }
    }
}