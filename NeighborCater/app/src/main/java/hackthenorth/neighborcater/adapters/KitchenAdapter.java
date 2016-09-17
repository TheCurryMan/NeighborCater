package hackthenorth.neighborcater.adapters;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.util.ArrayList;

import hackthenorth.neighborcater.R;
import hackthenorth.neighborcater.models.Kitchen;

import static java.lang.Math.round;

/**
 * Created by rowandempster on 9/17/16.
 */
public class KitchenAdapter extends RecyclerView.Adapter<KitchenAdapter.ViewHolder> {
    Context context;
    ArrayList<Kitchen> kitchenList;
    LatLng currentLocation;

    public KitchenAdapter(Context applicationContext, ArrayList<Kitchen> kitchenArray, LatLng currentLocation) {
        this.context = applicationContext;
        this.kitchenList = kitchenArray;
        this.currentLocation = currentLocation;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public TextView foodNameTextView;
        public TextView addressTextView;
        public TextView priceTextView;
        public TextView distanceTextView;

        public ViewHolder(View v) {

            super(v);

            titleTextView = (TextView) v.findViewById(R.id.kitchen_cell_title_text_view);
            foodNameTextView = (TextView) v.findViewById(R.id.kitchen_cell_food_name_text_view);
            addressTextView = (TextView) v.findViewById(R.id.kitchen_cell_address_text_view);
            priceTextView = (TextView) v.findViewById(R.id.kitchen_cell_price_text_view);
            distanceTextView = (TextView) v.findViewById(R.id.kitchen_cell_distance_text_view);

        }
    }

    @Override
    public KitchenAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.kitchen_list_cell, parent, false);

        ViewHolder viewHolder1 = new ViewHolder(view1);

        return viewHolder1;
    }



    @Override
    public void onBindViewHolder(KitchenAdapter.ViewHolder holder, int position) {
        String distance = getDistanceInKm(currentLocation, kitchenList.get(position).getLatitude(), kitchenList.get(position).getLongitude());
        holder.titleTextView.setText(kitchenList.get(position).getKitchenName());
        holder.foodNameTextView.setText(kitchenList.get(position).getFoodName());
        holder.addressTextView.setText(kitchenList.get(position).getAddress());
        holder.priceTextView.setText("$"+kitchenList.get(position).getPrice());
        holder.distanceTextView.setText(distance + " km");

    }

    private String getDistanceInKm(LatLng currentLocation, double latitude, double longitude) {
        float[] results = new float[1];
        Location.distanceBetween(currentLocation.latitude, currentLocation.longitude,
                latitude, longitude, results);
        double km = results[0]/1000;
        DecimalFormat df = new DecimalFormat("#.##");
        return String.valueOf(df.format(km));
    }

    @Override
    public int getItemCount() {
        return kitchenList.size();
    }
}
