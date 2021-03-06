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

public class TimeZoneRVAdapter extends RecyclerView.Adapter<TimeZoneRVAdapter.TimeZoneViewHolder> {
    private List<String> timeZoneList;

    TimeZoneRVAdapter(List<String> timeZoneList) {
        this.timeZoneList = timeZoneList;
    }

    @NonNull
    @Override
    public TimeZoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TimeZoneViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timezones_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TimeZoneViewHolder holder, int position) {
        String tz = this.timeZoneList.get(position);
        holder.bind(tz);
    }

    @Override
    public int getItemCount() {
        return this.timeZoneList.size();
    }

    public void updateTimeZones(List<String> updatedTimeZones) {
        this.timeZoneList = updatedTimeZones;
        notifyDataSetChanged();
    }

    class TimeZoneViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.TimeZone)
        AppCompatTextView timeZone;

        TimeZoneViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        private void bind(String timeZoneText) {
            timeZone.setText(timeZoneText);
        }
    }
}