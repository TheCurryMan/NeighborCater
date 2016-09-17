package hackthenorth.neighborcater.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;

import hackthenorth.neighborcater.R;
import hackthenorth.neighborcater.models.Kitchen;

/**
 * Created by rowandempster on 9/17/16.
 */
public class KitchenAdapter extends RecyclerView.Adapter<KitchenAdapter.ViewHolder> {
    Context context;
    ArrayList<Kitchen> kitchenList;

    public KitchenAdapter(Context applicationContext, ArrayList<Kitchen> kitchenArray) {
        this.context = applicationContext;
        this.kitchenList = kitchenArray;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public ViewHolder(View v) {

            super(v);

            textView = (TextView) v.findViewById(R.id.kitchen_cell_text_view);
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
        holder.textView.setText(SubjectValues[position]);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
